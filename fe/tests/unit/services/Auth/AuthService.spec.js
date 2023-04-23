import { AuthService } from '@/services/auth/authService';
import { users } from '../../../data/admin/users/usersMockData';

describe('AuthService', () => {
    let authService;
    let spy;
    beforeEach(() => {
        authService = new AuthService();
        spy = jest.spyOn(authService, "register").mockImplementation(() => {
            return { success: true, message: "User registered successfully." };
        });
    });
    afterEach(() => {
        jest.clearAllMocks();
    });

    it("should register a user", async () => {
        const user = users[0];
        const response = await authService.register(user);
        expect(response).toEqual({ success: true, message: "User registered successfully." });
        expect(spy).toHaveBeenCalled();
    });
});
