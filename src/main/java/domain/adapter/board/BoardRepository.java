package domain.adapter.board;

import domain.model.board.Board;

import java.util.HashMap;
import java.util.Map;

public class BoardRepository {
    Map<String, Board> map = new HashMap<String, Board>();

    public void add(Board board) {
        map.put(board.getBoardId(), board);
    }

    public void save(String boardId, Board board) {
    }

}
