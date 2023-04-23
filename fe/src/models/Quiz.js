import User from "@/models/User";

export default class Quiz {
    id
    title
    amountToRegister
    location
    link
    address
    totalQuestions
    createdBy
    startsAt
    endsAt
    createdAt
    updatedAt

    constructor(pId = 0, title, amountToRegister, location, link, address, totalQuestions, pCreatedBy = null, startsAt, endsAt, createdAt, updatedAt) {
        this.id = pId;
        this.title = title;
        this.amountToRegister = amountToRegister;
        this.location = location;
        this.link = link;
        this.address = address;
        this.totalQuestions = totalQuestions;
        this.createdBy = pCreatedBy;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    static copyConstructor(quiz) {
        if (quiz == null) return null;
        return Object.assign(new Quiz(), quiz);
        // copy.startsAt = new Date(quiz.startsAt)
        // copy.endsAt = new Date(quiz.endsAt)
        // copy.createdAt = new Date(quiz.createdAt)
        // copy.updatedAt = new Date(quiz.updatedAt)
    }

    static createSample(pId= 0) {
        let link, address = null
        const title = "Level 2 - Quiz"
        const amountToRegister = Math.floor(Math.random() * 50);
        const location = Quiz.pickRandom(Quiz.locationType)
        location === "Digital" ? link = Quiz.pickRandom(Quiz.links) : address = Quiz.pickRandom(Quiz.addresses)
        const totalQuestions = Math.floor(Math.random() * 10);
        const createdBy = User.createSample()
        const startsAt = new Date(2023, 1, 18, 9, 0, 0);
        const endsAt = new Date(2023, 1, 18, 9, 30, 0);
        const createdAt = new Date()
        const updatedAt = new Date()

        return new Quiz(pId, title,
            amountToRegister, location, link, address, totalQuestions, createdBy, startsAt, endsAt, createdAt, updatedAt);
    }


    static pickRandom(array) {
        return array[Math.floor(Math.random() * array.length)];
    }

    static locationType = [
        "Digital", "Real-life"
    ];

    static links = [
        "https://example.com/quiz/33",
        "https://example.com/quiz/88",
        "https://example.com/quiz/2",
        "https://example.com/quiz/5",
    ]

    static addresses = [
        "7 Warner Way",
        "3266 Dexter Trail",
        "56 Dayton Street",
        "81 Karstens Lane",
        "79 Summerview Crossing",
        "9 South Junction",
        "79 Springs Trail"
    ]


    get title() {
        return this.title;
    }

    set title(value) {
        this.title = value;
    }

    get amountToRegister() {
        return this.amountToRegister;
    }

    set amountToRegister(value) {
        this.amountToRegister = value;
    }

    get location() {
        return this.location;
    }

    set location(value) {
        this.location = value;
    }

    get link() {
        return this.link;
    }

    set link(value) {
        this.link = value;
    }

    get address() {
        return this.address;
    }

    set address(value) {
        this.address = value;
    }

    get totalQuestions() {
        return this.totalQuestions;
    }

    set totalQuestions(value) {
        this.totalQuestions = value;
    }

    get startsAt() {
        return this.startsAt;
    }

    set startsAt(value) {
        this.startsAt = value;
    }

    get endsAt() {
        return this.endsAt;
    }

    set endsAt(value) {
        this.endsAt = value;
    }

    get createdAt() {
        return this.createdAt;
    }

    set createdAt(value) {
        this.createdAt = value;
    }

    get updatedAt() {
        return this.updatedAt;
    }

    set updatedAt(value) {
        this.updatedAt = value;
    }
}