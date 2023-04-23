export class Level {
    id;
    level;
    name;
    description;
    isPlayable;

    constructor(pId = 0, level, name, description, isPlayable) {
        this.id = pId;
        this.level = level;
        this.name = name;
        this.description = description;
        this.isPlayable = isPlayable
    }

    static copyConstructor(level) {
        if (level == null) return null;
        return Object.assign(new Level(), level);
    }
}