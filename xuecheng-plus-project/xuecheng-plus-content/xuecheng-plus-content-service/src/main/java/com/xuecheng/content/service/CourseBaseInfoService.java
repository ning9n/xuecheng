package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import org.springframework.transaction.annotation.Transactional;

public interface CourseBaseInfoService {
   PageResult<CourseBase> page
            (PageParams pageParams, QueryCourseParamsDto queryCourseParams);

    CourseBaseInfoDto addCourse(AddCourseDto dto);

    AddCourseDto getCourse(Integer id);

    AddCourseDto update(AddCourseDto dto);

    @Transactional
    CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto dto);

    CourseBaseInfoDto getCourseBaseInfo(Long courseId);
}
