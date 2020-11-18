package com.guli.edu.guliedu.service;

import com.guli.edu.guliedu.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.guliedu.entity.subjectdto.OneSubjectDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author zby
 * @since 2020-09-20
 */
public interface EduSubjectService extends IService<EduSubject> {
    //poi读取excel分类数据
    List<String> importDataSubject(MultipartFile file);

    List<OneSubjectDto> findAllSubjcet();

    boolean removeByIdSubject(String id);

    //添加一级分类
    boolean saveOne(EduSubject eduSubject);

    //添加二级分类
    boolean saveTwo(EduSubject eduSubject);
}
