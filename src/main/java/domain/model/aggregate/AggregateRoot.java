package domain.model.aggregate;

import domain.model.DomainEvent;
import domain.model.DomainEventHolder;
import domain.model.aggregate.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AggregateRoot extends Entity implements DomainEventHolder {
    private final List<DomainEvent> domainEvents;

    public AggregateRoot(String name) {
        super(name);
        domainEvents = new ArrayList<DomainEvent>();
    }

    public AggregateRoot(String name, String id) {
        super(name, id);
        domainEvents = new ArrayList<DomainEvent>();
    }

    public void addDomainEvent(DomainEvent event){
        domainEvents.add(event);
    }

    public List<DomainEvent> getDomainEvents(){
        return Collections.unmodifiableList(domainEvents);
    }

    public void clearDomainEvents(){
        domainEvents.clear();
    }
}
