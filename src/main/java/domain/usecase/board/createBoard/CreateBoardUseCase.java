package domain.usecase.board.createBoard;

import domain.model.DomainEventBus;
import domain.model.board.Board;
import domain.usecase.board.BoardRepositoryDTOConverter;
import domain.usecase.repository.IBoardRepository;

public class CreateBoardUseCase implements CreateBoardInput{
    private String boardName;
    private String username;
    private IBoardRepository boardRepository;
    private DomainEventBus eventBus;

    public CreateBoardUseCase(IBoardRepository iBoardRepository, DomainEventBus eventBus) {
        this.boardRepository = iBoardRepository;
        this.eventBus = eventBus;
    }

    public void execute(CreateBoardInput input, CreateBoardOutput output) {
        Board board = new Board(input.getBoardName(), input.getUsername());

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
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }
}
