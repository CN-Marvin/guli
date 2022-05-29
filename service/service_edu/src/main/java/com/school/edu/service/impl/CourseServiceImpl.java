package com.school.edu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.school.edu.entity.Course;
import com.school.edu.entity.CourseDescription;
import com.school.edu.entity.vo.CourseInfoVo;
import com.school.edu.entity.vo.CoursePublishVo;
import com.school.edu.mapper.CourseMapper;
import com.school.edu.service.CourseDescriptionService;
import com.school.edu.service.CourseService;
import com.school.guli.config.handler.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author marvin-zl
 * @since 2022-05-27
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Resource
    private CourseDescriptionService courseDescriptionService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //往课程表中添加
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo, course);
        int insert = baseMapper.insert(course);
        if(insert <= 0){
            throw new GuliException();
        }
        String id = course.getId();
        //往课程描述表中添加
        CourseDescription courseDescription = new CourseDescription();
        BeanUtils.copyProperties(courseInfoVo, courseDescription);
        courseDescription.setId(id);
        courseDescriptionService.save(courseDescription);
        return id;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        Course course = baseMapper.selectById(courseId);

        if(course == null){
            throw new GuliException(20001,"没有此课程");
        }
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course, courseInfoVo);

        CourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo, course);
        int res = baseMapper.updateById(course);
        if(res == 0){
            throw new GuliException(20001,"修改失败");
        }
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(courseInfoVo.getId());
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(courseDescription);
    }

    @Override
    public CoursePublishVo publishCourseInfo(String courseId) {
       return baseMapper.getCoursePublishInfo(courseId);
    }
}
