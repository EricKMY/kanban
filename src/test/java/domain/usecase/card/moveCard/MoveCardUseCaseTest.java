//package domain.usecase.card.moveCard;
//
//import domain.adapter.card.CardRepository;
//import domain.model.card.Card;
//import domain.usecase.card.createCard.CreateCardInput;
//import domain.usecase.card.createCard.CreateCardOutput;
//import domain.usecase.card.createCard.CreateCardUseCase;
//import domain.usecase.card.editBlocker.EditBlockerInput;
//import domain.usecase.card.editBlocker.EditBlockerOutput;
//import domain.usecase.card.editBlocker.EditBlockerUseCase;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//public class MoveCardUseCaseTest {
//    private CardRepository cardRepository;
//    private Card card;
//
//    @Before
//    public void setup() {
//        cardRepository = new CardRepository();
//        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository);
//        CreateCardInput input = new CreateCardInput();
//        CreateCardOutput output = new CreateCardOutput();
//
//        input.setCardName("Design domain model");
//
//        createCardUseCase.execute(input, output);
//        String cardId = output.getCardId();
//
//        card = cardRepository.findById(cardId);
//        assertNotNull(card);
//    }
//
//    @Test
//    public void moveCardWithoutBlocker() {
//    }
//
//    @Test
//    public void moveCardWithBlocker() {
//    }
//}
