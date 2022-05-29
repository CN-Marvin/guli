package com.school.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.school.edu.entity.Chapter;
import com.school.edu.entity.Video;
import com.school.edu.entity.chapter.ChapterVo;
import com.school.edu.entity.chapter.VideoVo;
import com.school.edu.mapper.ChapterMapper;
import com.school.edu.mapper.VideoMapper;
import com.school.edu.service.ChapterService;
import com.school.guli.config.handler.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author marvin-zl
 * @since 2022-05-27
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Resource
    private VideoMapper videoMapper;

    @Override
    public List<ChapterVo> getChapterVideoCourseById(String courseId) {
        QueryWrapper<Chapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        List<Chapter> chapters = baseMapper.selectList(queryWrapper);

        List<ChapterVo> finalChapterVos = new ArrayList<>();

        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        List<Video> videos = videoMapper.selectList(videoQueryWrapper);
        for(Chapter chapter : chapters){
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter,chapterVo);
            //查询本课程的video小节

            ArrayList<VideoVo> videoVos = new ArrayList<>();
            for(Video video:videos){
                if(video.getChapterId().equals(chapterVo.getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVos.add(videoVo);
                }
                chapterVo.setChildren(videoVos);
            }
            finalChapterVos.add(chapterVo);
        }

        return finalChapterVos;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id",chapterId);
        Integer count = videoMapper.selectCount(videoQueryWrapper);
        if(count > 0){
            throw new GuliException(20001,"不能删除");
        }else{
            int res = baseMapper.deleteById(chapterId);
            return res > 0;
        }
    }

    @Override
    public void deleteChapterByCourseId(String courseId) {
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", courseId);
        baseMapper.delete(chapterQueryWrapper);
    }
}
