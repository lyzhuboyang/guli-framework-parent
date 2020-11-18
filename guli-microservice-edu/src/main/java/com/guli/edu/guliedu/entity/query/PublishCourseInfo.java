package com.guli.edu.guliedu.entity.query;

import lombok.Data;

@Data
public class PublishCourseInfo {
    private String id;//课程id
    private String title;//课程名称
    private String cover;//课程封面
    private Integer lessonNum;//课时数
    private String subjectLevelOne;//一级分类名称
    private String subjectLevelTwo;//二级分类名称
    private String teacherName;//讲师名称
    private String price;//课程价格 只用于显示
    private String description;//课程描述
}
