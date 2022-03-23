package com.arraisicode.customer.controller;

import com.arraisicode.customer.controller.request.CustomerRegistrationRequest;
import com.arraisicode.customer.model.Customer;
import com.arraisicode.customer.service.CustomerService;
import com.arraisicode.customer.util.CustomerExcelExporter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public void registerCustomer(@RequestBody CustomerRegistrationRequest customerRegistrationRequest) {
        log.info("new customer registration {}", customerRegistrationRequest);
        customerService.registerCustomer(customerRegistrationRequest);
    }

    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Customer_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Customer> listUsers = customerService.listAll();

        CustomerExcelExporter excelExporter = new CustomerExcelExporter(listUsers);

        excelExporter.export(response);
    }
}
