package domain.usecase.repository;

import domain.usecase.card.CardDTO;

public interface ICardRepository {
    void save(CardDTO card);
    CardDTO findById(String id);
}
