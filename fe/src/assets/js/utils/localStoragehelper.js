export default class LocalStorageHelper {
    static fields = {
        ID: "id",
        FULL_NAME: "fullName",
        EMAIl: "email",
        PROFILE_PICTURE: "profilePicture",
    }

    static get(key) {
        const data = localStorage.getItem(key);
        return data == null ? undefined : data;
    }

    static set(key, value) {
        localStorage.setItem(key, JSON.stringify(value));
    }

    static remove(key) {
        localStorage.removeItem(key);
    }

    static getRole() {
        return parseInt(LocalStorageHelper.get("role")) === 1 ? "Admin" : "User"
    }

    static getEmail() {
        return LocalStorageHelper.get("email") ? LocalStorageHelper.get("email") : "Email not available"
    }

    static getFullName() {
        return LocalStorageHelper.get("fullName") ? LocalStorageHelper.get("fullName") : "Name not available"
    }

    static getProfilePicture() {
        return LocalStorageHelper.get("profilePicture")
    }
}
