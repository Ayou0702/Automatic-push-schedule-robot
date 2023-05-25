package com.enterprise.controller;

import com.enterprise.entity.vo.ResultVo;
import com.enterprise.util.AvatarUtil;
import com.enterprise.util.LogUtil;
import com.enterprise.util.Result;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;

@RestController
public class AvatarController {

    @Resource
    Result result;

    @Resource
    AvatarUtil avatarUtil;

    ApplicationHome applicationHome = new ApplicationHome(this.getClass());


    @PostMapping("/uploadAvatar")
    public ResultVo uploadAvatar(@RequestParam("file") MultipartFile avatar, int id, String avatarType) {

        if (avatar.isEmpty()) {
            return result.failed("图片上传失败");
        }

        String originalFilename = avatar.getOriginalFilename();
        String extension = "." + originalFilename.split("\\.")[1];
        String filename = id + extension;

        String pre = applicationHome.getDir() + File.separator + avatarType + File.separator;
        String path = pre + filename;

        System.out.println(path);
        try {
            avatarUtil.duplicateChecking(id, pre);
            avatar.transferTo(new File(path));
            return result.success("图片上传成功");
        } catch (IOException e) {
            return result.failed(e.getMessage());
        }

    }

    @GetMapping("/getCourseAvatar/{courseId}")
    public ResponseEntity<ByteArrayResource> getCourseAvatar(@PathVariable String courseId, HttpServletRequest request) throws IOException {

        // 获取图片的输入流
        ClassPathResource resource = new ClassPathResource(applicationHome.getDir()+File.separator+"1.png");
        InputStream imageInputStream = resource.getInputStream();
        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        // 将输入流转换为 Resource 并返回
        ByteArrayResource byteArrayResource = new ByteArrayResource(IOUtils.toByteArray(imageInputStream));
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .body(byteArrayResource);

    }

    @GetMapping("/getTeacherAvatar/{teacherId}")
    public ResponseEntity<InputStreamResource> getTeacherAvatar(@PathVariable String teacherId, HttpServletRequest request) throws IOException {

        ClassPathResource resource = new ClassPathResource(applicationHome.getDir() +File.separator + "courseAvatar" + File.separator + teacherId + ".jpg");
        System.out.println(applicationHome.getDir() +File.separator + "courseAvatar" + File.separator + teacherId + ".jpg");
        if (!resource.exists()) {
            resource = new ClassPathResource(applicationHome.getDir() +File.separator + "courseAvatar" + File.separator + teacherId + ".png");
        }

        if (!resource.exists()) {
            resource = new ClassPathResource(applicationHome.getDir() +File.separator + "courseAvatar" + File.separator + teacherId + ".jpeg");
        }

        if (!resource.exists()) {
            LogUtil.error("图片不存在");
            return null;
        }

        // 设置Content-Type为image/jpeg
        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(new InputStreamResource(resource.getInputStream()));

    }

}
