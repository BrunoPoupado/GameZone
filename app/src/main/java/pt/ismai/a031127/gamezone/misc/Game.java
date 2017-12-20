package pt.ismai.a031127.gamezone.misc;

import java.io.Serializable;

public class Game implements Serializable {
    protected String id, name;

    public Game(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Game(String id) {
        this.id = id;
    }

    public Game() {
        this.id = "1";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}