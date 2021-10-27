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
     * group 메서드 : 스레드 그룹을 초기화
     *
     * 1) group(EventLoopGroup eventLoopGroup)
     *  : 인자로 받은 스레드들로 스레드 그룹을 초기화.
     *
     * 2) group(EventLoopGroup parentGroup, EventLoopGroup childGroup)
     *  : 1) 의 메서드와 같은 역할.
     *    parentGroup은 부모 스레드로써 클라이언트 요청을 수락하는 역할. childGroup은 자식 스레드로써 IO와 이벤트 처리를 담당.
     */

    /**
     * channel 메서드 : 채널 클래스들로 소켓 모드를 설정
     *
     * 1) NioServerSocketChannel : 논블로킹 모드의 서버 소켓 채널을 생성
     * 2) LocalServerChannel : 하나의 자바 가상 머신에서 가상 통신을 위한 서버 소켓 채널을 생성
     * 3) OioServerSocketChannel : 블로킹 모드의 서버 소켓 채널을 생성
     * 4) EpollServerSocketChannel : 리눅스 커널의 epoll 입출력 모드를 치언하는 서버 소켓 채널을 생성
     *                               (epoll은 리눅스에서만 사용할 수 있는 가장 빠른 입출력 처리 방식 중 하나)
     * 5) OioSctpServerChannel : SCTP 전송 계층을 사용하는 블로킹 모드의 서버 소켓 채널을 생성
     *                           SCTP는 스트림 제어 전송 프로토콜을 의미하며, TCP와 UDP의 특성을 같이 가지고 있고, TCP의 보안 문제를 해결
     * 6) NioSctpServerChannel : SCTP 전송 계층을 사용하는 논블로킹 모드의 서버 소켓 채널을 생성
     * 7) NioUdtByteAcceptorChannel : UDT를 지원하는 논블로킹 모드의 서버 소켓 채널을 생성
     *                                UDT는 UDP-based Data Transfer protocol 이라는 뜻으로 UDP를 기반으로 하지만,
     *                                데이터 전송의 신뢰성을 보장할 수 있는 대용량 데이터 전송 프로토콜이다
     * 8) NioUdtMessageAcceptorChannel : 프로토콜을 지원하는 블로킹 모드의 서버 소켓 채널을 생성
     */

    serverBootstrap.group(bossGroup, workerGroup)
      .channel(NioServerSocketChannel.class) // NIO 전송채널을 이용하도록 지정
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
