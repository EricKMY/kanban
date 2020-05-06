package domain.usecase.repository;

import domain.adapter.board.BoardInDatabaseRepository;
import domain.model.board.Board;

import domain.usecase.board.BoardDTOConverter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BoardInDatabaseRepositoryTest {

    @Test
    public void save_a_new_board() {
        Board board = new Board("Kanban_Project", "Tina");
        IBoardRepository boardRepository = new BoardInDatabaseRepository();

        boardRepository.save(BoardDTOConverter.toDTO(board));

        Board returnBoard = BoardDTOConverter.toEntity(boardRepository.findById(board.getId()));

        assertEquals(board.getId(), returnBoard.getId());
        assertEquals(board.getName(), returnBoard.getName());
        assertEquals(board.getUsername(), returnBoard.getUsername());
    }
}
