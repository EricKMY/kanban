package domain.usecase.board.findBoardById;

import domain.usecase.board.BoardOutputDTO;

public interface FindBoardByIdOutput {
    BoardOutputDTO getBoardOutputDTO();
    void setBoardOutputDTO(BoardOutputDTO boardOutputDTO);
}
