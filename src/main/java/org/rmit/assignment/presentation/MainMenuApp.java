package org.rmit.assignment.presentation;

import org.rmit.assignment.dao.entity.BankingInfo;
import org.rmit.assignment.dao.entity.Claim;
import org.rmit.assignment.dao.entity.Customer;
import org.rmit.assignment.dao.entity.InsuranceCard;
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
            System.out.println("              Insurance Claims Management System            ");
            System.out.println("============================================================");
            System.out.println("|   1. View all claims                                     |");
            System.out.println("|   2. View detail claim                                   |");
            System.out.println("|   3. Add a new claim                                     |");
            System.out.println("|   4. Update a claim                                      |");
            System.out.println("|   5. Delete a claim                                      |");
            System.out.println("|   6. View all customers                                  |");
            System.out.println("|   6. Add a new customer                                  |");
            System.out.println("|   0. Exit                                                |");
            System.out.println("============================================================");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewAllClaims();
                    System.out.print("Press enter to continue...");
                    scanner.nextLine();
                    break;
                case 2:
                    viewDetailClaim();
                    System.out.print("Press enter to continue...");
                    scanner.nextLine();
                    break;
                case 3:
                    addNewClaim();
                    System.out.println("Claim added successfully!");
                    System.out.print("Press enter to continue...");
                    scanner.nextLine();
                    break;
                case 4:
                    updateClaim();
                    System.out.println("Claim updated successfully!");
                    System.out.print("Press enter to continue...");
                    scanner.nextLine();
                    break;
                case 5:
                    boolean isDeleted = deleteClaim();
                    if (isDeleted) {
                        System.out.println("Claim deleted successfully!");
                    } else {
                        System.out.print("Operation cancelled!");
                    }
                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                    break;
                case 0:
                    System.out.println("Exiting program...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }
    }

    private void viewDetailClaim() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter claim ID to view detail: ");
        String claimId = scanner.nextLine();
        Claim claim = claimProcessManager.getOneWithAllData(claimId);
        if (claim == null) {
            System.out.println("Claim not found!");
            return;
        }

        Customer customer = claim.getCustomer();
        InsuranceCard insuranceCard = customer.getInsuranceCard();
        BankingInfo bankingInfo = claim.getBankingInfo();

        System.out.println("==================== Claim detail ===============================");
        System.out.println("Claim ID: " + claim.getId());
        System.out.println("== Customer ID: " + customer.getId());
        System.out.println("== Customer Name: " + customer.getFullName());
        System.out.println("== Customer Type: " + customer.getCustomerType());
        System.out.println("===== Insurance Card Number: " + insuranceCard.getCardNumber());
        System.out.println("===== Policy Owner: " + insuranceCard.getPolicyOwner());
        System.out.println("===== Expiration Date: " + insuranceCard.getExpirationDate());
        System.out.println("Exam Date: " + claim.getExamDate());
        System.out.println("List of documents: " + claim.getListDocuments());
        System.out.println("Claim amount: " + claim.getClaimAmount());
        System.out.println("Status: " + claim.getStatus());
        System.out.println("== Receiver Banking Name: " + bankingInfo.getBankName());
        System.out.println("== Receiver Account Number: " + bankingInfo.getAccountName());
        System.out.println("== Receiver Account Name: " + bankingInfo.getAccountNumber());
        System.out.println("=================================================================");
    }

    private boolean deleteClaim() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter claim ID to delete: ");
        String claimId = scanner.nextLine();
        Claim claim = claimProcessManager.getOne(claimId);
        if (claim == null) {
            System.out.println("Claim not found!");
            return false;
        }

        System.out.println("Are you sure you want to delete this claim? (Y/N)");
        String confirm = scanner.nextLine();
        if (!confirm.equalsIgnoreCase("Y")) {
            return false;
        }

        claimProcessManager.delete(claim);
        return true;
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
        System.out.println("Enter claim by status (new, processing, done, all): ");
        Scanner scanner = new Scanner(System.in);
        String status = scanner.nextLine();

        List<Claim> claims = claimProcessManager.getAllClaims(status);
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
