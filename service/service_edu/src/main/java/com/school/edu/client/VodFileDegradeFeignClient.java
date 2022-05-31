package com.school.edu.client;

import com.school.common.utils.R;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 功能描述：
 *
 * @Package: com.school.edu.client
 * @author: Marvin-zl
 * @date: 2022/5/31 12:44
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{


    @Override
    public R uploadVideo(MultipartFile file) {
        return R.error().message("上传视频出错了");
    }

    @Override
    public R deleteVideo(String videoId) {
        return R.error().message("删除视频出错了");
    }

    @Override
    public R deleteVideo(List<String> videoList) {
        return R.error().message("删除大量视频出错了");
    }
}
