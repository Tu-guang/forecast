package com.app.forecast.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.app.forecast.common.BaseResponse;
import com.app.forecast.common.ErrorCode;
import com.app.forecast.common.ResultUtils;
import com.app.forecast.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;
import java.io.File;

/**
 * 上传文件映射表
 */
@Slf4j
@RestController
@RequestMapping("file")
public class FileController {
    //文件磁盘路径
    @Value("${files.upload.path}")
    private String fileUploadPath;

    /**
     * 上传文件
     */
    @RequestMapping("/upload")
    public BaseResponse<String> upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new BusinessException(ErrorCode.FILE_ERROR);
        }
        //获取文件原始名称
        String originalFilename = file.getOriginalFilename();
        //获取文件的类型
        String type = FileUtil.extName(originalFilename);
        log.info("文件类型是：" + type);
        //获取文件大小
        long size = file.getSize();

        //获取文件
        File uploadParentFile = new File(fileUploadPath);
        //判断文件目录是否存在
        if (!uploadParentFile.exists()) {
            //如果不存在就创建文件夹
            uploadParentFile.mkdirs();
        }
        //定义一个文件唯一标识码（UUID）
        String uuid = UUID.randomUUID().toString();

        File uploadFile = new File(fileUploadPath + uuid + StrUtil.DOT + type);
        //将临时文件转存到指定磁盘位置
        log.info("文件：" + uploadFile);
        file.transferTo(uploadFile);
        return ResultUtils.success(originalFilename);
    }

    /**
     * 返回图片
     *
     * @return
     */
    @RequestMapping("/image")
    public BaseResponse<byte[]> image() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(fileUploadPath + "9d83954e-80e9-486f-96ad-45fb6da2cfd7.jpg");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = fileInputStream.read(b)) != -1) {
            bos.write(b, 0, len);
        }
        byte[] fileByte = bos.toByteArray();
        return ResultUtils.success(fileByte);
    }
}
