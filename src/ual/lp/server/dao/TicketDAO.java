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
    public int insertTicket(Ticket ticket) {
        String sql = "insert into tickets (number,hour) values (?,curtime());";
//        Object[] params = new Object[]{
//            pessoa.getName(), pessoa.getIdade()
//        };

        int[] types = {
            Types.VARCHAR
        };
        return jdbcTemplate.update(sql, new Object[]{ticket.getNumberTicket()}, types);
    }
    
    public List<Ticket> getTicket(){
        String sql = "select * from tickets where timecall is null;";
        return jdbcTemplate.query(sql, new TicketMapper());
    }
    
    private static final class TicketMapper implements RowMapper<Ticket>{

        @Override
        public Ticket mapRow(ResultSet rs, int i) throws SQLException {
            Ticket ticket = new Ticket(rs.getInt("idticket"), rs.getString("number"), rs.getDate("hour"));
            return ticket;
        }
        
    }
    
    public void setTransactionManager(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        DataSource dataSource = transactionManager.getDataSource();
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
