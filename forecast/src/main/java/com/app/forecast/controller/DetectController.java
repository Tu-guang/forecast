package com.app.forecast.controller;

import com.app.forecast.common.BaseResponse;
import com.app.forecast.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 进行预测
 */
@Slf4j
@RestController
@RequestMapping("process")
public class DetectController {
    @PostMapping("/detect")
    public BaseResponse<String> Detect(HttpServletRequest request)throws IOException, InterruptedException {
        Process pr;
        pr = Runtime.getRuntime().exec("D:\\Python\\Python39\\python.exe D:\\Python\\run\\1.py");
        BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream(), "GB2312"));
        String line = null;
        while ((line = in.readLine()) != null) {
            log.info(line);
        }
        in.close();
        log.info(String.valueOf(pr.waitFor()));
        if(pr.waitFor()==0){
            return ResultUtils.success("执行成功");
        }else {
            return ResultUtils.success("执行失败");
        }
    }
}
