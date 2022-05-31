package com.school.guli.controller;

import com.school.common.utils.R;
import com.school.guli.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

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

    @ApiOperation("从阿里云中删除视频")
    @DeleteMapping("/deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable String videoId){
        vodService.removeVideo(videoId);
        return R.ok();
    }

    @ApiOperation("从阿里云中删除多个视频")
    @DeleteMapping("/deleteVideoBatch")
    public R deleteVideo(@RequestBody List<String> videoList){
        vodService.removeVideoBatch(videoList);
        return R.ok();
    }
}
