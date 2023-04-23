/**
 * Function return the current datetime. Configured rightfully to send it successfully top the back-end
 * @returns {string} The current datetime
 */
function getCurrentDatetime(){
    let date = new Date();
    let datetime = new Date(date.getTime() - (date.getTimezoneOffset() * 60000)).toISOString();
    return datetime.replace(/\.\d+/, "").replace('Z', '')
}

/**
 * Function return the transformed datetime of the value received. Configured rightfully to send it successfully top the back-end
 * @returns {string} The transformed datetime
 */
function transformDate(value) {
    let date = new Date(value)
    let datetime = new Date(date.getTime() - (date.getTimezoneOffset() * 60000)).toISOString();
    return datetime.replace(/\.\d+/, "").replace('Z', '')
}

module.exports = {
    getCurrentDatetime, transformDate
}