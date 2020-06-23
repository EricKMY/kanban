package domain.model.aggregate.card;

import domain.model.aggregate.card.event.CardCreated;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CardTest {

    @Test
    public void create_a_Card_should_generate_a_CardCreated_event() {
        Card card = new Card("firstEvent", "1", "2");
        assertEquals(1, card.getDomainEvents().size());
        assertEquals(CardCreated.class, card.getDomainEvents().get(0).getClass());

        CardCreated cardCreated = (CardCreated)card.getDomainEvents().get(0);

        assertEquals(card.getId(), cardCreated.getCardId());
        assertEquals("1", cardCreated.getLaneId());
        assertEquals("2", cardCreated.getWorkflowId());
        assertEquals("firstEvent", cardCreated.getCardName());
        assertEquals("Card Created: firstEvent", cardCreated.getDetail());
    }
}
