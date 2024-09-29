package com.example.messmagic;

import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignUpFormValidation {

    // Function to validate name
    public boolean isValidName(String name) {
        // Name should not be empty and should contain only letters
        return !name.isEmpty() && name.matches("[a-zA-Z ]+");
    }

    // Function to validate email
    public boolean isValidEmail(String email) {
        // Email validation using a regular expression
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Function to validate mobile number
    public boolean isValidMobileNumber(String mobileNumber) {
        // Mobile number should have 10 digits and contain only numbers
        return mobileNumber.length() == 10 && mobileNumber.matches("[0-9]+");
    }

    // Function to validate password
    public String isValidPassword(String password) {
        // Password should have at least 6 characters
        if (password.length() < 6) {
            // If password is less than 6 characters, return error message
            return "Password must be at least 6 characters long.";
        }

        // Password meets length requirement, check if it's strong
        if (!isValidGreaterPassword(password)) {
            // If password is not strong, return error message
            return "Password should contain at least one uppercase letter, one lowercase letter, one special character, and one number.";
        }

        // Password is valid
        return null; // Return null to indicate no error
    }

    public boolean isValidGreaterPassword(String password) {
        // Regular expressions for password validation
        String uppercaseRegex = "(?=.*[A-Z])"; // At least one uppercase letter
        String lowercaseRegex = "(?=.*[a-z])"; // At least one lowercase letter
        String digitRegex = "(?=.*[0-9])"; // At least one digit
        String specialCharRegex = "(?=.*[@#$%^&+=!])"; // At least one special character
        String lengthRegex = "(?=\\S+$).{6,}"; // At least 6 characters in total

        // Combine all regex patterns
        String passwordPattern = uppercaseRegex + lowercaseRegex + digitRegex + specialCharRegex + lengthRegex;

        // Compile the pattern
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(password);

        // Check if password matches the pattern
        if (!matcher.matches()) {
            // If password does not meet requirements, show a toast message
            return false;
        }

        return true;
    }


    // Function to validate confirm password
    public boolean isValidConfirmPassword(String password, String confirmPassword) {
        // Confirm password should match the original password
        return password.equals(confirmPassword);
    }
}


