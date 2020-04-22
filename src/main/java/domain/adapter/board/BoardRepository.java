package domain.adapter.board;

import domain.model.board.Board;
import domain.usecase.repository.IBoardRepository;

import java.util.HashMap;
import java.util.Map;

public class BoardRepository implements IBoardRepository {
    Map<String, Board> map = new HashMap<String, Board>();

    public void save(Board board) {
        map.put(board.getBoardId(), board);
    }

    public Board findById(String boardId) {
        return map.get(boardId);
    }
}
