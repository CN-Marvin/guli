package com.school.guli.controller;

import com.school.common.utils.R;
import com.school.guli.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 功能描述：
 *
 * @Package: com.school.guli.controller
 * @author: Marvin-zl
 * @date: 2022/5/29 21:02
 */
@Api(tags = "上传视频管理")
@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {
    @Resource
    private VodService vodService;

    @ApiOperation("上传视频到阿里云")
    @PostMapping("/uploadVideo")
    public R uploadVideo(MultipartFile file) {
        String videoId = vodService.uploadVideo(file);
        return R.ok().data("videoId",videoId);
    }
}
