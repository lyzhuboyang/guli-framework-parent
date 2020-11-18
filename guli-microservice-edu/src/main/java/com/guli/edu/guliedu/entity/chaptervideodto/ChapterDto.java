package com.guli.edu.guliedu.entity.chaptervideodto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//章节dto
@Data
public class ChapterDto {
    private String id;//章节id
    private String title;//章节名称
    List<VideoDto> children = new ArrayList<>();//章节中的小节
}
