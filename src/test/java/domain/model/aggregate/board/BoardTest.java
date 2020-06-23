package domain.model.aggregate.board;

import domain.model.aggregate.board.event.BoardCreated;
import domain.model.aggregate.board.event.WorkflowCommitted;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoardTest {
    @Test
    public void create_a_Board_should_generate_a_BoardCreated_event() {
        Board board = new Board("dcTrack", "1");
        assertEquals(1, board.getDomainEvents().size());
        assertEquals(BoardCreated.class, board.getDomainEvents().get(0).getClass());

        BoardCreated boardCreated =  ((BoardCreated) board.getDomainEvents().get(0));

        assertEquals("1", boardCreated.getUserId());
        assertEquals(board.getId(), boardCreated.getBoardId());
        assertEquals(board.getName(), boardCreated.getBoardName());
        assertEquals("Board Created: dcTrack", boardCreated.getDetail());
    }

    @Test
    public void commit_Workflow_should_generate_a_WorkflowCommitted_event() {
        Board board = new Board("dcTrack", "1");
        assertEquals(1, board.getDomainEvents().size());
        assertEquals(BoardCreated.class, board.getDomainEvents().get(0).getClass());

        board.commitWorkflow("1");
        assertEquals(2, board.getDomainEvents().size());
        assertEquals(WorkflowCommitted.class, board.getDomainEvents().get(1).getClass());

        WorkflowCommitted workflowCommitted = (WorkflowCommitted)board.getDomainEvents().get(1);

        assertEquals(board.getId(),workflowCommitted.getBoardId());
        assertEquals("1",workflowCommitted.getWorkflowId());
        assertEquals("Workflow Committed 1 to Board " + board.getId(),workflowCommitted.getDetail());
    }
}
