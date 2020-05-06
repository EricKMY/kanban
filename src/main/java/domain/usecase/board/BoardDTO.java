package domain.usecase.board;

import java.util.ArrayList;
import java.util.List;

public class BoardDTO {
    private String name;
    private String id;
    private List<String> workflows;
    private String username;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWorkflows(List<String> workflows) {
        this.workflows = workflows;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getUsername() {
        return username;
    }

}
