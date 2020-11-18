package com.guli.edu.guliedu.entity.chaptervideodto;
//小节dto

import lombok.Data;

@Data
public class VideoDto {
    private String id;//小节id
    private String title;//小节名称
    private String videoSourceId;//视频id
}
