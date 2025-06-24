package com.smartfusiontech.cards.service.impl;

import com.smartfusiontech.cards.entity.Address;
import com.smartfusiontech.cards.entity.Employee;
import com.smartfusiontech.cards.repository.AddressRepository;
import com.smartfusiontech.cards.repository.EmployeeRepository;
import com.smartfusiontech.cards.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {
  @Autowired
  private EmployeeRepository employeeRepository;
  @Autowired
  private AddressService addressService;
  @Transactional
  public Employee addEmployee(Employee employee) {
    Employee objEmployee = this.employeeRepository.save(employee);
    Address objAddress = null;
    objAddress.setId(123L);
    objAddress.setAddress("Islamabad ");
    objAddress.setEmployee(objEmployee);
    this.addressService.AddressService(objAddress);
    return objEmployee;
  }
}
