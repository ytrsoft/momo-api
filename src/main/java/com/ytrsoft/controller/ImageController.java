package com.ytrsoft.controller;

import com.ytrsoft.core.HttpClient;
import com.ytrsoft.core.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/image")
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> query(@PathVariable String id) {
        String url = Image.parse(id);
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
}
