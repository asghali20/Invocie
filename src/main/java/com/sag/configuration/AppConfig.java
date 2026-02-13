/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sag.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author ahmed
 */

@Configuration
public class AppConfig {

    @Bean
    public ConcurrentHashMap<String, Object> dataMap() {
        ConcurrentHashMap<String, Object> dataMap = new ConcurrentHashMap<>();
        return dataMap;
    }
}