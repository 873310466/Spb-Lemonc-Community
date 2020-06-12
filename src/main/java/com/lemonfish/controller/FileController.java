package com.lemonfish.controller;

import com.lemonfish.service.impl.FileServiceImpl;
import com.lemonfish.util.MyJsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;


/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.controller
 * @date 2020/5/12 17:16
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    FileServiceImpl fileService;

    /**
     * 编写文章时候，上传图片
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public MyJsonResult upload(@RequestParam(value="image") MultipartFile file) {
        String url = fileService.postPicture(file);
        return MyJsonResult.success(url);
    }


    /**
     * 编写文章时候，删除图片
     * @param fileName
     * @return
     */
    @DeleteMapping("/delete")
    public MyJsonResult delete(@RequestParam("fileName") String fileName) {
        boolean res = fileService.deletePicture(fileName);
        return MyJsonResult.success(res);
    }


    @PostMapping("/upload/{uid}")
    public MyJsonResult upload(@RequestParam(value="file") MultipartFile file,
                               @PathVariable(value = "uid")Long uid) {
        String url = fileService.postAvatar(file,uid);
        return MyJsonResult.success(url);
    }


}
