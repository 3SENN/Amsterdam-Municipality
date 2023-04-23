import {UserInMemoryService} from "@/services/user/userInMemoryService";
import User from "@/models/User";

let usersService;

usersService = new UserInMemoryService(0, User.createSample);

describe('usersService.js', () => {
    // Source: https://gitlab.fdmci.hva.nl/se-ewa/unit-testing-workshop/-/blob/main/wk1.5-frontend-with-jest/src/services/books-service.spec.js
    it('userService was set up properly', function () {
        // The entities should contain data due to the sample
        expect(usersService.entities.length,
            'users-service was not set up properly')
    })

    it('findAll retrieves all users', function () {
        // Retrieve all users
        let users = usersService.findAll();

        // Expecting that the return value of .findAll should return the correct entities
        expect(users,
            'usersService.findAll does not retrieve all users')
            .toStrictEqual(usersService.entities);
    })

    it('getUserById returns the specified user', function () {
        // Retrieving the first user
        const user = usersService.entities[0];

        // Expecting that the return value of .getUserByID should return the correct entity
        expect(usersService.getUserById(user.id),
            `usersService.findById cannot find user=${user.id}`)
            .toStrictEqual(user);

        // Expecting that the return value of .getUserByID should return undefined
        expect(usersService.getUserById(2),
            'usersService.findById cannot find id=2')
            .toBeUndefined();
    })

    it('getUserByEmail returns the specified user', function () {
        // Retrieving the user by mail
        const user = usersService.getUserByEmail("john@gmail.com");

        // Expecting that the return value of .getUserByEmail should return the correct entity
        expect(usersService.getUserByEmail("john@gmail.com"),
            `usersService.getUserByEmail cannot find user=${user.email}`)
            .toStrictEqual(user);

        // Expecting that the return value of .getUserByID should return an empty array
        expect(usersService.getUserByEmail("john123@gmail.com"),
            'usersService.getUserByEmail cannot find user=john123@gmail.com')
            .toEqual([]);
    })

    it('deleteById deletes the specified user', function () {
        // Getting the first user
        const user = usersService.entities[0];

        // Deleting the user
        usersService.deleteById(user.id);

        // Expecting that the return value of .getUserByID should return undefined because the user was deleted.
        expect(usersService.getUserById(user.id),
            `usersService.deleteById did not delete user-${user.id}`)
            .toBeUndefined();
    })

    it('save updates the specified user', function () {
        // Retrieving the user by email
        const user = usersService.getUserByEmail('john@gmail.com');

        // Changing the user's email
        user.email = "johndoe@gmail.com";

        // Storing the updated user
        const updatedUser = usersService.save(user);

        // Expecting that the return value of .email to be the same as the updatedUser's email
        expect(user.email,
            `usersService.save did not save user-${user.id}`)
            .toStrictEqual(updatedUser.email);
    })
})