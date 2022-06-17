package com.school.edu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.school.common.utils.JwtUtils;
import com.school.common.utils.R;
import com.school.common.utils.ordervo.CourseOrderVo;
import com.school.edu.client.OrderClient;
import com.school.edu.entity.Course;
import com.school.edu.entity.chapter.ChapterVo;
import com.school.edu.entity.vo.CourseInfoVo;
import com.school.edu.entity.vo.CourseQueryVo;
import com.school.edu.entity.vo.CourseWebVo;
import com.school.edu.service.ChapterService;
import com.school.edu.service.CourseService;
import com.school.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：
 *
 * @Package: com.school.edu.controller.front
 * @author: Marvin-zl
 * @date: 2022/6/5 16:18
 */
@Api(tags = "前台课程管理")
@RestController
@RequestMapping("/eduService/courseFront")
public class CourseFrontController {
    @Resource
    private CourseService courseService;

    @Resource
    private ChapterService chapterService;

    @Resource
    private TeacherService teacherService;

    @Resource
    private OrderClient orderClient;

    @ApiOperation(value = "分页课程列表")
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page,
                                @PathVariable long limit,
                                @RequestBody(required = false) CourseQueryVo courseQueryVo) {
        Page<Course> coursePage = new Page<>(page,limit);
        Map<String,Object> courseMap =  courseService.getCourseFrontList(coursePage,courseQueryVo);
        return R.ok().data(courseMap);
    }

    @ApiOperation("查询课程详细信息")
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        boolean isBuy = orderClient.getOrderStatus(courseId, memberId);
        List<ChapterVo> list = chapterService.getChapterVideoCourseById(courseId);
        return R.ok().data("list",list).data("course",courseWebVo).data("isBuy",isBuy);
    }

    @ApiOperation("根据课程id查询课程信息forOrder")
    @GetMapping("getCourseInfoOrder/{id}")
    public CourseOrderVo getCourseInfoOrder(@PathVariable("id") String id){
        CourseInfoVo courseInfo = courseService.getCourseInfo(id);
        CourseOrderVo courseOrderVo = new CourseOrderVo();
        BeanUtils.copyProperties(courseInfo, courseOrderVo);
        courseOrderVo.setTeacherName(teacherService.getById(courseInfo.getTeacherId()).getName());
        return courseOrderVo;
    }
}
