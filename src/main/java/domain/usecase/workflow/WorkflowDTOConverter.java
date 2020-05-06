//package domain.usecase.workflow;
//
//import domain.model.card.Card;
//import domain.model.workflow.Workflow;
//import domain.usecase.card.CardDTO;
//
//public class WorkflowDTOConverter {
//    public static WorkflowDTO toDTO(Workflow workflow){
//        CardDTO cardDTO = new CardDTO();
//        cardDTO.setId(card.getId());
//        cardDTO.setName(card.getName());
//        cardDTO.setWorkflowId(card.getWorkflowId());
//        cardDTO.setLaneId(card.getLaneId());
//        return cardDTO;
//    }
//
//    public static Workflow toEntity(WorkflowDTO workflowDTO){
//        Card card = new Card(workflowDTO.getName(), workflowDTO.getId(), workflowDTO.getLaneId(), workflowDTO.getWorkflowId());
//        return card;
//    }
//}
