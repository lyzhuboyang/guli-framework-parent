package com.guli.edufront.client;

import com.guli.edu.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("edu-eduvod")
@Component
public interface VodClient {

    //根据视频id获取视频凭证
    @GetMapping("/eduvod/video/getPlayAuthVideo/{vid}")
    public R getPlayAuthVideo(@PathVariable(("vid")) String vid);
}
