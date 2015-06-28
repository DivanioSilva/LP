/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.objects.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import ual.lp.server.objects.Department;
import ual.lp.server.objects.Employee;

/**
 *
 * @author Pedro
 */
public class EmployeeMapper implements RowMapper<Employee> {

        @Override
        public Employee mapRow(ResultSet rs, int i) throws SQLException {
            Department department = new Department(rs.getInt("iddepartment"), rs.getString("department"), rs.getString("abbreviation"));
            Employee employee = new Employee(rs.getInt("idemployee"), rs.getString("name"), rs.getInt("desknumber"), department);
            return employee;
        }
    }
