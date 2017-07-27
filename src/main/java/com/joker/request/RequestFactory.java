package com.joker.request;

import com.alibaba.fastjson.JSONObject;
import com.joker.command.FuseCommand;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.CharsetUtil;

public final class RequestFactory {
	public static FuseCommand requestToCommand(FullHttpRequest request) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		ByteBuf jsonBuf = request.content();
		JSONObject requestJSON = JSONObject.parseObject(jsonBuf.toString(CharsetUtil.UTF_8));
		String entityName = requestJSON.getString("entity");
		FuseCommand command = (FuseCommand) Class.forName("com.joker.command."+entityName).newInstance();
		return command;
	}
}
