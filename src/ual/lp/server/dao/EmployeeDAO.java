/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import ual.lp.server.objects.Department;
import ual.lp.server.objects.Employee;

/**
 *
 * @author Divanio Silva, Pedro Almeida e Pedro Tomás
 */
public class EmployeeDAO {

    private DataSourceTransactionManager transactionManager;
    private JdbcTemplate jdbcTemplate;

    public int insertEmployee(Employee employee) {
        String sql = "insert into employee(name, desknumber, iddepartment) values (?, ?, ?);";

        int[] types = {
            Types.VARCHAR, Types.INTEGER, Types.INTEGER
        };
        return jdbcTemplate.update(sql, new Object[]{employee.getName(), employee.getDeskNumber(), employee.getDepartment().getId()}, types);
    }

    public boolean verifyEmployee(Employee employee) {

        String sql = null;

        sql = "select * from department where department = ? and abbreviation = ?;";

        try {
            jdbcTemplate.queryForObject(sql, new Object[]{employee.getDepartment().getName(), employee.getDepartment().getAbbreviation()}, new DepartmentMapper());

        } catch (Exception e) {
            //não existe o departemento, precisamos adiciona-lo na DB
            sql = "insert into department(department, abbreviation) values(?, ?);";

            int[] types = {
                Types.VARCHAR, Types.VARCHAR
            };

            jdbcTemplate.update(sql, new Object[]{employee.getDepartment().getName(), employee.getDepartment().getAbbreviation()}, types);
        }

        //ver se ele já esta na DB.
        sql = "select * from employee where employee.name=?;";

        try {
            //se passar, é pq existe o employee na db.
            jdbcTemplate.queryForObject(sql, new Object[]{employee.getName()}, new simpleEmployeeMapper());

        } catch (Exception e) {
            
        }

        
        
        
        
        
        
        
        
        
        //ver se ele já esta na DB.
        //se não, inseri-lo
        sql = "select * from employee\n"
                + "join department on employee.iddepartment=department.iddepartment\n"
                + "where employee.name=?;";

        try {
            jdbcTemplate.queryForObject(sql, new Object[]{employee.getName()}, new EmployeeMapper());
            return true;

        } catch (EmptyResultDataAccessException e) {
            sql = "insert into employee(name, desknumber, iddepartment) values (?, ?, ?);";

            int[] types = {
                Types.VARCHAR, Types.INTEGER, Types.INTEGER
            };

            jdbcTemplate.update(sql, new Object[]{employee.getName(), employee.getDeskNumber(), employee.getDepartment().getId()}, types);

            return false;
        }
    }

    private static final class EmployeeMapper implements RowMapper<Employee> {

        @Override
        public Employee mapRow(ResultSet rs, int i) throws SQLException {
            Department department = new Department(rs.getInt("iddepartment"), rs.getString("department"), rs.getString("abbreviation"));
            Employee employee = new Employee(rs.getInt("idemployee"), rs.getString("name"), rs.getInt("desknumber"), department);
            return employee;
        }
    }

    private static final class simpleEmployeeMapper implements RowMapper<Employee> {

        @Override
        public Employee mapRow(ResultSet rs, int i) throws SQLException {
            Employee employee = new Employee(rs.getInt("idemployee"), rs.getString("name"), rs.getInt("desknumber"));
            return employee;
        }
    }

    private static final class DepartmentMapper implements RowMapper<Department> {

        @Override
        public Department mapRow(ResultSet rs, int i) throws SQLException {
            Department department = new Department(rs.getInt("iddepartment"), rs.getString("department"), rs.getString("abbreviation"));
            return department;
        }
    }

    public void setTransactionManager(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        DataSource dataSource = transactionManager.getDataSource();
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

}
