package org.rmit.assignment.presentation;

import org.rmit.assignment.dao.entity.Claim;
import org.rmit.assignment.service.ClaimProcessManager;
import org.rmit.assignment.utils.AppUtils;

import java.util.List;
import java.util.Scanner;

public class MainMenuApp {
    private final ClaimProcessManager claimProcessManager;

    public MainMenuApp(ClaimProcessManager claimProcessManager) {
        this.claimProcessManager = claimProcessManager;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            AppUtils.clearConsole();

            System.out.println(" ============ Insurance Claims Management System ===========");
            System.out.println("1. View all claims");
            System.out.println("2. Add a new claim");
            System.out.println("3. Update a claim");
            System.out.println("4. Delete a claim");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewAllClaims();
                    break;
                case 2:
                    addNewClaim();
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }
    }


    private void addNewClaim() {

    }

    private void viewAllClaims() {
        List<Claim> all = claimProcessManager.getAll();
        System.out.println("All claims:");
        for (Claim claim : all) {
            System.out.println(claim);
        }
    }
}
