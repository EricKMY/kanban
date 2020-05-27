package domain.adapter.card;

import domain.usecase.card.CardDTO;
import domain.usecase.repository.ICardRepository;

import java.util.HashMap;
import java.util.Map;

public class CardInMemoryRepository implements ICardRepository {
    Map<String, CardDTO> cardDTOMap = new HashMap<String, CardDTO>();

    public void save(CardDTO cardDTO) {
        cardDTOMap.put(cardDTO.getId(), cardDTO);
    }

    public CardDTO findById(String cardId) {
        return cardDTOMap.get(cardId);
    }
}