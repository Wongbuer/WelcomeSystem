package com.wong.facedetect.controller;

import com.wong.common.utils.CommonResponse;
import com.wong.facedetect.model.UserFace;
import com.wong.facedetect.service.FaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Wongbuer
 * @Description 人脸信息处理
 */
@RestController
@RequestMapping("/face")
public class FaceController {
    @Resource
    private FaceService faceService;

    /**
     * 人脸信息录入
     *
     * @param multipartFile 图片
     * @param userFace      人脸信息
     * @return
     * @throws IOException
     */
    @Operation(summary = "人脸信息录入")
    @Parameters({
            @Parameter(name = "multipartFile", description = "图片"),
            @Parameter(name = "userFace", description = "人脸信息")
    })
    @PostMapping("/face_entry")
    public CommonResponse<String> faceEntry(@RequestPart MultipartFile multipartFile, UserFace userFace) throws IOException {
        if (multipartFile.isEmpty() || multipartFile.getOriginalFilename() == null || multipartFile.getSize() == 0) {
            throw new IllegalArgumentException("图片不能为空");
        }
        return faceService.faceEntry(multipartFile, userFace);
    }

    /**
     * 人脸信息搜索
     *
     * @param multipartFile 人脸图片
     * @param userFace      人脸信息
     * @return
     * @throws IOException
     */
    @Operation(summary = "人脸信息搜索")
    @Parameters({
            @Parameter(name = "multipartFile", description = "图片"),
            @Parameter(name = "userFace", description = "人脸信息")
    })
    @PostMapping("/face_search")
    public CommonResponse<String> faceSearch(@RequestPart MultipartFile multipartFile, UserFace userFace) throws IOException {
        if (multipartFile.isEmpty() || multipartFile.getOriginalFilename() == null || multipartFile.getSize() == 0) {
            throw new IllegalArgumentException("图片不能为空");
        }
        return faceService.faceSearch(multipartFile, userFace);
    }

    /**
     * 人脸信息删除
     *
     * @param userFace 人脸信息
     * @return
     * @throws IOException
     */
    @Operation(summary = "人脸信息删除")
    @Parameters({
            @Parameter(name = "userFace", description = "人脸信息")
    })
    @PostMapping("/face_remove")
    public CommonResponse<String> faceRemove(UserFace userFace) throws IOException {
        return faceService.faceRemove(userFace);
    }
}
