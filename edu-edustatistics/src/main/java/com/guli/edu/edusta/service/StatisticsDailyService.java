package com.guli.edu.edusta.service;

import com.guli.edu.edusta.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author zby
 * @since 2020-10-05
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    //获取用户模块每天注册人数，添加统计分析表里面
    void getCountRegisterCount(String day);

    //返回统计数据用于图标显示
    Map<String, Object> getDataCount(String begin, String end, String type);
}
