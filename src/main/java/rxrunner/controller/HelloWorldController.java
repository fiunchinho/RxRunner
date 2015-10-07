package rxrunner.controller;

import com.google.inject.Inject;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import rx.Observable;
import rxrunner.domain.User;
import scmspain.karyon.restrouter.annotation.Endpoint;
import scmspain.karyon.restrouter.annotation.Path;

import javax.ws.rs.HttpMethod;

@Endpoint
public class HelloWorldController {

    @Path(value = "/hello", method = HttpMethod.GET)
    public Observable<Object> hello(HttpServerResponse<ByteBuf> response) {
        return Observable.just(new User("jose", 31));
    }
}
