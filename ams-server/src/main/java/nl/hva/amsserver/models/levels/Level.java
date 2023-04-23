package nl.hva.amsserver.models.levels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Level {

    @JsonProperty("id")
    private int id;
    @JsonProperty("level")
    private int level;
    @JsonProperty("name")
    private String name;
    @JsonProperty("info")
    private String description;
    @JsonProperty("isPlayable")
    private boolean playable;

    public Level(int id, int level, String name, String description, boolean playable) {
        this.id = id;
        this.level = level;
        this.name = name;
        this.description = description;
        this.playable = playable;
    }

    public Level() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPlayable() {
        return playable;
    }

    public void setPlayable(boolean isPlayable) {
        playable = isPlayable;
    }

    @Override
    public String toString() {
        return "Levels{" +
                "id=" + id +
                ", level=" + level +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isPlayable=" + playable +
                '}';
    }
}
