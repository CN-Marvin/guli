package com.school.edu.controller;


import com.school.common.utils.R;
import com.school.edu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author marvin-zl
 * @since 2022-05-26
 */
@Api("课程分类管理")
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

}

