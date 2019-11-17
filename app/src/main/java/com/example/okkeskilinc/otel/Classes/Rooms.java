package com.example.okkeskilinc.otel.Classes;

public class Rooms {
    private String no,id,kNo,durum,type,cutomerId;
    public Rooms(){}

    public Rooms(String id, String OdaNo,String katno, String type, String durum){
        this.durum=durum;
        this.no=OdaNo;
        this.kNo=katno;
        this.id=id;
        this.type=type;
    }

    public Rooms(String id,String OdaNo,String durum){
        this.id=id;
        this.durum=durum;
        this.no=OdaNo;
    }

    public String getNo(){
        return no;
    }
    public void setNo(String No){
        this.no=No;
    }
    public String getType(){
        return type;
    }
    public void setType(String oType){
        this.type=oType;
    }

    public String getkatNo(){
        return kNo;
    }
    public void setkatNo(String katNo){
        this.kNo=katNo;
    }

    public void setId(String id){
        this.id=id;
    }
    public String getId(){
        return id;
    }

    public void setDurum(String OdaDurum){
        this.durum=OdaDurum;
    }
    public String getDurum(){
        return durum;
    }
}
