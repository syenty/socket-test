package com.syenty.example.common.listener;

import com.syenty.example.common.socket.NettySocketServer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.stereotype.Component;
import org.wildfly.common.annotation.NotNull;

@Component
@RequiredArgsConstructor
public class SocketLoaderListener implements ApplicationListener<ApplicationContextEvent> {

  private final NettySocketServer nettySocketServer;

  @Override
  public void onApplicationEvent(@NotNull final ApplicationContextEvent event) {
    nettySocketServer.start();
  }

}
