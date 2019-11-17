package com.example.okkeskilinc.otel.Classes;

public class Customer {
    private String id,name,TC,telephone,address,date,durum;


    public Customer(){

    }
    public Customer(String id, String name, String TC, String telephone, String address, String tvdate, String AktifDurum)
    {
        this.id=id;
        this.name=name;
        this.TC=TC;
        this.telephone=telephone;
        this.address=address;
        this.date=tvdate;
        this.durum=AktifDurum;
    }


    public void setId(String id){
        this.id=id;
    }
    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }
    public void setName(String Name){
        this.name=Name;
    }

    public String getTC(){
        return TC;
    }
    public void setTC(String TC){
        this.TC=TC;
    }

    public String getTelephone(){
        return telephone;
    }
    public void setTelephone(String Telephone){
        this.telephone=Telephone;
    }

    public String getAddress(){
        return address;
    }
    public void setAddress(String Address){
        this.address=Address;
    }

    public String getDate(){
        return date;
    }
    public void setDate(String Date){
        this.date=Date;
    }

    public String getDurum(){
        return durum;
    }
    public void setDurum(String Durum){
        this.durum=Durum;
    }

}
