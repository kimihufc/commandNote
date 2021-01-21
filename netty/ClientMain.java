package com.yan.nettyproject.netty.mydubbo;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ClientMain {

    public static void main(String[] args) throws Exception {
        ClientHandler clientHandler = new ClientHandler();
        EventLoopGroup clientgroup = new NioEventLoopGroup();
        Bootstrap clientBoot = new Bootstrap();
        clientBoot
                .group(clientgroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        sc.pipeline().addLast(new StringDecoder());
                        sc.pipeline().addLast(new StringEncoder());
                        sc.pipeline().addLast(clientHandler);
                    }
                });
        ChannelFuture future = clientBoot.connect("127.0.0.1", 8888).sync();
        System.out.println(future.isSuccess());
        HelloService helloService = (HelloService)clientHandler.getbean(HelloService.class);
        helloService.hello("i am ok");
        Thread.sleep(1000L);
        SayService sayService = (SayService)clientHandler.getbean(SayService.class);
        sayService.ok("test");

    }
}
