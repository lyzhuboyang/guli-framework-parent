package com.guli.edu.edusta.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.edu.common.R;
import com.guli.edu.edusta.client.UcenterClient;
import com.guli.edu.edusta.entity.StatisticsDaily;
import com.guli.edu.edusta.mapper.StatisticsDailyMapper;
import com.guli.edu.edusta.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author zby
 * @since 2020-10-05
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    //获取用户模块每天注册人数，添加统计分析表里面
    @Override
    public void getCountRegisterCount(String day) {
        R r = ucenterClient.countRegister(day);
        //通过R对象获取数据
        Integer num = (Integer) r.getData().get("countRegister");
        //把获取数据添加到分析表里面
        StatisticsDaily sta = new StatisticsDaily();
        sta.setDateCalculated(day);
        sta.setRegisterNum(num);

        //TODO 在实际操作中查询其他模块获取数据，模拟随机数字
        sta.setCourseNum(RandomUtils.nextInt(100, 200));
        sta.setLoginNum(RandomUtils.nextInt(100, 200));
        sta.setVideoViewNum(RandomUtils.nextInt(100, 200));

        //添加之前需要把相同日期的数据进行删除，再进行添加
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);
        baseMapper.insert(sta);

    }

    //返回统计数据用于图标显示
    @Override
    public Map<String, Object> getDataCount(String begin, String end, String type) {
        //根据日期范围查询统计数量和具体日期
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin, end);
        wrapper.select(type, "date_calculated");
        List<StatisticsDaily> statisticsDailies = baseMapper.selectList(wrapper);

        //把查询出来的数据，变成两个数组
        //创建两个list集合
        //list 用于存储日期
        List<String> timeList = new ArrayList<>();
        //list 用于存储数量
        List<Integer> dataList = new ArrayList<>();

        //遍历从数据库中查询出来的statisticsDailies
        for (int i = 0; i < statisticsDailies.size(); i++) {
            StatisticsDaily daily = statisticsDailies.get(i);
            String dateCalculated = daily.getDateCalculated();
            timeList.add(dateCalculated);
            switch (type) {
                case "login_num":
                    Integer loginNum = daily.getLoginNum();
                    dataList.add(loginNum);
                    break;
                case "register_num":
                    Integer registerNum = daily.getRegisterNum();
                    dataList.add(registerNum);
                    break;
                case "video_view_num":
                    Integer videoViewNum = daily.getVideoViewNum();
                    dataList.add(videoViewNum);
                    break;
                case "course_num":
                    Integer courseNum = daily.getCourseNum();
                    dataList.add(courseNum);
                    break;
                default:
                    break;
            }
        }
        //把封装的两个list集合，放到map中，返回。
        Map<String, Object> map = new HashMap<>();
        map.put("timeList", timeList);
        map.put("dataList", dataList);
        return map;
    }
}
