package domain.usecase.repository;

import domain.adapter.repository.board.dto.BoardRepositoryDTO;

public interface IBoardRepository {
    void save(BoardRepositoryDTO boardRepositoryDTO);
    BoardRepositoryDTO findById(String id);
}
