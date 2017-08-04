package org.brave.common;

import lombok.Data;
import org.brave.config.CuratorZkConfig;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by junzhang on 2017/8/3.
 */
@Component
public class TaskServiceTest {

    @Value("${zk.connect}")
    public String connect;

    public static ExecutorService executorService = Executors.newFixedThreadPool(1);

//    TaskServiceTest(){
//        System.out.println(connect);
//    }

    @PostConstruct
    void run(){
        DistributedLock distributedLock = new DistributedLock(connect);
        executorService.submit(() -> distributedLock.lock());
    }

}
