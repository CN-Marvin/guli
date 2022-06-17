package com.school.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.school.common.utils.R;
import com.school.edu.entity.Course;
import com.school.edu.entity.vo.CourseInfoVo;
import com.school.edu.entity.vo.CoursePublishVo;
import com.school.edu.entity.vo.CourseQuery;
import com.school.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author marvin-zl
 * @since 2022-05-27
 */
@Api(tags = "课程管理")
@RestController
@RequestMapping("/eduService/course")
public class CourseController {
    @Resource
    private CourseService courseService;

    @ApiOperation("条件查询课程列表")
    @PostMapping("/findCourseListCondition/{page}/{limit}")
    public R findCourseListCondition(@PathVariable long page,
                                     @PathVariable long limit,
                                     @RequestBody(required = false) CourseQuery courseQuery){
        Page<Course> iPage = new Page<>(page,limit);
        Page<Course> resPage = courseService.findCourseListCondition(iPage,courseQuery);
        List<Course> list = resPage.getRecords();
        long total = resPage.getTotal();
        return R.ok().data("list",list).data("total",total);
    }

    @ApiOperation("查询所有课程")
    @GetMapping("/getAll")
    public R getAllCourse(){
        List<Course> list = courseService.list(null);
        return R.ok().data("list",list);
    }

    @ApiOperation("添加课程基本信息")
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }

    @ApiOperation("根据课程id查询课程信息")
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfoById(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfo", courseInfoVo);
    }

    @ApiOperation("修改课程信息")
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    @ApiOperation("根据课程id查询课程确定信息")
    @GetMapping("getPublishCourseInfo/{courseId}")
    public R getPublishCourseInfo(@PathVariable String courseId){
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(courseId);
        return R.ok().data("info",coursePublishVo);
    }
    @ApiOperation("修改课程状态为已发布")
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        Course course = new Course();
        course.setId(id);
        course.setStatus("Normal");
        courseService.updateById(course);
        return R.ok();
    }
    @ApiOperation("删除课程")
    @DeleteMapping("/{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        courseService.deleteCourse(courseId);
        return R.ok();
    }
}

