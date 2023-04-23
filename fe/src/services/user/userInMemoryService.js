export class UserInMemoryService {
    // Source: https://gitlab.fdmci.hva.nl/se-ewa/unit-testing-workshop/-/blob/main/wk1.5-frontend-with-jest/src/services/in-memory-entities-service.js
    #lastId;
    #entities;

    constructor(initialId = 1, sample = null) {
        this.#lastId = initialId;
        this.#entities = [];

        if (sample != null) {
            this.save(sample);
        }
    }

    get entities() {
        return this.#entities;
    }

    findAll() {
        return this.#entities;
    }

    getTotalAmountOfUsers() {
        return this.#entities.length;
    }

    getUserById(id) {
        return this.#entities.find(e => e?.id == id);
    }

    getUserByEmail(email) {
        return this.#entities.filter(e => e?.email == email);
    }

    deleteById(id) {
        this.#entities = this.#entities.filter(e => e?.id !== id);
    }

    save(user) {
        // Combination of add and update
        if (user == null) return;
        const index = this.#entities.findIndex(e => e?.id === user.id);
        if (index >= 0) {
            this.#entities[index] = user;
        } else {
            // Increment user id if 0
            if (user?.id === 0 || user?.id === undefined) user.id = ++this.#lastId;
            this.#entities.push(user);
        }
        return user;
    }


}
