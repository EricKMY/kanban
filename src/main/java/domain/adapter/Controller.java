/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.adapter;

import domain.adapter.board.BoardInMemoryRepository;
import domain.usecase.board.BoardDTO;
import domain.usecase.board.createBoard.CreateBoardInput;
import domain.usecase.board.createBoard.CreateBoardOutput;
import domain.usecase.board.createBoard.CreateBoardUseCase;

/**
 *
 * @author lab1321
 */
public class Controller {
    private BoardInMemoryRepository boardInMemoryRepository;
    private String boardName;

    public Controller() {
        boardInMemoryRepository = new BoardInMemoryRepository();
    }

    public BoardDTO createBoard(String userName, String boardName) {
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardInMemoryRepository);
        CreateBoardInput input = new CreateBoardInput();
        CreateBoardOutput output = new CreateBoardOutput();

        input.setUsername(userName);
        input.setBoardName(boardName);

        createBoardUseCase.execute(input, output);
        return boardInMemoryRepository.findById(output.getBoardId());
    }

//    public BoardDTO getBoard(String boardName) {
//        return boardInMemoryRepository.findById(output.getBoardId());
//    }
}
