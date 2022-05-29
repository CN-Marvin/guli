package com.school.edu.controller;


import com.school.common.utils.R;
import com.school.edu.entity.vo.CourseInfoVo;
import com.school.edu.entity.vo.CoursePublishVo;
import com.school.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
@CrossOrigin
public class CourseController {
    @Resource
    private CourseService courseService;

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
}

