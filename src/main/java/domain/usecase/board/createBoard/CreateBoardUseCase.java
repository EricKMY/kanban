package domain.usecase.board.createBoard;

import domain.model.DomainEventBus;
import domain.model.aggregate.board.Board;
import domain.adapter.repository.board.converter.BoardRepositoryDTOConverter;
import domain.usecase.repository.IBoardRepository;

public class CreateBoardUseCase implements CreateBoardInput{
    private String boardName;
    private String userId;
    private IBoardRepository boardRepository;
    private DomainEventBus eventBus;

    public CreateBoardUseCase(IBoardRepository iBoardRepository, DomainEventBus eventBus) {
        this.boardRepository = iBoardRepository;
        this.eventBus = eventBus;
    }

    public void execute(CreateBoardInput input, CreateBoardOutput output) {
        Board board = new Board(input.getBoardName(), input.getUserId());

        boardRepository.save(BoardRepositoryDTOConverter.toDTO(board));
        eventBus.postAll(board);

        output.setBoardId(board.getId());
    }

    @Override
    public String getBoardName() {
        return boardName;
    }

    @Override
    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
