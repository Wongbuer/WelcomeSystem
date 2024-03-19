package com.wong.common.model.face;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Wongbuer
 * @Description 用户人脸信息包装类
 */
@Data
public class UserFace {
    private String userId;
    @JsonProperty("")
    private String picString;
    private String userInfo;

    public UserFace() {
    }

    public UserFace(String userId, String picString) {
        this.userId = userId;
        this.picString = picString;
    }
}
