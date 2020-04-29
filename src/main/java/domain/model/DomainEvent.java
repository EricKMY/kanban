package domain.model;

import java.util.Date;

public interface DomainEvent {
    public Date getOccurredOn();
    public String getId();
}
