package domain.model;

import java.util.List;

public interface DomainEventHolder {

    public void addDomainEvent(DomainEvent event);

    public List<DomainEvent> getDomainEvents();

    public void clearDomainEvents();
}
