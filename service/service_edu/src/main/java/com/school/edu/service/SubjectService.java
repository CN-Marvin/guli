package com.school.edu.service;

import com.school.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.school.edu.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author marvin-zl
 * @since 2022-05-26
 */
public interface SubjectService extends IService<Subject> {

    /**
     * excel导入文件添加课程
     * @param file
     * @param subjectService
     */
    void saveSubject(MultipartFile file,SubjectService subjectService);

    /**
     * 获得所有课程分类列表
     * @return 课程分类列表
     */
    List<OneSubject> getAllOneTwoSubject();
}
