package org.brave.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;

/**
 * Created by junzhang on 2017/8/4.
 */
@Component
@Slf4j
public class CuratorLocker {
    @Autowired CuratorFramework curatorFramework;

    public static ExecutorService executorService = Executors.newFixedThreadPool(1);

    public  InterProcessMutex mutex;

    public void init(){
        try {
            Optional<Stat> statOptional = Optional.ofNullable(curatorFramework.checkExists().forPath("/clock"));
            InterProcessMutex mutex = statOptional.map(stat -> {
                InterProcessMutex mutex1 = new InterProcessMutex(curatorFramework,"/clock");
                return mutex1;
            }).orElseGet(()->{
                    try {
                        curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath("/clock", new byte[0]);
                        InterProcessMutex mutex2 = new InterProcessMutex(curatorFramework,"/clock");
                        return mutex2;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

            );

            this.mutex = mutex;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void run(){
        init();
        if(mutex == null){
            log.info("初始化失败.......");
        }
        executorService.submit(() -> {
            try {
                mutex.acquire();
                log.info("本应用获取到了锁");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


}
