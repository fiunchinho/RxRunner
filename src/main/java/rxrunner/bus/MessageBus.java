package rxrunner.bus;

import java.util.ArrayList;
import rx.Subscriber;
import rx.Subscription;
import rx.subjects.PublishSubject;

public class MessageBus {

    private PublishSubject<EmittedValue> subject;
    private ArrayList<Subscriber> subscribers;
    private Subscription subscription;
    private int subscriberIndex = 0;

    public MessageBus() {
        this.subject = PublishSubject.create();
        this.subscribers = new ArrayList();
    }


    public void dispatch(Message message){
        EmittedValue EmittedValue = new EmittedValue(message, this);
        if (subscriberIndex > 0) {
            this.subscription.unsubscribe();
        }

        try {
            Subscriber currentSubscriber = this.subscribers.get(subscriberIndex++);
            this.subscription = this.subject.subscribe(currentSubscriber);
            this.subject.onNext(EmittedValue);
        }catch(IndexOutOfBoundsException e){
            this.subject.onCompleted();
        }
    }

    public void addSubscriber(Subscriber subscriber){
        this.subscribers.add(subscriber);
    }

    public void complete(){
        this.subject.onCompleted();
        this.subscription.unsubscribe();
    }

    public void fail(Throwable e){
        if (subscriberIndex > 0) {
            this.subscription.unsubscribe();
        }

        try {
            Subscriber currentSubscriber = this.subscribers.get(subscriberIndex++);
            this.subscription = this.subject.subscribe(currentSubscriber);
            this.subject.onError(e);
        }catch(IndexOutOfBoundsException ex){
            this.subject.onCompleted();
        }
    }
}
