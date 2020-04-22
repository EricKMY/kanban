package domain.usecase.repository;

import domain.adapter.board.BoardInMemoryRepository;
import domain.model.board.Board;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoardInMemoryRepositoryTest {

    @Test
    public void save() {
        Board board = new Board("Kanban_Project", "Tina");
        IBoardRepository boardRepository = new BoardInMemoryRepository();

        boardRepository.save(board);
        Board returnBoard = boardRepository.findById(board.getBoardId());

        assertEquals(board.getBoardId(), returnBoard.getBoardId());
        assertEquals(board.getBoardName(), returnBoard.getBoardName());
        assertEquals(board.getUsername(), returnBoard.getUsername());
    }
}
