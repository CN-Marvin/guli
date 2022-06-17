package com.school.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.school.common.utils.JwtUtils;
import com.school.common.utils.R;
import com.school.common.utils.ordervo.CenterMember;
import com.school.edu.client.CenterClient;
import com.school.edu.entity.Comment;
import com.school.edu.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author marvin-zl
 * @since 2022-06-06
 */
@Api(tags = "前台评论管理")
@RestController
@RequestMapping("/eduService/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    @Resource
    private CenterClient centerClient;

    @ApiOperation("获取评论分页列表")
    @GetMapping("/getCommentList/{courseId}/{page}/{limit}")
    public R getCommentList(@PathVariable String courseId,
                            @PathVariable long page,
                            @PathVariable long limit){
        Page<Comment> commentPage = new Page<>(page,limit);
        Map<String, Object> map = commentService.getCommentByCourseId(commentPage, courseId);
        return R.ok().data(map);
    }

    @ApiOperation("根据token获得用户信息,添加评论")
    @PostMapping("/savaComment")
    public R savaComment(HttpServletRequest request,@RequestBody Comment comment){
        try {
            String memberId = JwtUtils.getMemberIdByJwtToken(request);
            CenterMember member = centerClient.getMemberInfoById(memberId);
            comment.setMemberId(memberId);
            comment.setAvatar(member.getAvatar());
            comment.setNickname(member.getNickname());
            commentService.save(comment);
            return R.ok();
        }catch (Exception e){
            return R.error().message("发表失败");
        }
    }
}

