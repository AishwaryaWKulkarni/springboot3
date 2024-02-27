package com.csi.service;

import com.csi.model.Customer;
import com.csi.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomerServiceImpl {

    @Autowired
    private CustomerRepository customerRepositoryImpl;

    public Customer signUp(Customer customer) {
        return customerRepositoryImpl.save(customer);
    }

    public boolean signIn(String custEmailId, String custPassword) {
        boolean flag = false;
        Customer customer = customerRepositoryImpl.findByCustEmailIdAndCustPassword(custEmailId, custPassword);

        if (customer != null && customer.getCustEmailId().equals(custEmailId) && customer.getCustPassword().equals(custPassword)) {
            flag = true;
        }
        return flag;

    }

    @Cacheable(value = "custId")
    public Optional<Customer> findById(int custId) {
        log.info("#######Fetching data from DB:" + custId);
        return customerRepositoryImpl.findById(custId);
    }

    public List<Customer> findAll() {
        return customerRepositoryImpl.findAll();
    }

    public Customer update(Customer customer) {
        return customerRepositoryImpl.save(customer);
    }

    public void deleteById(int custId) {
        customerRepositoryImpl.deleteById(custId);
    }


}



