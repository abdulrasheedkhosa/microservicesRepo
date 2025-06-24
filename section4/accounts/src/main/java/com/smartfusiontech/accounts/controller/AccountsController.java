package com.smartfusiontech.accounts.controller;

import com.smartfusiontech.accounts.constants.AccountsConstants;
import com.smartfusiontech.accounts.dto.CustomerDto;
import com.smartfusiontech.accounts.dto.ErrorResponseDto;
import com.smartfusiontech.accounts.dto.ResponseDto;
import com.smartfusiontech.accounts.service.IAccountsService;
import com.smartfusiontech.accounts.service.impl.CsvExportImpService;
import com.smartfusiontech.accounts.service.impl.CsvImportimpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Smartfusiontech
 */

@Tag(
        name = "CRUD REST APIs for Accounts in Smartfusiontech",
        description = "CRUD REST APIs in Smartfusiontech to CREATE, UPDATE, FETCH AND DELETE account details"
)
@RestController
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class AccountsController {
  private static final Logger logger = LoggerFactory.getLogger(AccountsController.class);

  private IAccountsService iAccountsService;
  private CsvImportimpService csvImportService;
  private CsvExportImpService csvExportImpService;

    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create new Customer &  Account inside Smartfusiontech"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        iAccountsService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch Account Details REST API",
            description = "REST API to fetch Customer &  Account details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam
                                                               @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                               String mobileNumber) {
        CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @Operation(
            summary = "Update Account Details REST API",
            description = "REST API to update Customer &  Account details based on a account number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = iAccountsService.updateAccount(customerDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete Account & Customer Details REST API",
            description = "REST API to delete Customer &  Account details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam
                                                                @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                                String mobileNumber) {
        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
        }
    }
  @PostMapping(value = "/csv", consumes = "multipart/form-data")
  public ResponseEntity<String> importCsv(@RequestParam("file") MultipartFile file) {
    try {
      csvImportService.importCsv(file);
      return ResponseEntity.status(HttpStatus.CREATED).body("CSV imported successfully");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("CSV import failed: " + e.getMessage());
    }
  }
    @GetMapping(value = "/export", produces = { "text/csv", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" })
    public ResponseEntity<?> exportData(@RequestParam(defaultValue = "csv") String format) {
      try {
        if ("excel".equalsIgnoreCase(format)) {
          logger.info("Exporting data as Excel file");
          ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
          csvExportImpService.writeCustomersAccountsExcel(outputStream);
          byte[] bytes = outputStream.toByteArray();

          logger.info("Excel export successful, size: {} bytes", bytes.length);

          return ResponseEntity.ok()
              .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=customers_accounts.xlsx")
              .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
              .body(bytes);

        } else {
          logger.info("Exporting data as CSV file");
          StringWriter writer = new StringWriter();
          csvExportImpService.writeCustomersAccountsCsv(writer);
          String csvContent = writer.toString();

          logger.info("CSV export successful, length: {} characters", csvContent.length());

          return ResponseEntity.ok()
              .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=customers_accounts.csv")
              .contentType(MediaType.parseMediaType("text/csv"))
              .body(csvContent);
        }
      } catch (Exception e) {
        logger.error("Export failed due to exception: ", e);
        return ResponseEntity.status(500).body("Export failed: " + e.getMessage());
      }
    }
}
