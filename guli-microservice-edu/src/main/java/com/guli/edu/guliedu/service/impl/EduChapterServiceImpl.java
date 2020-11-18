package com.guli.edu.guliedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.edu.guliedu.entity.EduChapter;
import com.guli.edu.guliedu.entity.EduVideo;
import com.guli.edu.guliedu.entity.chaptervideodto.ChapterDto;
import com.guli.edu.guliedu.entity.chaptervideodto.VideoDto;
import com.guli.edu.guliedu.handler.EduException;
import com.guli.edu.guliedu.mapper.EduChapterMapper;
import com.guli.edu.guliedu.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.edu.guliedu.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zby
 * @since 2020-09-26
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    EduVideoService eduVideoService;

    //根据课程id返回当前课程章节和小节的所有数据
    @Override
    public List<ChapterDto> getChapterVideoByCourseId(String courseId) {

        //根据课程id获取所有的章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        wrapperChapter.orderByAsc("sort");
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        //根据课程id获取所有的小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        wrapperVideo.orderByAsc("sort");
        List<EduVideo> eduVideoList = eduVideoService.list(wrapperVideo);

        //定义一个集合用于存储最终数据
        List<ChapterDto> finalList = new ArrayList<>();
        //封装章节
        //遍历课程里面章节list集合
        for (int i = 0; i < eduChapterList.size(); i++) {
            //得到每个章节数据
            EduChapter eduChapter = eduChapterList.get(i);
            //把eduChapter中的数据复制到ChapterDto里面
            ChapterDto chapterDto = new ChapterDto();
            BeanUtils.copyProperties(eduChapter, chapterDto);
            //把dto对象放到list集合
            finalList.add(chapterDto);
            //封装小节
            //创建集合用于封装章节里所有的小节
            List<VideoDto> videoDtoList = new ArrayList<>();
            for (int j = 0; j < eduVideoList.size(); j++) {
                //每个小节
                EduVideo eduVideo = eduVideoList.get(j);
                //判断
                //小节chapterid和章节id进行比较
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    //eduVideo复制到videoDto里面
                    VideoDto videoDto = new VideoDto();
                    BeanUtils.copyProperties(eduVideo, videoDto);
                    videoDtoList.add(videoDto);
                }
            }
            //把所有小节list集合放到章节对象里面
            chapterDto.setChildren(videoDtoList);
        }
        return finalList;
    }

    //删除章节，如果章节里面有小节，不进行删除
    @Override
    public void removeChapter(String chapterId) {
        //查询章节里面是否有小节
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", chapterId);
        int count = eduVideoService.count(queryWrapper);
        if (count > 0) {//章节里面有小节
            throw new EduException(20001, "不能删除");
        } else {
            //章节里面没有小节，删除章节
            int result = baseMapper.deleteById(chapterId);
            if (result <= 0) {
                throw new EduException(20001, "删除失败");
            }
        }

    }

    //根据课程id删除章节
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }
}
