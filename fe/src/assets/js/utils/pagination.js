/**
 * Class Pagination
 * The pagination helps to show a data sets in manageable chunks.
 *
 * @author Harmohat Khangura
 */
export default class Pagination {
    #dataPaginate
    #dataSplit
    #data
    #rows

    /**
     * Instantiates a new pagination
     * @param data The data set to split into manageable chunks
     * @param rows The amount of rows to have inside a single chunk
     */
    constructor(data, rows) {
        this.#data = data;
        this.#rows = rows;

        this.splitAndPaginate()
    }

    /**
     * Assigning the split data to the variable `this.#dataSplit`
     */
    splitAndPaginate() {
        this.#dataSplit = this.splitArray(this.#data, this.#rows)
        this.#dataPaginate = this.#dataSplit[0]
    }

    /**
     * Splits the data from the JSON file into the amount of rows which are
     * given as param and returns the split array
     * @param {object} data The data from the JSON file
     * @param {number} rows The amount where the data has to split
     * @returns {object[]} The split array
     */
    splitArray(data, rows) {
        const arraySplit = [];
        while (data.length > 0) arraySplit.push(data.splice(0, rows));

        return arraySplit;
    }

    /**
     * Gets the number of the array in the split array and gives the value to `#dataPaginate`
     * @param {number} number The number of the page
     */
    onPageSelect(number) {
        // let number = parseInt(event.target.value)
        this.#dataPaginate = this.#dataSplit[number]
        return this.#dataPaginate
    }

    get dataPaginate() {
        return this.#dataPaginate;
    }

    set dataPaginate(value) {
        this.#dataPaginate = value;
    }

    get dataSplit() {
        return this.#dataSplit;
    }

    set dataSplit(value) {
        this.#dataSplit = value;
    }

    get data() {
        return this.#data;
    }

    set data(value) {
        this.#data = value;
    }

    get rows() {
        return this.#rows;
    }

    set rows(value) {
        this.#rows = value;
    }
}