/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.test;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Pedro
 */
public class Teste {

    public static void main(String[] args) {

        String url = "1234567890";
        int size = url.length();
        int maxSize = 3;
        int blocks = size / maxSize;
        int pos1 = 0;
        int pos2 = maxSize;
        String finalUrl = "";
        List<String> finalUrlList = new LinkedList<>();

        if (blocks >= 1) {

            for (int i = 0; i < (blocks + 1); i++) {

                if (i == blocks) {
                    finalUrl = finalUrl + url.substring(pos2);;

                    break;
                } else {

                    finalUrl = finalUrl + url.substring(pos1, pos2);
                    if (url.substring(pos2).length() != 0) {
                        finalUrl = finalUrl + "\n";
                    } else {
                        break;
                    }

                    if (i != (blocks - 1)) {
                        pos1 = pos1 + maxSize;
                        pos2 = pos2 + maxSize;
                    }
                }
            }

        } else {
            finalUrl = url;
        }

        System.out.println(finalUrl);

        
        
        ////////////////////////////////////////////////////////
        //versão em list
        System.out.println("Versão Lista: ");
        pos1 = 0;
        pos2 = maxSize;
        
        if (blocks >= 1) {

            for (int i = 0; i < (blocks + 1); i++) {

                if (i == blocks) {
                    if (url.substring(pos2).length() != 0){
                        finalUrlList.add(url.substring(pos2));
                    }
                } else {
                    finalUrlList.add(url.substring(pos1, pos2));
                }

                if (i != (blocks - 1)) {
                    pos1 = pos1 + maxSize;
                    pos2 = pos2 + maxSize;
                }

            }

        } else {
            finalUrlList.add(url);
        }
        
        for (String s: finalUrlList) {
            System.out.println(s);
        }

    }
}
