package com.school.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.school.common.utils.R;
import com.school.edu.entity.Course;
import com.school.edu.entity.Teacher;
import com.school.edu.service.CourseService;
import com.school.edu.service.TeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能描述：
 *
 * @Package: com.school.edu.controller.front
 * @author: Marvin-zl
 * @date: 2022/5/31 19:59
 */
@RestController
@RequestMapping("/eduService/indexfront")
public class IndexFrontController {

    @Resource
    private CourseService courseService;
    @Resource
    private TeacherService teacherService;

    @ApiOperation("查询前8条热门课程和4名名师")
    @GetMapping("index")
    public R index(){
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("id");
        courseQueryWrapper.last("limit 8");
        List<Course> courseList = courseService.list(courseQueryWrapper);


        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("id");
        teacherQueryWrapper.last("limit 4" );
        List<Teacher> teacherList = teacherService.list(teacherQueryWrapper);
        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }
}
