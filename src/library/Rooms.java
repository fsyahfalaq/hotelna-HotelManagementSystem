/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import javafx.scene.control.Button;

/**
 *
 * @author fikri
 */
public class Rooms {
    private int room_no;
    private String type;
    private int capacity;
    private String status;
    private Button button;

    public Rooms(int Room_no, String Type, int Capacity, String Status){
        this.room_no = Room_no;
    	this.type = Type;
        this.capacity = Capacity;
        this.status = Status;
        this.button = button;
    }

    public int getRoom_no() {
    	return room_no;
    }
    public String getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getStatus() {
        return status;
    }
    
    public Button getButton() {
        return button;
    }
}
