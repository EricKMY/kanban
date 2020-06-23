package domain.adapter.repository.board.converter;

import domain.adapter.repository.board.dto.BoardRepositoryDTO;
import domain.model.aggregate.board.Board;

public class BoardRepositoryDTOConverter {
    public static BoardRepositoryDTO toDTO(Board board){
        BoardRepositoryDTO boardRepositoryDTO = new BoardRepositoryDTO();
        boardRepositoryDTO.setId(board.getId());
        boardRepositoryDTO.setName(board.getName());
        boardRepositoryDTO.setWorkflows(board.getWorkflowList());
        boardRepositoryDTO.setUserId(board.getUserId());
        return boardRepositoryDTO;
    }

    public static Board toEntity(BoardRepositoryDTO boardRepositoryDTO){
        Board board = new Board(boardRepositoryDTO.getName(), boardRepositoryDTO.getUserId(), boardRepositoryDTO.getId(), boardRepositoryDTO.getWorkflows());
        return board;
    }
}
