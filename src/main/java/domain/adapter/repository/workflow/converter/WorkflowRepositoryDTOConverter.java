package domain.adapter.repository.workflow.converter;

import domain.adapter.repository.lane.converter.LaneRepositoryDTOConverter;
import domain.adapter.repository.lane.dto.LaneRepositoryDTO;
import domain.adapter.repository.workflow.dto.WorkflowRepositoryDTO;
import domain.model.aggregate.workflow.Lane;
import domain.model.aggregate.workflow.Workflow;

public class WorkflowRepositoryDTOConverter {

    public static WorkflowRepositoryDTO toDTO(Workflow workflow){
        WorkflowRepositoryDTO workflowRepositoryDTO = new WorkflowRepositoryDTO();
        workflowRepositoryDTO.setId(workflow.getId());
        workflowRepositoryDTO.setBoardId(workflow.getBoardId());
        workflowRepositoryDTO.setName(workflow.getName());

        for (Lane topLane : workflow.getLaneMap().values()){
            workflowRepositoryDTO.getLanes().add(LaneRepositoryDTOConverter.toDTO(topLane));
        }
        return workflowRepositoryDTO;
    }

    public static Workflow toEntity(WorkflowRepositoryDTO workflowRepositoryDTO){
        Workflow workflow = new Workflow(
                workflowRepositoryDTO.getName(),
                workflowRepositoryDTO.getBoardId(),
                workflowRepositoryDTO.getId()
        );
        for (LaneRepositoryDTO laneRepositoryDTO : workflowRepositoryDTO.getLanes()){
            workflow.addToLaneMap(LaneRepositoryDTOConverter.toEntity(laneRepositoryDTO));
        }

        return workflow;
    }
}
