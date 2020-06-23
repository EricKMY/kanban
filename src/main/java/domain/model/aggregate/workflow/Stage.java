package domain.model.aggregate.workflow;

import java.util.List;

public class Stage extends Lane {
    public Stage(String stageName){
        super(stageName, LaneDirection.VERTICAL);
    }
    public Stage(String stageName, String id, List<String> cardList){
        super(stageName, LaneDirection.VERTICAL, id, cardList);
    }
}
