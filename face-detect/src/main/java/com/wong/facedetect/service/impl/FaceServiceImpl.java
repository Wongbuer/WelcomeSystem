package com.wong.facedetect.service.impl;

import com.wong.common.utils.CommonResponse;
import com.wong.common.utils.ResultUtil;
import com.wong.facedetect.face.FaceOperation;
import com.wong.facedetect.model.UserFace;
import com.wong.facedetect.service.FaceService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Wongbuer
 * @Description 人脸信息服务实现类
 */
@Service("faceService")
public class FaceServiceImpl implements FaceService {
    @Resource
    private FaceOperation faceOperation;

    @Override
    public CommonResponse<String> faceEntry(MultipartFile multipartFile, UserFace userFace) throws IOException {
        String picString = faceOperation.getFileContentAsBase64(multipartFile, false);
        userFace.setPicString(picString);
        String errorMsg = faceOperation.faceInformationEntry(userFace);
        return ResultUtil.success(errorMsg);
    }

    @Override
    public CommonResponse<String> faceSearch(MultipartFile multipartFile, UserFace userFace) throws IOException {
        String picString = faceOperation.getFileContentAsBase64(multipartFile, false);
        userFace.setPicString(picString);
        Integer userId = faceOperation.faceInformationSearch(userFace);
        int i = Integer.parseInt(userFace.getUserId());
        if (userId != null && userId > 0 && userId.equals(i)) {
            return ResultUtil.success(200, "人脸信息匹配成功");
        } else {
            return ResultUtil.error(400, "人脸信息匹配失败");
        }
    }

    @Override
    public CommonResponse<String> faceRemove(UserFace userFace) throws IOException {
        String errorMsg = faceOperation.faceInformationRemove(userFace);
        return ResultUtil.success(errorMsg);
    }
}
