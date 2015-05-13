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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import ual.lp.server.objects.Ticket;

/**
 *
 * @author Divanio Silva, Pedro Almeida e Pedro Tomás
 */
public class TicketDAO {
    private DataSourceTransactionManager transactionManager;
    private JdbcTemplate jdbcTemplate;
    
   //------>A preparar implementaçao deste DAO depois de falar com o divanio
//    public int insertTicket(Ticket ticket) {
//        String sql = "insert into employees(name, department) values (?, ?);";
////        Object[] params = new Object[]{
////            pessoa.getName(), pessoa.getIdade()
////        };
//
//        int[] types = {
//            Types.VARCHAR, Types.VARCHAR
//        };
//        return jdbcTemplate.update(sql, new Object[]{employee.getName(), employee.getDepartment()}, types);
//    }
//    
//    public Ticket getEmployee(Ticket ticket){
//        String sql = "select * from employees where idemployees=?";
//        return jdbcTemplate.queryForObject(sql, new Object[]{employee.getEmpNumber()}, new EmployeeMapper());
//    }
//    
//    private static final class TicketMapper implements RowMapper<Employee>{
//
//        @Override
//        public Ticket mapRow(ResultSet rs, int i) throws SQLException {
//            Employee employee = new Employee(rs.getInt("idemployees"), rs.getString("name"), rs.getString("department"));
//            return employee;
//        }
//        
//    }
    
    public void setTransactionManager(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        DataSource dataSource = transactionManager.getDataSource();
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
