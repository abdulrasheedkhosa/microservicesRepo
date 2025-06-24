package com.smartfusiontech.accounts.service.impl;

import com.smartfusiontech.accounts.entity.Accounts;
import com.smartfusiontech.accounts.entity.Customer;
import com.smartfusiontech.accounts.repository.AccountsRepository;
import com.smartfusiontech.accounts.repository.CustomerRepository;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;
import java.util.Optional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CsvExportImpService {

  private static final Logger logger = LoggerFactory.getLogger(CsvExportImpService.class);

  private final CustomerRepository customerRepository;
  private final AccountsRepository accountsRepository;

  public CsvExportImpService(CustomerRepository customerRepository, AccountsRepository accountsRepository) {
    this.customerRepository = customerRepository;
    this.accountsRepository = accountsRepository;
  }

  public void writeCustomersAccountsCsv(Writer writer) throws IOException {
    logger.info("Starting CSV export of customers and accounts");

    List<Customer> customers = customerRepository.findAll();
    logger.info("Fetched {} customers from repository", customers.size());

    try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
        .withHeader("customerId", "name", "email", "mobile", "accountNumber", "type", "branch"))) {

      for (Customer customer : customers) {
        Optional<Accounts> accountOpt = accountsRepository.findByCustomerId(customer.getCustomerId());

        if (accountOpt.isPresent()) {
          Accounts account = accountOpt.get();
          csvPrinter.printRecord(
              customer.getCustomerId(),
              customer.getName(),
              customer.getEmail(),
              customer.getMobileNumber(),
              account.getAccountNumber(),
              account.getAccountType(),
              account.getBranchAddress());
          logger.debug("Wrote record for customerId {} with account info", customer.getCustomerId());
        } else {
          csvPrinter.printRecord(
              customer.getCustomerId(),
              customer.getName(),
              customer.getEmail(),
              customer.getMobileNumber(),
              "", "", "");
          logger.debug("Wrote record for customerId {} without account info", customer.getCustomerId());
        }
      }

      csvPrinter.flush();
      logger.info("CSV export completed successfully");
    } catch (IOException e) {
      logger.error("Error during CSV export", e);
      throw e;  // Rethrow to be handled by controller
    }
  }

  public void writeCustomersAccountsExcel(OutputStream outputStream) throws IOException {
    logger.info("Starting Excel export of customers and accounts");

    List<Customer> customers = customerRepository.findAll();
    logger.info("Fetched {} customers from repository", customers.size());

    try (Workbook workbook = new XSSFWorkbook()) {
      Sheet sheet = workbook.createSheet("Customers Accounts");
      Row header = sheet.createRow(0);
      String[] columns = {"Customer ID", "Name", "Email", "Mobile", "Account Number", "Type", "Branch"};
      for (int i = 0; i < columns.length; i++) {
        Cell cell = header.createCell(i);
        cell.setCellValue(columns[i]);
      }

      int rowIdx = 1;
      for (Customer customer : customers) {
        Optional<Accounts> accountOpt = accountsRepository.findByCustomerId(customer.getCustomerId());
        Row row = sheet.createRow(rowIdx++);
        row.createCell(0).setCellValue(customer.getCustomerId());
        row.createCell(1).setCellValue(customer.getName());
        row.createCell(2).setCellValue(customer.getEmail());
        row.createCell(3).setCellValue(customer.getMobileNumber());

        if (accountOpt.isPresent()) {
          Accounts account = accountOpt.get();
          row.createCell(4).setCellValue(account.getAccountNumber());
          row.createCell(5).setCellValue(account.getAccountType());
          row.createCell(6).setCellValue(account.getBranchAddress());

          logger.debug("Row {}: customerId={}, name={}, email={}, mobile={}, accountNumber={}, type={}, branch={}",
              rowIdx - 1,
              customer.getCustomerId(),
              customer.getName(),
              customer.getEmail(),
              customer.getMobileNumber(),
              account.getAccountNumber(),
              account.getAccountType(),
              account.getBranchAddress());
        } else {
          row.createCell(4).setCellValue("");
          row.createCell(5).setCellValue("");
          row.createCell(6).setCellValue("");

          logger.debug("Row {}: customerId={}, name={}, email={}, mobile={}, accountNumber=EMPTY, type=EMPTY, branch=EMPTY",
              rowIdx - 1,
              customer.getCustomerId(),
              customer.getName(),
              customer.getEmail(),
              customer.getMobileNumber());
        }
      }

      workbook.write(outputStream);
      logger.info("Excel export completed successfully");
    } catch (IOException e) {
      logger.error("Error during Excel export", e);
      throw e;  // Rethrow for controller to handle
    }
  }

}
