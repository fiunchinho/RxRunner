package rxrunner.middleware;

import rx.Observable;
import rxrunner.Context;

public class ContentNegotiationSubscriber extends Middleware {
    @Override
    protected String name() {
        return "ContentNegotiator";
    }

    @Override
    public Observable<Object> handle(Context context, Observable endpointResponse) {
        context.getResponse().getHeaders().setHeader("Content-Type", "application/json");
        return endpointResponse;
    }
}
