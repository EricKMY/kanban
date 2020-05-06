package domain.adapter.board;

import domain.model.board.Board;
import domain.usecase.board.BoardDTO;
import domain.usecase.repository.IBoardRepository;

import java.util.HashMap;
import java.util.Map;

public class BoardInMemoryRepository implements IBoardRepository {
    Map<String, BoardDTO> boardDTOMap = new HashMap<String, BoardDTO>();


    @Override
    public void save(BoardDTO boardDTO) {
        boardDTOMap.put(boardDTO.getId(), boardDTO);
    }

    public BoardDTO findById(String boardId) {
        return boardDTOMap.get(boardId);
    }
}
