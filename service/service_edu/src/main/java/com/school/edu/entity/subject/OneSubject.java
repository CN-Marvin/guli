package com.school.edu.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：
 *
 * @Package: com.school.edu.entity.subject
 * @author: Marvin-zl
 * @date: 2022/5/27 12:34
 */
@Data
public class OneSubject {
    private String id;
    private String title;
    private List<TwoSubject> children = new ArrayList<>();
}
