package com.joker.correspond.handler;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.alibaba.fastjson.JSONObject;
import com.joker.command.FuseCommand;
import com.joker.entity.User;
import com.joker.request.RequestFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.AsciiString;

public class FuseHandler extends ChannelInboundHandlerAdapter {
	private static final AsciiString CONTENT_TYPE = new AsciiString("Content-Type");
	private static final AsciiString CONTENT_LENGTH = new AsciiString("Content-Length");
	private static final AsciiString CONNECTION = new AsciiString("Connection");
	private static final AsciiString KEEP_ALIVE = new AsciiString("keep-alive");

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof FullHttpRequest) {
			@SuppressWarnings("unused")
			ByteBuf jsonBuf = ((FullHttpRequest)msg).content();
			FullHttpRequest req = (FullHttpRequest) msg;// 客户端的请求对象
			@SuppressWarnings("rawtypes")
			FuseCommand command = RequestFactory.requestToCommand(req);
			Object object = AsyMethod(command);
//			Object object = SynMethod(command);
			JSONObject responseJson = new JSONObject();
			responseJson.put("data", object);
			ResponseJson(ctx, req, responseJson.toString());

		}
	}
	
	private Object AsyMethod(FuseCommand command) throws InterruptedException, ExecutionException {
		Future<Object> future = command.queue();
		Object object = future.get();
		return object;
	}
	
	private Object SynMethod(FuseCommand command) throws InterruptedException, ExecutionException {
		return command.execute();
	}
	
	private void ResponseJson(ChannelHandlerContext ctx, FullHttpRequest req ,String jsonStr) {
		boolean keepAlive = HttpUtil.isKeepAlive(req);
		byte[] jsonByteByte = jsonStr.getBytes();
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(jsonByteByte));
		response.headers().set(CONTENT_TYPE, "text/json");
		response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());

		if (!keepAlive) {
			ctx.write(response).addListener(ChannelFutureListener.CLOSE);
		} else {
			response.headers().set(CONNECTION, KEEP_ALIVE);
			ctx.write(response);
		}
	}
	
	

}
