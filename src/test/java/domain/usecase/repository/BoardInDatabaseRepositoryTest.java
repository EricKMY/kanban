package domain.usecase.repository;

import domain.adapter.board.BoardInDatabaseRepository;
import domain.model.board.Board;

import domain.usecase.board.BoardDTOConverter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoardInDatabaseRepositoryTest {

    @Test
    public void new_a_Board_and_stored_in_database() {
        Board board = new Board("Kanban_Project", "Tina");
        IBoardRepository boardRepository = new BoardInDatabaseRepository();

        boardRepository.save(BoardDTOConverter.toDTO(board));

        Board returnBoard = BoardDTOConverter.toEntity(boardRepository.findById(board.getId()));

        assertEquals(board.getId(), returnBoard.getId());
        assertEquals(board.getName(), returnBoard.getName());
        assertEquals(board.getUsername(), returnBoard.getUsername());
    }
}
