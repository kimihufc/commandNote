package com.yan.nettyproject.netty.mydubbo;

import com.yan.nettyproject.netty.dubborpc.privoder.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    public static Map<String,Object> beancontain ;
    {
        beancontain = new HashMap<>();
        HelloService bean = new helloServerImpl();
        beancontain.put(HelloService.class.getName(),bean);
        SayService sayService = new SayServiceImpl();
        beancontain.put(SayService.class.getName(),sayService);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg);
        String detailstr = msg.toString();
        String[] strings = detailstr.split("#");
        String classStr = strings[0];
        String methonStr = strings[1];
        String param = strings[2];
        System.out.println(classStr+"------"+methonStr+"**********"+param);
        Class<?> aClass = Class.forName(classStr);
        Object instance = beancontain.get(classStr);
        Method method = aClass.getMethod(methonStr,String.class);
        Object invoke = method.invoke(instance,param);
        ctx.writeAndFlush(invoke.toString()+"$");
    }
}
