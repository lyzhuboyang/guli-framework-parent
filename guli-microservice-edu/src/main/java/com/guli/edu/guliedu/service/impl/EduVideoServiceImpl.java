package com.guli.edu.guliedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.edu.common.R;
import com.guli.edu.guliedu.clent.VodClient;
import com.guli.edu.guliedu.entity.EduVideo;
import com.guli.edu.guliedu.mapper.EduVideoMapper;
import com.guli.edu.guliedu.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author zby
 * @since 2020-09-26
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    //小节删除的方法
    @Override
    public void removeVideo(String videoId) {
        EduVideo eduVideo = baseMapper.selectById(videoId);
        String videoSourceId = eduVideo.getVideoSourceId();
        //如果小节中有视频id，先删除视频
        if (!StringUtils.isEmpty(videoSourceId)) {
            vodClient.removeVideoById(videoSourceId);
        }
        baseMapper.deleteById(videoId);
    }

    // 根据课程id删除小节（包含视频）

    @Override
    public void removeVideoByCourseId(String courseId) {
        //根据课程id查询所有小节
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_Id", courseId);
        videoWrapper.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(videoWrapper);
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < eduVideoList.size(); i++) {
            //获取每个小节里面的视频id
            String videoSourceId = eduVideoList.get(i).getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)) {
                //放到集合list集合
                ids.add(videoSourceId);
            }
        }
        //根据vod模块的方法删除阿里云里面视频
        vodClient.deleteMoreVideo(ids);
        //根据课程id删除小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }
}
