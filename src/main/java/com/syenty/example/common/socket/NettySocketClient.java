package com.syenty.example.common.socket;

import com.syenty.example.common.handler.NettySocketClientHandler;
import com.syenty.example.common.properties.ConnectionProperties;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NettySocketClient {

  private final ConnectionProperties connectionProperties;

  public void start(final String msg) {

    EventLoopGroup workerGroup = new NioEventLoopGroup();

    try {
      Bootstrap bootstrap = new Bootstrap();
      bootstrap.group(workerGroup);
      bootstrap.channel(NioSocketChannel.class);
      bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
      bootstrap.handler(new ChannelInitializer<SocketChannel>() {
        @Override
        public void initChannel(SocketChannel ch) {
          ch.pipeline().addLast(new NettySocketClientHandler(msg));
        }
      });

      //client connect
      try {

        ChannelFuture f = bootstrap.connect(
          connectionProperties.getConnectionServerIp(),
          Integer.parseInt(connectionProperties.getConnectionServerPort())
        ).sync();

        f.channel().closeFuture().sync();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }finally {
      workerGroup.shutdownGracefully();
    }
  }

}
