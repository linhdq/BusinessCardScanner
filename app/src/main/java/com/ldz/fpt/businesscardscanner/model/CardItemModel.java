package com.ldz.fpt.businesscardscanner.model;

/**
 * Created by linhdq on 4/20/17.
 */

public class CardItemModel {
    private int cardImage;
    private String name;
    private String phoneNumber;
    private String email;

    public CardItemModel(int cardImage, String name, String phoneNumber, String email) {
        this.cardImage = cardImage;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public int getCardImage() {
        return cardImage;
    }

    public void setCardImage(int cardImage) {
        this.cardImage = cardImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
