/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bc.example.camel.web;

import javax.ejb.Stateless;

/**
 *
 * @author bruno
 */
@Stateless
public class HelloSessionBean {

    public String helloMethod(String name) {
        return "Hello "+name;
    }


}
