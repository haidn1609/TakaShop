package support_functions;

import AllListForder.AllKeyLocal;
import AllListForder.AllList;
import AllListForder.Object.InfoLogin;
import AllListForder.Object.ItemSell;
import AllListForder.Object.User;


public class Classify_item_list implements AllList, AllKeyLocal {
    public static void Classify_list() {
        getItemSaleInDay();
        getItemYourMayLike();
        getHotDeal();
        getBestPrice();
        getNew();
    }

    public static void getItemSaleInDay() {
        if (ITEM_SALE_IN_DAY_LIST != null || ITEM_SALE_IN_DAY_LIST.isEmpty() == false) {
            ITEM_SALE_IN_DAY_LIST.clear();
        }
        for (int i = 0; i < ALL_ITEM_SELL_LIST.size(); i++) {
            ItemSell itemSell = ALL_ITEM_SELL_LIST.get(i);
            if (itemSell.getSale().equals("yes")
                    && itemSell.getPriceSale() < 200000
                    && itemSell.getSalePercent() > 40) {
                ITEM_SALE_IN_DAY_LIST.add(itemSell);
            }
        }
    }

    public static void getItemYourMayLike() {
        if (INFO_LOGIN_LIST == null || INFO_LOGIN_LIST.isEmpty()) {
            ITEM_YOUR_MAY_LIKE_LIST.clear();
            ITEM_YOUR_MAY_LIKE_LIST.addAll(ALL_ITEM_SELL_LIST);
        } else {
            InfoLogin infoLogin = INFO_LOGIN_LIST.get(INFO_LOGIN_LIST.size() - 1);
            if (infoLogin.getInfoLogin().equals(USER_LOGOUT)) {
                ITEM_YOUR_MAY_LIKE_LIST.clear();
                ITEM_YOUR_MAY_LIKE_LIST.addAll(ALL_ITEM_SELL_LIST);
            } else if (infoLogin.getInfoLogin().equals(USER_LOGIN)) {
                String nameUser = infoLogin.getNameUserLogin();
                User user;
                for (int i = 0; i < USER_LIST.size(); i++) {
                    User checkUser = USER_LIST.get(i);
                    if (checkUser.getAccountName().toLowerCase().equals(nameUser.toLowerCase())) {
                        user = checkUser;
                        break;
                    }
                }
                ITEM_YOUR_MAY_LIKE_LIST.clear();
                ITEM_YOUR_MAY_LIKE_LIST.addAll(ALL_ITEM_SELL_LIST);
            }
        }
    }

    public static void getHotDeal() {
        if (ITEM_HOT_DEAL != null || ITEM_HOT_DEAL.isEmpty() == false) {
            ITEM_HOT_DEAL.clear();
        }
        for (int i = 0; i < ALL_ITEM_SELL_LIST.size(); i++) {
            ItemSell itemSell = ALL_ITEM_SELL_LIST.get(i);
            String eventCode = itemSell.getEventCode();
            if (eventCode.indexOf("HOT_DEAL") != -1) {
                ITEM_HOT_DEAL.add(itemSell);
            }
        }
    }

    public static void getBestPrice() {
        if (ITEM_BEST_PRICE != null || ITEM_BEST_PRICE.isEmpty() == false) {
            ITEM_BEST_PRICE.clear();
        }
        for (int i = 0; i < ALL_ITEM_SELL_LIST.size(); i++) {
            ItemSell itemSell = ALL_ITEM_SELL_LIST.get(i);
            String eventCode = itemSell.getEventCode();
            if (eventCode.indexOf("BEST_PRICE") != -1) {
                ITEM_BEST_PRICE.add(itemSell);
            }
        }
    }

    public static void getNew() {
        if (ITEM_NEW != null || ITEM_NEW.isEmpty() == false) {
            ITEM_NEW.clear();
        }
        for (int i = 0; i < ALL_ITEM_SELL_LIST.size(); i++) {
            ItemSell itemSell = ALL_ITEM_SELL_LIST.get(i);
            String eventCode = itemSell.getEventCode();
            if (eventCode.indexOf("NEW_ITEM") != -1) {
                ITEM_NEW.add(itemSell);
            }
        }
    }

    public static void getItemInCategory(String typeCategory, String codeIdCategory) {
        ITEM_IN_CATEGORY.clear();
        if (typeCategory.equals(TYPE_MAIN)) {
            for (int i = 0; i < ALL_ITEM_SELL_LIST.size(); i++) {
                ItemSell itemSell = ALL_ITEM_SELL_LIST.get(i);
                if (itemSell.getCodeMainCateId().equals(codeIdCategory)) {
                    ITEM_IN_CATEGORY.add(itemSell);
                }
            }
        } else if (typeCategory.equals(TYPE_SIDE)) {
            for (int i = 0; i < ALL_ITEM_SELL_LIST.size(); i++) {
                ItemSell itemSell = ALL_ITEM_SELL_LIST.get(i);
                if (itemSell.getCodeSideCateId().equals(codeIdCategory)) {
                    ITEM_IN_CATEGORY.add(itemSell);
                }
            }
        }
    }

    public static void getItemInEvent(String eventCode) {
        ITEM_IN_EVENT.clear();
        for (int i = 0; i < ALL_ITEM_SELL_LIST.size(); i++) {
            ItemSell itemSell = ALL_ITEM_SELL_LIST.get(i);
            if (itemSell.getEventCode().indexOf(eventCode) > -1) {
                ITEM_IN_EVENT.add(itemSell);
            }
        }
    }

    public static void getItemInUser(String typeSelect, String nameUser) {
        ITEM_IN_USER.clear();
        if (typeSelect.equals(TYPE_TRADEMARK)) {
            for (int i = 0; i < ALL_ITEM_SELL_LIST.size(); i++) {
                ItemSell itemSell = ALL_ITEM_SELL_LIST.get(i);
                if (itemSell.getTrademark().equals(nameUser)) {
                    ITEM_IN_USER.add(itemSell);
                }
            }
        } else if (typeSelect.equals(TYPE_SELLER)) {
            for (int i = 0; i < ALL_ITEM_SELL_LIST.size(); i++) {
                ItemSell itemSell = ALL_ITEM_SELL_LIST.get(i);
                if (itemSell.getIdUserSell() == Integer.parseInt(nameUser)) {
                    ITEM_IN_USER.add(itemSell);
                }
            }
        }
    }
}
