/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.dao;

import java.sql.Types;
import java.util.List;
import javax.sql.DataSource;
import javax.swing.JOptionPane;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import ual.lp.exceptions.NoTicketsException;
import ual.lp.server.objects.Department;
import ual.lp.server.objects.Employee;
import ual.lp.server.objects.Ticket;
import ual.lp.server.objects.rowmappers.DepartmentMapper;
import ual.lp.server.objects.rowmappers.SimpleTicketMapper;
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
    public Ticket getNextTicket(Employee employee) throws NoTicketsException {
        String sql, sqlUpdate = null;
        Ticket ticket;

        //verifica se existe transferência para aquele employee
        try {
            sql = "select * from tickets \n"
                    + "join department on tickets.iddepartment=department.iddepartment \n"
                    + "join employee on employee.iddepartment=department.iddepartment \n"
                    + "where tickets.status=0 and employee.idemployee=transferid and employee.name=? order by createhour limit 1;";

            ticket = jdbcTemplate.queryForObject(sql, new Object[]{employee.getName()}, new TicketMapper());

            sqlUpdate = "update tickets \n"
                    + "set tickets.idemployee=?, tickets.status=1, tickets.timecall=now()\n"
                    + "where tickets.idticket=?;";

            int[] types = {
                Types.INTEGER, Types.INTEGER
            };
            System.out.println(ticket.getEmployee().getEmpNumber() + " " + ticket.getEmployee().getName());

            jdbcTemplate.update(sqlUpdate, new Object[]{ticket.getEmployee().getEmpNumber(), ticket.getIdTicket()}, types);

            System.out.println("Possui transferências!\nID Ticket: " + ticket.getIdTicket());

            return ticket;

        } catch (EmptyResultDataAccessException e) {
            System.out.println("Não há transferências!");
            //ele não possui transferências!
        }

        //A fila possui tickets para serem atendidos, será atribuido o mais antigo para este caller.
        //Sem transferências.
        try {
            sql = "select * from tickets join department on tickets.iddepartment=department.iddepartment \n"
                    + "join employee on employee.iddepartment=department.iddepartment \n"
                    + "where tickets.status=0 and employee.name=? and tickets.transferid is null order by createhour limit 1;";

            ticket = jdbcTemplate.queryForObject(sql, new Object[]{employee.getName()}, new TicketMapper());

            sqlUpdate = "update tickets \n"
                    + "set tickets.idemployee=?, tickets.status=1, tickets.timecall=now()\n"
                    + "where tickets.idticket=?;";

            int[] types = {
                Types.INTEGER, Types.INTEGER
            };

            jdbcTemplate.update(sqlUpdate, new Object[]{ticket.getEmployee().getEmpNumber(), ticket.getIdTicket()}, types);
            System.out.println("O ticket atribuido é " + ticket.getNumberticket() + " e o colaborador é "
                    + ticket.getEmployee().getName() + ".\nidTicket " + ticket.getIdTicket());
            return ticket;

        } catch (EmptyResultDataAccessException e) {
//            System.out.println("Deu merda dentro do segundo try/catch");
            System.out.println("Não existem tickets para serem atendidos nesta fila.");
            throw new NoTicketsException("Não há tickets");
        }
    }

    /**
     * Método utilizado pelo mgr/dispenser para inserir um ticket.
     *
     * @param dept nome do departamento
     * @return o ticket para ser impresso pelo dispenser
     */
    public String autoCreateTicket(String dept) {
        Ticket ticket = new Ticket();
        String sql, ticketNumber = null;
        String curTime;
        curTime = String.valueOf(System.currentTimeMillis()).substring(0, 10);
        //nivelar com o PT os novos parâmetros de retorno do ticket, foi adicionado o clientkey para a password do qrcode.

        try {
            sql = "select * from tickets join department on tickets.iddepartment=department.iddepartment\n"
                    + "where tickets.reset is null and department.department=? order by tickets.createhour desc limit 1;";

            ticket = jdbcTemplate.queryForObject(sql, new Object[]{dept}, new SimpleTicketMapper());

//            sql = "insert into tickets(number, createhour, status, iddepartment) values(?, now(), 0, ?);";
            sql = "insert into tickets(number, createhour, status, iddepartment, clientkey) values(?, now(), 0, ?, ?);";

            int[] types = {
                Types.INTEGER, Types.INTEGER, Types.VARCHAR
            };
            jdbcTemplate.update(sql, new Object[]{ticket.getNumberticket() + 1, ticket.getDepartment().getId(), curTime}, types);
            //S01
//            ticketNumber = String.valueOf(ticket.getNumberticket() + 1);
//            ticketNumber = ticket.getDepartment().getAbbreviation() + "" + String.valueOf(ticket.getNumberticket() + 1);
            ticketNumber = ticket.getDepartment().getAbbreviation() + "" + String.valueOf(ticket.getNumberticket() + 1 + "," + curTime);
//                        ticketNumber = ticket.getDepartment().getAbbreviation() + "" + String.valueOf(ticket.getNumberticket() + 1 + ",ola"+ curTime );
            //,http://192.168.1.101:8081/LPWebExterno/Servlet?clientkey=
            //T01
            System.out.println("Irei inserir na db o seguinte ticket number: " + ticketNumber);
            return ticketNumber;

        } catch (EmptyResultDataAccessException e) {

            sql = "select *\n"
                    + "from department \n"
                    + "where department.department=? limit 1;";

//            int[] types = {
//                Types.INTEGER, Types.INTEGER
//            };
//            int deptId = jdbcTemplate.queryForInt(sql, new Object[]{dept});
            Department department;
            department = jdbcTemplate.queryForObject(sql, new Object[]{dept}, new DepartmentMapper());

//            System.out.println("Não existem tickets para serem atendidos nesta fila.");
            sql = "insert into tickets(number, createhour, status, iddepartment, clientkey) values(1, now(), 0, ?, ?);";

            int[] typesForDepartId = {
                Types.INTEGER, Types.VARCHAR
            };

            jdbcTemplate.update(sql, new Object[]{department.getId(), curTime}, typesForDepartId);

            System.out.println("Acabei de inserir um ticket com o numero" + department.getAbbreviation() + "" + 1);

            return department.getAbbreviation() + 1 + "," + curTime;
        }
    }

    /**
     * Método para fazer a transferência do ticket.
     *
     * @param employee
     */
    public void transferTicket(Ticket ticket) {

        String sql = "update tickets \n"
                + "set tickets.transferid=?, tickets.status=0\n"
                + "where tickets.idticket=?;";

        int[] types = {
            Types.INTEGER, Types.INTEGER
        };

        jdbcTemplate.update(sql, new Object[]{ticket.getTransferId(), ticket.getIdTicket()}, types);

        System.out.println("O ticket " + ticket.getIdTicket() + " foi transferido do colaborador " + ticket.getEmployee().getName() + ""
                + " para o employee com o id " + ticket.getTransferId());

    }

    /**
     * Método para encerar o ticket.
     *
     * @param ticket
     */
    public void closeTicket(Ticket ticket) {
        String sql = "update tickets set tickets.status=2, tickets.closedcall = now() where tickets.idticket=?;";

        int[] types = {
            Types.INTEGER
        };

        jdbcTemplate.update(sql, new Object[]{ticket.getIdTicket()}, types);
    }

    public List<Ticket> getTickets() {
        String sql = "select * from tickets where closedcall is null;";

        return jdbcTemplate.query(sql, new TicketMapper());
    }

    /**
     * Método utilizado no final do dia para fechar todos os tickets que
     * encontram-se em aberto.
     */
    public void closeDay() {
        String sql = "update tickets set tickets.closedcall=now(), tickets.status=3\n"
                + "where tickets.status=0;";

        jdbcTemplate.update(sql);

        //fazer o reset em todos os tickets
        sql = "update tickets set tickets.closedcall=now(), tickets.reset=1\n"
                + "where tickets.reset is null;";

        jdbcTemplate.update(sql);
    }

    /**
     * Método para fazer o reset de uma determinada fila
     *
     * @param department que será feito o reset
     */
    public void resetQueue(Employee employee) {
        //saber o id do department.
        String sql = null;

        sql = "select department.iddepartment from department where department.department=?;";

        int[] types = {
            Types.VARCHAR
        };

        int deptID = jdbcTemplate.queryForInt(sql, new Object[]{employee.getDepartment().getName()}, types);
//        department.setId(deptID);
        employee.getDepartment().setId(deptID);
        //encerrar todos os tickets que estão por atender.
        sql = "update tickets set tickets.closedcall=now(), tickets.status=3, tickets.reset=1\n"
                + "where tickets.iddepartment=?;";

        int[] typesUpdate = {
            Types.INTEGER
        };

        jdbcTemplate.update(sql, new Object[]{employee.getDepartment().getId()}, typesUpdate);

        sql = "update tickets set tickets.closedcall=now(), tickets.reset=1\n"
                + "where tickets.reset is null;";

        jdbcTemplate.update(sql);

    }

