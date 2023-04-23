import bcrypt from "bcryptjs"

/**
 * Class CryptoHelper
 * Includes methods to help with hashed values
 *
 * @author Ayoub Ezzaouia & Harmohat Khangura
 */
export class CryptoHelper {
    /**
     * Function hashes the given value
     * @param saltRounds The cost factor controls how much time is needed to calculate a single BCrypt hash. The higher
     *                   the cost factor, the more hashing rounds are done. Increasing the cost factor by 1 doubles
     *                   the necessary time. The more time is necessary, the more difficult is brute-forcing.
     *
     * @param value The value to hash
     * @returns {String} resulting hash
     */
    static hash(saltRounds, value) {
        const salt = bcrypt.genSaltSync(saltRounds)
        return bcrypt.hashSync(value, salt)
    }

    /**
     * Function compares the plaintext with the hashed value
     * @param plainText The plain text value
     * @param hashedValue The hashed value
     * @returns {Promise<*>}
     */
    static async compare(plainText, hashedValue) {
        return await bcrypt.compare(plainText, hashedValue)
    }
}