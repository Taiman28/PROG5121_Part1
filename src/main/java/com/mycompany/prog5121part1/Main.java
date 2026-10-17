package com.mycompany.prog5121part1;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        Login user = new Login();

        System.out.println("=== USER REGISTRATION ===");

        System.out.print("Enter your first name: ");
        user.firstName = input.nextLine();

        System.out.print("Enter your surname: ");
        user.lastName = input.nextLine();

        System.out.print("Enter your username: ");
        user.username = input.nextLine();

        System.out.print("Enter your password: ");
        user.password = input.nextLine();

        System.out.print("Enter your cell phone number: ");
        user.cellPhoneNumber = input.nextLine();

        System.out.println(user.registerUser());

        System.out.println();
        System.out.println("=== USER LOGIN ===");

        System.out.print("Enter your username: ");
        user.loginUsername = input.nextLine();

        System.out.print("Enter your password: ");
        user.loginPassword = input.nextLine();

        if (user.loginUser()) {

            System.out.println("Welcome to QuickChat.");

            int choice = 0;

            while (choice != 3) {

                System.out.println();
                System.out.println("1) Send Messages");
                System.out.println("2) Show recently sent messages");
                System.out.println("3) Quit");

                System.out.print("Choose an option: ");

                if (input.hasNextInt()) {
                    choice = input.nextInt();
                    input.nextLine();

                    switch (choice) {

                        case 1:
                            System.out.println(
                                "Message feature coming next."
                            );
                            break;

                        case 2:
                            System.out.println("Coming Soon.");
                            break;

                        case 3:
                            System.out.println("Thank you for using QuickChat.");
                            break;

                        default:
                            System.out.println("Invalid option. Try again.");
                    }

                } else {
                    System.out.println("Please enter a number.");
                    input.nextLine();
                }
            }

        } else {
            System.out.println(
                "Username or password incorrect, please try again."
            );
        }

        input.close();
    }
}