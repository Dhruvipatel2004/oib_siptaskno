import java.util.Random;
import javax.swing.JOptionPane;

public class Guess_the_number {
    public static void main(String[] args) {
        final int MIN_NUMBER = 1;
        final int MAX_NUMBER = 100;
        final int MAX_ATTEMPTS = 5;
        int score = 0;
        
        JOptionPane.showMessageDialog(null, "Welcome to Guess the Number game!\nI'm thinking of a number between "
                + MIN_NUMBER + " and " + MAX_NUMBER + ". Try to guess it.");

        Random rand = new Random();
        int randomNumber = rand.nextInt(MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER;

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            String input = JOptionPane.showInputDialog("Attempt " + attempt + "/" + MAX_ATTEMPTS + ": Enter your guess:");
            
            if (input == null) {
                JOptionPane.showMessageDialog(null, "Game aborted.");
                return;
            }
            
            try {
                int guess = Integer.parseInt(input);
                
                if (guess < MIN_NUMBER || guess > MAX_NUMBER) {
                    JOptionPane.showMessageDialog(null, "Please enter a number between " + MIN_NUMBER + " and " + MAX_NUMBER + ".");
                    continue;
                }
                
                if (guess == randomNumber) {
                    JOptionPane.showMessageDialog(null, "Congratulations! You guessed the number in " + attempt + " attempt(s)!");
                    score += MAX_ATTEMPTS - attempt + 1;
                    break;
                } else if (guess < randomNumber) {
                    JOptionPane.showMessageDialog(null, "Too low! Try again.");
                } else {
                    JOptionPane.showMessageDialog(null, "Too high! Try again.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input! Please enter a valid number.");
            }
        }
        
        JOptionPane.showMessageDialog(null, "Your score: " + score);
    }
}
