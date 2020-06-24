package domain.adapter.repository.lane.converter;

import domain.adapter.repository.lane.dto.LaneRepositoryDTO;
import domain.model.aggregate.workflow.Lane;
import domain.model.aggregate.workflow.Stage;
import domain.model.aggregate.workflow.SwimLane;

public class LaneRepositoryDTOConverter {
    public static LaneRepositoryDTO toDTO(Lane lane) {
        LaneRepositoryDTO laneRepositoryDTO = new LaneRepositoryDTO();
        laneRepositoryDTO.setId(lane.getId());
        laneRepositoryDTO.setName(lane.getName());
        laneRepositoryDTO.setCardList(lane.getCardList());
        laneRepositoryDTO.setLaneDirection(lane.getLaneDirection());
        if (lane.getChildAmount() != 0){
            for (Lane childLane : lane.getChildMap().values()){
                laneRepositoryDTO.getChildLanes().add(LaneRepositoryDTOConverter.toDTO(childLane));
            }
        }
        return laneRepositoryDTO;
    }

    public static Lane toEntity(LaneRepositoryDTO laneRepositoryDTO) {
        Lane lane = null;
        switch (laneRepositoryDTO.getLaneDirection()) {
            case VERTICAL:
                lane = new Stage(
                        laneRepositoryDTO.getName(),
                        laneRepositoryDTO.getId(),
                        laneRepositoryDTO.getCardList());
                break;
            case HORIZONTAL:
                lane = new SwimLane(
                        laneRepositoryDTO.getName(),
                        laneRepositoryDTO.getId(),
                        laneRepositoryDTO.getCardList()
                );
                break;
        }
        if (laneRepositoryDTO.getChildLanes().size() != 0){
            for (LaneRepositoryDTO childLaneDTO : laneRepositoryDTO.getChildLanes()) {
                lane.addLane(LaneRepositoryDTOConverter.toEntity(childLaneDTO));
            }
        }
        return lane;
    }
}
