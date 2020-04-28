package domain.adapter.board;

import domain.adapter.database.IDatabase;
import domain.model.board.Board;
import domain.usecase.repository.IBoardRepository;

import java.util.Map;

public class BoardInDatabaseRepository implements IBoardRepository {

    private IDatabase database;

    public BoardInDatabaseRepository(IDatabase database) {
        this.database = database;
        database.createTable("board");
    }

    public void save(Board board) {
        database.save(convertFormat(board));
    }

    public Board findById(String boardId) {
        Map<String, String> result = database.findById(boardId);
        Board board = getInstance(result);
        return board;
    }

    private String[] convertFormat(Board board) {
        String attribute[] = new String[3];
        attribute[0] = board.getId();
        attribute[1] = board.getName();
        attribute[2] = board.getUsername();
        return attribute;
    }

    private Board getInstance(Map<String, String> result) {
        String boardId = result.get("boardId");
        String boardName = result.get("boardName");
        String username = result.get("userName");
        Board board = new Board(boardName, username, boardId);

        return board;
    }
}