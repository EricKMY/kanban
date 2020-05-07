package domain.usecase.card;

import domain.model.card.Card;

public class CardDTOConverter {
    public static CardDTO toDTO(Card card){
        CardDTO cardDTO = new CardDTO();
        cardDTO.setId(card.getId());
        cardDTO.setName(card.getName());
        cardDTO.setWorkflowId(card.getWorkflowId());
        cardDTO.setLaneId(card.getLaneId());
        return cardDTO;
    }

    public static Card toEntity(CardDTO cardDTO){
        Card card = new Card(cardDTO.getName(), cardDTO.getId(), cardDTO.getLaneId(), cardDTO.getWorkflowId());
        return card;
    }
}
