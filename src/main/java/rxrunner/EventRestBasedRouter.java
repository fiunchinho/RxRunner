package rxrunner;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import rx.Observable;
import rxrunner.bus.Message;
import rxrunner.bus.MessageBus;
import rxrunner.middleware.*;
import scmspain.karyon.restrouter.RestBasedRouter;
import scmspain.karyon.restrouter.core.MethodParameterResolver;
import scmspain.karyon.restrouter.core.ResourceLoader;
import scmspain.karyon.restrouter.core.URIParameterParser;

public class EventRestBasedRouter extends RestBasedRouter {
    MessageBus messageBus;

    @Inject
    public EventRestBasedRouter(Injector inject, URIParameterParser parameterParser, MethodParameterResolver rmParameterInjector, ResourceLoader resourceLoader) {
        super(inject, parameterParser, rmParameterInjector, resourceLoader);
    }

    @Override
    public Observable<Void> handle(HttpServerRequest<ByteBuf> request, HttpServerResponse<ByteBuf> response) {
        createMessageBus(request, response);
        // Subscribers to filter or validate requests are executed first. If they fail, an error will be emitted
        messageBus.dispatch(
                new Message(
                        "Pipeline Pre for filters",
                        new Context(request, response),
                        Observable.empty()
                        )
        );

        // We execute the endpoint
//        Observable<Void> endpointResponse = super.handle(request, response);

        // Subscribers to transform the response or adding headers will be executed afterwards
//        messageBus.dispatch(
//                new Message(
//                        "Pipeline Post for transformations",
//                        new Context(request, response),
//                        endpointResponse
//                )
//        );
        return Observable.empty();
    }

    private void createMessageBus(HttpServerRequest<ByteBuf> request, HttpServerResponse<ByteBuf> response) {
        messageBus = new MessageBus();
        messageBus.addSubscriber(new ContentNegotiationSubscriber());
        messageBus.addSubscriber(Guice.createInjector().getInstance(Router.class));
        messageBus.addSubscriber(new Logger());
//        messageBus.addSubscriber(new ErrorEmitter());
        messageBus.addSubscriber(new ResponseSerializer());
        messageBus.addSubscriber(new ResponseSubscriber());
    }
}
