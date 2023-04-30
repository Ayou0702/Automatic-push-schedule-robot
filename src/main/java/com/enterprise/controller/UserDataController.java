package com.enterprise.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 负责前端页面的用户数据获取
 *
 * @author PrefersMin
 * @version 1.0
 */
@RestController
@RequestMapping("/image")
public class UserDataController {

    @GetMapping(value = "/prefersmin.jpg", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> getImage() throws IOException {
        ClassPathResource imgFile = new ClassPathResource("static/images/prefersmin.jpg");
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).contentLength(imgFile.contentLength()).body(imgFile);
    }

}
