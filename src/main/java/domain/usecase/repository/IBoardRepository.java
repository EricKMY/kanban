package domain.usecase.repository;

import domain.usecase.board.BoardRepositoryDTO;

public interface IBoardRepository {
    void save(BoardRepositoryDTO boardRepositoryDTO);
    BoardRepositoryDTO findById(String id);
}
