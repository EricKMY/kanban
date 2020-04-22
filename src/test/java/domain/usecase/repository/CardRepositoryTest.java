package domain.usecase.repository;

import domain.adapter.card.CardRepository;
import domain.model.card.Card;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CardRepositoryTest {

    @Test
    public void getConnection() {
        CardRepository cardRepository = new CardRepository();
        assertNotEquals(null, cardRepository.getConnection());
    }

    @Test
    public void save() {
        Card card = new Card("SA_hw1");
        CardRepository cardRepository = new CardRepository();

        cardRepository.save(card);
        Card returenCard = cardRepository.findById(card.getCardId());

        assertEquals(card.getCardId(), returenCard.getCardId());
        assertEquals(card.getCardName(), returenCard.getCardName());
        assertEquals(card.getBlocker(), returenCard.getBlocker());
    }
}
