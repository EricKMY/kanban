package domain.model;

import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

public class Stage {
    private String stageName;
    private String stageId;
    private Map<String, Card> cardMap = new HashMap<String, Card>();

    public Stage(String stageName) {
        this.stageName = stageName;
        stageId = "S" + UUID.randomUUID().toString();
    }

    public String getStageId() {
        return stageId;
    }

    public String createCard(String cardName) {
        Card card = new Card(cardName);
        cardMap.put(card.getCardId(), card);
        return card.getCardId();
    }
}
