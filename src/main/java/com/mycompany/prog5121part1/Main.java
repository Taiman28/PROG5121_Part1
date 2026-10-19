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
                            System.out.print("How many messages do you want to enter? ");
                            if (!input.hasNextInt()) {
                                System.out.println("Please enter a valid number.");
                                input.nextLine();
                                break;
                            }
                            int numberOfMessages = input.nextInt();
                            input.nextLine();
                            if (numberOfMessages < 1) {
                                System.out.println("Enter at least one message.");
                                break;
                            }
                            for (int i = 1; i <= numberOfMessages; i++) {
                                System.out.println("\nMessage " + i);
                                System.out.print("Enter recipient cell number: ");
                                String recipient = input.nextLine();
                                System.out.print("Enter your message: ");
                                String messageText = input.nextLine();
                                if (messageText.length() > 250) {
                                    System.out.println(
                                        "Please enter a message of less than 250 characters."
                                    );
                                    i--;
                                    continue;
                                }
                                Message message = new Message(
                                    i, recipient, messageText
                                );
                                System.out.println(
                                    "Message ID: " + message.getMessageID()
                                );
                                System.out.println(
                                    "Message Hash: " + message.getMessageHash()
                                );
                                System.out.println(message.SentMessage());
                                System.out.println(
                                    "\n" + Message.printMessages()
                                );
                            }
                            System.out.println(
                                "Total messages sent: "
                                + Message.returnTotalMessagess()
                            );
                            break;
                        case 2:
                            System.out.println("Coming Soon.");
                            break;
                        case 3:
                            System.out.println(
                                "Thank you for using QuickChat."
                            );
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