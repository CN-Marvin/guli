package com.school.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.school.edu.entity.Course;
import com.school.edu.entity.CourseDescription;
import com.school.edu.entity.vo.*;
import com.school.edu.mapper.CourseMapper;
import com.school.edu.service.ChapterService;
import com.school.edu.service.CourseDescriptionService;
import com.school.edu.service.CourseService;
import com.school.edu.service.VideoService;
import com.school.guli.config.handler.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Resource
    private VideoService videoService;

    @Resource
    private ChapterService chapterService;


    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //往课程表中添加
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo, course);
        int insert = baseMapper.insert(course);
        if (insert <= 0) {
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
        if (course == null) {
            throw new GuliException(20001, "没有此课程");
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
        if (res == 0) {
            throw new GuliException(20001, "修改失败");
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

    @Override
    public Page<Course> findCourseListCondition(Page<Course> iPage, CourseQuery courseQuery) {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        String title = courseQuery.getTitle();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();
        String teacherId = courseQuery.getTeacherId();
        if (!StringUtils.isEmpty(title)) {
            courseQueryWrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            courseQueryWrapper.like("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            courseQueryWrapper.like("subject_id", subjectId);
        }
        if (!StringUtils.isEmpty(teacherId)) {
            courseQueryWrapper.like("teacher_id", teacherId);
        }
        baseMapper.selectPage(iPage, courseQueryWrapper);
        return iPage;
    }

    @Override
    public void deleteCourse(String courseId) {
        videoService.deleteVideoByCourseId(courseId);
        chapterService.deleteChapterByCourseId(courseId);
        courseDescriptionService.removeById(courseId);
        int res = baseMapper.deleteById(courseId);
        if (res == 0) {
            throw new GuliException(20001, "删除失败");
        }
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<Course> coursePage, CourseQueryVo courseQueryVo) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseQueryVo.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id", courseQueryVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseQueryVo.getSubjectId())) {
            queryWrapper.eq("subject_id", courseQueryVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseQueryVo.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }

        if (!StringUtils.isEmpty(courseQueryVo.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseQueryVo.getPriceSort())) {
            queryWrapper.orderByDesc("price");
        }

        baseMapper.selectPage(coursePage, queryWrapper);

        List<Course> records = coursePage.getRecords();
        long current = coursePage.getCurrent();
        long pages = coursePage.getPages();
        long size = coursePage.getSize();
        long total = coursePage.getTotal();
        boolean hasNext = coursePage.hasNext();
        boolean hasPrevious = coursePage.hasPrevious();
        Map<String, Object> courseMap = new HashMap<>(16);
        courseMap.put("items", records);
        courseMap.put("current", current);
        courseMap.put("pages", pages);
        courseMap.put("size", size);
        courseMap.put("total", total);
        courseMap.put("hasNext", hasNext);
        courseMap.put("hasPrevious", hasPrevious);
        return courseMap;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
