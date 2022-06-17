package com.school.edu.mapper;

import com.school.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.school.edu.entity.vo.CoursePublishVo;
import com.school.edu.entity.vo.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author marvin-zl
 * @since 2022-05-27
 */
public interface CourseMapper extends BaseMapper<Course> {
    /**
     * 获得发布课程的基本信息
     * @param courseId
     * @return
     */
    public CoursePublishVo getCoursePublishInfo(String courseId);

    /**
     * 前台 根据课程id查询课程具体信息
     * @param courseId
     * @return
     */
    CourseWebVo getBaseCourseInfo(String courseId);
}
