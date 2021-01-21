package com.yan.nettyproject.netty.mydubbo;

public class helloServerImpl implements HelloService{
    @Override
    public String hello(String param) {
        return "hello "+param;
    }
}
