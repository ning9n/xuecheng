package com.bilibili.search.domain.dto;

import lombok.Data;
import lombok.ToString;

/**
 * 搜索视频
 */
@Data
@ToString
public class SearchCourseParamDto {

    //关键字
    private String keywords;

    //排序方式:最多播放，最多点赞，最多评论，最多收藏，最新发布
    private String searchOrder;
    //时长
    private Long minTime;
    private Long maxTime;

}
