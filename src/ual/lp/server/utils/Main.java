/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.utils;

/**
 *
 * @author Pedro
 */
public class Main {
    public static void main(String[] args) {
        Serverconfig config = new Serverconfig();
        config.getDepartments();
        config.getServerIP();
        config.getUrl();
        config.getUsername();
        config.getPassword();
    }
}
