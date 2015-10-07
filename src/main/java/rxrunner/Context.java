package rxrunner;

import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;

public class Context {
    private HttpServerRequest request;
    private HttpServerResponse response;

    public Context(HttpServerRequest request, HttpServerResponse response) {
        this.request = request;
        this.response = response;
    }

    public HttpServerRequest getRequest() {
        return request;
    }

    public HttpServerResponse getResponse() {
        return response;
    }
}
