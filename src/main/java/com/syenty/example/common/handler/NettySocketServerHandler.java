package com.syenty.example.common.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

@ChannelHandler.Sharable
public class NettySocketServerHandler extends ChannelInboundHandlerAdapter {

  @Override
  public void channelRead(final ChannelHandlerContext ctx,
                          final Object msg) throws Exception {
    String readMessage = ((ByteBuf) msg).toString(CharsetUtil.UTF_8);
    ctx.write(msg);
    System.out.println("Server received: " + readMessage);
  }

  @Override
  public void channelReadComplete(final ChannelHandlerContext ctx) throws Exception {
    System.out.println("Bye");
    ctx.writeAndFlush(Unpooled.EMPTY_BUFFER) // 대기중인 메시지를 플러시하고 채널을 닫음
      .addListener(ChannelFutureListener.CLOSE);
  }

  @Override
  public void exceptionCaught(final ChannelHandlerContext ctx,
                              final Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }

}
