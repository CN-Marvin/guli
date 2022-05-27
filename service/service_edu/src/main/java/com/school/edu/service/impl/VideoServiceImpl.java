package com.school.edu.service.impl;

import com.school.edu.entity.Video;
import com.school.edu.mapper.VideoMapper;
import com.school.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
