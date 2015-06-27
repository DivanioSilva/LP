 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.objects;

import java.util.Date;

/**
 *
 * @author Divanio Silva
 */
public class Main {
    public static void main(String[] args) {
        //departmet e tickets
        Ticket t = new Ticket();
//        t.setNumberTicket("1");
        t.setStatus(0);
        t.setDepartment(new Department(1, null, "S"));
        
        
    }
}
