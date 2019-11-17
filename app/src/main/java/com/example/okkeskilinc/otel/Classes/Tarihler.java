package com.example.okkeskilinc.otel.Classes;

public class Tarihler {

    private String id,costumerTc, costumerEntry, costumerOut, roomId;

    public Tarihler(){

    }

    public Tarihler(String id,String costumerTc, String costumerEntry,
                    String costumerOut, String roomId){
        this.id=id;
        this.costumerTc=costumerTc;
        this.costumerEntry=costumerEntry;
        this.costumerOut=costumerOut;
        this.roomId=roomId;
    }

    public void setId(String id){
        this.id=id;
    }
    public String getId(){
        return id;
    }

    public String getCostumerTc(){
        return costumerTc;
    }
    public void setCostumerTc(String cTc){
        this.costumerTc=cTc;
    }

    public String getCostumerEntry(){
        return costumerEntry;
    }
    public void setCostumerEntry(String cEntry){
        this.costumerEntry=cEntry;
    }

    public String getCostumerOut(){
        return costumerOut;
    }
    public void setCostumerOut(String cOut){
        this.costumerOut=cOut;
    }

    public String getRoomId(){
        return roomId;
    }
    public void setRoomId(String rId){
        this.roomId=rId;
    }


}
