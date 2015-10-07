package rxrunner;

import com.google.inject.Guice;
import io.reactivex.netty.RxNetty;

public final class Server {

    public static final int PORT = 8090;

    public static void main(final String[] args) {
        RxNetty
                .createHttpServer(PORT, Guice.createInjector().getInstance(EventRestBasedRouter.class))
                .startAndWait();
    }
}
