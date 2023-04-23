import {CountingService} from "@/services/countingService";
import countingMockData from "../../../data/counting/countingMockData.js";

const mockCountingLevels = countingMockData.countingLevels;
// Instance for the testing
let countingService;

// Runs before each test
beforeEach(() => {
    countingService = new CountingService("http://localhost:8083/api/counting");
});

describe('Counting service', () => {
    it('countingService.findAll returns all counting levels', async () => {
        // Mocking the return value of countingService.findAll
        countingService.findAll = jest.fn().mockReturnValue(mockCountingLevels);

        // Storing the counting levels in a variable
        const countingData = countingService.findAll();

        expect(countingData,
            'countingService.findAll does not return all counting levels')
            .toStrictEqual(mockCountingLevels);

        expect(countingData.length,
            'countingService.findAll does not return any counting levels')
            .toBeGreaterThan(0);

        expect(countingData.length,
            'countingService.findAll doest not return 2 counting levels')
            .toBe(2);
    })
})