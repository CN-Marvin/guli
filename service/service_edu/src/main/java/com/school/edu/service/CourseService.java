package com.school.edu.service;

import com.school.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.school.edu.entity.vo.CourseInfoVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author marvin-zl
 * @since 2022-05-27
 */
public interface CourseService extends IService<Course> {

    /**
     * 添加课程信息
     * @param courseInfoVo
     */
    String saveCourseInfo(CourseInfoVo courseInfoVo);
}
