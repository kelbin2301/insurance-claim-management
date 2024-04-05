package org.rmit.assignment.presentation;

import org.rmit.assignment.dao.entity.BankingInfo;
import org.rmit.assignment.dao.entity.Claim;
import org.rmit.assignment.enumeration.ClaimStatus;
import org.rmit.assignment.service.ClaimProcessManager;
import org.rmit.assignment.utils.AppUtils;
import org.rmit.assignment.utils.DateUtils;

import java.util.Arrays;
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

            System.out.println("============================================================");
            System.out.println("              Insurance Claims Management System             ");
            System.out.println("============================================================");
            System.out.println("|   1. View all claims                                     |");
            System.out.println("|   2. Add a new claim                                     |");
            System.out.println("|   3. Update a claim                                      |");
            System.out.println("|   4. Delete a claim                                      |");
            System.out.println("|   5. Exit                                                |");
            System.out.println("============================================================");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewAllClaims();
                    System.out.println("Press any key to continue...");
                    scanner.nextLine();
                    break;
                case 2:
                    addNewClaim();
                    System.out.println("Claim added successfully!");
                    System.out.println("Press any key to continue...");
                    scanner.nextLine();
                    break;
                case 3:
                    updateClaim();
                    System.out.println("Claim updated successfully!");
                    System.out.println("Press any key to continue...");
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

    private void updateClaim() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter claim ID to update: ");
        String claimId = scanner.nextLine();
        Claim claim = claimProcessManager.getOne(claimId);
        if (claim == null) {
            System.out.println("Claim not found!");
            return;
        }

        System.out.println("Enter new exam date (yyyy-MM-dd). Current exam date: " + claim.getExamDate());
        String examDate = scanner.nextLine();
        claim.setExamDate(DateUtils.convertStringToDate(examDate));

        System.out.println("Enter new list documents name, separate by a comma. Current list documents: " + claim.getListDocuments());
        String listDocuments = scanner.nextLine();
        claim.setListDocuments(listDocuments);

        System.out.println("Enter new claim amount. Current claim amount: " + claim.getClaimAmount());
        double claimAmount = scanner.nextDouble();
        claim.setClaimAmount(claimAmount);
        scanner.nextLine();

        System.out.println("Enter status. Status option: " + ClaimStatus.getValuesByString() + ". Current status: " + claim.getStatus());
        String status = scanner.nextLine();
        if (Arrays.stream(ClaimStatus.values()).noneMatch(claimStatus -> claimStatus.getValue().equals(status.toLowerCase()))) {
            System.out.println("Invalid status. Please enter a valid status.");
            return;
        }
        claim.setStatus(status);

        claimProcessManager.update(claim);
    }


    private void addNewClaim() {
        Scanner scanner = new Scanner(System.in);

        Claim claim = new Claim();

        System.out.println("Enter customer ID: ");
        String customerId = scanner.nextLine();
        claim.setCustomerId(customerId);

        System.out.println("Enter exam date (yyyy-MM-dd): ");
        String examDate = scanner.nextLine();
        claim.setExamDate(DateUtils.convertStringToDate(examDate));

        System.out.println("Enter list documents name, separate by a comma: ");
        String listDocuments = scanner.nextLine();
        claim.setListDocuments(listDocuments);

        System.out.println("Enter claim amount: ");
        double claimAmount = scanner.nextDouble();
        claim.setClaimAmount(claimAmount);
        scanner.nextLine();

        System.out.print("Enter receiver banking information: ");
        BankingInfo bankingInfo = new BankingInfo();
        System.out.println("Enter banking name: ");
        String bankingName = scanner.nextLine();
        bankingInfo.setBankName(bankingName);

        System.out.println("Enter account number: ");
        String accountNumber = scanner.nextLine();
        bankingInfo.setAccountNumber(accountNumber);

        System.out.println("Enter banking account name: ");
        String accountName = scanner.nextLine();
        bankingInfo.setAccountName(accountName);

        claim.setBankingInfo(bankingInfo);

        claimProcessManager.add(claim);
    }

    private void viewAllClaims() {
        List<Claim> claims = claimProcessManager.getAllClaims();
        System.out.println("+------------+------------+-----------------------+--------------+-----------------+--------------+--------------+--------------+--------------+--------------+");
        System.out.println("|    ID      | CustomerID |     CustomerName      | CustomerType |    ExamDate    |  Documents   | ClaimAmount  |    Status    | Banking Name | Account No   |");
        System.out.println("+------------+------------+-----------------------+--------------+-----------------+--------------+--------------+--------------+--------------+--------------+");

        for (Claim claim : claims) {
            String bankingName = claim.getBankingName() != null ? claim.getBankingName() : "";
            String accountNumber = claim.getBankingAccountNumber() != null ? claim.getBankingAccountNumber() : "";
            System.out.printf("| %-10s | %-10s | %-21s | %-12s | %-15s | %-12s | %-12.2f | %-12s | %-12s | %-12s |\n",
                    claim.getId(),
                    claim.getCustomerId(),
                    claim.getCustomerName(),
                    claim.getCustomerType(),
                    claim.getExamDate(),
                    claim.getListDocuments(),
                    claim.getClaimAmount(),
                    claim.getStatus(),
                    bankingName,
                    accountNumber
            );
        }
        System.out.println("+------------+------------+-----------------------+--------------+-----------------+--------------+--------------+--------------+--------------+--------------+");
    }
}
