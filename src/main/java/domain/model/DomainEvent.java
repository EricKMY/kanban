package domain.model;

import java.util.Date;

public interface DomainEvent {
    Date getOccurredOn();
    String getDetail();
}
