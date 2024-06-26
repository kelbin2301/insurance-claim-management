package org.rmit.assignment.presentation;

import org.rmit.assignment.dao.entity.BankingInfo;
import org.rmit.assignment.dao.entity.Claim;
import org.rmit.assignment.dao.entity.Customer;
import org.rmit.assignment.dao.entity.InsuranceCard;
import org.rmit.assignment.enumeration.ClaimStatus;
import org.rmit.assignment.enumeration.CustomerType;
import org.rmit.assignment.service.ClaimProcessManager;
import org.rmit.assignment.utils.AppUtils;
import org.rmit.assignment.utils.DateUtils;
import org.rmit.assignment.utils.IdGeneratorUtils;

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
            System.out.println("|   7. Add a new customer                                  |");
            System.out.println("|   0. Exit                                                |");
            System.out.println("============================================================");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    try {
                        viewAllClaims();
                    } catch (Exception e) {
                        System.out.println("Error occurred while viewing claims!");
                    }
                    System.out.print("Press enter to continue...");
                    scanner.nextLine();
                    break;
                case 2:
                    try {
                        viewDetailClaim();
                    }  catch (Exception e) {
                        System.out.println("Error occurred while viewing claim detail!");
                    }
                    System.out.print("Press enter to continue...");
                    scanner.nextLine();
                    break;
                case 3:
                    try {
                        addNewClaim();
                        System.out.println("Claim added successfully!");
                    } catch (Exception e) {
                        System.out.println("Error occurred while adding new claim!");
                    }
                    System.out.print("Press enter to continue...");
                    scanner.nextLine();
                    break;
                case 4:
                    try {
                        updateClaim();
                        System.out.println("Claim updated successfully!");
                    } catch (Exception e) {
                        System.out.println("Error occurred while updating claim!");
                    }
                    System.out.print("Press enter to continue...");
                    scanner.nextLine();
                    break;
                case 5:
                    try {
                        boolean isDeleted = deleteClaim();
                        if (isDeleted) {
                            System.out.println("Claim deleted successfully!");
                        } else {
                            System.out.print("Operation cancelled!");
                        }
                    } catch (Exception e) {
                        System.out.println("Error occurred while deleting claim!");
                    }
                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                    break;
                case 6:
                    try {
                        viewAllCustomers();
                    } catch (Exception e) {
                        System.out.println("Error occurred while viewing customers!");
                    }
                    System.out.print("Press enter to continue...");
                    scanner.nextLine();
                    break;
                case 7:
                    try {
                        addNewCustomer();
                    } catch (Exception e) {
                        System.out.println("Error occurred while adding new customer!");
                    }
                    System.out.print("Press enter to continue...");
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

    private void addNewCustomer() {
        Scanner scanner = new Scanner(System.in);

        Customer customer = new Customer();

        System.out.println("Enter customer full name: ");
        String fullName = scanner.nextLine();
        customer.setFullName(fullName);

        System.out.println("Enter customer type (policy_holder, dependent): ");
        String customerType = scanner.nextLine();
        CustomerType customerTypeValue = CustomerType.fromValue(customerType);
        if (customerTypeValue == null) {
            System.out.println("Invalid customer type. Please enter a valid customer type.");
            return;
        }
        customer.setCustomerType(customerType);

        if (customerTypeValue == CustomerType.DEPENDENT) {
            System.out.println("Enter policy holder ID: ");
            String policyHolderId = scanner.nextLine();
            customer.setDependentOf(policyHolderId);
        }

        InsuranceCard insuranceCard = new InsuranceCard();
        String cardNumber = IdGeneratorUtils.generateInsuranceCardNumber();
        System.out.println("Insurance card number will be generated automatically. Generated card number: " + cardNumber);
        insuranceCard.setCardNumber(cardNumber);

        System.out.println("Enter policy owner: ");
        String policyOwner = scanner.nextLine();
        insuranceCard.setPolicyOwner(policyOwner);

        System.out.println("Enter expiration date (yyyy-MM-dd): ");
        String expirationDate = scanner.nextLine();
        insuranceCard.setExpirationDate(DateUtils.convertStringToDate(expirationDate));

        customer.setInsuranceCard(insuranceCard);

        claimProcessManager.addCustomer(customer);
    }

    private void viewAllCustomers() {
        List<Customer> customers = claimProcessManager.getAllCustomersInformation();

        System.out.println("+------------+-----------------------+--------------+-----------------+--------------+--------------+--------------+--------------+");
        System.out.println("|    ID      |     Customer Name     | Customer Type | Insurance Card  | Policy Owner | Expiration   | Claim Count  | Total Claim  |");
        System.out.println("+------------+-----------------------+--------------+-----------------+--------------+--------------+--------------+--------------+");
        for (Customer customer : customers) {
            InsuranceCard insuranceCard = customer.getInsuranceCard();
            System.out.printf("| %-10s | %-21s | %-12s | %-15s | %-12s | %-12s | %-12d | %-12.2f |\n",
                    customer.getId(),
                    customer.getFullName(),
                    customer.getCustomerType(),
                    insuranceCard.getCardNumber(),
                    insuranceCard.getPolicyOwner(),
                    insuranceCard.getExpirationDate(),
                    customer.getClaimCount(),
                    customer.getTotalClaimAmount()
            );
        }
        System.out.println("+------------+-----------------------+--------------+-----------------+--------------+--------------+--------------+--------------+");
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
