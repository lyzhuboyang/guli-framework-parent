package com.guli.edu.guliedu.entity.query;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseQuery {
    private String title;//课程名称
    private String status;//课程状态
    private BigDecimal price;//课程价格
}
