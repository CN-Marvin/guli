package com.school.guli.service;

import org.springframework.web.multipart.MultipartFile;

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
}
