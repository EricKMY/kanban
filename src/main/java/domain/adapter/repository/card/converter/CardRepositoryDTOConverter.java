package domain.adapter.repository.card.converter;

import domain.adapter.repository.card.dto.CardRepositoryDTO;
import domain.model.aggregate.card.Card;

public class CardRepositoryDTOConverter {
    public static CardRepositoryDTO toDTO(Card card){
        CardRepositoryDTO cardRepositoryDTO = new CardRepositoryDTO();
        cardRepositoryDTO.setId(card.getId());
        cardRepositoryDTO.setName(card.getName());
        cardRepositoryDTO.setWorkflowId(card.getWorkflowId());
        cardRepositoryDTO.setLaneId(card.getLaneId());
        return cardRepositoryDTO;
    }

    public static Card toEntity(CardRepositoryDTO cardRepositoryDTO){
        Card card = new Card(cardRepositoryDTO.getName(), cardRepositoryDTO.getId(), cardRepositoryDTO.getLaneId(), cardRepositoryDTO.getWorkflowId());
        return card;
    }
}
