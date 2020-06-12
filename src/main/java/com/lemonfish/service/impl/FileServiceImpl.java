package com.lemonfish.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.lemonfish.dto.AliyunOSSDTO;
import com.lemonfish.entity.User;
import com.lemonfish.enumcode.InfoEnum;
import com.lemonfish.mapper.UserMapper;
import com.lemonfish.util.RedisUtil;
import com.lemonfish.util.UserFactory;
import com.lemonfish.util.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.service.impl
 * @date 2020/5/12 18:12
 */
@Service
public class FileServiceImpl {
    /**
     * 10年时间
     */
    public static final long TEN_YEARS = 3600 * 24 * 365 * 10 *1000L;
    /**
     * 社区名称前缀
     */
    public static final String LEMONC = "LEMONC-";
    @Autowired
    AliyunOSSDTO aliyunOSSDTO;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    UserFactory userFactory;
    /**
     * 上传写文章的时候的图片
     * @param file
     * @return
     */
    public String postPicture(MultipartFile file)  {
        OSS ossClient = getOssClient();

        String fullName = file.getOriginalFilename();
        // 获取图片后缀
        assert fullName != null;
        String suffixName = fullName.substring(fullName.lastIndexOf("."));

        // 获取文件名称
        StringBuilder resultName = getFullName(suffixName);


        File toFile = multipartFileToFile(file, resultName, suffixName);
        return postReturnUrl(ossClient, resultName, toFile);

    }

    /**
     * 写文章的时候的 删除图片
     * @param fileName
     * @return
     */
    public boolean deletePicture(String fileName) {
        OSS ossClient = getOssClient();
        ossClient.deleteObject(aliyunOSSDTO.getBucketName(), fileName);
        return true;
    }


    /**
     * 获取文件名称
     * @param suffixName
     * @return
     */
    private StringBuilder getFullName(String suffixName) {
        // 合成文件名称
        StringBuilder resultName = new StringBuilder();

        resultName
                .append(LEMONC)
                .append(UUID.randomUUID().toString().replace("-",""))
                .append(suffixName);
        return resultName;
    }





    /**
     * 更新用户头像
     * @param file
     * @return
     */
    public String postAvatar(MultipartFile file,Long uid)  {
        OSS ossClient = getOssClient();

        String fullName = file.getOriginalFilename();
        assert fullName != null;
        String suffixName = fullName.substring(fullName.lastIndexOf("."));


        // 获取文件名称,因为是用户头像，所以key相同覆盖
        StringBuilder resultName = new StringBuilder();
        resultName.append(LEMONC).append(uid).append(suffixName);

        // MultipartFile -> File
        File toFile = multipartFileToFile(file, resultName, suffixName);

        // 上传文件
        String url = postReturnUrl(ossClient, resultName, toFile);
        User user = userFactory.build(uid);
        user.setAvatarUrl(url);
        // 更新redis
        userFactory.set(user);

        return url;
    }

    /**
     * 获取Oss客户端
     * @return
     */
    private OSS getOssClient() {
        return new OSSClientBuilder().build(aliyunOSSDTO.getEndpoint(), aliyunOSSDTO.getAccessKeyId(), aliyunOSSDTO.getAccessKeySecret());
    }


    /**
     * 上传图片并返回图片的URL
     * @param ossClient
     * @param resultName
     * @param toFile
     * @return
     */
    private String postReturnUrl(OSS ossClient, StringBuilder resultName, File toFile) {
        // 上传文件并获取url
        ossClient.putObject(new PutObjectRequest(aliyunOSSDTO.getBucketName(), resultName.toString(), toFile));
        //通过 ossClient 可以获取 刚上传文件的 访问URL
        Date expiration = new Date(System.currentTimeMillis() + TEN_YEARS);
        URL url = ossClient.generatePresignedUrl(aliyunOSSDTO.getBucketName(), resultName.toString(), expiration);
        return url.toString();
    }




    /**
     * multipartFile -> File
     * @param file
     * @param resultName
     * @param suffixName
     * @return
     */
    private File multipartFileToFile(MultipartFile file, StringBuilder resultName, String suffixName) {
        // MultipartFile -> File
        File toFile = null;
        try {
            toFile = File.createTempFile(resultName.toString(), suffixName);
            file.transferTo(toFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toFile;
    }


}

