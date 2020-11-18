package com.guli.edu.eduoss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * 该类是服务已启动就实例化，一实例化就会执行afterPropertiesSet方法
 */
@Component
public class ConstantPropertiesUtils implements InitializingBean {

    //读取配置文件的内容
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.keyid}")
    private String keyid;

    @Value("${aliyun.oss.file.keysecret}")
    private String keysecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String buketnname;

    public static String END_POINT;
    public static String ACESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUKET_NNAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;
        ACESS_KEY_ID = keyid;
        ACCESS_KEY_SECRET = keysecret;
        BUKET_NNAME = buketnname;
    }
}
