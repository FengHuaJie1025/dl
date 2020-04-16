package com.dl.mjweb.listener;

import com.dl.mjweb.event.TestEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Title:
 * @Auther: hangyu
 * @Date: 2019/3/13
 * @Description
 * @Version:1.0
 */
@Component
public class TestEventListener implements ApplicationListener<TestEvent> {

    @Async
    @Override
    public void onApplicationEvent(TestEvent testEvent) {
        System.out.println("得到消息："+testEvent.getMsg());
    }
}