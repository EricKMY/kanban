package domain.usecase;

import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;

public class DomainExceptionHandler implements SubscriberExceptionHandler {

    @Override
    public void handleException(Throwable throwable, SubscriberExceptionContext subscriberExceptionContext) {
        if( throwable instanceof RuntimeException ){
            throw (RuntimeException) throwable;
        }
    }
}
