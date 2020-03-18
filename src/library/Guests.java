/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

/**
 *
 * @author fikri
 */
public class Guests {
    private int bookingId;
    private String name;
    private String address;
    private String phone;
    private String identityNumber;
    private int room;

    public Guests(int bookingId, String name, String address, String phone, String identityNumber, int room) {
        this.bookingId = bookingId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.identityNumber = identityNumber;
        this.room = room;
    }
    
    public int getBookingId() {
        return bookingId;
    }
    
    public String getName() {
        return name;
    }
    
    public String getAddress() {
        return address;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public String getIdentityNumber() {
        return identityNumber;
    }
    
    public int getRoom() {
        return room;
    }
}


