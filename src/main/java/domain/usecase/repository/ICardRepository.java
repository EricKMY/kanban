package domain.usecase.repository;

import domain.model.card.Card;

public interface ICardRepository {
    void save(Card card);
    Card findById(String id);
}
