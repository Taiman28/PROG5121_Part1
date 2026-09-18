package com.mycompany.prog5121part1;

public class Login {

    String firstName;
    String lastName;

    String username;
    String password;
    String cellPhoneNumber;

    String registeredUsername;
    String registeredPassword;

    String loginUsername;
    String loginPassword;

    // Check if username is correctly formatted
    public boolean checkUserName() {

        if (username.contains("_") && username.length() <= 5) {
            return true;
        } else {
            return false;
        }
    }

    // Check if password meets complexity requirements
    public boolean checkPasswordComplexity() {

        if (password.length() < 8) {
            return false;
        }

        boolean hasCapitalLetter = false;
        boolean hasNumber = false;
        boolean hasSpecialCharacter = false;

        for (int i = 0; i < password.length(); i++) {

            char ch = password.charAt(i);

            if (Character.isUpperCase(ch)) {
                hasCapitalLetter = true;
            }

            if (Character.isDigit(ch)) {
                hasNumber = true;
            }

            if (!Character.isLetterOrDigit(ch)) {
                hasSpecialCharacter = true;
            }
        }

        return hasCapitalLetter && hasNumber && hasSpecialCharacter;
    }

    // Check if cell phone number is correctly formatted
    public boolean checkCellPhoneNumber() {

    // Regex reference: https://www.regextester.com/
    String regex = "^\\+27\\d{9}$";
    
        return cellPhoneNumber.matches(regex);
    }

    // Register the user
    public String registerUser() {

        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber()) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        registeredUsername = username;
        registeredPassword = password;

        return "User registered successfully.";
    }

    // Check if login details are correct
    public boolean loginUser() {

        if (loginUsername.equals(registeredUsername)
                && loginPassword.equals(registeredPassword)) {

            return true;
        } else {
            return false;
        }
    }

    // Return the login status message
    public String returnLoginStatus() {

        if (loginUser()) {
            return "Welcome " + firstName + " " + lastName
                    + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}
