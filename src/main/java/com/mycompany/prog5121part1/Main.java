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

        System.out.println(user.returnLoginStatus());

        System.out.println("Thank you for.");

        input.close();
    }
}