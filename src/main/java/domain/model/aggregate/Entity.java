package domain.model.aggregate;

import java.util.UUID;

public abstract class Entity {
    protected String name;
    protected final String id;

    public Entity(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public Entity(String name) {
        this.name = name;
        id = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getId() {
        return id;
    }

}
