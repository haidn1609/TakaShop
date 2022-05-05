package AllListForder.Object;

public class MainAdsImg {
    private int idADSInHome;
    private String  codeMainAdsId, local, urlIMG;

    public MainAdsImg(int idADSInHome, String codeMainAdsId, String local, String urlIMG) {
        this.idADSInHome = idADSInHome;
        this.codeMainAdsId = codeMainAdsId;
        this.local = local;
        this.urlIMG = urlIMG;
    }

    public int getId() {
        return idADSInHome;
    }

    public void setId(int id) {
        this.idADSInHome = id;
    }

    public String getCodeMainAdsId() {
        return codeMainAdsId;
    }

    public void setCodeMainAdsId(String codeMainAdsId) {
        this.codeMainAdsId = codeMainAdsId;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getUrlIMG() {
        return urlIMG;
    }

    public void setUrlIMG(String urlIMG) {
        this.urlIMG = urlIMG;
    }
}
