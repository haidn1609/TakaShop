package AllListForder.Object;

public class ItemBuy {
    private int id, idItem, priceOnceItem, TotalItemPrice, purchased;
    private String itemName, avatarItem, timeBuy, nameAccountSell, nameAccountBuy;

    public ItemBuy(int id, int idItem, int priceOnceItem, int TotalItemPrice, int purchased, String nameAccountSell, String nameAccountBuy,
                   String itemName, String avatarItem, String timeBuy) {
        this.id = id;
        this.idItem = idItem;
        this.priceOnceItem = priceOnceItem;
        this.TotalItemPrice = TotalItemPrice;
        this.purchased = purchased;
        this.nameAccountSell = nameAccountSell;
        this.nameAccountBuy = nameAccountBuy;
        this.itemName = itemName;
        this.avatarItem = avatarItem;
        this.timeBuy = timeBuy;
    }

    public int getTotalItemPrice() {
        return TotalItemPrice;
    }

    public void setTotalItemPrice(int totalItemPrice) {
        TotalItemPrice = totalItemPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public int getPriceOnceItem() {
        return priceOnceItem;
    }

    public void setPriceOnceItem(int priceOnceItem) {
        this.priceOnceItem = priceOnceItem;
    }

    public int getPurchased() {
        return purchased;
    }

    public void setPurchased(int purchased) {
        this.purchased = purchased;
    }

    public String getNameAccountSell() {
        return nameAccountSell;
    }

    public void setNameAccountSell(String nameAccountSell) {
        this.nameAccountSell = nameAccountSell;
    }

    public String getNameAccountBuy() {
        return nameAccountBuy;
    }

    public void setNameAccountBuy(String nameAccountBuy) {
        this.nameAccountBuy = nameAccountBuy;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getAvatarItem() {
        return avatarItem;
    }

    public void setAvatarItem(String avatarItem) {
        this.avatarItem = avatarItem;
    }

    public String getTimeBuy() {
        return timeBuy;
    }

    public void setTimeBuy(String timeBuy) {
        this.timeBuy = timeBuy;
    }
}
