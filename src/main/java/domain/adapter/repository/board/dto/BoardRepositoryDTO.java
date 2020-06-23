package domain.adapter.repository.board.dto;

import java.util.List;

public class BoardRepositoryDTO {
    private String name;
    private String id;
    private List<String> workflows;
    private String userId;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWorkflows(List<String> workflows) {
        this.workflows = workflows;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public List<String> getWorkflows() {
        return workflows;
    }

    public String getUserId() {
        return userId;
    }

}
