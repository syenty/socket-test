package com.syenty.example.common.socket;

import com.syenty.example.common.handler.NettySocketServerHandler;
import com.syenty.example.common.properties.ConnectionProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NettySocketServer {

  private static final int NUMBER_OF_THREADS = 1;

  private final ConnectionProperties connectionProperties;

  public void start() {

    // EventLoopGroup : 스레드의 그룹
    EventLoopGroup bossGroup = new NioEventLoopGroup(NUMBER_OF_THREADS);
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    ServerBootstrap serverBootstrap = new ServerBootstrap();

    /**
     * group 메서드
     *
     * 1) group(EventLoopGroup eventLoopGroup)
     *  : 인자로 받은 스레드들로 스레드 그룹을 초기화.
     *
     * 2) group(EventLoopGroup parentGroup, EventLoopGroup childGroup)
     *  : 1) 의 메서드와 같은 역할.
     *    parentGroup은 부모 스레드로써 클라이언트 요청을 수락하는 역할. childGroup은 자식 스레드로써 IO와 이벤트 처리를 담당.
     */

    /**
     *
     */

    serverBootstrap.group(bossGroup, workerGroup)
      .channel(NioServerSocketChannel.class)
      .childHandler(new ChannelInitializer<SocketChannel>() {
        @Override
        public void initChannel(SocketChannel ch) {
          ChannelPipeline pipeline = ch.pipeline();
          //handler setting
          pipeline.addLast(new NettySocketServerHandler());
        }
      })
      .option(ChannelOption.SO_BACKLOG, 128)
      .childOption(ChannelOption.SO_KEEPALIVE, true);

    try {
      ChannelFuture f = serverBootstrap.bind(Integer.parseInt(connectionProperties.getConnectionProxyPort())).sync();
      f.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

}
