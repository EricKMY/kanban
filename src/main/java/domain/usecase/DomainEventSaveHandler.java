package domain.usecase;

import com.google.common.eventbus.Subscribe;
import domain.model.DomainEvent;
import domain.usecase.domainEvent.repository.IDomainEventRepository;

public class DomainEventSaveHandler {

    private IDomainEventRepository domainEventRepository;

    public DomainEventSaveHandler(IDomainEventRepository domainEventRepository) {
        this.domainEventRepository = domainEventRepository;

    }

    @Subscribe
    public void handleEvent(DomainEvent domainEvent) {
        domainEventRepository.save(domainEvent);
    }

}
