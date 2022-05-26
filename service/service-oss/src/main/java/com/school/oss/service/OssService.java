package com.school.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 功能描述：
 *
 * @Package: com.school.oss.service
 * @author: Marvin-zl
 * @date: 2022/5/26 11:57
 */
public interface OssService {
    /**
     * 上传文件
     * @param file
     * @return
     */
    String uploadFileAvatar(MultipartFile file);
}
