export class Counting {
    id
    userId
    startingPoint
    startingTime
    boardingStop
    transfer
    exitStop
    destination
    destinationTime
    publicSpaces
    ov
    score

    constructor(startingPoint, startingTime, boardingStop, transfer, exitStop, destination, destinationTime, publicSpaces, ov, score) {
        this.startingPoint = startingPoint;
        this.startingTime = startingTime;
        this.boardingStop = boardingStop;
        this.transfer = transfer;
        this.exitStop = exitStop;
        this.destination = destination;
        this.destinationTime = destinationTime;
        this.publicSpaces = publicSpaces;
        this.ov = ov;
        this.score = score;
    }

    static copyConstructor(counting) {
        if (counting == null) return null;
        return Object.assign(new Counting(), counting);
    }

    set id(id) {
        this.id = id;
    }

    set userId(userId) {
        this.userId = userId;
    }
}