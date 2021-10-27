package com.syenty.example.common.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

@ChannelHandler.Sharable
public class NettySocketServerHandler extends ChannelInboundHandlerAdapter {

  @Override
  public void channelRead(final ChannelHandlerContext ctx,
                          final Object msg) {
    String readMessage = ((ByteBuf) msg).toString(Charset.forName("UTF8"));
    ctx.write(msg);
    System.out.println("message from received: " + readMessage);
  }

  @Override
  public void exceptionCaught(final ChannelHandlerContext ctx,
                              final Throwable cause) {
    cause.printStackTrace();
    ctx.close();
  }

}
