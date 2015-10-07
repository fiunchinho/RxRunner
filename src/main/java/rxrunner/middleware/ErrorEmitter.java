package rxrunner.middleware;

import rx.Subscriber;
import rxrunner.Context;
import rxrunner.bus.EmittedValue;
import rxrunner.bus.MessageBus;

public class ErrorEmitter <T extends EmittedValue> extends Subscriber<T> {
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
        Context context = o.getMessage().getContext();
        MessageBus eventDispatcher = o.getPipelineEventDispatcher();
        eventDispatcher.fail(new Exception("OMG HE FALLAO!"));
    }
}
