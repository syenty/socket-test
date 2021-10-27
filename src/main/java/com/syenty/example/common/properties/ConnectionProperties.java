package com.syenty.example.common.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application/properties/connection/connection-${spring.profiles.active}.properties")
@Getter
public class ConnectionProperties {

  @Value("${connection.proxy.port}")
  private String connectionProxyPort;

  @Value("${connection.server.port}")
  private String connectionServerPort;

  @Value("${connection.server.ip}")
  private String connectionServerIp;

}
