package com.school.edu.controller;


import com.school.common.utils.R;
import com.school.edu.entity.Chapter;
import com.school.edu.entity.chapter.ChapterVo;
import com.school.edu.service.ChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author marvin-zl
 * @since 2022-05-27
 */
@Api(tags = "课程大纲管理")
@RestController
@RequestMapping("/eduService/chapter")
public class ChapterController {
    @Resource
    private ChapterService chapterService;

    @ApiOperation("获得课程大纲列表")
    @GetMapping("/getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> list = chapterService.getChapterVideoCourseById(courseId);
        return R.ok().data("list", list);
    }

    @ApiOperation("添加章节")
    @PostMapping("/addChapter")
    public R addChapter(@RequestBody Chapter chapter) {
        chapterService.save(chapter);
        return R.ok();
    }

    @ApiOperation("根据Id查询章节")
    @GetMapping("/getChapterById/{chapterId}")
    public R getChapterById(@PathVariable String chapterId) {
        Chapter chapter = chapterService.getById(chapterId);
        return R.ok().data("z`", chapter);
    }

    @ApiOperation("修改章节")
    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody Chapter chapter){
        chapterService.updateById(chapter);
        return R.ok();
    }

    @ApiOperation("删除章节")
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        boolean res = chapterService.deleteChapter(chapterId);
        if(res){
            return R.ok();
        }else{
            return R.error();
        }
    }
}

