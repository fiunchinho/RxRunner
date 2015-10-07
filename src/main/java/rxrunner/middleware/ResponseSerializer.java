package rxrunner.middleware;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import rx.Observable;
import rxrunner.Context;

public class ResponseSerializer<E> extends Middleware {
    private Gson gson;

    public ResponseSerializer() {
        this.gson = new GsonBuilder().create();
    }

    @Override
    protected String name() {
        return "ResponseSerializer";
    }

    @Override
    public Observable<Object> handle(Context context, Observable endpointResponse) {
        return endpointResponse.map(data -> gson.toJson(data));
    }
}
