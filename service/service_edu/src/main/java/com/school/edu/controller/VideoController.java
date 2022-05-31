package com.school.edu.controller;


import com.school.common.utils.R;
import com.school.edu.client.VodClient;
import com.school.edu.entity.Video;
import com.school.edu.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author marvin-zl
 * @since 2022-05-27
 */
@Api(tags = "小节视频管理")
@RestController
@RequestMapping("/eduService/video")
@CrossOrigin
public class VideoController {
    @Resource
    private VideoService videoService;

    @Resource
    private VodClient vodClient;

    @ApiOperation("添加小节")
    @PostMapping("addVideo")
    public R addVideo(@RequestBody Video video) {
        videoService.save(video);

        return R.ok();
    }

    @ApiOperation("删除小节")
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable("id") String id) {
        Video video = videoService.getById(id);
        String videoSourceId = video.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)){
            vodClient.deleteVideo(videoSourceId);
        }
        videoService.removeById(id);
        return R.ok();
    }

    @ApiOperation("根据id获得video信息")
    @GetMapping("{id}")
    public R getVideoById(@PathVariable String id) {
        Video video = videoService.getById(id);
        return R.ok().data("video", video);
    }

    @ApiOperation("更新video信息")
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody Video video) {
        videoService.updateById(video);
        return R.ok();
    }
}

