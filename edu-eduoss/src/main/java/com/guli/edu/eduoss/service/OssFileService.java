package com.guli.edu.eduoss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssFileService {
    String fileUploadOss(MultipartFile filem, String host);
}
