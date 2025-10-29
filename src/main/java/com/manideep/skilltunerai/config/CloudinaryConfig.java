package com.manideep.skilltunerai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {

    @Bean
    Cloudinary cloudinary() {

        return new Cloudinary(
            System.getenv("CLOUDINARY_URL")
        );

    }

}
