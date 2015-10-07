package rxrunner.middleware;

import rx.Observable;
import rx.Subscriber;
import rxrunner.Context;
import rxrunner.bus.EmittedValue;
import rxrunner.bus.Message;
import rxrunner.bus.MessageBus;

/**
 * Base class to make it easier to create new Middlewares for the Pipeline.
 * @param <T>
 */
abstract public class Middleware <T extends EmittedValue> extends Subscriber<T> {
    @Override
    public void onCompleted() {
        System.out.println(this.getClass().getName()+" Completed!");
    }

    @Override
    public void onError(Throwable e) {
        System.out.println(e.getMessage());
    }

    @Override
    public void onNext(EmittedValue value) {
        Context context = value.getMessage().getContext();
        MessageBus eventDispatcher = value.getPipelineEventDispatcher();

        eventDispatcher.dispatch(
                new Message(
                        name(),
                        context,
                        handle(context, value.getMessage().getEndpointResponse())
                )
        );
    }

    /**
     * Name of the emitted value. This is not used for the Pipeline.
     *
     * @return
     */
    abstract protected String name();

    /**
     * MessageBus will execute this method for every Middleware so they can transform the response.
     * For example, you could add headers to the Response object inside the context.
     * Or you could transform the endpointResponse, like serializing.
     *
     * @param context
     * @param endpointResponse
     * @return
     */
    abstract protected Observable<Object> handle(Context context, Observable endpointResponse);
}
