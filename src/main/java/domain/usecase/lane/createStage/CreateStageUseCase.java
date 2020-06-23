package domain.usecase.lane.createStage;

import domain.adapter.repository.workflow.converter.WorkflowRepositoryDTOConverter;
import domain.model.DomainEventBus;
import domain.model.aggregate.workflow.Workflow;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.WorkflowDTOConverter;

public class CreateStageUseCase implements CreateStageInput{
    private IWorkflowRepository workflowRepository;
    private IBoardRepository boardRepository;
    private Workflow workflow;
    private DomainEventBus eventBus;
    private String stageName;
    private String workflowId;
    private String parentLaneId;


    public CreateStageUseCase(IWorkflowRepository workflowRepository, IBoardRepository boardRepository, DomainEventBus eventBus) {
        this.workflowRepository = workflowRepository;
        this.boardRepository = boardRepository;
        this.eventBus = eventBus;
    }

    public void execute(CreateStageInput input, CreateStageOutput output) {

        workflow = WorkflowRepositoryDTOConverter.toEntity(workflowRepository.findById(input.getWorkflowId()));
        String stageId;
        if (input.getParentLaneId() == null){
            stageId = workflow.createStage(input.getStageName());
        }else{
            stageId = workflow.createStage(input.getStageName(), input.getParentLaneId());
        }

        workflowRepository.save(WorkflowRepositoryDTOConverter.toDTO(workflow));
        eventBus.postAll(workflow);

        output.setStageId(stageId);
    }

    @Override
    public String getStageName() {
        return stageName;
    }

    @Override
    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    @Override
    public String getWorkflowId() {
        return workflowId;
    }

    @Override
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    @Override
    public String getParentLaneId() {
        return parentLaneId;
    }

    @Override
    public void setParentLaneId(String parentLaneId) {
        this.parentLaneId = parentLaneId;
    }
}
