/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.dao;

import java.sql.Types;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import ual.lp.server.objects.Employee;
import ual.lp.server.objects.rowmappers.DepartmentMapper;
import ual.lp.server.objects.rowmappers.simpleEmployeeMapper;

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

    public void verifyEmployee(Employee employee) {
        boolean hasDepart = false, hasEmployee = false;

        String sql = null;

        sql = "select * from department where department = ? and abbreviation = ?;";

        try {
//            jdbcTemplate.queryForObject(sql, new Object[]{employee.getDepartment().getName(), employee.getDepartment().getAbbreviation()}, new DepartmentMapper());
            jdbcTemplate.queryForObject(sql, new Object[]{employee.getDepartment().getName(), employee.getDepartment().getAbbreviation()}, new DepartmentMapper());
            hasDepart = true;
            System.out.println("O dept existe na db.");
        } catch (Exception e) {
            //não existe o departemento, precisamos adiciona-lo na DB
            sql = "insert into department(department, abbreviation) values(?, ?);";

            int[] types = {
                Types.VARCHAR, Types.VARCHAR
            };

            jdbcTemplate.update(sql, new Object[]{employee.getDepartment().getName(), employee.getDepartment().getAbbreviation()}, types);
            System.out.println("O dept não existia e foi criado agora por mim!");
        }

        //ver se ele já esta na DB.
        sql = "select * from employee where employee.name=?;";

        try {
            //se passar, é pq existe o employee na db.
            jdbcTemplate.queryForObject(sql, new Object[]{employee.getName()}, new simpleEmployeeMapper());

            System.out.println("O employee já esta na db.");

            //verificar se o gajo pertence ao departamento e pertece a secretária.
            try {
                sql = "select * from employee\n"
                        + "join department on employee.iddepartment=department.iddepartment\n"
                        + "where employee.name=? and department.department=? and desknumber=?;";

                Employee emp = jdbcTemplate.queryForObject(sql, new Object[]{employee.getName(), employee.getDepartment().getName(), employee.getDeskNumber()}, new simpleEmployeeMapper());

                System.out.println("Ele pertence mesmo ao departamento");

            } catch (Exception e) {

                String sqlDeptID = "select iddepartment from department where department=? limit 1;";

                int[] types = {
                    Types.VARCHAR
                };

                int idDept = jdbcTemplate.queryForInt(sqlDeptID, new Object[]{employee.getDepartment().getName()}, types);
                employee.getDepartment().setId(idDept);

                System.out.println("Deu merda ao verificar se o employee pertece ao departamento");
                //fazer update do gajo para o departamento certo!
                String sqlUpdate = "update employee set desknumber=?, iddepartment=? where employee.name=?;;";

                int[] typesUpdate = {
                    Types.INTEGER, Types.INTEGER, Types.VARCHAR
                };

                jdbcTemplate.update(sqlUpdate, new Object[]{employee.getDeskNumber(), employee.getDepartment().getId(), employee.getName()}, typesUpdate);

            }

        } catch (Exception e) {
//            System.out.println("Deu merda, o gajo não esta na db, preciso inseri-lo.");

            String sqlDeptID = "select iddepartment from department where department=? limit 1;";

            int[] types = {
                Types.VARCHAR
            };

            int idDept = jdbcTemplate.queryForInt(sqlDeptID, new Object[]{employee.getDepartment().getName()}, types);
            employee.getDepartment().setId(idDept);

            //insert into employee(name, desknumber, iddepartment) values ('Paulo Cabrita', 3, 2);
            sql = "insert into employee(name, desknumber, iddepartment) values (?, ?, ?);";

            int[] types2 = {
                Types.VARCHAR, Types.INTEGER, Types.INTEGER
            };

            jdbcTemplate.update(sql, new Object[]{employee.getName(), employee.getDeskNumber(), employee.getDepartment().getId()}, types2);
            System.out.println("O employee não existia mas acabei de cria-lo!");

        }
    }


    public void setTransactionManager(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        DataSource dataSource = transactionManager.getDataSource();
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

}
