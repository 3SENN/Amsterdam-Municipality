/**
 * Class User represents a User
 */
export default class User {
    id
    firstName
    lastName
    email
    role
    password
    profilePhoto
    createdAt
    updatedAt

    /**
     * Constructor for class User to create a user
     * @param {number} id The id of the user
     * @param {string} firstName The firstname of the user
     * @param {string} lastName The firstname of the user
     * @param {string} email The email address of the user
     * @param {number} role The role of the user
     * @param {string} password The password of the user
     * @param {string} profilePhoto The profile photo of the user
     * @param {Date} createdAt the datetime of when the user was created
     * @param {Date} updatedAt the datetime of when the user was updated
     */
    constructor(firstName, lastName, email, role, password, profilePhoto, createdAt, updatedAt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.password = password;
        this.profilePhoto = profilePhoto;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    static copyConstructor(user) {
        if (user == null) return null;
        let copy = Object.assign(new User(), user);
        // copy._createdAt = new Date(user.createdAt)
        // copy._updatedAt = new Date(user.updatedAt)
        return copy
    }

    static createSample() {
        return new User("John", "Doe", "john@gmail.com", 0, "12345678", "https://ui-avatars.com/api/?name=John+Doe", Date.now(), Date.now());
    }

    static createRandomSample(pId = 0) {
        const firstName = User.pickRandom(User.firstNames)
        const lastName = User.pickRandom(User.lastNames)
        const email = User.pickRandom(User.emails)
        const role = Math.floor(Math.random())
        const password = "12345678"
        const profilePhoto = `https://ui-avatars.com/api/?name=${firstName}+${lastName}`

        let user = new User(firstName, lastName, email, role, password, profilePhoto, Date.now(), Date.now())
        user.id = pId
        return user
    }

    static firstNames = ["John", "Aaran", "Aaren", "Aarez", "Aarman", "Aaron", "Aaron-James", "Aarron", "Aaryan", "Aaryn"]

    static lastNames = ["Anderson", "Ashwoon", "Aikin", "Bateman", "Bongard", "Bowers", "Boyd", "Cannon", "Cast", "Deitz"]

    static emails = [
        "pashkevichukr@btcmod.com",
        "jdclassics@tipsehat.click",
        "owusuv@dmailx.com",
        "loriklidiy@taimurfun.fun",
        "sunetcute@hieu.in",
        "santimurgi@superhostformula.com",
        "eileen05@innoberg.com",
        "rsl58@emvil.com"
    ]

    static pickRandom(array) {
        return array[Math.floor(Math.random() * array.length)];
    }

    get id() {
        return this.id;
    }

    set id(value) {
        this.id = value;
    }

    get firstName() {
        return this.firstName;
    }

    set firstName(value) {
        this.firstName = value;
    }

    get lastName() {
        return this.lastName;
    }

    set lastName(value) {
        this.lastName = value;
    }

    get email() {
        return this.email;
    }

    set email(value) {
        this.email = value;
    }

    get role() {
        return this.role;
    }

    set role(value) {
        this.role = value;
    }

    get password() {
        return this.password;
    }

    set password(value) {
        this.password = value;
    }

    get profilePhoto() {
        return this.profilePhoto;
    }

    set profilePhoto(value) {
        this.profilePhoto = value;
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

    toString() {
        return `{id=${this.id}, firstName=${this.firstName}, lastName=${this.lastName}, email=${this.email}, 
        password=${this.email}, profilePhoto=${this.profilePhoto}, createdAt=${this.createdAt}, updatedAt=${this.updatedAt}`
    }
}