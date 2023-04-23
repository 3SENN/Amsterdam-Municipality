export class LevelsLocked {
    id
    userId
    levels

    constructor(levels) {
        this.levels = levels;
    }

    static copyConstructor(levelsLocked) {
        if (levelsLocked == null) return null;
        return Object.assign(new LevelsLocked(), levelsLocked);
    }

    set id(id) {
        this.id = id;
    }

    set userId(userId) {
        this.userId = userId;
    }
}