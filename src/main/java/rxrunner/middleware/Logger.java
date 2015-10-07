package rxrunner.middleware;

import io.netty.handler.codec.http.HttpHeaders;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rxrunner.Context;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger extends Middleware {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Override
    protected String name() {
        return "Logger";
    }

    @Override
    public Observable<Object> handle(Context context, Observable endpointResponse) {
        HttpServerRequest request = context.getRequest();
        HttpServerResponse response = context.getResponse();
        System.out.println(context.getRequest().getNettyChannel().remoteAddress());
        InetSocketAddress socketAddress = (InetSocketAddress) context.getRequest().getNettyChannel().remoteAddress();
        LOGGER.info(
                socketAddress.getAddress().getHostAddress()
                        + " "
                        + "["
                        + new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss.SSS Z").format(new Date())
                        + "]"
                        + " "
                        + '"'
                        + request.getHttpMethod().toString()
                        + " "
                        + request.getUri()
                        + " "
                        + request.getHttpVersion().toString()
                        + '"'
                        + " "
                        + response.getStatus().code()
                        + " "
                        + '"'
                        + request.getHeaders().getHeader(HttpHeaders.Names.REFERER)
                        + '"'
                        + " "
                        + '"'
                        + request.getHeaders().getHeader(HttpHeaders.Names.USER_AGENT)
                        + '"'
        );
        return endpointResponse;
    }
}
