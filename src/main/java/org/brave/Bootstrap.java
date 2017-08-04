package org.brave;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by junzhang on 2017/8/3.
 */
@SpringBootApplication
@EnableScheduling
public class Bootstrap {

    public static void main(String[] args){
        SpringApplication.run(Bootstrap.class,args);
    }

}
