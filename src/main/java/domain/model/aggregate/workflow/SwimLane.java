package domain.model.aggregate.workflow;

import java.util.List;

public class SwimLane extends Lane{
    public SwimLane(String swimLaneName) {
        super(swimLaneName, LaneDirection.HORIZONTAL);
    }
    public SwimLane(String swimLaneName, String id, List<String> cardList){
        super(swimLaneName, LaneDirection.HORIZONTAL, id, cardList);
    }
}
