package domain.adapter.stage;

import domain.model.Stage;

import java.util.HashMap;
import java.util.Map;

public class StageRepository {
    Map<String, Stage> map = new HashMap<String, Stage>();

    public void add(Stage stage) {
        map.put(stage.getStageId(), stage);
    }

    public Stage getStageById(String stageId) {
        return map.get(stageId);
    }
}
