/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.caller.mgr;

import ual.lp.caller.inf.CallerInf;
import ual.lp.server.objects.Department;
import ual.lp.server.objects.Employee;

/**
 *
 * @author Divanio Silva, Pedro Almeida e Pedro Tomás
 */
public class CallerMGR implements CallerInf{

    @Override
    public void callTicket() {
        //Aceder a um método do servidor para chamar uma senha e imprimi-la na aplicação.
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void knowNextCallTicket() {
        //Thread(?) para saber qual será a próxima senha a ser chamada?
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void colleagueTransfer(Employee[] employees) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void departmentTransfer(Department[] departments) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void numberPeopleWaiting(int qtd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}