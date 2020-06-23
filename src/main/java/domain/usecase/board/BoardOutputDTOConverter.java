package domain.usecase.board;

import domain.model.aggregate.board.Board;

public class BoardOutputDTOConverter {
    public static BoardOutputDTO toDTO(Board board){
        BoardOutputDTO boardOutputDTO = new BoardOutputDTO();
        boardOutputDTO.setId(board.getId());
        boardOutputDTO.setName(board.getName());
        boardOutputDTO.setWorkflows(board.getWorkflowList());
        boardOutputDTO.setUsername(board.getUserId());
        return boardOutputDTO;
    }
}
