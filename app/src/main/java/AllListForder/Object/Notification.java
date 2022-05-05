package AllListForder.Object;

public class Notification {
    private int idNotification;
    private String nameUserReceive, nameUserSend, addressUserSend, phoneUserSend, daySendNotification, contentMail;

    public Notification(int idNotification, String nameUserReceive, String nameUserSend,
                        String addressUserSend, String phoneUserSend, String daySendNotification, String contentMail) {
        this.idNotification = idNotification;
        this.nameUserReceive = nameUserReceive;
        this.nameUserSend = nameUserSend;
        this.addressUserSend = addressUserSend;
        this.phoneUserSend = phoneUserSend;
        this.daySendNotification = daySendNotification;
        this.contentMail = contentMail;
    }

    public int getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(int idNotification) {
        this.idNotification = idNotification;
    }

    public String getNameUserReceive() {
        return nameUserReceive;
    }

    public void setNameUserReceive(String nameUserReceive) {
        this.nameUserReceive = nameUserReceive;
    }

    public String getNameUserSend() {
        return nameUserSend;
    }

    public void setNameUserSend(String nameUserSend) {
        this.nameUserSend = nameUserSend;
    }

    public String getAddressUserSend() {
        return addressUserSend;
    }

    public void setAddressUserSend(String addressUserSend) {
        this.addressUserSend = addressUserSend;
    }

    public String getPhoneUserSend() {
        return phoneUserSend;
    }

    public void setPhoneUserSend(String phoneUserSend) {
        this.phoneUserSend = phoneUserSend;
    }

    public String getDaySendNotification() {
        return daySendNotification;
    }

    public void setDaySendNotification(String daySendNotification) {
        this.daySendNotification = daySendNotification;
    }

    public String getContentMail() {
        return contentMail;
    }

    public void setContentMail(String contentMail) {
        this.contentMail = contentMail;
    }
}
