package com.school.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.school.edu.client.VodClient;
import com.school.edu.entity.Video;
import com.school.edu.mapper.VideoMapper;
import com.school.edu.service.VideoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author marvin-zl
 * @since 2022-05-27
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Resource
    private VodClient vodClient;

    @Override
    public void deleteVideoByCourseId(String courseId) {
        //删除aliyun平台视频
        QueryWrapper<Video> videoQueryWrapper1 = new QueryWrapper<>();
        videoQueryWrapper1.eq("course_id", courseId);
        videoQueryWrapper1.select("video_source_id");
        List<Video> videos = baseMapper.selectList(videoQueryWrapper1);
        ArrayList<String> videoIds = new ArrayList<>();
        for(Video video:videos){
            String sourceId = video.getVideoSourceId();
            if(!StringUtils.isEmpty(sourceId)){
                videoIds.add(sourceId);
            }
        }
        vodClient.deleteVideo(videoIds);

        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        baseMapper.delete(videoQueryWrapper);
    }
}
