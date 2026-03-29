package com.example.library_storage.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long user_id;
    private String dni;
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    @OneToMany(mappedBy = "user")
    private List<Loan> loans = new ArrayList<>();;

    public User(String dni, String name, String surname, String address, String phoneNumber) {
        this.dni = dni;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    protected User(){

    }

    public Long getUserID() {
        return user_id;
    }

    public String getDni() {
        return dni;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<Loan> getLoans() {
        return loans;
    }
}
