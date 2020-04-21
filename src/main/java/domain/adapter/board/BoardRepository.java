package domain.adapter.board;

import domain.model.board.Board;
import domain.model.workflow.Workflow;

import java.util.HashMap;
import java.util.Map;

public class BoardRepository {
    Map<String, Board> map = new HashMap<String, Board>();

    public void save(Board board) {
        map.put(board.getBoardId(), board);
    }

    public Board findById(String boardId) {
        return map.get(boardId);
    }

}
