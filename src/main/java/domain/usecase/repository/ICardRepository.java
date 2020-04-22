package domain.usecase.repository;

import domain.model.card.Card;

import java.sql.Connection;

public interface ICardRepository {
    Connection getConnection();
    void save(Card card);
    Card findById(String id);
}
