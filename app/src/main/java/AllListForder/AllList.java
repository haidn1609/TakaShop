package AllListForder;

import java.util.ArrayList;
import java.util.List;

import AllListForder.Object.EventInHome;
import AllListForder.Object.InfoLogin;
import AllListForder.Object.ItemBuy;
import AllListForder.Object.ItemSell;
import AllListForder.Object.MainAdsImg;
import AllListForder.Object.MainCategory;
import AllListForder.Object.User;

public interface AllList {
    //    ListItem
    List<ItemSell> ALL_ITEM_SELL_LIST = new ArrayList<>();
    List<ItemSell> ITEM_SALE_IN_DAY_LIST = new ArrayList<>();
    List<ItemSell> ITEM_YOUR_MAY_LIKE_LIST = new ArrayList<>();
    List<ItemSell> ITEM_HOT_DEAL = new ArrayList<>();
    List<ItemSell> ITEM_BEST_PRICE = new ArrayList<>();
    List<ItemSell> ITEM_NEW = new ArrayList<>();
    List<ItemSell> ITEM_IN_CATEGORY = new ArrayList<>();
    List<ItemSell> ITEM_IN_EVENT = new ArrayList<>();
    List<ItemSell> ITEM_IN_USER = new ArrayList<>();
    List<ItemBuy> ITEM_BUY_LIST = new ArrayList<>();
    List<ItemBuy> ITEM_ORDER_LIST = new ArrayList<>();
    //    Event
    List<EventInHome> EVENT_IN_HOME_LIST = new ArrayList<>();
    //    ADS
    List<MainAdsImg> MAIN_ADS_IMG_LIST = new ArrayList<>();
    //    User
    List<InfoLogin> INFO_LOGIN_LIST = new ArrayList<>();
    List<User> USER_LIST = new ArrayList<>();
    //    Category
    List<MainCategory> MAIN_CATEGORY_LIST = new ArrayList<>();
}
