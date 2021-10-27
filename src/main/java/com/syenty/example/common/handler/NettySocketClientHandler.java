package com.syenty.example.common.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

public class NettySocketClientHandler extends ChannelInboundHandlerAdapter {

  private String msg;

  public NettySocketClientHandler(final String msg) {
    this.msg = msg;
  }

  @Override
  public void channelActive(final ChannelHandlerContext ctx) {
    ByteBuf messageBuffer = Unpooled.buffer();
    messageBuffer.writeBytes(msg.getBytes());

    ctx.writeAndFlush(messageBuffer);

    System.out.println("send message {" + msg + "}");
  }

  @Override
  public void channelRead(final ChannelHandlerContext ctx,
                          final Object msg) {
    System.out.println("receive message {" + ((ByteBuf) msg).toString(Charset.defaultCharset()) +"}");
  }

  @Override
  public void channelReadComplete(final ChannelHandlerContext ctx) {
    ctx.close();
  }

  @Override
  public void exceptionCaught(final ChannelHandlerContext ctx,
                              final Throwable cause) {
    System.out.println(cause);
    ctx.close();
  }

}
