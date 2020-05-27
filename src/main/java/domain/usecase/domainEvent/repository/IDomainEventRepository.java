package domain.usecase.domainEvent.repository;

import domain.model.DomainEvent;

import java.util.List;

public interface IDomainEventRepository {
    void save(DomainEvent domainEvent);

    List<DomainEvent> getAll();
}
