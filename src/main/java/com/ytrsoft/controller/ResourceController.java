package com.ytrsoft.controller;

import com.ytrsoft.core.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> image(@PathVariable String id) {
        String url = Resource.image(id);
        try {
            RestTemplate restTemplate = new RestTemplate();
            byte[] imageBytes = restTemplate.getForObject(url, byte[].class);
            if (imageBytes == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("图片读取失败: {}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/video/{id}")
    public ResponseEntity<byte[]> video(@PathVariable String id) {
        String url = Resource.mp4(id);
        try {
            RestTemplate restTemplate = new RestTemplate();
            byte[] videoBytes = restTemplate.getForObject(url, byte[].class);
            if (videoBytes == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("video/mp4"));
            headers.setContentLength(videoBytes.length);
            headers.setCacheControl(CacheControl.noCache().getHeaderValue());
            return new ResponseEntity<>(videoBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("视频加载失败: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
