package com.school.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.school.edu.entity.Subject;
import com.school.edu.entity.excel.SubjectData;
import com.school.edu.entity.subject.OneSubject;
import com.school.edu.entity.subject.TwoSubject;
import com.school.edu.listener.SubjectExcelListener;
import com.school.edu.mapper.SubjectMapper;
import com.school.edu.service.SubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author marvin-zl
 * @since 2022-05-26
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {


    @Override
    public void saveSubject(MultipartFile file,SubjectService subjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        QueryWrapper<Subject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id", 0);
        List<Subject> oneSubjectList = baseMapper.selectList(oneWrapper);


        QueryWrapper<Subject> twoWrapper = new QueryWrapper<>();
        twoWrapper.ne("parent_id", 0);
        List<Subject> twoSubjectList = baseMapper.selectList(twoWrapper);

        List<OneSubject> finalSubjectList = new ArrayList<>();
        for(Subject sub: oneSubjectList){
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(sub,oneSubject);
            finalSubjectList.add(oneSubject);

            ArrayList<TwoSubject> twoList = new ArrayList<>();
            for(Subject twoSub: twoSubjectList){
                TwoSubject twoSubject = new TwoSubject();
                if(twoSub.getParentId().equals(oneSubject.getId())){
                    BeanUtils.copyProperties(twoSub, twoSubject);
                    twoList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoList);
        }
        return finalSubjectList;
    }
}