//    public ticket showToDisplay(Department department){
//        Ticket ticket;
//        String sql = null;
//        
//        sql = "select iddepartment from department where department.department=?;";
//
//        int[] types = {
//            Types.VARCHAR
//        };
//
//        int deptID = jdbcTemplate.queryForInt(sql, new Object[]{department.getName()}, types);
//        department.setId(deptID);
//        return ticket;
//    }
    /**
     * @param department
     * @return o último ticket criado para aquele departamento.
     */
    public Ticket getLastTicket(Department department) throws NoTicketsException{
        Ticket ticket = new Ticket();

        try {
            String sql = "select * from tickets\n"
                    + "join department on department.iddepartment=tickets.iddepartment\n"
                    + "where department.department=? and tickets.status=0 order by tickets.createhour desc limit 1;";

            ticket = jdbcTemplate.queryForObject(sql, new Object[]{department.getName()}, new SimpleTicketMapper());
            
            System.out.println("O último ticket é o: "+ ticket.getDepartment().getAbbreviation()+ticket.getNumberticket());
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Não existem tickets para serem atendidos.\nCaiu na exception!");
//            ticket.setNumberticket(00);
//            ticket.setDepartment(new Department(department.getAbbreviation()));
            throw new NoTicketsException("Não existem tickets para serem atendidas.");
        }
        return ticket;
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
//        String sql = "select * from tickets where closedcall is null;";
//        return jdbcTemplate.query(sql, new TicketMapper());
//    }
