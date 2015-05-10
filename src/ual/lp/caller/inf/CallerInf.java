/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.caller.inf;

import ual.lp.caller.dataSource.EmployeeSource;

/**
 *
 * @author Divanio Silva, Pedro Almeida e Pedro Tomás
 */
public interface CallerInf {
    
    //DS: usar para chamar a próxima senha.
    //Devolve uma string para imprimir o que ele chamou.
    public String callTicket();
    //Recebe do servidor uma mensagem para saber qual é o próximo a ser chamado.
    public String showNextCallTicket();
    //Recebe a lista dos departamentos para imprimi-los na dropdown list, será 
    //útil para quando for selecionar o dept para as transferências.
//    public void departmentTransfer(Department departments[]);
    //Mesma finalidade do método acima, mas contém a lista dos colaboradores do
    //que estão em atendimento no mesmo departamento.
    public EmployeeSource colleagueList();
    //Saber a quantidade de senhas estão em espera.
//    public void numberPeopleWaiting(int qtd);
}
