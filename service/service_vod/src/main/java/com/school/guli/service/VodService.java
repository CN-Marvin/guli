package com.school.guli.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 功能描述：
 *
 * @Package: com.school.guli.service
 * @author: Marvin-zl
 * @date: 2022/5/29 21:02
 */
public interface VodService {
    /**
     * 上传视频
     * @param file
     * @return
     */
    String uploadVideo(MultipartFile file);

    /**
     * 根据id删除video
     * @param videoId
     */
    void removeVideo(String videoId);

    /**
     * 删除多个视频
     * @param videoList
     */
    void removeVideoBatch(List<String> videoList);
}
