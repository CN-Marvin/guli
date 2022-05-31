package com.school.guli.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.school.guli.config.handler.exceptionhandler.GuliException;
import com.school.guli.service.VodService;
import com.school.guli.utils.ConstantVodUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

import static com.school.guli.utils.InitVodClient.initVodClient;

/**
 * 功能描述：
 *
 * @Package: com.school.guli.service.impl
 * @author: Marvin-zl
 * @date: 2022/5/29 21:03
 */
@Service
public class VodServiceImpl implements VodService {


    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            InputStream inputStream = file.getInputStream();

            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();

            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else {
                //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
                throw new GuliException(20001, response.getMessage());
            }
            return videoId;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"上传失败");
        }
    }

    @Override
    public void removeVideo(String videoId) {
        try {
            DefaultAcsClient client = initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            //支持传入多个视频ID，多个用逗号分隔
            request.setVideoIds(videoId);
            client.getAcsResponse(request);
        } catch (Exception e) {
            throw new GuliException(20001,"删除视频失败");
        }
    }

    @Override
    public void removeVideoBatch(List<String> videoList) {
        try {
            DefaultAcsClient client = initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            //支持传入多个视频ID，多个用逗号分隔
            String videos = StringUtils.join(videoList.toArray(), ",");
            request.setVideoIds(videos);
            client.getAcsResponse(request);
        } catch (Exception e) {
            throw new GuliException(20001,"删除视频失败");
        }
    }
}
