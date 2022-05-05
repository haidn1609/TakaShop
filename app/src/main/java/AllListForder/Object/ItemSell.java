package AllListForder.Object;

import java.io.Serializable;
import java.util.List;

public class ItemSell implements Serializable {
    private int idItemSell, priceDontSale, priceSale, salePercent, totalItem, totalItemSold, itemSoldInMonth, idUserSell;
    private String CodeMainCateId, CodeSideCateId, nameItemSell, trademark, characteristics, EventCode, sale, DaySell;
    private List<AvatarURL> avatarItemSell;

    public ItemSell(int idItemSell, String codeMainCateId, String codeSideCateId, String nameItemSell, String sale, int salePercent,
                    int priceDontSale, int priceSale, int totalItem, int totalItemSold, int itemSoldInMonth, int idUserSell,
                    String trademark, String characteristics, String eventCode, String daySell,
                    List<AvatarURL> avatarItemSell) {
        this.idItemSell = idItemSell;
        this.priceDontSale = priceDontSale;
        this.priceSale = priceSale;
        this.salePercent = salePercent;
        this.totalItem = totalItem;
        this.totalItemSold = totalItemSold;
        this.itemSoldInMonth = itemSoldInMonth;
        this.idUserSell = idUserSell;
        this.CodeMainCateId = codeMainCateId;
        this.CodeSideCateId = codeSideCateId;
        this.nameItemSell = nameItemSell;
        this.trademark = trademark;
        this.characteristics = characteristics;
        this.EventCode = eventCode;
        this.sale = sale;
        this.DaySell = daySell;
        this.avatarItemSell = avatarItemSell;
    }

    public String getTrademark() {
        return trademark;
    }

    public void setTrademark(String trademark) {
        this.trademark = trademark;
    }

    public int getIdItemSell() {
        return idItemSell;
    }

    public void setIdItemSell(int idItemSell) {
        this.idItemSell = idItemSell;
    }

    public int getPriceDontSale() {
        return priceDontSale;
    }

    public void setPriceDontSale(int priceDontSale) {
        this.priceDontSale = priceDontSale;
    }

    public int getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(int priceSale) {
        this.priceSale = priceSale;
    }

    public int getSalePercent() {
        return salePercent;
    }

    public void setSalePercent(int salePercent) {
        this.salePercent = salePercent;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public int getTotalItemSold() {
        return totalItemSold;
    }

    public void setTotalItemSold(int totalItemSold) {
        this.totalItemSold = totalItemSold;
    }

    public int getItemSoldInMonth() {
        return itemSoldInMonth;
    }

    public void setItemSoldInMonth(int itemSoldInMonth) {
        this.itemSoldInMonth = itemSoldInMonth;
    }

    public int getIdUserSell() {
        return idUserSell;
    }

    public void setIdUserSell(int idUserSell) {
        this.idUserSell = idUserSell;
    }

    public String getCodeMainCateId() {
        return CodeMainCateId;
    }

    public void setCodeMainCateId(String codeMainCateId) {
        CodeMainCateId = codeMainCateId;
    }

    public String getCodeSideCateId() {
        return CodeSideCateId;
    }

    public void setCodeSideCateId(String codeSideCateId) {
        CodeSideCateId = codeSideCateId;
    }

    public String getNameItemSell() {
        return nameItemSell;
    }

    public void setNameItemSell(String nameItemSell) {
        this.nameItemSell = nameItemSell;
    }

    public List<AvatarURL> getAvatarItemSell() {
        return avatarItemSell;
    }

    public void setAvatarItemSell(List<AvatarURL> avatarItemSell) {
        this.avatarItemSell = avatarItemSell;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public String getEventCode() {
        return EventCode;
    }

    public void setEventCode(String eventCode) {
        EventCode = eventCode;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String getDaySell() {
        return DaySell;
    }

    public void setDaySell(String daySell) {
        DaySell = daySell;
    }
}
