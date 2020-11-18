package com.guli.edu.guliedu.service;

import com.guli.edu.guliedu.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.guliedu.entity.chaptervideodto.ChapterDto;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zby
 * @since 2020-09-26
 */
public interface EduChapterService extends IService<EduChapter> {

    //返回当前课程章节和小节的所有数据
    List<ChapterDto> getChapterVideoByCourseId(String courseId);

    //删除章节，如果章节里面有小节，不进行删除
    void removeChapter(String chapterId);

    //根据课程id删除章节
    void removeChapterByCourseId(String courseId);
}
