package com.windsnow1025.javaspringboot;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Media {

    @GetMapping("/video")
    public ResponseEntity<Resource> serveVideo(@RequestParam(value = "filename", defaultValue = "Google I_O '23 in under 10 minutes.mp4") String filename) {
        Resource videoResource = new ClassPathResource("static/" + filename);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("video/mp4"))
                .body(videoResource);
    }

}
