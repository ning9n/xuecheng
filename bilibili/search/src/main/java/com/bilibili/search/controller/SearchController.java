package com.bilibili.search.controller;

import com.bilibili.base.result.PageParams;
import com.bilibili.base.result.PageResult;
import com.bilibili.search.domain.dto.SearchCourseParamDto;
import com.bilibili.search.domain.po.CourseIndex;
import com.bilibili.search.service.SearchService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;
    @ApiOperation("课程搜索列表")
    @GetMapping("/list")
    public PageResult<CourseIndex> list(PageParams pageParams, SearchCourseParamDto searchCourseParamDto){
        return searchService.list(pageParams,searchCourseParamDto);

    }

}
