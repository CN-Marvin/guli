package com.school.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.school.edu.entity.Subject;
import com.school.edu.entity.excel.SubjectData;
import com.school.edu.service.SubjectService;
import com.school.guli.config.handler.exceptionhandler.GuliException;

/**
 * 功能描述：
 *
 * @Package: com.school.edu.listener
 * @author: Marvin-zl
 * @date: 2022/5/26 22:07
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    public SubjectService subjectService;

    public SubjectExcelListener(){}

    public SubjectExcelListener(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData == null){
            throw new GuliException(20001,"文件数据为空");
        }
        Subject exitOneSubject = this.exitOneSubject(subjectService, subjectData.getOneSubjectName());
        if(exitOneSubject == null){
            exitOneSubject = new Subject();
            exitOneSubject.setParentId("0");
            exitOneSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(exitOneSubject);
        }
        String pid = exitOneSubject.getId();
        Subject exitTwoSubject = this.exitTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
        if(exitTwoSubject == null){
            exitTwoSubject = new Subject();
            exitTwoSubject.setParentId(pid);
            exitTwoSubject.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(exitTwoSubject);
        }

    }

    private Subject exitOneSubject(SubjectService subjectService,String name){
        QueryWrapper<Subject> subjectQueryWrapper = new QueryWrapper<>();
        subjectQueryWrapper.eq("title", name);
        subjectQueryWrapper.eq("parent_id", "0");
        return subjectService.getOne(subjectQueryWrapper);
    }

    private Subject exitTwoSubject(SubjectService subjectService,String name,String pid){
        QueryWrapper<Subject> subjectQueryWrapper = new QueryWrapper<>();
        subjectQueryWrapper.eq("title", name);
        subjectQueryWrapper.eq("parent_id", pid);
        return subjectService.getOne(subjectQueryWrapper);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
