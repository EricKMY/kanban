package domain.usecase.repository;

import domain.adapter.board.BoardInDatabaseRepository;
import domain.adapter.database.IDatabase;
import domain.database.MySqlDatabase;
import domain.model.board.Board;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoardInDatabaseRepositoryTest {

    @Test
    public void save() {
        Board board = new Board("Kanban_Project", "Tina");
        IDatabase database = new MySqlDatabase();
        IBoardRepository boardRepository = new BoardInDatabaseRepository(database);

        boardRepository.save(board);

        Board returnBoard = boardRepository.findById(board.getId());

        assertEquals(board.getId(), returnBoard.getId());
        assertEquals(board.getName(), returnBoard.getName());
        assertEquals(board.getUsername(), returnBoard.getUsername());
    }
}
