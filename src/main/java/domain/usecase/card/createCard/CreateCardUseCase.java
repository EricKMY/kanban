//package domain.usecase.card.createCard;
//
//import domain.adapter.card.CardRepository;
//import domain.model.card.Card;
//
//public class CreateCardUseCase {
//    private CardRepository cardRepository;
//    private String workflowId;
//
//    public CreateCardUseCase(CardRepository cardRepository, String workflowId) {
//        this.cardRepository = cardRepository;
//        this.workflowId = workflowId;
//    }
//
//    public void execute(CreateCardInput input, CreateCardOutput output) {
//        Card card = new Card(input.getCardName(), workflowId);
//        cardRepository.save(card);
//
//        output.setCardId(card.getCardId());
//    }
//}
