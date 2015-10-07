package rxrunner.middleware;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import rx.Subscriber;
import rxrunner.bus.EmittedValue;

public class ResponseSubscriber <T extends EmittedValue> extends Subscriber<T> {
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
        HttpServerResponse<ByteBuf> response = o.getMessage().getContext().getResponse();
        o
                .getMessage()
                .getEndpointResponse()
                .map(data -> response.writeStringAndFlush(data.toString()))
//                .onErrorResumeNext(throwable -> {
//                    Context context = o.getMessage().getContext();
//                    MessageBus eventDispatcher = o.getPipelineEventDispatcher();
//
//                    eventDispatcher.dispatch(
//                            new Message(
//                                    name(),
//                                    context,
//                                    handle(context, o.getMessage().getEndpointResponse())
//                            )
//                    )
//                })
        .subscribe()
        ;
    }
}
