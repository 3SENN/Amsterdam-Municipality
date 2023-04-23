export class CountingLabel {
    id
    name
    publicSpaces
    ov
    counting

    constructor(name, publicSpaces, ov) {
        this.name = name;
        this.publicSpaces = publicSpaces;
        this.ov = ov;
    }

    static copyConstructor(countingLabel) {
        if (countingLabel == null) return null;
        return Object.assign(new CountingLabel(), countingLabel);
    }
}