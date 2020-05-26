package domain.usecase.board.findBoardById;

import domain.model.DomainEventBus;
import domain.model.board.Board;
import domain.usecase.board.BoardOutputDTO;
import domain.usecase.board.BoardOutputDTOConverter;
import domain.usecase.board.BoardRepositoryDTOConverter;
import domain.usecase.board.BoardRepositoryDTO;
import domain.usecase.repository.IBoardRepository;

public class FindBoardByIdUseCase implements FindBoardByIdInput{
    private String boardId;
    private IBoardRepository boardRepository;
    private DomainEventBus eventBus;

    public FindBoardByIdUseCase(IBoardRepository boardRepository, DomainEventBus eventBus) {
        this.boardRepository = boardRepository;
        this.eventBus = eventBus;
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

        eventBus.postAll(board);

        output.setBoardOutputDTO(boardOutputDTO);
    }
}
