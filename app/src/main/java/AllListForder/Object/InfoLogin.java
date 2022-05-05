package AllListForder.Object;

public class InfoLogin {
    private int idCheck;
    private String infoLogin, nameUserLogin;

    public InfoLogin(int idCheck, String nameUserLogin, String infoLogin) {
        this.idCheck = idCheck;
        this.nameUserLogin = nameUserLogin;
        this.infoLogin = infoLogin;
    }

    public int getIdCheck() {
        return idCheck;
    }

    public void setIdCheck(int idCheck) {
        this.idCheck = idCheck;
    }

    public String getNameUserLogin() {
        return nameUserLogin;
    }

    public void setNameUserLogin(String nameUserLogin) {
        this.nameUserLogin = nameUserLogin;
    }

    public String getInfoLogin() {
        return infoLogin;
    }

    public void setInfoLogin(String infoLogin) {
        this.infoLogin = infoLogin;
    }
}
