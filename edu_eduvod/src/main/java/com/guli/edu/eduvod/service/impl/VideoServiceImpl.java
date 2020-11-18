package com.guli.edu.eduvod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.guli.edu.eduvod.service.VideoService;
import com.guli.edu.eduvod.utils.AliyunVodSDKUtils;
import com.guli.edu.eduvod.utils.ConstantPropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {
    @Override
    public String uploadAliYunVideo(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            String title = file.getOriginalFilename().substring(0, fileName.lastIndexOf("."));
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        } catch (Exception e) {
            return null;
        }
    }

    //根据视频id删除阿里云视频
    @Override
    public void deleteVideoAliyun(String videoId) {
        try {
            //1初始化对象
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            //2创建request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //3向request中设置视频id
            //支持传入多个视频ID，多个用逗号分隔
            request.setVideoIds(videoId);
            //4调用方法删除
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.println(response.getRequestId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除多个阿里云视频的方法
    @Override
    public void removeVideoMore(List videoListIds) {
        try {
            //1初始化对象
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            //2创建request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //3向request中设置视频id
            //支持传入多个视频ID，多个用逗号分隔
            String ids = "";
            //一次只能删除20个
            if (videoListIds.size() <= 20) {
                ids = StringUtils.join(videoListIds.toArray(), ",");
                request.setVideoIds(ids);
                //4调用方法删除
                client.getAcsResponse(request);
            } else {
                for (int i = 0; i < Math.ceil(videoListIds.size() / 20.0); i++) {
                    if (i == Math.ceil(videoListIds.size() / 20.0) - 1 && videoListIds.size() % 20 != 0) {
                        ids = StringUtils.join(videoListIds.subList(20 * i, videoListIds.size()).toArray(), ",");
                        request.setVideoIds(ids);
                        //4调用方法删除
                        client.getAcsResponse(request);
                    } else {
                        ids = StringUtils.join(videoListIds.subList(20 * i, 20 * i + 20).toArray(), ",");
                        request.setVideoIds(ids);
                        //4调用方法删除
                        client.getAcsResponse(request);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //根据视频id获取视频凭证
    @Override
    public String getPlayAuthVideo(String vid) {
        try {
            //初始化操作
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            //创建request对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request设置视频id
            request.setVideoId(vid);
            //调用方法得到结果
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            //根据response获取凭证
            String playAuth = response.getPlayAuth();
            return playAuth;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
