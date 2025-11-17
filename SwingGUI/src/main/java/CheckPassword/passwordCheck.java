package CheckPassword;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static Hash.passwordHash.hashPassword;

public class passwordCheck {
    public static boolean isValidPassword(String password) {
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(null, "Password must be at least 6 characters");
            return false;
        }

        boolean hasSpecialChar = false;
        boolean hasDigit = false;

        for (char ch : password.toCharArray()) {
            if (!Character.isLetterOrDigit(ch)) {
                hasSpecialChar = true;
            }
            if (Character.isDigit(ch)) {
                hasDigit = true;
            }
        }

        if (!hasSpecialChar) {
            JOptionPane.showMessageDialog(null, "Password must contain at least one special character");
            return false;
        }

        if (!hasDigit) {
//            System.out.println("Password must contain at least one digit");
            JOptionPane.showMessageDialog(null, "Password must contain at least one digit");
            return false;
        }

        return true;
    }

    public static boolean verifyPassword(String enteredPassword, String storedHash, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String hashOfInput = hashPassword(enteredPassword, salt);
        return storedHash.equals(hashOfInput);
    }
}

