export class quizResultInMemoryService {
    _lastId;
    _entities;
    get entities() {
        return this._entities;
    }

    constructor(initialId = 10000, sampleCreator = null) {
        this._lastId = initialId;
        this._entities = [];
        if (sampleCreator != null) {
            this.save(sampleCreator);
        }
    }

    findAll() {
        return this._entities;
    }

    save(quizResult) {
        // Combination of add and update
        if (quizResult == null) return;
        const index = this._entities.findIndex(e => e?.id == quizResult.id);
        if (index >= 0) {
            this._entities[index] = quizResult;
        } else {
            if (quizResult?.id == 0 || quizResult?.id == undefined) quizResult.id = ++this._lastId;
            this._entities.push(quizResult);
        }
        return quizResult;
    }


}