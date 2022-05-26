package com.school.edu.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 功能描述：
 *
 * @Package: com.school.edu.entity.excel
 * @author: Marvin-zl
 * @date: 2022/5/26 21:42
 */
@Data
public class SubjectData {
    @ExcelProperty(index = 0)
    private String oneSubjectName;
    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
