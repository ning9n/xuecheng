package com.bilibili.search.service;

import com.bilibili.base.result.PageParams;
import com.bilibili.base.result.PageResult;
import com.bilibili.search.domain.dto.SearchCourseParamDto;
import com.bilibili.search.domain.po.CourseIndex;

public interface SearchService {
    PageResult<CourseIndex> list(PageParams pageParams, SearchCourseParamDto searchCourseParamDto);
}
