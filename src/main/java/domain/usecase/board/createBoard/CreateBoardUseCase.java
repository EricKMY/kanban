package domain.usecase.board.createBoard;

import domain.adapter.board.BoardRepository;
import domain.model.Workflow;
import domain.model.board.Board;

public class CreateBoardUseCase {
    private BoardRepository boardRepository;

    public CreateBoardUseCase(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public void execute(CreateBoardInput input, CreateBoardOutput output) {
        Board board = new Board(input.getBoardName());
        boardRepository.add(board);

        output.setBoardId(board.getBoardId());
        boardRepository.save(board.getBoardId(), board);
    }
}
