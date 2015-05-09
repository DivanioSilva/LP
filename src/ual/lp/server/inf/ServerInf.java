/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.inf;

import ual.lp.server.objects.Department;
import ual.lp.server.objects.Employee;
import ual.lp.server.objects.Ticket;

/**
 *
 * @author Divanio Silva
 */
public interface ServerInf {
    
    //DS: usar para chamar a próxima senha.
    public Ticket callTicket();
    //Recebe do servidor uma mensagem para saber qual é o próximo a ser chamado.
    public Ticket showNextCallTicket();
    
    //Envia a lista dos departamentos para os callers.
    public Department[] departmentList(); 
    //Recebe um pedito de transferencia para outro departamento,
    //Ver se faz sentindo poder escolher a pessoa do dept que quero transferir.
    public boolean departmentTransfer(Department dept, Ticket ticket);
    //Método que o callser irá chamar para fazer a transfência entre colegas do mesmo dept.
    public boolean colleagueTransfer(Employee employees[], Ticket ticket);
    //Lista de employees que é solicitada pelo caller, estará contida no servidor.
    public Employee[] colleagueList();
}
