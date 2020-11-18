package com.guli.edu.eduoss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.guli.edu.eduoss.service.OssFileService;
import com.guli.edu.eduoss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceFileImpl implements OssFileService {

    //上传讲师头像
    @Override
    public String fileUploadOss(MultipartFile file, String host) {
        try {
            // Endpoint以杭州为例，其它Region请按实际情况填写。String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
            // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。String accessKeyId = "<yourAccessKeyId>";
            String endpoint = ConstantPropertiesUtils.END_POINT;
            ;
            String accessKeyId = ConstantPropertiesUtils.ACESS_KEY_ID;
            String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            //获取buckeName
            String buketNname = ConstantPropertiesUtils.BUKET_NNAME;
            String filename = file.getOriginalFilename();

            //在文件名称添加uuid值，让每个文件名称唯一的
            String uuid = UUID.randomUUID().toString().replace("-", "");
            //把当前时间转换2020/9/18/
            String dateurl = new DateTime().toString("yyyy/MM/dd");
            // /2020/9/18/01.jpg
            filename = dateurl + "/" + uuid + filename;

            if (!StringUtils.isEmpty(host)) {
                filename = host + "/" + filename;
            }
            ossClient.putObject(buketNname, filename, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            //返回文件路径
            String url = "https://" + buketNname + "." + endpoint + "/" + filename;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
