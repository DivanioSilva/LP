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
import ual.lp.server.objects.Ticket;

/**
 *
 * @author Pedro
 */
public class TicketMapper implements RowMapper<Ticket> {

    Ticket ticket = new Ticket();

    @Override
    public Ticket mapRow(ResultSet rs, int i) throws SQLException {
        ticket.setIdTicket(rs.getInt("tickets.idticket"));
        ticket.setNumberticket(rs.getInt("tickets.number"));
        ticket.setStatus(rs.getInt("tickets.status"));
        ticket.setTransferId(rs.getInt("tickets.transferid"));
        Department department = new Department(rs.getInt("department.iddepartment"), rs.getString("department.department"), rs.getString("department.abbreviation"));
        ticket.setDepartment(department);
        ticket.setEmployee(new Employee(rs.getInt("employee.idemployee"), rs.getString("employee.name"), rs.getInt("employee.desknumber"), department));
        return ticket;
    }
}
