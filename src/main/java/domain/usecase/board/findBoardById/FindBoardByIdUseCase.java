package domain.usecase.board.findBoardById;

import domain.model.DomainEventBus;
import domain.model.aggregate.board.Board;
import domain.usecase.board.BoardOutputDTO;
import domain.usecase.board.BoardOutputDTOConverter;
import domain.adapter.repository.board.converter.BoardRepositoryDTOConverter;
import domain.adapter.repository.board.dto.BoardRepositoryDTO;
import domain.usecase.repository.IBoardRepository;

public class FindBoardByIdUseCase implements FindBoardByIdInput{
    private String boardId;
    private IBoardRepository boardRepository;

    public FindBoardByIdUseCase(IBoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public String getBoardId() {
        return boardId;
    }

    @Override
    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public void execute(FindBoardByIdInput input, FindBoardByIdOutput output) {
        BoardRepositoryDTO boardRepositoryDTO = boardRepository.findById(input.getBoardId());
        Board board = BoardRepositoryDTOConverter.toEntity(boardRepositoryDTO);
        BoardOutputDTO boardOutputDTO = BoardOutputDTOConverter.toDTO(board);

        output.setBoardOutputDTO(boardOutputDTO);
    }
}
