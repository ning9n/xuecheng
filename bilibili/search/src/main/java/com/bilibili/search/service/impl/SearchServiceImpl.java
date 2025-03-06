package com.bilibili.search.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.bilibili.base.result.PageParams;
import com.bilibili.base.result.PageResult;
import com.bilibili.search.domain.dto.SearchCourseParamDto;
import com.bilibili.search.domain.po.CourseIndex;
import com.bilibili.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final RestHighLevelClient client;
    @Override
    public PageResult<CourseIndex> list(PageParams pageParams, SearchCourseParamDto searchCourseParamDto) {
        SearchRequest request=new SearchRequest("production");
        request.source()
                //关键字匹配
                .query(QueryBuilders.matchQuery("title",searchCourseParamDto.getKeywords()))
                //排序
                .sort(searchCourseParamDto.getSearchOrder(), SortOrder.DESC)
                //分页
                .from((int) ((pageParams.getPageNo()-1)*pageParams.getPageSize()))
                .size(Math.toIntExact(pageParams.getPageSize()))
                //高亮
                .highlighter(SearchSourceBuilder.highlight()
                        .field(searchCourseParamDto.getKeywords())
                        .preTags("<em>")
                        .postTags("</em>"));
        SearchResponse response=null;
        try {
            response = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("es查询失败");
        }
        if(response==null){
            log.error("es查询失败");
            return null;
        }
        //解析结果
        SearchHits hits = response.getHits();
        long counts=hits.getTotalHits().value;
        SearchHit[] searchHits = hits.getHits();
        List<CourseIndex> list=new ArrayList<>((int)counts);
        for (SearchHit searchHit : searchHits) {
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            CourseIndex courseIndex = BeanUtil.mapToBean(sourceAsMap, CourseIndex.class, true, null);
            list.add(courseIndex);
        }

        return new PageResult<>(list, counts, pageParams.getPageNo(), pageParams.getPageSize());
    }
}
