package com.xuecheng.content;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CourseBaseMapperTests {

    @Autowired
    CourseBaseMapper courseBaseMapper;

    @Test
    void testCourseBaseMapper() {
        //根据id查询
        CourseBase courseBase = courseBaseMapper.selectById(74);
        Assertions.assertNotNull(courseBase);
        //条件查询
        QueryCourseParamsDto dto = QueryCourseParamsDto.builder()
                .courseName("java")
                .auditStatus("202004")
                .publishStatus("203001")
                .build();
        /*LambdaQueryWrapper<CourseBase> wrapper=new LambdaQueryWrapper<>()
                .like(StringUtils.isNotEmpty(dto.getCourseName()),CourseBase::getName,dto.getCourseName());
        */
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(dto.getCourseName()),CourseBase::getName,dto.getCourseName())
                .eq(StringUtils.isNotEmpty(dto.getAuditStatus()),CourseBase::getAuditStatus,dto.getAuditStatus());
        List<CourseBase> courseBases = courseBaseMapper.selectList(queryWrapper);
        Assertions.assertNotNull(courseBases);
        System.out.println(courseBases);

        //分页查询
        Page<CourseBase> page=new Page<>(1,10);
        courseBaseMapper.selectPage(page, queryWrapper);
        List<CourseBase> records = page.getRecords();

    }

}