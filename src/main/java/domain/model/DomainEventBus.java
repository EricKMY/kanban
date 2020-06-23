package domain.model;

import com.google.common.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class DomainEventBus extends EventBus {

    public DomainEventBus(){
        super();
    }

    public void postAll(DomainEventHolder domainEventHolder){
        List<DomainEvent> events =
                new ArrayList(domainEventHolder.getDomainEvents());
        domainEventHolder.clearDomainEvents();

        for(DomainEvent each : events){
            post(each);
        }
        events.clear();
    }
}
