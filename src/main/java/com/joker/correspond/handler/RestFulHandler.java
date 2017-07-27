package com.joker.correspond.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;

public class RestFulHandler extends ChannelInitializer<SocketChannel> {
	private final SslContext sslCtx;

    public RestFulHandler(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
        if (sslCtx != null) {
            p.addLast(sslCtx.newHandler(ch.alloc()));
        }
        p.addLast(new HttpServerCodec());/*HTTP 服务的解码器*/
        p.addLast(new HttpObjectAggregator(2048));/*HTTP 消息的合并处理*/
        p.addLast(new FuseHandler()); /*自己写的服务器逻辑处理*/
	}

}
