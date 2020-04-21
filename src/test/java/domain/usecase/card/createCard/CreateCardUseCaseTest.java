package domain.usecase.card.createCard;

import domain.adapter.card.CardRepository;
import domain.model.workflow.Stage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateCardUseCaseTest {
    @Test
    public void createCard() {
        CardRepository cardRepository = new CardRepository();
        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository);
        CreateCardInput input = new CreateCardInput();
        CreateCardOutput output = new CreateCardOutput();

        input.setCardName("firstEvent");

        createCardUseCase.execute(input, output);
        assertEquals('C', cardRepository.findById(output.getCardId()).getCardId().charAt(0));
    }
}
