package domain.usecase.workflow.createWorkflow;

import domain.model.DomainEventBus;
import domain.model.workflow.Workflow;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.WorkflowDTOConverter;

public class CreateWorkflowUseCase implements CreateWorkflowInput {
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;
    private String workflowName;
    private String boardId;

    public CreateWorkflowUseCase(IWorkflowRepository workflowRepository, DomainEventBus eventBus) {
        this.workflowRepository = workflowRepository;
        this.eventBus = eventBus;
    }

    public void execute(CreateWorkflowInput input, CreateWorkflowOutput output) {
        Workflow workflow = new Workflow(input.getWorkflowName(), input.getBoardId());

        workflowRepository.save(WorkflowDTOConverter.toDTO(workflow));
        eventBus.postAll(workflow);

        output.setWorkflowId(workflow.getId());
    }

    @Override
    public String getWorkflowName() {
        return workflowName;
    }

    @Override
    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    @Override
    public String getBoardId() {
        return boardId;
    }

    @Override
    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }
}
