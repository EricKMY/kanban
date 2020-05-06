package domain.usecase.repository;

import domain.model.board.Board;
import domain.usecase.board.createBoard.BoardDTO;

public interface IBoardRepository {
    void save(BoardDTO boardDTO);
    BoardDTO findById(String id);
}
