package AllListForder.Object;

public class EventInHome {
    private int idHomeEvent;
    private String codeEvent,UrlMainEventImg,UrlSideEventImg1,UrlSideEventImg2,UrlSideEventImg3,UrlSideEventImg4;

    public EventInHome(int idHomeEvent, String codeEvent, String urlMainEventImg, String urlSideEventImg1
            , String urlSideEventImg2, String urlSideEventImg3, String urlSideEventImg4) {
        this.idHomeEvent = idHomeEvent;
        this.codeEvent = codeEvent;
        UrlMainEventImg = urlMainEventImg;
        UrlSideEventImg1 = urlSideEventImg1;
        UrlSideEventImg2 = urlSideEventImg2;
        UrlSideEventImg3 = urlSideEventImg3;
        UrlSideEventImg4 = urlSideEventImg4;
    }

    public int getIdHomeEvent() {
        return idHomeEvent;
    }

    public void setIdHomeEvent(int idHomeEvent) {
        this.idHomeEvent = idHomeEvent;
    }

    public String getCodeEvent() {
        return codeEvent;
    }

    public void setCodeEvent(String codeEvent) {
        this.codeEvent = codeEvent;
    }

    public String getUrlMainEventImg() {
        return UrlMainEventImg;
    }

    public void setUrlMainEventImg(String urlMainEventImg) {
        UrlMainEventImg = urlMainEventImg;
    }

    public String getUrlSideEventImg1() {
        return UrlSideEventImg1;
    }

    public void setUrlSideEventImg1(String urlSideEventImg1) {
        UrlSideEventImg1 = urlSideEventImg1;
    }

    public String getUrlSideEventImg2() {
        return UrlSideEventImg2;
    }

    public void setUrlSideEventImg2(String urlSideEventImg2) {
        UrlSideEventImg2 = urlSideEventImg2;
    }

    public String getUrlSideEventImg3() {
        return UrlSideEventImg3;
    }

    public void setUrlSideEventImg3(String urlSideEventImg3) {
        UrlSideEventImg3 = urlSideEventImg3;
    }

    public String getUrlSideEventImg4() {
        return UrlSideEventImg4;
    }

    public void setUrlSideEventImg4(String urlSideEventImg4) {
        UrlSideEventImg4 = urlSideEventImg4;
    }
}
