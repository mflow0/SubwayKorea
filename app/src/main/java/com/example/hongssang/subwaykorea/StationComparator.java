package com.example.hongssang.subwaykorea;

/**
 * Created by HongSSang on 2016-01-18.
 */
public class StationComparator {
    private DeajeonStation previousStation, currentStation;
    private boolean preSet, curSet;

    public StationComparator(){
        preSet = false;
        curSet = false;
        previousStation = null;
        currentStation = null;
    }

    public void setPreviousStation(DeajeonStation aPreviousStation){
        previousStation = aPreviousStation;
        preSet = (previousStation != null);
    }

    public void setCurrentStation(DeajeonStation aCurrentStation){
        currentStation = aCurrentStation;
        curSet = (currentStation != null);
    }

    public boolean setStationState(){
        if(preSet && curSet){
            int curTime = currentStation.getNextTime(),
                 prvTime = previousStation.getNextTime();
            if(curTime < prvTime){
                //현재 역의 열차 도착시간까지 남은 시간이 전 역의 도착시간보다 작을 경우
                //열차가 전 역을 떠나 현재 역으로 향하고 있다는 뜻이다.
                if(curTime < 30){
                    currentStation.setStationState("atStation");
                    currentStation = previousStation = null;
                    preSet = curSet = false;
                    return true;
                    //현재역 도착까지 남은 시간이 30초 이내일 경우 currentStation의 상태가 arriving임
                }
                currentStation.setStationState("arriving");
                currentStation = previousStation = null;
                preSet = curSet = false;
                return true;
            }
            else{
                //전 역의 열차 도착시간까지 남은 시간이 현재 역의 도착시간보다 작을 경우
                //전 역과 현재 역의 사이에 열차는 존재하지 않는다.
                currentStation.setStationState("nothing");
                currentStation = previousStation = null;
                preSet = curSet = false;
                return true;
            }
        }
        else if(preSet && !curSet){
            //조건이 충족되지 않았을 경우
            return false;
        }
        else if(!preSet && curSet){
            return false;
        }
        else{
            return false;
        }
    }
}
