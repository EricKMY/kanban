package domain.usecase.board;

import domain.model.board.Board;

public class BoardRepositoryDTOConverter {
    public static BoardRepositoryDTO toDTO(Board board){
        BoardRepositoryDTO boardRepositoryDTO = new BoardRepositoryDTO();
        boardRepositoryDTO.setId(board.getId());
        boardRepositoryDTO.setName(board.getName());
        boardRepositoryDTO.setWorkflows(board.getWorkflowList());
        boardRepositoryDTO.setUsername(board.getUserName());
        return boardRepositoryDTO;
    }

    public static Board toEntity(BoardRepositoryDTO boardRepositoryDTO){
        Board board = new Board(boardRepositoryDTO.getName(), boardRepositoryDTO.getUsername(), boardRepositoryDTO.getId(), boardRepositoryDTO.getWorkflows());
        return board;
    }
}
