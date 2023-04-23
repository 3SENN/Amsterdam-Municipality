export class Card {
id;
userId;
title;
cardDate
content;

constructor(id, userId, title, cardDate, content) {
    this._id = id;
    this._userId = userId;
    this._title = title;
    this._cardDate = cardDate;
    this._content = content;
}


    get id() {
        return this._id;
    }

    get userId() {
        return this._userId;
    }

    get title() {
        return this._title;
    }

    get cardDate() {
        return this._cardDate;
    }

    get content() {
        return this._content;
    }

    set id(value) {
        this._id = value;
    }

    set userId(value) {
        this._userId = value;
    }

    set title(value) {
        this._title = value;
    }

    set cardDate(value) {
        this._cardDate = value;
    }

    set content(value) {
        this._content = value;
    }

    static copyConstructor(journalCard){
    if (journalCard == null) return null;
    return Object.assign(new Card(), journalCard);
}

static createJournalCard(id){
    return new Card(id, this.getUserId(), this.cardName(), this.createCardDate(), this.createCardContent());
}

static cardName(){
    return "Level 1";
}

static getUserId(){
    return 2;
}

static createCardDate(){
    return "27/09/2022 5:48 PM";
}

static createCardContent(){
    return "Wij gaan lekker samen delen en spelen";
}
}