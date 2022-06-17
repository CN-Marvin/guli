package com.school.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.school.edu.entity.Comment;

import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author marvin-zl
 * @since 2022-06-06
 */
public interface CommentService extends IService<Comment> {

    /**
     * 根据课程id查询课程
     *
     * @param commentPage
     * @param courseId
     * @return
     */
    Map<String,Object> getCommentByCourseId(Page<Comment> commentPage, String courseId);
}
