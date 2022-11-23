package com.gkms.Login.repo;

import com.choongang.erpproject.yslogin.domain.emp_table;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpTableRepository extends JpaRepository<emp_table, String> {
    Optional<emp_table> findByEmpId(String empID);
}
