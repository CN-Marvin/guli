package com.school.edu.controller;


import com.school.common.utils.R;
import com.school.edu.entity.subject.OneSubject;
import com.school.edu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author marvin-zl
 * @since 2022-05-26
 */
@Api(tags = "课程分类管理")
@RestController
@RequestMapping("/eduService/subject")
@CrossOrigin
public class SubjectController {
    @Resource
    private SubjectService subjectService;

    @ApiOperation("Excel批量导入添加课程分类")
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file){
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }
    @ApiOperation("获得所有课程分类")
    @GetMapping("/getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }

}

