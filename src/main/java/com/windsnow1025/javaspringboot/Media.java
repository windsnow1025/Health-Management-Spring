package com.windsnow1025.javaspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class Media {

    @Autowired
    private ResourcePatternResolver resourcePatternResolver;

    @GetMapping("/api/video")
    public ResponseEntity<Resource> serveVideo(@RequestParam(value = "filename") String filename) {
        Resource videoResource = resourcePatternResolver.getResource("classpath:static/" + filename);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("video/mp4"))
                .body(videoResource);
    }

    @GetMapping("/api/video-list")
    public ResponseEntity<String[]> getVideoFileNames() {
        try {
            Resource[] resources = resourcePatternResolver.getResources("classpath:static/*");
            String[] filenames = new String[resources.length];
            for (int i = 0; i < resources.length; i++) {
                filenames[i] = resources[i].getFilename();
            }
            return ResponseEntity.ok(filenames);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new String[] { "Error retrieving filenames" });
        }
    }

}
