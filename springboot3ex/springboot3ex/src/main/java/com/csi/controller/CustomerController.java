package com.csi.controller;

import com.csi.exception.RecordNotFoundException;
import com.csi.model.Customer;
import com.csi.service.CustomerServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @PostMapping("/signup")
    public ResponseEntity<Customer> signUp(@Valid @RequestBody Customer customer) {
        return new ResponseEntity<>(customerServiceImpl.signUp(customer), HttpStatus.CREATED);
    }

    @GetMapping("/signin/{custEmailId}/{custPassword}")
    public ResponseEntity<Boolean> signIn(@PathVariable String custEmailId, @PathVariable String custPassword) {
        return ResponseEntity.ok(customerServiceImpl.signIn(custEmailId, custPassword));
    }

    @GetMapping("/findbyid/{custId}")
    public Optional<Customer> findById(@PathVariable int custId) {
        return customerServiceImpl.findById(custId);
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Customer>> findAll() {
        return ResponseEntity.ok(customerServiceImpl.findAll());
    }

    @PutMapping("/update/{custId}/{customer}")
    public ResponseEntity<Customer> update(@PathVariable int custId, @Valid @RequestBody Customer customer) {

        Customer customer1 = customerServiceImpl.findById(custId).orElseThrow(() -> new RecordNotFoundException("Customer Id does not exist"));

        customer1.setCustPassword(customer.getCustPassword());
        customer1.setCustDOB(customer.getCustDOB());
        customer1.setCustEmailId(customer.getCustEmailId());
        customer1.setCustName(customer.getCustName());
        customer1.setCustAddress(customer.getCustAddress());
        customer1.setCustAccBalance(customer.getCustAccBalance());
        customer1.setCustContactNo(customer.getCustContactNo());
        customer1.setCustEmailId(customer.getCustEmailId());

        return new ResponseEntity<>(customerServiceImpl.update(customer1), HttpStatus.CREATED);
    }

    @GetMapping("/sortbyid")
    public ResponseEntity<List<Customer>> sortById() {
        return ResponseEntity.ok(customerServiceImpl.findAll().stream().sorted(Comparator.comparing(Customer::getCustId)).toList());
    }

    @GetMapping("/sortbyname")
    public ResponseEntity<List<Customer>> sortByName() {
        return ResponseEntity.ok(customerServiceImpl.findAll().stream().sorted(Comparator.comparing(Customer::getCustName)).toList());
    }

    @GetMapping("/sortbyaccbalance")
    public ResponseEntity<List<Customer>> sortByAccBalance() {
        return ResponseEntity.ok(customerServiceImpl.findAll().stream().sorted(Comparator.comparing(Customer::getCustAccBalance)).toList());
    }

    @GetMapping("/sortbydob")
    public ResponseEntity<List<Customer>> sortByDOB() {
        return ResponseEntity.ok(customerServiceImpl.findAll().stream().sorted(Comparator.comparing(Customer::getCustDOB)).toList());
    }

    @GetMapping("/filterbyaccbalance/{accbalance}")
    public ResponseEntity<List<Customer>> filterByAccBalance(@PathVariable double accbalance) {
        return ResponseEntity.ok(customerServiceImpl.findAll().stream().filter(cust -> cust.getCustAccBalance() >= accbalance).toList());
    }

    @GetMapping("/filterbyid/{custId}")
    public ResponseEntity<Customer> filterById(@PathVariable int custId) {
        return ResponseEntity.ok(customerServiceImpl.findAll().stream().filter(cust -> cust.getCustId() == custId).toList().get(0));

    }

    @GetMapping("/filterbyname/{custName}")
    public ResponseEntity<List<Customer>> filterByName(@PathVariable String custName) {
        return ResponseEntity.ok(customerServiceImpl.findAll().stream().filter(cust -> cust.getCustName().equals(custName)).toList());
    }

    @GetMapping("/filterbydob/{custDOB}")
    public ResponseEntity<List<Customer>> filterByDOB(@PathVariable String custDOB) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return ResponseEntity.ok(customerServiceImpl.findAll().stream().filter(cust -> simpleDateFormat.format(cust.getCustDOB()).equals(custDOB)).toList());
    }

    @GetMapping("/filterbyanyinput/{input}")
    public ResponseEntity<List<Customer>> filterByAnyInput(@PathVariable String input) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        return ResponseEntity.ok(customerServiceImpl.findAll().stream().filter(customer -> simpleDateFormat.format(customer.getCustDOB()).equals(input)
                || String.valueOf(customer.getCustId()).equals(input)
                || String.valueOf(customer.getCustContactNo()).equals(input)
                || customer.getCustName().equals(input)
                || customer.getCustEmailId().equals(input)).toList());
    }

    @GetMapping("/sumofaccbalance")
    public ResponseEntity<Double> sumOfAccBalance() {
        double sum = customerServiceImpl.findAll().stream().collect(Collectors.summingDouble(Customer::getCustAccBalance));

        return ResponseEntity.ok(sum);

    }

    @DeleteMapping("/deletebyid/{custId}")

    public ResponseEntity<String> deleteById(@PathVariable int custId) {
        customerServiceImpl.deleteById(custId);

        return ResponseEntity.ok("Data Deleted Successfully");
    }
}
