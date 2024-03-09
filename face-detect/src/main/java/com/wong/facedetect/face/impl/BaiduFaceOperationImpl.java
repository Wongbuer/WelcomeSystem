package com.wong.facedetect.face.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.wong.facedetect.face.FaceOperation;
import com.wong.facedetect.model.UserFace;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wongbuer
 */
@Slf4j
@Component
@RefreshScope
public class BaiduFaceOperationImpl implements FaceOperation {

    private static String API_KEY;

    private static String SECRET_KEY;

    private static final String DEFAULT_ERROR_MSG = "操作失败";

    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    @Value("${baidu.api-key}")
    public void setApiKey(String apiKey) {
        API_KEY = apiKey;
    }

    @Value("${baidu.secret-key}")
    public void setSecretKey(String secretKey) {
        SECRET_KEY = secretKey;
    }

    @Override
    public String faceInformationEntry(UserFace userFace) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        Map<String, Object> params = new HashMap<>(5);
        params.put("group_id", "test");
        params.put("image", userFace.getPicString());
        params.put("user_id", userFace.getUserId());
        params.put("image_type", "BASE64");
        params.put("user_info", userFace.getUserInfo());
        String jsonStr = JSONUtil.toJsonStr(params);
        RequestBody body = RequestBody.create(jsonStr, mediaType);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        if (response.body() != null) {
            return JSONUtil.parseObj(response.body().string()).getStr("error_msg");
        }
        return DEFAULT_ERROR_MSG;
    }

    @Override
    public Integer faceInformationSearch(UserFace userFace) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        Map<String, Object> params = new HashMap<>(4);
        params.put("group_id_list", "test");
        params.put("image", userFace.getPicString());
        params.put("image_type", "BASE64");
        params.put("user_id", userFace.getUserId());
        String jsonStr = JSONUtil.toJsonStr(params);
        RequestBody body = RequestBody.create(jsonStr, mediaType);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rest/2.0/face/v3/search?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        try {
            if (response.body() != null) {
                JSONObject result = JSONUtil.parseObj(response.body().string()).getJSONObject("result");
                JSONArray userList = result.getJSONArray("user_list");
                return userList.getJSONObject(0).getInt("user_id");
            }
        } catch (Exception e) {
            if (log.isTraceEnabled()) {
                log.trace("face Information search failed-{}", e.getMessage());
            }
            return -1;
        }
        return 0;
    }

    @Override
    public String faceInformationRemove(UserFace userFace) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        Map<String, Object> params = new HashMap<>(2);
        params.put("group_id", "test");
        params.put("user_id", userFace.getUserId());
        String jsonStr = JSONUtil.toJsonStr(params);
        RequestBody body = RequestBody.create(jsonStr, mediaType);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/delete?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        if (response.body() != null) {
            return JSONUtil.parseObj(response.body().string()).getStr("error_msg");
        }
        return DEFAULT_ERROR_MSG;
    }

    /**
     * 从用户的AK，SK生成鉴权签名（Access Token）
     *
     * @return 鉴权签名（Access Token）
     * @throws IOException IO异常
     */
    static String getAccessToken() throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + API_KEY
                + "&client_secret=" + SECRET_KEY);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        return (String) JSONUtil.parseObj(response.body().string()).get("access_token");
    }
}
