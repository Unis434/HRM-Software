package HRS;

public class ErrorHandling {
    public static void main(String[] args) {
        try {
            // Simulate a risky operation that might throw an exception
            int result = divide(10, 0);

            // Continue processing if no exception is thrown
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            // Handle the specific exception (division by zero in this case)
            System.err.println("An error occurred: " + e.getMessage());
            // Perform graceful error recovery or log the error
        } catch (Exception e) {
            // Handle other exceptions (if needed)
            System.err.println("An unexpected error occurred: " + e.getMessage());
            // Perform graceful error recovery or log the error
        } finally {
            // Code in the 'finally' block runs regardless of whether an exception occurred or not.
            System.out.println("Cleanup and resource release.");
        }

        // Continue with normal program flow
        System.out.println("Program continues...");
    }

    public static int divide(int numerator, int denominator) {
        if (denominator == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        return numerator / denominator;
    }
}

