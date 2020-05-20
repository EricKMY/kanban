package domain.usecase.workflow;

import domain.model.workflow.Lane;
import domain.model.workflow.Workflow;
import domain.usecase.lane.LaneDTO;
import domain.usecase.lane.LaneDTOConverter;


public class WorkflowDTOConverter {
    public static WorkflowDTO toDTO(Workflow workflow){
        WorkflowDTO workflowDTO = new WorkflowDTO();
        workflowDTO.setId(workflow.getId());
        workflowDTO.setBoardId(workflow.getBoardId());
        workflowDTO.setName(workflow.getName());

        for (Lane topLane : workflow.getLanes().values()){
            workflowDTO.getLanes().add(LaneDTOConverter.toDTO(topLane));
        }
        return workflowDTO;
    }

    public static Workflow toEntity(WorkflowDTO workflowDTO){
        Workflow workflow = new Workflow(
                workflowDTO.getName(),
                workflowDTO.getBoardId(),
                workflowDTO.getId()
        );
        for (LaneDTO laneDTO : workflowDTO.getLanes()){
            workflow.addToLaneMap(LaneDTOConverter.toEntity(laneDTO));
        }

        return workflow;
    }


}
