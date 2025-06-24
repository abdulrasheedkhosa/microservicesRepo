package com.smartfusiontech.cards.repository;

import com.smartfusiontech.cards.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
