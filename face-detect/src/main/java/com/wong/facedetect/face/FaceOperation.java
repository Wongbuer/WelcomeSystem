package com.wong.facedetect.face;

import com.wong.facedetect.model.UserFace;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * @author Wongbuer
 * @Description 人脸识别抽象接口, 用于后期更换不同的人脸识别商
 */
public interface FaceOperation {
    /**
     * 人脸信息录入
     *
     * @param userFace  用户人脸信息包装类
     * @return 错误信息(状态信息)
     * @throws IOException
     */
    String faceInformationEntry(UserFace userFace) throws IOException;

    /**
     * 人脸信息查询
     *
     * @param userFace 用户人脸信息包装类
     * @return
     * @throws IOException
     */
    Integer faceInformationSearch(UserFace userFace) throws IOException;

    /**
     * 人脸信息删除
     *
     * @param userFace 用户人脸信息包装类
     * @return 错误信息(状态信息)
     * @throws IOException
     */
    String faceInformationRemove(UserFace userFace) throws IOException;

    /**
     * 获取文件base64编码
     *
     * @param path      文件路径
     * @param urlEncode 如果Content-Type是application/x-www-form-urlencoded时,传true
     * @return base64编码信息，不带文件头
     * @throws IOException IO异常
     */
    default String getFileContentAsBase64(String path, boolean urlEncode) throws IOException {
        byte[] b = Files.readAllBytes(Paths.get(path));
        String base64 = Base64.getEncoder().encodeToString(b);
        if (urlEncode) {
            base64 = URLEncoder.encode(base64, StandardCharsets.UTF_8);
        }
        return base64;
    }

    default String getFileContentAsBase64(MultipartFile file, boolean urlEncode) throws IOException {
        byte[] b = file.getBytes();
        String base64 = Base64.getEncoder().encodeToString(b);
        if (urlEncode) {
            base64 = URLEncoder.encode(base64, StandardCharsets.UTF_8);
        }
        return base64;
    }
}
