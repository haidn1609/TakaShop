package support_functions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import AllListForder.Object.AvatarURL;
import AllListForder.Object.InfoLogin;
import AllListForder.Object.ItemBuy;
import AllListForder.Object.ItemSell;
import AllListForder.Object.Notification;
import AllListForder.Object.SQLKey;
import AllListForder.Object.User;

public class SqlLiteHelper extends SQLiteOpenHelper implements SQLKey {
    private static final String DB_NAME = "UserList.db";
    private static final String DB_USER_TABLE = "User";
    private static final String DB_CHECK_LOGIN_TABLE = "CheckLogin";
    private static final String DB_ITEMBUY = "ItemBuy";
    private static final String DB_ITEMSELL = "ItemSell";
    private static final String DB_ITEMSELL_PENDING = "ItemSellPending";
    private static final String DB_NOTIFICATION = "Notification";
    private static final int DB_VERSION = 1;

    SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;
    private Cursor cursor;

    public SqlLiteHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCreateUserTable = "CREATE TABLE User(" +
                "idUser INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "accountType TEXT," +
                "accountName TEXT," +
                "accountPass TEXT," +
                "userFistName TEXT," +
                "userLastName TEXT," +
                "userEmail TEXT," +
                "userPhone TEXT," +
                "sex TEXT," +
                "sourceAvatar TEXT," +
                "avatar TEXT," +
                "address TEXT)";
        String queryCreateCheckLoginTable = "CREATE TABLE CheckLogin(" +
                "idCheckLogin INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "nameUserLogin TEXT," +
                "infoLogin TEXT)";

        String queryCreateNotificationTable = "CREATE TABLE Notification(" +
                "idNotification INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "nameUserReceive TEXT," +
                "nameUserSend TEXT," +
                "addressUserSend TEXT," +
                "phoneUserSend TEXT," +
                "daySendNotification TEXT," +
                "contentMail TEXT)";

        String queryCreateItemBuyTable = "CREATE TABLE ItemBuy(" +
                "idTrade INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "idItemBuy INTEGER," +
                "idItemName TEXT," +
                "priceOnceItem INTEGER," +
                "purchased INTEGER," +
                "totalItemPrice INTEGER," +
                "avatarItem TEXT," +
                "timeBuy TEXT," +
                "nameAccountSell TEXT," +
                "nameAccountBuy TEXT)";

        String queryCreateItemSellTable = "CREATE TABLE ItemSell(" +
                "idItemSell INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "CodeMainCateId TEXT," +
                "CodeSideCateId TEXT," +
                "nameItemSell TEXT," +
                "sale TEXT," +
                "salePercent INTEGER," +
                "priceDontSale INTEGER," +
                "priceSale INTEGER," +
                "totalItem INTEGER," +
                "totalItemSold INTEGER," +
                "itemSoldInMonth INTEGER," +
                "idUserSell INTEGER," +
                "trademark TEXT," +
                "characteristics TEXT," +
                "eventCode TEXT," +
                "daySell TEXT," +
                "UrlImg TEXT)";

