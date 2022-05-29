package com.school.edu.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：
 *
 * @Package: com.school.edu.entity.chapter
 * @author: Marvin-zl
 * @date: 2022/5/28 10:27
 */
@Data
public class ChapterVo {
    private String id;
    private String title;
    private List<VideoVo> children = new ArrayList<>();
}
