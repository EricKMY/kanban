package domain.adapter.repository.card;

import domain.adapter.repository.card.dto.CardRepositoryDTO;
import domain.usecase.repository.ICardRepository;

import java.util.HashMap;
import java.util.Map;

public class CardInMemoryRepository implements ICardRepository {
    Map<String, CardRepositoryDTO> cardDTOMap = new HashMap<String, CardRepositoryDTO>();

    public void save(CardRepositoryDTO cardRepositoryDTO) {
        cardDTOMap.put(cardRepositoryDTO.getId(), cardRepositoryDTO);
    }

    public CardRepositoryDTO findById(String cardId) {
        return cardDTOMap.get(cardId);
    }
}