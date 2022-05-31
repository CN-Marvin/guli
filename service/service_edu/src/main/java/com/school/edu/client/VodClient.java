package com.school.edu.client;

import com.school.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 功能描述：
 *
 * @Package: com.school.edu.client
 * @author: Marvin-zl
 * @date: 2022/5/30 18:26
 */
@Component
@FeignClient(value = "service-vod",fallback = VodFileDegradeFeignClient.class)
public interface VodClient {
    /**
     * 上传视频到阿里云
     * @param file
     * @return
     */
    @PostMapping("/eduvod/video/uploadVideo")
    public R uploadVideo(MultipartFile file);


    /**
     * 从阿里云中删除视频
     * @param videoId
     * @return
     */
    @DeleteMapping("/eduvod/video/deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable("videoId") String videoId);


    /**
     * 从阿里云中删除多个视频
     * @param videoList
     * @return
     */
    @DeleteMapping("/eduvod/video/deleteVideoBatch")
    public R deleteVideo(@RequestBody List<String> videoList);
}
