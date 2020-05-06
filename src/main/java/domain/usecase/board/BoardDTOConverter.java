package domain.usecase.board;

import domain.model.board.Board;

public class BoardDTOConverter {
    public static BoardDTO toDTO(Board board){
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(board.getId());
        boardDTO.setName(board.getName());
        boardDTO.setWorkflows(board.getWorkflows());
        boardDTO.setUsername(board.getUsername());
        return boardDTO;
    }

    public static Board toEntity(BoardDTO boardDTO){
        Board board = new Board(boardDTO.getName(), boardDTO.getUsername(), boardDTO.getId(), boardDTO.getWorkflows());
        return board;
    }
}
