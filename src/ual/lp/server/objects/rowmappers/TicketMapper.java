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
        ticket.setIdTicket(rs.getInt("idticket"));
        ticket.setNumberticket(rs.getInt("number"));
        ticket.setStatus(rs.getInt("status"));
        ticket.setTransferId(rs.getInt("transferid"));
        Department department = new Department(rs.getInt("iddepartment"), rs.getString("department"), rs.getString("abbreviation"));
        ticket.setDepartment(department);
        ticket.setEmployee(new Employee(rs.getInt("idemployee"), rs.getString("name"), rs.getInt("desknumber"), department));
        return ticket;
    }
}
