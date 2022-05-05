package View.showItemFragment;

import AllListForder.AllKeyLocal;

public class ShowItemPresenter implements AllKeyLocal {
    ModelShowItem modelShowItem;

    public ShowItemPresenter(ModelShowItem modelShowItem) {
        this.modelShowItem = modelShowItem;
    }

    public void ShowItem(String key) {
        if (key.equals(SALE_IN_HOME)) {
            modelShowItem.setListSaleInHome();
        } else if (key.equals(YOU_MAY_LIKE)) {
            modelShowItem.setListYouMayLike();
        } else if (key.equals(HOT_DEAL_ITEM)) {
            modelShowItem.setListHotDealItem();
        } else if (key.equals(BEST_PRICE_ITEM)) {
            modelShowItem.setListBestPriceItem();
        } else if (key.equals(NEW_ITEM)) {
            modelShowItem.setListNewItem();
        } else if (key.equals(ALL_ITEM)) {
            modelShowItem.setListAllItem();
        } else if (key.equals(ITEM_FROM_CATEGORY)) {
            modelShowItem.setListItemFromCategory();
        } else if (key.equals(ITEM_FROM_EVENT_IN_HOME)) {
            modelShowItem.setListItemFromEventInHome();
        } else if (key.equals(ITEM_FROM_USER)) {
            modelShowItem.setListItemFromUser();
        }

    }
}
