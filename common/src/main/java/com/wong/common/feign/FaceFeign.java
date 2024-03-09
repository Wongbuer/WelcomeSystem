package com.wong.common.feign;

import com.wong.common.model.face.UserFace;
import com.wong.common.utils.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Wongbuer
 */
@FeignClient(value = "face-detect", path = "/face")
public interface FaceFeign {
    /**
     * 人脸信息录入
     *
     * @param multipartFile
     * @param userFace
     * @return
     */
    @PostMapping("/face_entry")
    CommonResponse<String> faceEntry(@RequestPart MultipartFile multipartFile, UserFace userFace);

    /**
     * 人脸信息搜索
     *
     * @param multipartFile
     * @param userFace
     * @return
     */
    @PostMapping("/face_search")
    CommonResponse<String> faceSearch(@RequestPart MultipartFile multipartFile, UserFace userFace);

    /**
     * 人脸信息删除
     *
     * @param userFace
     * @return
     */
    @PostMapping("/face_remove")
    CommonResponse<String> faceRemove(UserFace userFace);
}
