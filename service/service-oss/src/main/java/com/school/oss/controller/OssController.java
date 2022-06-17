package com.school.oss.controller;


import com.school.common.utils.R;
import com.school.oss.service.OssService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 功能描述：
 *
 * @Package: com.school.oss.controller
 * @author: Marvin-zl
 * @date: 2022/5/26 11:56
 */
@RestController
@RequestMapping("/eduoss/fileoss")

public class OssController {
    @Resource
    private OssService ossService;

    @PostMapping("/uploadFile")
    public R uploadOssFile(MultipartFile file) {
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("imageUrl", url);
    }
}
