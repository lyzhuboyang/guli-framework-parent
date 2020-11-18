package com.guli.edufront.client;

import com.guli.edu.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("guli-edu")
@Component
public interface ChapterVideoInfoClient {

    //返回当前课程章节和小节的所有数据
    @GetMapping("/guliedu/chapter/getChapterVideoByCourseId/{courseId}")
    public R getChapterVideoByCourseId(@PathVariable("courseId") String courseId);

}
