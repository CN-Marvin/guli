package com.school.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.school.common.utils.R;
import com.school.edu.entity.Course;
import com.school.edu.entity.Teacher;
import com.school.edu.service.CourseService;
import com.school.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：
 *
 * @Package: com.school.edu.controller.front
 * @author: Marvin-zl
 * @date: 2022/6/5 12:14
 */
@Api(tags = "前台教师管理")
@RestController
@RequestMapping("/eduService/teacherFront")
public class TeacherFrontController {
    @Resource
    private TeacherService teacherService;

    @Resource
    private CourseService courseService;

    @ApiOperation("分页查询教师")
    @GetMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page,@PathVariable long limit){
        Page<Teacher> teacherPage = new Page<>(page,limit);
        Map<String,Object> teacherMap = teacherService.getTeacherFrontList(teacherPage);
        return R.ok().data(teacherMap);
    }

    @ApiOperation("根据讲师id查询讲师信息")
    @GetMapping("getTeacherFrontInfo/{id}")
    public R getTeacherFrontInfo(@PathVariable String id){
        Teacher teacher = teacherService.getById(id);

        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("teacher_id", id);
        courseQueryWrapper.orderByAsc("gmt_modified");

        List<Course> courses = courseService.list(courseQueryWrapper);
        return R.ok().data("teacher",teacher).data("courses",courses);
    }



}
