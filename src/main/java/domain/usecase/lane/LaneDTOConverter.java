package domain.usecase.lane;

import domain.model.workflow.Lane;
import domain.model.workflow.Stage;
import domain.model.workflow.SwimLane;


public class LaneDTOConverter {
    public static LaneDTO toDTO(Lane lane) {
        LaneDTO laneDTO = new LaneDTO();
        laneDTO.setId(lane.getId());
        laneDTO.setName(lane.getName());
        laneDTO.setCardList(lane.getCardList());
        laneDTO.setLaneDirection(lane.getLaneDirection());
        if (lane.getChildAmount() != 0){
            for (Lane childLane : lane.getChildMap().values()){
                laneDTO.getChildLanes().add(LaneDTOConverter.toDTO(childLane));
            }
        }
        return laneDTO;
    }

    public static Lane toEntity(LaneDTO laneDTO) {
        Lane lane = null;
        switch (laneDTO.getLaneDirection()) {
            case VERTICAL:
                lane = new Stage(
                        laneDTO.getName(),
                        laneDTO.getId(),
                        laneDTO.getCardList());
            case HORIZONTAL:
                lane = new SwimLane(
                        laneDTO.getName(),
                        laneDTO.getId(),
                        laneDTO.getCardList()
                );
        }
        if (laneDTO.getChildLanes().size() != 0){
            for (LaneDTO childLaneDTO : laneDTO.getChildLanes()) {
                lane.addLane(LaneDTOConverter.toEntity(childLaneDTO));
            }
        }
        return lane;
    }
}
