package com.yan.nettyproject.netty.mydubbo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Proxy;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    private ChannelHandlerContext context;

    private Object result;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.context = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result=msg.toString();
        System.out.println("return********"+result);
        ctx.flush();
    }

    public Object getbean(Class proxyclass) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{proxyclass}, (proxy, method, args) -> {
            context.writeAndFlush(proxyclass.getName()+"#"+method.getName()+"#"+args[0]);
            return result;
        });
    }
}
