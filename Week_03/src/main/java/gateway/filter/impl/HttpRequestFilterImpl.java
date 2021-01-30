package gateway.filter.impl;

import gateway.filter.HttpRequestFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class HttpRequestFilterImpl implements HttpRequestFilter {

    private static final String ACCESS_ID = "accessId";

    private static final String accessId = "foo";

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        fullRequest.headers().set(ACCESS_ID, accessId);
    }
}
