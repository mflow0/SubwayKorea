package com.example.hongssang.subwaykorea;

/**
 * Created by HongSSang on 2016-02-23.
 */
public abstract class Station {
    protected int stationState;
    //0:표시할 도착정보 없음, 1:현재역에 진입중, 2:현재역에 정차중, 3:에러
    protected int stationID;
    protected String extraState;

    public void setStationState(String aState){
        if(aState.equals("nothing")){
            stationState = 0;
        }
        else if(aState.equals("arriving")){
            stationState = 1;
        }
        else if(aState.equals("atStation")){
            stationState = 2;
        }
        else{
            stationState = 3;
        }
    }

    public String getStationState(){
        switch(stationState){
            case 0:
                return "nothing";
            case 1:
                return "arriving";
            case 2:
                return "atStation";
            default:
                return "error";
        }
    }

    public int getID(){
        return stationID;
    }

    public abstract int getNextTime();
    //열차 도착까지의 시간을 얻는 메서드

    public abstract String getExtraState();
    //그 외 추가정보를 얻는 메서드
}
