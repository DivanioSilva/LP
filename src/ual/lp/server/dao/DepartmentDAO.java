/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.dao;

import java.sql.Types;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import ual.lp.server.objects.Department;
import ual.lp.server.objects.rowmappers.DepartmentMapper;

/**
 *
 * @author Divanio Silva
 */
public class DepartmentDAO {
    private DataSourceTransactionManager transactionManager;
    private JdbcTemplate jdbcTemplate;
    
    public void loadDepartmens(List<Department> departments){
        String sql = null;
        for(Department d: departments){
            sql = "select * from department where department = ? and abbreviation = ?;";
        //verifica se o dept existe.
            try {
        //            jdbcTemplate.queryForObject(sql, new Object[]{employee.getDepartment().getName(), employee.getDepartment().getAbbreviation()}, new DepartmentMapper());
                jdbcTemplate.queryForObject(sql, new Object[]{d.getName(), d.getAbbreviation()}, new DepartmentMapper());
                System.out.println("O dept existe na db.");
            } catch (EmptyResultDataAccessException e) {
                //não existe o departemento, precisamos adiciona-lo na DB
                sql = "insert into department(department, abbreviation) values(?, ?);";

                int[] types = {
                    Types.VARCHAR, Types.VARCHAR
                };

                jdbcTemplate.update(sql, new Object[]{d.getName(), d.getAbbreviation()}, types);
                System.out.println("O dept não existia e foi criado agora por mim!");
            }
            
        }
               
    }
    
    public void setTransactionManager(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        DataSource dataSource = transactionManager.getDataSource();
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
