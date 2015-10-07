package rxrunner.bus;

import rx.Observable;
import rxrunner.Context;

public class Message<T> {
    private String name;

    private Context context;

    private Observable<T> endpointResponse;

    public Message(String name, Context context, Observable<T> endpointResponse) {
        this.name = name;
        this.context = context;
        this.endpointResponse = endpointResponse;
    }

    public String getName() {
        return name;
    }

    public Context getContext() {
        return context;
    }

    public Observable<T> getEndpointResponse() {
        return endpointResponse;
    }
}
