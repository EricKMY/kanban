//package domain.usecase.card.editBlocker;
//
//import domain.adapter.card.CardRepository;
//import domain.model.card.Card;
//import domain.usecase.card.createCard.CreateCardInput;
//import domain.usecase.card.createCard.CreateCardOutput;
//import domain.usecase.card.createCard.CreateCardUseCase;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//public class EditBlockerUseCaseTest {
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
//    public void editBlocker() {
//        EditBlockerUseCase editBlockerUseCase = new EditBlockerUseCase(cardRepository);
//        EditBlockerInput input = new EditBlockerInput();
//        EditBlockerOutput output = new EditBlockerOutput();
//
//        input.setCardId(card.getCardId());
//        input.setBlocker("Wait for hardware support.");
//
//        editBlockerUseCase.execute(input, output);
//        assertEquals("Wait for hardware support.", cardRepository.findById(card.getCardId()).getBlocker());
//    }
//}
