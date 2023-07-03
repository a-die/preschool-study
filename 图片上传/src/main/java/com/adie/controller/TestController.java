package com.adie.controller;

import com.adie.utils.QiniuCloudUtil;
import com.adie.utils.ResultInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Controller
@CrossOrigin
public class TestController {
    @ResponseBody
    @RequestMapping(value="/uploadImg", method= RequestMethod.POST)
    public ResultInfo uploadImg(@RequestParam MultipartFile image, HttpServletRequest request) {
        ResultInfo result = new ResultInfo();
        if (image.isEmpty()) {
            result.setCode(400);
            result.setMsg("文件为空，请重新上传");
            return result;
        }

        try {
            byte[] bytes = image.getBytes();
            String imageName = UUID.randomUUID().toString();

            QiniuCloudUtil qiniuUtil = new QiniuCloudUtil();
            try {
                //使用base64方式上传到七牛云
                String url = qiniuUtil.put64image(bytes, imageName);
                result.setCode(200);
                result.setMsg("文件上传成功");
                result.setInfo(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        } catch (IOException e) {
            result.setCode(500);
            result.setMsg("文件上传发生异常！");
            return result;
        }
    }
}