        String queryCreateItemSellPendingTable = "CREATE TABLE ItemSellPending(" +
                "idItemSellPending INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "CodeMainCateIdPending TEXT," +
                "CodeSideCateIdPending TEXT," +
                "nameItemSellPending TEXT," +
                "salePending TEXT," +
                "salePercentPending INTEGER," +
                "priceDontSalePending INTEGER," +
                "priceSalePending INTEGER," +
                "totalItemPending INTEGER," +
                "totalItemSoldPending INTEGER," +
                "itemSoldInMonthPending INTEGER," +
                "idUserSellPending INTEGER," +
                "trademarkPending TEXT," +
                "characteristicsPending TEXT," +
                "eventCodePending TEXT," +
                "daySellPending TEXT," +
                "UrlImgPending TEXT)";
        db.execSQL(queryCreateUserTable);
        db.execSQL(queryCreateCheckLoginTable);
        db.execSQL(queryCreateItemBuyTable);
        db.execSQL(queryCreateItemSellTable);
        db.execSQL(queryCreateItemSellPendingTable);
        db.execSQL(queryCreateNotificationTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
            onCreate(db);
        }
    }

    public void addUserTable(User user) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(USER_TYPE, user.getAccountType());
        contentValues.put(USER_ACCOUNT, user.getAccountName());
        contentValues.put(USER_PASS, user.getAccountPass());
        contentValues.put(USER_FIST_NAME, user.getUserFistName());
        contentValues.put(USER_LAST_NAME, user.getUserLastName());
        contentValues.put(USER_EMAIL, user.getUserEmail());
        contentValues.put(USER_PHONE, user.getUserPhone());
        contentValues.put(USER_SEX, user.getSex());
        contentValues.put(USER_SOURCE_AVATAR, user.getSourceAvatar());
        contentValues.put(USER_AVATAR, user.getAvatar());
        contentValues.put(USER_ADDRESS, user.getAddress());

        sqLiteDatabase.insert(DB_USER_TABLE, null, contentValues);
        closeDB();
    }

    public void addCheckLoginTable(InfoLogin infoLogin) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(NAME_USER_LOGIN, infoLogin.getNameUserLogin());
        contentValues.put(INFO_LOGIN, infoLogin.getInfoLogin());

        sqLiteDatabase.insert(DB_CHECK_LOGIN_TABLE, null, contentValues);
        closeDB();
    }

    public void addNotification(Notification notification) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(NAME_USER_RECEIVE, notification.getNameUserReceive());
        contentValues.put(NAME_USER_SEND, notification.getNameUserSend());
        contentValues.put(ADDRESS_USER_SEND, notification.getAddressUserSend());
        contentValues.put(PHONE_USER_SELL, notification.getPhoneUserSend());
        contentValues.put(DAY_SEND_NOTIFICATION, notification.getDaySendNotification());
        contentValues.put(CONTENT_MAIL, notification.getContentMail());

        sqLiteDatabase.insert(DB_NOTIFICATION, null, contentValues);
        closeDB();
    }

    public void addItemBuy(ItemBuy itemBuy) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(ID_ITEM_BUY, itemBuy.getIdItem());
        contentValues.put(ID_ITEM_NAME, itemBuy.getItemName());
        contentValues.put(PRICE_ONCE_ITEM, itemBuy.getPriceOnceItem());
        contentValues.put(PURCHASED, itemBuy.getPurchased());
        contentValues.put(TOTAL_PRICE_ITEM, itemBuy.getTotalItemPrice());
        contentValues.put(ITEM_AVATAR, itemBuy.getAvatarItem());
        contentValues.put(ACCOUNT_NAME_SELL_ITEM, itemBuy.getNameAccountSell());
        contentValues.put(ACCOUNT_NAME_SELL_BUY, itemBuy.getNameAccountBuy());
        contentValues.put(TIME_BUY, itemBuy.getTimeBuy());

        sqLiteDatabase.insert(DB_ITEMBUY, null, contentValues);
        closeDB();
    }

    public void addItemSell(ItemSell itemSell) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(CODE_MAIN_CATE_ID, itemSell.getCodeMainCateId());
        contentValues.put(CODE_SIDE_CATE_ID, itemSell.getCodeSideCateId());
        contentValues.put(NAME_ITEM_SELL, itemSell.getNameItemSell());
        contentValues.put(SALE, itemSell.getSale());
        contentValues.put(SALE_PERCENT, itemSell.getSalePercent());
        contentValues.put(PRICE_DONT_SALE, itemSell.getPriceDontSale());
        contentValues.put(PRICE_SALE, itemSell.getPriceSale());
        contentValues.put(TOTAL_ITEM, itemSell.getTotalItem());
        contentValues.put(TOTAL_ITEM_SOLD, itemSell.getTotalItemSold());
        contentValues.put(ITEM_SOLD_IN_MONTH, itemSell.getItemSoldInMonth());
        contentValues.put(ID_USER_SELL, itemSell.getIdUserSell());
        contentValues.put(TRADEMARK, itemSell.getTrademark());
        contentValues.put(CHARACTERISTICS, itemSell.getCharacteristics());
        contentValues.put(EVENT_CODE, itemSell.getEventCode());
        contentValues.put(DAY_SELL, itemSell.getDaySell());
        contentValues.put(URL_IMG, itemSell.getAvatarItemSell().get(0).getUrlImg());

        sqLiteDatabase.insert(DB_ITEMSELL, null, contentValues);
        closeDB();
    }

    public void addItemSellPending(ItemSell itemSell) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(CODE_MAIN_CATE_ID_PENDING, itemSell.getCodeMainCateId());
        contentValues.put(CODE_SIDE_CATE_ID_PENDING, itemSell.getCodeSideCateId());
        contentValues.put(NAME_ITEM_SELL_PENDING, itemSell.getNameItemSell());
        contentValues.put(SALE_PENDING, itemSell.getSale());
        contentValues.put(SALE_PERCENT_PENDING, itemSell.getSalePercent());
        contentValues.put(PRICE_DONT_SALE_PENDING, itemSell.getPriceDontSale());
        contentValues.put(PRICE_SALE_PENDING, itemSell.getPriceSale());
        contentValues.put(TOTAL_ITEM_PENDING, itemSell.getTotalItem());
        contentValues.put(TOTAL_ITEM_SOLD_PENDING, itemSell.getTotalItemSold());
        contentValues.put(ITEM_SOLD_IN_MONTH_PENDING, itemSell.getItemSoldInMonth());
        contentValues.put(ID_USER_SELL_PENDING, itemSell.getIdUserSell());
        contentValues.put(TRADEMARK_PENDING, itemSell.getTrademark());
        contentValues.put(CHARACTERISTICS_PENDING, itemSell.getCharacteristics());
        contentValues.put(EVENT_CODE_PENDING, itemSell.getEventCode());
        contentValues.put(DAY_SELL_PENDING, itemSell.getDaySell());
        contentValues.put(URL_IMG_PENDING, itemSell.getAvatarItemSell().get(0).getUrlImg());

        sqLiteDatabase.insert(DB_ITEMSELL_PENDING, null, contentValues);
        closeDB();
    }

    public void editUserTable(User user) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(USER_TYPE, user.getAccountType());
        contentValues.put(USER_ACCOUNT, user.getAccountName());
        contentValues.put(USER_PASS, user.getAccountPass());
        contentValues.put(USER_FIST_NAME, user.getUserFistName());
        contentValues.put(USER_LAST_NAME, user.getUserLastName());
        contentValues.put(USER_EMAIL, user.getUserEmail());
        contentValues.put(USER_PHONE, user.getUserPhone());
        contentValues.put(USER_SOURCE_AVATAR, user.getSourceAvatar());
        contentValues.put(USER_SEX, user.getSex());
        contentValues.put(USER_AVATAR, user.getAvatar());
        contentValues.put(USER_ADDRESS, user.getAddress());

        sqLiteDatabase.update(DB_USER_TABLE, contentValues, "idUser=?",
                new String[]{String.valueOf(user.getIdUser())});
        closeDB();
    }

    public void editItemSell(ItemSell itemSell) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(CODE_MAIN_CATE_ID, itemSell.getCodeMainCateId());
        contentValues.put(CODE_SIDE_CATE_ID, itemSell.getCodeSideCateId());
        contentValues.put(NAME_ITEM_SELL, itemSell.getNameItemSell());
        contentValues.put(SALE, itemSell.getSale());
        contentValues.put(SALE_PERCENT, itemSell.getSalePercent());
        contentValues.put(PRICE_DONT_SALE, itemSell.getPriceDontSale());
        contentValues.put(PRICE_SALE, itemSell.getPriceSale());
        contentValues.put(TOTAL_ITEM, itemSell.getTotalItem());
        contentValues.put(TOTAL_ITEM_SOLD, itemSell.getTotalItemSold());
        contentValues.put(ITEM_SOLD_IN_MONTH, itemSell.getItemSoldInMonth());
        contentValues.put(ID_USER_SELL, itemSell.getIdUserSell());
        contentValues.put(TRADEMARK, itemSell.getTrademark());
        contentValues.put(CHARACTERISTICS, itemSell.getCharacteristics());
        contentValues.put(EVENT_CODE, itemSell.getEventCode());
        contentValues.put(DAY_SELL, itemSell.getDaySell());
        contentValues.put(URL_IMG, itemSell.getAvatarItemSell().get(0).getUrlImg());

        sqLiteDatabase.update(DB_ITEMSELL, contentValues, "idItemSell=?",
                new String[]{String.valueOf(itemSell.getIdItemSell())});
    }

    public void delUser(int idUser) {
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(DB_USER_TABLE, "idUser=?", new String[]{String.valueOf(idUser)});
        closeDB();
    }


    public void delItemSell(int idItemSell) {
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(DB_ITEMSELL, "idItemSell=?", new String[]{String.valueOf(idItemSell)});
        closeDB();
    }

    public void delItemSellPending(int idItemSell) {
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(DB_ITEMSELL_PENDING, "idItemSellPending=?", new String[]{String.valueOf(idItemSell)});
        closeDB();
    }

    public void delNotification(int idNotification) {
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(DB_NOTIFICATION, "idNotification=?", new String[]{String.valueOf(idNotification)});
        closeDB();
    }

    public List<User> getAllListUser() {
        List<User> userList = new ArrayList<>();
        User user;

        sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.query(false, DB_USER_TABLE, null
                , null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(ID_USER));
            String userType = cursor.getString(cursor.getColumnIndex(USER_TYPE));
            String userAccount = cursor.getString(cursor.getColumnIndex(USER_ACCOUNT));
            String userPass = cursor.getString(cursor.getColumnIndex(USER_PASS));
            String userFistName = cursor.getString(cursor.getColumnIndex(USER_FIST_NAME));
            String userLastName = cursor.getString(cursor.getColumnIndex(USER_LAST_NAME));
            String userEmail = cursor.getString(cursor.getColumnIndex(USER_EMAIL));
            String userPhone = cursor.getString(cursor.getColumnIndex(USER_PHONE));
            String userSex = cursor.getString(cursor.getColumnIndex(USER_SEX));
            String userSourceAvatar = cursor.getString(cursor.getColumnIndex(USER_SOURCE_AVATAR));
            String userAvatar = cursor.getString(cursor.getColumnIndex(USER_AVATAR));
            String userAddress = cursor.getString(cursor.getColumnIndex(USER_ADDRESS));

            user = new User(id, userType, userAccount, userPass, userFistName, userLastName, userEmail
                    , userPhone, userAddress, userSex, userSourceAvatar, userAvatar);
            userList.add(user);
        }
        closeDB();
        return userList;
    }

    public List<InfoLogin> getAllListCheckLogin() {
        List<InfoLogin> infoLoginList = new ArrayList<>();
        InfoLogin infoLogin;
        sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.query(false, DB_CHECK_LOGIN_TABLE, null
                , null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(ID_CHECK_LOGIN));
            String nameUserLogin = cursor.getString(cursor.getColumnIndex(NAME_USER_LOGIN));
            String infoLogin1 = cursor.getString(cursor.getColumnIndex(INFO_LOGIN));
            infoLogin = new InfoLogin(id, nameUserLogin, infoLogin1);
            infoLoginList.add(infoLogin);
        }
        closeDB();
        return infoLoginList;
    }

    public List<ItemBuy> getAllListItemBuy() {
        List<ItemBuy> itemBuyList = new ArrayList<>();
        ItemBuy itemBuy;
        sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.query(false, DB_ITEMBUY, null
                , null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int idTrade = cursor.getInt(cursor.getColumnIndex(ID_TRADE));
            int idItemSell = cursor.getInt(cursor.getColumnIndex(ID_ITEM_BUY));
            String itemName = cursor.getString(cursor.getColumnIndex(ID_ITEM_NAME));
            int priceOnceItem = cursor.getInt(cursor.getColumnIndex(PRICE_ONCE_ITEM));
            int purchased = cursor.getInt(cursor.getColumnIndex(PURCHASED));
            int totalItemPrice = cursor.getInt(cursor.getColumnIndex(TOTAL_PRICE_ITEM));
            String itemAvatar = cursor.getString(cursor.getColumnIndex(ITEM_AVATAR));
            String timeBuy = cursor.getString(cursor.getColumnIndex(TIME_BUY));
            String nameAccountSell = cursor.getString(cursor.getColumnIndex(ACCOUNT_NAME_SELL_ITEM));
            String nameAccountBuy = cursor.getString(cursor.getColumnIndex(ACCOUNT_NAME_SELL_BUY));
            itemBuy = new ItemBuy(idTrade, idItemSell, priceOnceItem, totalItemPrice, purchased, nameAccountSell
                    , nameAccountBuy, itemName, itemAvatar, timeBuy);
            itemBuyList.add(itemBuy);
        }
        closeDB();
        return itemBuyList;
    }

    public List<ItemSell> getAllListItemSell() {
        List<ItemSell> itemSellList = new ArrayList<>();

        sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.query(false, DB_ITEMSELL, null
                , null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idItemSell = cursor.getInt(cursor.getColumnIndex(ID_ITEM_SELL));
            String CodeMainCateId = cursor.getString(cursor.getColumnIndex(CODE_MAIN_CATE_ID));
            String CodeSideCateId = cursor.getString(cursor.getColumnIndex(CODE_SIDE_CATE_ID));
            String nameItemSell = cursor.getString(cursor.getColumnIndex(NAME_ITEM_SELL));
            String sale = cursor.getString(cursor.getColumnIndex(SALE));
            int salePercent = cursor.getInt(cursor.getColumnIndex(SALE_PERCENT));
            int priceDontSale = cursor.getInt(cursor.getColumnIndex(PRICE_DONT_SALE));
            int priceSale = cursor.getInt(cursor.getColumnIndex(PRICE_SALE));
            int totalItem = cursor.getInt(cursor.getColumnIndex(TOTAL_ITEM));
            int totalItemSold = cursor.getInt(cursor.getColumnIndex(TOTAL_ITEM_SOLD));
            int itemSoldInMonth = cursor.getInt(cursor.getColumnIndex(ITEM_SOLD_IN_MONTH));
            int idUserSell = cursor.getInt(cursor.getColumnIndex(ID_USER_SELL));
            String trademark = cursor.getString(cursor.getColumnIndex(TRADEMARK));
            String characteristics = cursor.getString(cursor.getColumnIndex(CHARACTERISTICS));
            String eventCode = cursor.getString(cursor.getColumnIndex(EVENT_CODE));
            String daySell = cursor.getString(cursor.getColumnIndex(DAY_SELL));
            String UrlImg = cursor.getString(cursor.getColumnIndex(URL_IMG));
            List<AvatarURL> avatarURLList = new ArrayList<>();
            avatarURLList.add(new AvatarURL(UrlImg));

            ItemSell itemSell = new ItemSell(idItemSell, CodeMainCateId, CodeSideCateId, nameItemSell, sale, salePercent, priceDontSale, priceSale
                    , totalItem, totalItemSold, itemSoldInMonth, idUserSell, trademark, characteristics, eventCode, daySell, avatarURLList);
            itemSellList.add(itemSell);
        }
        closeDB();
        return itemSellList;
    }

    public List<ItemSell> getAllListItemSellPending() {
        List<ItemSell> itemSellList = new ArrayList<>();

        sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.query(false, DB_ITEMSELL_PENDING, null
                , null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idItemSell = cursor.getInt(cursor.getColumnIndex(ID_ITEM_SELL_PENDING));
            String CodeMainCateId = cursor.getString(cursor.getColumnIndex(CODE_MAIN_CATE_ID_PENDING));
            String CodeSideCateId = cursor.getString(cursor.getColumnIndex(CODE_SIDE_CATE_ID_PENDING));
            String nameItemSell = cursor.getString(cursor.getColumnIndex(NAME_ITEM_SELL_PENDING));
            String sale = cursor.getString(cursor.getColumnIndex(SALE_PENDING));
            int salePercent = cursor.getInt(cursor.getColumnIndex(SALE_PERCENT_PENDING));
            int priceDontSale = cursor.getInt(cursor.getColumnIndex(PRICE_DONT_SALE_PENDING));
            int priceSale = cursor.getInt(cursor.getColumnIndex(PRICE_SALE_PENDING));
            int totalItem = cursor.getInt(cursor.getColumnIndex(TOTAL_ITEM_PENDING));
            int totalItemSold = cursor.getInt(cursor.getColumnIndex(TOTAL_ITEM_SOLD_PENDING));
            int itemSoldInMonth = cursor.getInt(cursor.getColumnIndex(ITEM_SOLD_IN_MONTH_PENDING));
            int idUserSell = cursor.getInt(cursor.getColumnIndex(ID_USER_SELL_PENDING));
            String trademark = cursor.getString(cursor.getColumnIndex(TRADEMARK_PENDING));
            String characteristics = cursor.getString(cursor.getColumnIndex(CHARACTERISTICS_PENDING));
            String eventCode = cursor.getString(cursor.getColumnIndex(EVENT_CODE_PENDING));
            String daySell = cursor.getString(cursor.getColumnIndex(DAY_SELL_PENDING));
            String UrlImg = cursor.getString(cursor.getColumnIndex(URL_IMG_PENDING));
            List<AvatarURL> avatarURLList = new ArrayList<>();
            avatarURLList.add(new AvatarURL(UrlImg));

            ItemSell itemSell = new ItemSell(idItemSell, CodeMainCateId, CodeSideCateId, nameItemSell, sale, salePercent, priceDontSale, priceSale
                    , totalItem, totalItemSold, itemSoldInMonth, idUserSell, trademark, characteristics, eventCode, daySell, avatarURLList);
            itemSellList.add(itemSell);
        }
        closeDB();
        return itemSellList;
    }

    public List<Notification> getAllListNotification() {
        List<Notification> notificationList = new ArrayList<>();

        sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.query(false, DB_NOTIFICATION, null
                , null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idNotification = cursor.getInt(cursor.getColumnIndex(ID_NOTIFICATION));
            String nameUserReceive = cursor.getString(cursor.getColumnIndex(NAME_USER_RECEIVE));
            String nameUserSend = cursor.getString(cursor.getColumnIndex(NAME_USER_SEND));
            String addressUserSend = cursor.getString(cursor.getColumnIndex(ADDRESS_USER_SEND));
            String phoneUserSend = cursor.getString(cursor.getColumnIndex(PHONE_USER_SELL));
            String daySendNotification = cursor.getString(cursor.getColumnIndex(DAY_SEND_NOTIFICATION));
            String contentMail = cursor.getString(cursor.getColumnIndex(CONTENT_MAIL));
            Notification notification = new Notification(idNotification, nameUserReceive, nameUserSend
                    , addressUserSend, phoneUserSend, daySendNotification, contentMail);
            notificationList.add(notification);
        }
        closeDB();
        return notificationList;
    }

    private void closeDB() {
        if (contentValues != null) contentValues.clear();
        if (cursor != null) cursor.close();
        if (sqLiteDatabase != null) sqLiteDatabase.close();
    }
}
