package domain.adapter.repository.domainEvent;

import domain.model.DomainEvent;
import domain.usecase.domainEvent.repository.IDomainEventRepository;

import java.util.ArrayList;
import java.util.List;

public class DomainEventInMemoryRepository implements IDomainEventRepository {
    List<DomainEvent> domainEvents;

    public DomainEventInMemoryRepository() {
        this.domainEvents = new ArrayList<>();
    }

    @Override
    public void save(DomainEvent domainEvent) {
        domainEvents.add(domainEvent);
    }

    @Override
    public List<DomainEvent> getAll() {
        return domainEvents;
    }
}
