package com.dl.mjweb.listener;

import com.dl.mjweb.event.TestEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @Title:
 * @Auther: hangyu
 * @Date: 2019/3/13
 * @Description
 * @Version:1.0
 */
@Component
public class TestEventPublisher {

    @Autowired
    private ApplicationContext applicationContext;
    private ApplicationEventPublisher publisher;

    public void pushlish( String msg){
        publisher.publishEvent(new TestEvent(this,msg));
    }
//    一样的效果
//    public void pushlish(String name, String msg){
//        applicationContext.publishEvent(new TestEvent(this, name,msg));
//    }
}