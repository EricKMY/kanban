package domain.usecase.board.createBoard;

import domain.model.board.Board;
import domain.usecase.board.BoardRepositoryDTOConverter;
import domain.usecase.repository.IBoardRepository;

public class CreateBoardUseCase implements CreateBoardInput{
    private String boardName;
    private String username;
    private IBoardRepository boardRepository;

    public CreateBoardUseCase(IBoardRepository iBoardRepository) {
        this.boardRepository = iBoardRepository;
    }

    public void execute(CreateBoardInput input, CreateBoardOutput output) {
        Board board = new Board(input.getBoardName(), input.getUsername());
        boardRepository.save(BoardRepositoryDTOConverter.toDTO(board));

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
