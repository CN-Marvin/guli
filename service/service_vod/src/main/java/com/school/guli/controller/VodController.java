package com.school.guli.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.school.common.utils.R;
import com.school.guli.config.handler.exceptionhandler.GuliException;
import com.school.guli.service.VodService;
import com.school.guli.utils.ConstantVodUtils;
import com.school.guli.utils.InitVodClient;
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

    @ApiOperation("根据视频id获得视频凭证")
    @GetMapping("getPlayAuth/{vid}")
    public R getPlayAuth(@PathVariable String vid){

        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

            request.setVideoId(vid);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();

            return R.ok().data("playAuth",playAuth);
        } catch (ClientException e) {
            throw new GuliException(20001,"error");
        }
    }
}
