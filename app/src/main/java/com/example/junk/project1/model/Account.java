package com.example.junk.project1.model;

public class Account {

    private String username;
    private String password;
    private String priceRange;
    private String clothesCategory;
    private String cardType;
    private String cardNumber;
    private String expiryDate;
    private String cvcCode;
    private long dbId;

    // constructor that will include all account field variables including long dbID
    //used in accountDb class
    public Account(String username, String password, String priceRange,
                   String clothesCategory, String cardType, String cardNumber,
                   String expiryDate, String cvcCode, long dbId) {

        this.username = username;
        this.password = password;
        this.priceRange = priceRange;
        this.clothesCategory = clothesCategory;
        this.cardType = cardType;
        this.cardNumber =cardNumber;
        this.expiryDate = expiryDate;
        this.cvcCode = cvcCode;
        this.dbId = dbId;

    }

    // constructor for creating instances of account. Used in createAccount activity
    public Account (String username, String password,  String priceRange, String clothesCategory,
                    String cardType, String cardNumber, String expiryDate, String cvcCode){

        this.username = username;
        this.password = password;
        this.priceRange = priceRange;
        this.clothesCategory = clothesCategory;
        this.cardType = cardType;
        this.cardNumber =cardNumber;
        this.expiryDate = expiryDate;
        this.cvcCode = cvcCode;
    }

    //getters and setters for all account field variable
    public String getUsername(){ return username; }

    public String getPassword(){ return password; }

    public String getPriceRange(){ return priceRange; }

    public String getClothesCategory(){ return clothesCategory; }

    public String getCardType() { return cardType; }

    public String getCardNumber() { return cardNumber; }

    public String getExpiryDate() { return expiryDate; }

    public String getCvcCode() { return cvcCode; }

    public void setDbId(long dbId){
        this.dbId = dbId;
    }


    // 'toString' to return textual representation of an object
    @Override
    public String toString() {
        return(username + password  + priceRange + clothesCategory + cardType +
                cardNumber + expiryDate + cvcCode);
    }

}
