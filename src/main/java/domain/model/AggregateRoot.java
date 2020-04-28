package domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AggregateRoot extends Entity {
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
