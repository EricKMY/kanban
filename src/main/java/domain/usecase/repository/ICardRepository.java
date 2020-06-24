package domain.usecase.repository;

import domain.adapter.repository.card.dto.CardRepositoryDTO;

public interface ICardRepository {
    void save(CardRepositoryDTO cardRepositoryDTO);
    CardRepositoryDTO findById(String id);
}
