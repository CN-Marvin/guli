package com.school.edu.service;

import com.school.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.school.edu.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author marvin-zl
 * @since 2022-05-27
 */
public interface ChapterService extends IService<Chapter> {

    /**
     * 根据课程id查询课程大纲
     * @param courseId
     * @return
     */
    List<ChapterVo> getChapterVideoCourseById(String courseId);

    /**
     * 删除章节,如果章节有小节则不能删除
     * @param chapterId
     * @return
     */
    boolean deleteChapter(String chapterId);
}
