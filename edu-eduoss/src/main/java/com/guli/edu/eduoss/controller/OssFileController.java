package com.guli.edu.eduoss.controller;

import com.guli.edu.common.R;
import com.guli.edu.eduoss.service.OssFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileupload")
@CrossOrigin
public class OssFileController {

    @Autowired
    OssFileService ossFileService;

    //上传头像
    //<input type="file" name="file"/>
    @PostMapping("upload")
    public R uploadFile(@RequestParam(value = "file", required = true) MultipartFile file, @RequestParam(value = "host", required = false) String host) {
        //1 获取上传的文件  MultipartFile
        //因为把上传文件oss地址存到数据库中
        //返回oss文件地址
        String url = ossFileService.fileUploadOss(file, host);
        return R.ok().data("url", url);
    }

}
