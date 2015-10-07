package rxrunner.middleware;

import com.google.inject.Inject;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import rx.Subscriber;
import rxrunner.Context;
import rxrunner.bus.EmittedValue;
import rxrunner.bus.Message;
import rxrunner.bus.MessageBus;
import scmspain.karyon.restrouter.RestBasedRouter;

public class Router<T extends EmittedValue> extends Subscriber<T> {

    private final RestBasedRouter router;

    @Inject
    public Router(RestBasedRouter router) {
        this.router = router;
    }

    @Override
    public void onCompleted() {
        System.out.println(this.getClass().getName()+" Completed!");
    }

    @Override
    public void onError(Throwable e) {

        System.out.println(e.getMessage());
    }

    @Override
    public void onNext(EmittedValue o) {
        HttpServerRequest<ByteBuf> request = o.getMessage().getContext().getRequest();
        HttpServerResponse<ByteBuf> response = o.getMessage().getContext().getResponse();
        Context context = o.getMessage().getContext();
        MessageBus eventDispatcher = o.getPipelineEventDispatcher();

        eventDispatcher.dispatch(
                new Message(
                        "Router",
                        context,
                        router.handle(request, response)
                )
        );
    }
}
