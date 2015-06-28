/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import ual.lp.server.objects.Department;
import ual.lp.server.objects.Employee;
import ual.lp.server.objects.Ticket;
import ual.lp.server.objects.rowmappers.TicketMapper;

/**
 *
 * @author Divanio Silva, Pedro Almeida e Pedro Tomás
 */
public class TicketDAO {

    private DataSourceTransactionManager transactionManager;
    private JdbcTemplate jdbcTemplate;

    public int createTicket(int number, int idDept) {
//        String sql = "insert into tickets (number,hour) values (?,curtime());";
        String sql = "insert into tickets(number, createhour, status, iddepartment) values(?, now(), 0, ?);";

        int[] types = {
            Types.INTEGER, Types.INTEGER
        };
        return jdbcTemplate.update(sql, new Object[]{number, idDept}, types);
    }

    /**
     * Método para buscar o próximo ticket para caller. Será usado quando o
     * botão next for acionado, sem transferência.
     *
     * @return
     */
    public Ticket getNextTicket(Employee employee) {
        String sql = null;
        Ticket ticket;

        //verifica se existe transferência para aquele employee
        try {
            sql = "select *from tickets \n"
                    + "join department on tickets.iddepartment=department.iddepartment \n"
                    + "join employee on employee.iddepartment=department.iddepartment\n"
                    + "where tickets.status=0 and employee.idemployee=? and transferid=? order by createhour limit 1;";

            ticket = jdbcTemplate.queryForObject(sql, new Object[]{employee.getEmpNumber(), employee.getEmpNumber()}, new TicketMapper());

            System.out.println("Possui transferências!\nID Ticket: " + ticket.getIdTicket());
            return ticket;

        } catch (EmptyResultDataAccessException e) {
            System.out.println("Não há transferências!");
            //ele não possui transferências!
        }

        try {
            sql = "select * \n"
                    + "from tickets \n"
                    + "join department on tickets.iddepartment=department.iddepartment\n"
                    + "join employee on employee.iddepartment=department.iddepartment\n"
                    + "where tickets.status=0 and employee.idemployee=? order by createhour limit 1;";
            return jdbcTemplate.queryForObject(sql, new Object[]{employee.getEmpNumber()}, new TicketMapper());
            //A fila possui tickets para serem atendidos, será atribuido o mais antigo para este caller.
            
            
        } catch (EmptyResultDataAccessException e) {
//            System.out.println("Deu merda dentro do segundo try/catch");
            System.out.println("Não existem tickets para serem atendidos nesta fila.");
            return null;
        }
    }

    /**
     * Método para encerar o ticket.
     *
     * @param ticket
     */
    public void closeTicket(Ticket ticket) {
        String sql = "update tickets set tickets.status=2, tickets.timecall = now() where tickets.idticket=?;";

        int[] types = {
            Types.INTEGER
        };

        jdbcTemplate.update(sql, new Object[]{ticket.getIdTicket()}, types);
    }

    public List<Ticket> getTickets() {
        String sql = "select * from tickets where timecall is null;";

        return jdbcTemplate.query(sql, new TicketMapper());
    }

    public void setTransactionManager(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        DataSource dataSource = transactionManager.getDataSource();
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
}

//        public int insertTicket(Ticket ticket) {
////        String sql = "insert into tickets (number,hour) values (?,curtime());";
//        String sql = "insert into tickets(number, createhour, status, iddepartment) values('1', now(), 0, 1);";
////        Object[] params = new Object[]{
////            pessoa.getName(), pessoa.getIdade()
////        };
//
//        int[] types = {
//            Types.VARCHAR
//        };
//        return jdbcTemplate.update(sql, new Object[]{ticket.getNumberTicket()}, types);
//    }
//    
//    public List<Ticket> getTicket(){
//        String sql = "select * from tickets where timecall is null;";
//        return jdbcTemplate.query(sql, new TicketMapper());
//    }
