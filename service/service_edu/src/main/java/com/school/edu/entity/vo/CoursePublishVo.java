package com.school.edu.entity.vo;

/**
 * 功能描述：
 *
 * @Package: com.school.edu.entity.vo
 * @author: Marvin-zl
 * @date: 2022/5/28 23:28
 */

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "课程发布信息")
@Data
public class CoursePublishVo {
    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;
}
