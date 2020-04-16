package com.dl.mjweb.event;

import org.springframework.context.ApplicationEvent;

/**
 * @Title:
 * @Auther: hangyu
 * @Date: 2019/3/13
 * @Description
 * @Version:1.0
 */
public class TestEvent extends ApplicationEvent {


    private String msg;

    public TestEvent(Object source){
        super(source);
    }

    public TestEvent(Object source,String msg){
        super(source);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}