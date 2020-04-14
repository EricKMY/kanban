//package domain.usecase;
//
//public class CommitWorkflowUseCase {
//    private BoardRepository boardRepository;
//
//    public CommitWorkflowUseCase(BoardRepository boardRepository) {
//        this.boardRepository = boardRepository;
//    }
//
//
//    public void execute(CommitWorkflowInput commitWorkflowInput, CommitWorkflowOutput commitWorkflowOutput) {
//        Board board = boardRepository.findBoardById(commitWorkflowInput.getBoardId());
//        board.addWorkflow(CommitWorkflowInput.getWorkflowId());
//
//        commitWorkflowOutput.setResult(false);
//    }
//}
