package com.school.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.school.edu.entity.Course;
import com.school.edu.entity.vo.*;

import java.util.Map;

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

    /**
     * 根据课程id查询课程信息
     * @param courseId
     * @return
     */
    CourseInfoVo getCourseInfo(String courseId);

    /**
     * 修改课程信息
     * @param courseInfoVo
     */
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 获得课程的确定信息
     * @param courseId
     * @return
     */
    CoursePublishVo publishCourseInfo(String courseId);

    /**
     * 条件查询课程列表
     * @param objectPage 分页对象
     * @param courseQuery 条件查询
     * @return
     */
    Page<Course> findCourseListCondition(Page<Course> objectPage, CourseQuery courseQuery);

    /**
     * 删除课程所有相关的信息
     * @param courseId
     */
    void deleteCourse(String courseId);

    /**
     * 前台 根据条件分页查询课程
     * @param coursePage
     * @param courseQueryVo
     * @return
     */
    Map<String, Object> getCourseFrontList(Page<Course> coursePage, CourseQueryVo courseQueryVo);

    /**
     * 前台查询课程详细信息
     * @param courseId
     * @return
     */
    CourseWebVo getBaseCourseInfo(String courseId);
}
