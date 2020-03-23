/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author fikri
 */
public class Users {
    private int id;
    private String name;
    private String username;
    private String password;
    private int role;
    
    public Users(int id, String name, String username, int role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.role = role;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getUsername() {
        return username;
    }    
    
    public int getRole() {
        return role;
    }
}
