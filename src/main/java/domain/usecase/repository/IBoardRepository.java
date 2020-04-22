package domain.usecase.repository;

import domain.model.board.Board;

public interface IBoardRepository {
    void save(Board board);
    Board findById(String id);
}
