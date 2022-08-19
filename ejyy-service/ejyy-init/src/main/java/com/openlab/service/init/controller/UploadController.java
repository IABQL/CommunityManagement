package com.openlab.service.init.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/upload")
public class UploadController {

    private String endpoint = "oss-cn-beijing.aliyuncs.com";

    private String accessKeyId = "LTAI5tLJDTEmVekc3Rq7UXzJ";

    private String accessKeySecret = "boeDf4p3IaD4SLoEpM4oxRS6Yar9ZE";

    private String host = "https://blogs-community.oss-cn-beijing.aliyuncs.com";


    @GetMapping("/sign")
    public Map<String,Object> token(){

        Map<String, Object> respMap = new LinkedHashMap<>();

        OSS client = new OSSClientBuilder().build(endpoint,accessKeyId, accessKeySecret);

        try {
            long expireTime = 3600;// token过期时间
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, "avatar/");

            String postPolicy = client.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = new byte[0];
            binaryData = postPolicy.getBytes("utf-8");

            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = client.calculatePostSignature(postPolicy);

            respMap.put("accessid", "LTAI5tLJDTEmVekc3Rq7UXzJ");
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            //respMap.put("expire", formatISO8601Date(expiration));
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
            respMap.put("code",200);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return respMap;
    }
}
