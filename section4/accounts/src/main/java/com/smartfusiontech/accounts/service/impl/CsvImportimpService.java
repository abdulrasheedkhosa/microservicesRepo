package com.smartfusiontech.accounts.service.impl;

import com.smartfusiontech.accounts.entity.Accounts;
import com.smartfusiontech.accounts.entity.Customer;
import com.smartfusiontech.accounts.repository.AccountsRepository;
import com.smartfusiontech.accounts.repository.CustomerRepository;
import java.util.Optional;
import java.util.Random;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
public class CsvImportimpService {

  private final CustomerRepository customerRepository;
  private final AccountsRepository accountsRepository;
  private final Random random = new Random();

  public CsvImportimpService(CustomerRepository customerRepository, AccountsRepository accountsRepository) {
    this.customerRepository = customerRepository;
    this.accountsRepository = accountsRepository;
  }

  // Public method to be called by controller
  public void importCsv(MultipartFile file) throws Exception {
    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
      reader.lines()
          .skip(1) // skip header line
          .filter(line -> !line.isBlank())
          .map(this::parseCsvLine)
          .flatMap(Optional::stream)
          .forEach(this::processRecord);
    }
  }

  // Parse CSV line to fields - expect 6 columns now (no customerId)
  private Optional<String[]> parseCsvLine(String line) {
    String[] parts = line.split(",");
    return parts.length == 6 ? Optional.of(parts) : Optional.empty();
  }

  // Process one CSV record
  private void processRecord(String[] data) {
    // Columns: name,email,mobile,accountNumber,type,branch
    var name = data[0].trim();
    var email = data[1].trim();
    var mobile = data[2].trim();
    var accountNumberOpt = parseLong(data[3]);
    var accountType = data[4].trim();
    var branchAddress = data[5].trim();

    // Find customer by mobile (unique) or create new
    Customer customer = customerRepository.findByMobileNumber(mobile)
        .orElseGet(Customer::new);

    customer.setName(name);
    customer.setEmail(email);
    customer.setMobileNumber(mobile);
    Customer savedCustomer = customerRepository.save(customer);

    // Find or create Account
    Accounts account = accountNumberOpt
        .flatMap(accountsRepository::findById)
        .orElseGet(Accounts::new);

    if (account.getAccountNumber() == null) {
      account.setAccountNumber(generateAccountNumber());
    }

    account.setCustomerId(savedCustomer.getCustomerId());
    account.setAccountType(accountType);
    account.setBranchAddress(branchAddress);

    accountsRepository.save(account);
  }

  private Optional<Long> parseLong(String s) {
    try {
      return Optional.of(Long.parseLong(s.trim()));
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  private long generateAccountNumber() {
    return 1000000000L + random.nextInt(900000000);
  }
}
