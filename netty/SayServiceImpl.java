package com.yan.nettyproject.netty.mydubbo;

public class SayServiceImpl implements SayService{
    @Override
    public String ok(String param) {
        return "say ****"+param;
    }
}
