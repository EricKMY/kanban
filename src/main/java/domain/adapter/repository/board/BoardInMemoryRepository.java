package domain.adapter.repository.board;

import domain.adapter.repository.board.dto.BoardRepositoryDTO;
import domain.usecase.repository.IBoardRepository;

import java.util.HashMap;
import java.util.Map;

public class BoardInMemoryRepository implements IBoardRepository {
    Map<String, BoardRepositoryDTO> boardDTOMap = new HashMap<String, BoardRepositoryDTO>();


    @Override
    public void save(BoardRepositoryDTO boardRepositoryDTO) {
        boardDTOMap.put(boardRepositoryDTO.getId(), boardRepositoryDTO);
    }

    public BoardRepositoryDTO findById(String boardId) {
        return boardDTOMap.get(boardId);
    }
}
