package rxrunner.bus;

public class EmittedValue {

    private Message message;
    private MessageBus pipelineEventDispatcher;

    public EmittedValue(Message message, MessageBus pipelineEventDispatcher) {
        this.message = message;
        this.pipelineEventDispatcher = pipelineEventDispatcher;
    }

    public Message getMessage() {
        return message;
    }

    public MessageBus getPipelineEventDispatcher() {
        return pipelineEventDispatcher;
    }
}
