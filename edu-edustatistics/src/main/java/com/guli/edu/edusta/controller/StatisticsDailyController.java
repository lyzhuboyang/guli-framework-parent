package com.guli.edu.edusta.controller;


import com.guli.edu.common.R;
import com.guli.edu.edusta.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author zby
 * @since 2020-10-05
 */
@RestController
@RequestMapping("/edusta/statistics")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService dailyService;

    //返回统计数据用于图标显示
    @GetMapping("getCountData/{begin}/{end}/{type}")
    public R getCountData(@PathVariable String begin, @PathVariable String end, @PathVariable String type) {
        //前端需要的数据：两个数组，所以我们返回一个map集合
        Map<String, Object> map = dailyService.getDataCount(begin, end, type);
        return R.ok().data(map);
    }

    //获取用户模块每天注册人数，添加统计分析表里面
    @GetMapping("getRegisterCount/{day}")
    public R getRegisterCount(@PathVariable String day) {
        dailyService.getCountRegisterCount(day);
        return R.ok();
    }


}

