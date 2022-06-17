package com.school.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.school.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author marvin-zl
 * @since 2022-05-23
 */
public interface TeacherService extends IService<Teacher> {

    /**
     * 分页查询教师
     * @param teacherPage
     * @return
     */
    Map<String, Object> getTeacherFrontList(Page<Teacher> teacherPage);
}
