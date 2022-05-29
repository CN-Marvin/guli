package com.school.edu.service;

import com.school.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author marvin-zl
 * @since 2022-05-27
 */
public interface VideoService extends IService<Video> {

    /**
     * 根据课程id删除video
     * @param courseId
     */
    void deleteVideoByCourseId(String courseId);
}
