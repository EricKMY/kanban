package domain.adapter.card;

import domain.model.card.Card;
import domain.usecase.card.CardDTO;
import domain.usecase.repository.ICardRepository;

import java.util.HashMap;
import java.util.Map;

public class CardRepository implements ICardRepository {
    Map<String, CardDTO> cardDTOMap = new HashMap<String, CardDTO>();

    public void save(CardDTO cardDTO) {
        cardDTOMap.put(cardDTO.getId(), cardDTO);
    }

    public CardDTO findById(String cardId) {
        return cardDTOMap.get(cardId);
    }
}