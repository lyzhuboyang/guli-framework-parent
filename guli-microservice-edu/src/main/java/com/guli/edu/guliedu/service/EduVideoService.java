package com.guli.edu.guliedu.service;

import com.guli.edu.guliedu.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author zby
 * @since 2020-09-26
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeVideo(String videoId);

    void removeVideoByCourseId(String courseId);
}
