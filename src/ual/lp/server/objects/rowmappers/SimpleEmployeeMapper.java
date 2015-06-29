package ual.lp.server.objects.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import ual.lp.server.objects.Employee;

/**
 *
 * @author Pedro
 */
 public class SimpleEmployeeMapper implements RowMapper<Employee> {

        @Override
        public Employee mapRow(ResultSet rs, int i) throws SQLException {
            Employee employee = new Employee(rs.getInt("employee.idemployee"), rs.getString("employee.name"), rs.getInt("employee.desknumber"));
            return employee;
        }
    }