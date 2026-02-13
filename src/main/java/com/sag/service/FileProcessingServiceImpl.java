/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sag.service;

/**
 *
 * @author ahmed
 */

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileProcessingServiceImpl  {

 //   @Value("${filePath}")
 //   private String basePath;

    public String uploadFile(String fileName, MultipartFile multipartFile, String basePath) {
        File dir = new File(basePath+fileName);

        if(dir.exists()){
            return "EXIST";
        }

        Path path = Path.of(basePath+fileName);

        try{
            Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return "CREATED";
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "FAILED";
    }   

    

    
}
