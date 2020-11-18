package com.guli.edu.guliedu.entity.query;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseInfoForm {
    private String id;//课程id
    private String title;//课程名称
    private BigDecimal price;//课程价格
    private Integer lessonNum;//课程章节数
    private String cover;//课程封面
    private String subjectId;//课程分类二级Id
    private String subjectParentId;//课程分类一级Id
    private String teacherId;//讲师id
    private String description;//课程描述
}
