package com.wong.facedetect.service;

import com.wong.common.utils.CommonResponse;
import com.wong.facedetect.model.UserFace;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Wongbuer
 * @Description 人脸信息服务
 */
public interface FaceService {
    CommonResponse<String> faceEntry(MultipartFile multipartFile, UserFace userFace) throws IOException;

    CommonResponse<String> faceSearch(MultipartFile multipartFile, UserFace userFace) throws IOException;

    CommonResponse<String> faceRemove(UserFace userFace) throws IOException;

    CommonResponse<String> faceSearchWithBase64(UserFace userFace) throws IOException;
}
