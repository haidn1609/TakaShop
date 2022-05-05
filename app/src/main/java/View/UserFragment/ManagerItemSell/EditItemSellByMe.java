package View.UserFragment.ManagerItemSell;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.myproject.MainActivity;
import com.example.myproject.R;
import com.example.myproject.databinding.EditItemSellByMeBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import AllListForder.AllKeyLocal;
import AllListForder.Object.AvatarURL;
import AllListForder.Object.ItemSell;
import AllListForder.Object.Notification;
import AllListForder.Object.User;
import support_functions.SqlLiteHelper;

public class EditItemSellByMe extends Fragment implements AllKeyLocal {
    private EditItemSellByMeBinding editItemSellByMeBinding;
    private MainActivity mainActivity;
    private SqlLiteHelper sqlLiteHelper;
    private boolean checkMainCategory = false;
    private boolean checkSideCategory = false;
    private boolean checkSale = false;
    private boolean checkAvatar = true;
    private String mainCategory;
    private String sideCategory;
    private String urlImgAvatar;

    public static EditItemSellByMe newInstance() {

        Bundle args = new Bundle();

        EditItemSellByMe fragment = new EditItemSellByMe();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        editItemSellByMeBinding = DataBindingUtil.inflate(inflater, R.layout.edit_item_sell_by_me, container, false);
        mainActivity = (MainActivity) getActivity();
        sqlLiteHelper = new SqlLiteHelper(getContext());

        ItemSell itemSellEditNow = mainActivity.getItemSell();
//        setInfo
        if (itemSellEditNow.getSale().equals("yes")) {
            editItemSellByMeBinding.checkSaleInEditItem.isChecked();
            checkSale = true;
            editItemSellByMeBinding.etSalePriceItemInEditItem.setVisibility(View.VISIBLE);
            editItemSellByMeBinding.etSalePriceItemInEditItem.setText(itemSellEditNow.getPriceSale() + "");
        } else {
            editItemSellByMeBinding.checkDontSaleInEditItem.isChecked();
            checkSale = false;
            editItemSellByMeBinding.etSalePriceItemInEditItem.setVisibility(View.GONE);
        }
        editItemSellByMeBinding.etItemSellNameEdit.setText(itemSellEditNow.getNameItemSell());
        editItemSellByMeBinding.etTrademarkInEditItem.setText(itemSellEditNow.getTrademark());
        editItemSellByMeBinding.etPriceOnceItemInEditItem.setText(itemSellEditNow.getPriceDontSale() + "");
        editItemSellByMeBinding.etInvetoryItemInEditItem.setText(itemSellEditNow.getTotalItem() + "");
        editItemSellByMeBinding.etCharacteristicsItemInEditItem.setText(itemSellEditNow.getCharacteristics());
        editItemSellByMeBinding.etUrlAvatarItemInEditItem.setText(itemSellEditNow.getAvatarItemSell().get(0).getUrlImg());
        Picasso.get().load(itemSellEditNow.getAvatarItemSell().get(0).getUrlImg()).error(R.drawable.dont_loading_img)
                .into(editItemSellByMeBinding.imgItemAvatarInEditItem);
        //        back
        editItemSellByMeBinding.imgBackInEditItemSellByMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.getFragment(ManagerItemSellByMeFragment.newInstance());
            }
        });
        //        setSale
        editItemSellByMeBinding.checkDontSaleInEditItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSale = false;
                editItemSellByMeBinding.checkDontSaleInEditItem.isChecked();
                editItemSellByMeBinding.etSalePriceItemInEditItem.setVisibility(View.GONE);
            }
        });
        editItemSellByMeBinding.checkSaleInEditItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSale = true;
                editItemSellByMeBinding.checkSaleInEditItem.isChecked();
                editItemSellByMeBinding.etSalePriceItemInEditItem.setVisibility(View.VISIBLE);
            }
        });
        //        setCate
        editItemSellByMeBinding.selectMainCategoryInEditItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu1 = new PopupMenu(getActivity().getBaseContext(), v);
                MenuInflater menuInflater1 = popupMenu1.getMenuInflater();
                menuInflater1.inflate(R.menu.menu_main_category, popupMenu1.getMenu());
                popupMenu1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.main_category_toy_and_mom: {
                                checkMainCategory = true;
                                setMainCategory(TOY_AND_MOM);
                                Toast.makeText(getContext(), getString(R.string.toy_and_mom), Toast.LENGTH_SHORT).show();
                                editItemSellByMeBinding.selectMainCategoryInEditItem.setText(getString(R.string.toy_and_mom));
                                break;
                            }
                            case R.id.main_category_phone_and_tablet: {
                                checkMainCategory = true;
                                setMainCategory(PHONE_AND_TABLET);
                                editItemSellByMeBinding.selectMainCategoryInEditItem.setText(getString(R.string.phone_and_tablet));
                                break;
                            }
                            case R.id.main_category_cosmetic: {
                                checkMainCategory = true;
                                setMainCategory(COSMETIC);
                                editItemSellByMeBinding.selectMainCategoryInEditItem.setText(getString(R.string.cosmetic));
                                break;
                            }
                            case R.id.electric_appliances: {
                                checkMainCategory = true;
                                setMainCategory(ELECTRIC_APPLIANCES);
                                editItemSellByMeBinding.selectMainCategoryInEditItem.setText(getString(R.string.electric_appliances));
                                break;
                            }
                            case R.id.main_category_women_fashion: {
                                checkMainCategory = true;
                                setMainCategory(WOMEN_FASHION);
                                editItemSellByMeBinding.selectMainCategoryInEditItem.setText(getString(R.string.women_fashion));
                                break;
                            }
                            case R.id.main_category_men_fashion: {
                                checkMainCategory = true;
                                setMainCategory(MEN_FASHION);
                                editItemSellByMeBinding.selectMainCategoryInEditItem.setText(getString(R.string.men_fashion));
                                break;
                            }
                            case R.id.main_category_jewelry: {
                                checkMainCategory = true;
                                setMainCategory(JEWELRY);
                                editItemSellByMeBinding.selectMainCategoryInEditItem.setText(getString(R.string.jewelry));
                                break;
                            }
                            case R.id.main_category_laptop_pc: {
                                checkMainCategory = true;
                                setMainCategory(LAPTOP_PC);
                                editItemSellByMeBinding.selectMainCategoryInEditItem.setText(getString(R.string.laptop_pc));
                                break;
                            }
                            case R.id.main_category_car_moto: {
                                checkMainCategory = true;
                                setMainCategory(CAR_MOTO);
                                editItemSellByMeBinding.selectMainCategoryInEditItem.setText(getString(R.string.car_moto));
                                break;
                            }
                            case R.id.main_category_food_drink: {
                                checkMainCategory = true;
                                setMainCategory(FOODS_AND_DRINK);
                                editItemSellByMeBinding.selectMainCategoryInEditItem.setText(getString(R.string.food_drink));
                                break;
                            }
                        }
                        return false;
                    }
                });
                popupMenu1.show();
            }
        });
        editItemSellByMeBinding.selectSideCategoryInEditItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu2 = new PopupMenu(getActivity().getBaseContext(), v);
                MenuInflater menuInflater2 = popupMenu2.getMenuInflater();
                menuInflater2.inflate(R.menu.menu_side_category, popupMenu2.getMenu());
                popupMenu2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.side_category_taLot: {
                                checkSideCategory = true;
                                setSideCategory(TA_LOT);
                                editItemSellByMeBinding.selectSideCategoryInEditItem.setText(getString(R.string.taLot));
                                break;
                            }
                            case R.id.side_category_suaBot: {
                                checkSideCategory = true;
                                setSideCategory(SUA_BOT);
                                editItemSellByMeBinding.selectSideCategoryInEditItem.setText(getString(R.string.suaBot));
                                break;
                            }
                            case R.id.side_category_doChoi: {
                                checkSideCategory = true;
                                setSideCategory(DO_CHOI);
                                editItemSellByMeBinding.selectSideCategoryInEditItem.setText(getString(R.string.doChoi));
                                break;
                            }
                            case R.id.side_category_quanAoTreEm: {
                                checkSideCategory = true;
                                setSideCategory(QUAN_AO_TRE_EM);
                                editItemSellByMeBinding.selectSideCategoryInEditItem.setText(getString(R.string.quanAoTreEm));
                                break;
                            }
                            case R.id.side_category_dienThoai: {
                                checkSideCategory = true;
                                setSideCategory(DIEN_THOAI);
                                editItemSellByMeBinding.selectSideCategoryInEditItem.setText(getString(R.string.dienThoai));
                                break;
                            }
                            case R.id.side_category_mayTinhBang: {
                                checkSideCategory = true;
                                setSideCategory(MAY_TINH_BANG);
                                editItemSellByMeBinding.selectSideCategoryInEditItem.setText(getString(R.string.mayTinhBang));
                                break;
                            }
                            case R.id.side_category_lipstick: {
                                checkSideCategory = true;
                                setSideCategory(LIPSTICK);
                                editItemSellByMeBinding.selectSideCategoryInEditItem.setText(getString(R.string.lipstick));
                                break;
                            }
                            case R.id.side_category_nuocHoa: {
                                checkSideCategory = true;
                                setSideCategory(NUOC_HOA);
                                editItemSellByMeBinding.selectSideCategoryInEditItem.setText(getString(R.string.nuocHoa));
                                break;
                            }
                            case R.id.side_category_lamMat: {
                                checkSideCategory = true;
                                setSideCategory(LAM_MAT);
                                editItemSellByMeBinding.selectSideCategoryInEditItem.setText(getString(R.string.lamMat));
                                break;
                            }
                            case R.id.side_category_nauAn: {
                                checkSideCategory = true;
                                setSideCategory(NAU_AN);
                                editItemSellByMeBinding.selectSideCategoryInEditItem.setText(getString(R.string.nauAn));
                                break;
                            }
                            case R.id.side_category_giaDung: {
                                checkSideCategory = true;
                                setSideCategory(GIA_DUNG);
                                editItemSellByMeBinding.selectSideCategoryInEditItem.setText(getString(R.string.giaDung));
                                break;
                            }
                            case R.id.side_category_aoNu: {
                                checkSideCategory = true;
                                setSideCategory(AO_NU);
                                editItemSellByMeBinding.selectSideCategoryInEditItem.setText(getString(R.string.aoNu));
                                break;
                            }
                            case R.id.side_category_quanNu: {
                                checkSideCategory = true;
                                setSideCategory(QUAN_NU);
                                editItemSellByMeBinding.selectSideCategoryInEditItem.setText(getString(R.string.quanNu));
                                break;
                            }
                            case R.id.side_category_vayNu: {
                                checkSideCategory = true;
                                setSideCategory(VAY_NU);
                                editItemSellByMeBinding.selectSideCategoryInEditItem.setText(getString(R.string.vayNu));
                                break;
                            }
                            case R.id.side_category_aoNam: {
                                checkSideCategory = true;
                                setSideCategory(AO_NAM);
                                editItemSellByMeBinding.selectSideCategoryInEditItem.setText(getString(R.string.aoNam));
                                break;
                            }
                            case R.id.side_category_quanNam: {
                                checkSideCategory = true;
                                setSideCategory(QUAN_NAM);
                                editItemSellByMeBinding.selectSideCategoryInEditItem.setText(getString(R.string.quanNam));
                                break;
                            }
                            case R.id.side_category_trangSuc: {
                                checkSideCategory = true;
                                setSideCategory(TRANG_SUC);
                                editItemSellByMeBinding.selectSideCategoryInEditItem.setText(getString(R.string.trangSuc));
                                break;
                            }
                            case R.id.side_category_balo: {
                                checkSideCategory = true;
                                setSideCategory(BALO);
                                editItemSellByMeBinding.selectSideCategoryInEditItem.setText(getString(R.string.balo));
                                break;
                            }
                            case R.id.side_category_tuiXach: {
                                checkSideCategory = true;
                                setSideCategory(TUI_XACH);
                                editItemSellByMeBinding.selectSideCategoryInEditItem.setText(getString(R.string.tuiXach));
                                break;
                            }
                            case R.id.side_category_laptop: {
                                checkSideCategory = true;
                                setSideCategory(LAPTOP);
                                editItemSellByMeBinding.selectSideCategoryInEditItem.setText(getString(R.string.laptop));
                                break;
                            }
                            case R.id.side_category_pc: {
                                checkSideCategory = true;
                                setSideCategory(PC);
                                editItemSellByMeBinding.selectSideCategoryInEditItem.setText(getString(R.string.pc));
                                break;
                            }
                            case R.id.side_category_phuKien: {
                                checkSideCategory = true;
                                setSideCategory(PHU_KIEN);
                                editItemSellByMeBinding.selectSideCategoryInEditItem.setText(getString(R.string.phuKien));
                                break;
                            }
                            case R.id.side_category_oto: {
                                checkSideCategory = true;
                                setSideCategory(OTO);
                                editItemSellByMeBinding.selectSideCategoryInEditItem.setText(getString(R.string.oto));
                                break;
                            }
                            case R.id.side_category_xeMay: {
                                checkSideCategory = true;
                                setSideCategory(XE_MAY);
                                editItemSellByMeBinding.selectSideCategoryInEditItem.setText(getString(R.string.xeMay));
                                break;
                            }
                            case R.id.side_category_foods: {
                                checkSideCategory = true;
                                setSideCategory(THUC_PHAM);
                                editItemSellByMeBinding.selectSideCategoryInEditItem.setText(getString(R.string.foods));
                                break;
                            }
                            case R.id.side_category_drinks: {
                                checkSideCategory = true;
                                setSideCategory(NUOC_UONG);
                                editItemSellByMeBinding.selectSideCategoryInEditItem.setText(getString(R.string.drinks));
                                break;
                            }
                        }
                        return false;
                    }
                });
                popupMenu2.show();
            }
        });
        //        checkAvatar
        editItemSellByMeBinding.confirmUrlImgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String avatarUrl = editItemSellByMeBinding.etUrlAvatarItemInEditItem.getText().toString().trim();
                if (avatarUrl.isEmpty() || avatarUrl == null) {
                    checkAvatar = false;
                    Toast.makeText(getContext(), getString(R.string.noneAcceptImg), Toast.LENGTH_SHORT).show();
                } else {
                    checkAvatar = true;
                    Picasso.get().load(avatarUrl).error(R.drawable.dont_loading_img).into(editItemSellByMeBinding.imgItemAvatarInEditItem);
                }
            }
        });
        //        confirm
        editItemSellByMeBinding.btnConfirmEditItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User userLoginNow = mainActivity.getUserLoginNow();
                String nameItem = editItemSellByMeBinding.etItemSellNameEdit.getText().toString();
                String trademark = editItemSellByMeBinding.etTrademarkInEditItem.getText().toString();
                String priceDontSale = editItemSellByMeBinding.etPriceOnceItemInEditItem.getText().toString();
                String inventoryItem = editItemSellByMeBinding.etInvetoryItemInEditItem.getText().toString();
                String characteristicsItem = editItemSellByMeBinding.etCharacteristicsItemInEditItem.getText().toString();
                String avatarUrl = editItemSellByMeBinding.etUrlAvatarItemInEditItem.getText().toString().trim();
                if (checkSale) {
                    String priceSale = editItemSellByMeBinding.etSalePriceItemInEditItem.getText().toString();
                    if (nameItem.trim().isEmpty() || nameItem == null || trademark.trim().isEmpty() || trademark == null ||
                            priceDontSale.trim().isEmpty() || priceDontSale == null || inventoryItem.trim().isEmpty() || inventoryItem == null ||
                            checkAvatar == false || priceSale.trim().isEmpty() || priceSale == null || checkSideCategory == false ||
                            characteristicsItem.trim().isEmpty() || characteristicsItem == null || checkMainCategory == false) {
                        Toast.makeText(getContext(), getString(R.string.notificationDontAcceptAddItem), Toast.LENGTH_SHORT).show();
                    } else {
                        Calendar calendar = Calendar.getInstance();
                        int priceDontSales = Integer.parseInt(priceDontSale);
                        int priceSales = Integer.parseInt(priceSale);
                        int salePercent = priceSales / priceDontSales;
                        int invetoryItems = Integer.parseInt(inventoryItem);
                        int dayEdit = calendar.get(Calendar.DAY_OF_MONTH);
                        int monthEdit = calendar.get(Calendar.MONTH) + 1;
                        int yearEdit = calendar.get(Calendar.YEAR);
                        List<AvatarURL> avatarURLList = new ArrayList<>();
                        avatarURLList.add(new AvatarURL(avatarUrl));
                        ItemSell itemSell = new ItemSell(itemSellEditNow.getIdItemSell(), mainCategory, sideCategory, nameItem, "yes", salePercent,
                                priceDontSales, priceSales, invetoryItems, 0, 0, userLoginNow.getIdUser(),
                                trademark, characteristicsItem, NEW_ITEM, dayEdit + "/" + monthEdit + "/" + yearEdit, avatarURLList);
                        sqlLiteHelper.editItemSell(itemSell);
                        String contentNotification = getString(R.string.User) + userLoginNow.getAccountName()
                                + "\n" + getString(R.string.addressNameUser) + " : " + userLoginNow.getAddress()
                                + "\n" + getString(R.string.phoneNameUser) + " : " + userLoginNow.getUserPhone()
                                + "\n" + getString(R.string.notificationEditItem) + " " + nameItem;
                        Notification notification = new Notification(0, 1 + "",
                                userLoginNow.getAccountName(), userLoginNow.getAddress(), userLoginNow.getUserPhone(),
                                dayEdit + "/" + monthEdit + "/" + yearEdit, contentNotification);
                        sqlLiteHelper.addNotification(notification);
                        Toast.makeText(getContext(), getString(R.string.notificationAcceptEditItem), Toast.LENGTH_SHORT).show();
                        mainActivity.getFragment(ManagerItemSellByMeFragment.newInstance());
                    }
                } else if (checkSale == false) {
                    if (nameItem.trim().isEmpty() || nameItem == null || trademark.trim().isEmpty() || trademark == null ||
                            priceDontSale.trim().isEmpty() || priceDontSale == null || inventoryItem.trim().isEmpty() || inventoryItem == null ||
                            avatarUrl.trim().isEmpty() || avatarUrl == null || characteristicsItem.trim().isEmpty() || characteristicsItem == null) {
                        Toast.makeText(getContext(), getString(R.string.notificationDontAcceptAddItem), Toast.LENGTH_SHORT).show();
                    } else {
                        Calendar calendar = Calendar.getInstance();
                        int priceDontSales = Integer.parseInt(priceDontSale);
                        int priceSales = priceDontSales;
                        int salePercent = 0;
                        int invetoryItems = Integer.parseInt(inventoryItem);
                        int dayEdit = calendar.get(Calendar.DAY_OF_MONTH);
                        int monthEdit = calendar.get(Calendar.MONTH) + 1;
                        int yearEdit = calendar.get(Calendar.YEAR);
                        List<AvatarURL> avatarURLList = new ArrayList<>();
                        avatarURLList.add(new AvatarURL(avatarUrl));
                        ItemSell itemSell = new ItemSell(itemSellEditNow.getIdItemSell(), mainCategory, sideCategory, nameItem, "no", salePercent,
                                priceDontSales, priceSales, invetoryItems, 0, 0, userLoginNow.getIdUser(),
                                trademark, characteristicsItem, NEW_ITEM, dayEdit + "/" + monthEdit + "/" + yearEdit, avatarURLList);
                        sqlLiteHelper.editItemSell(itemSell);
                        String contentNotification = getString(R.string.User) + userLoginNow.getAccountName()
                                + "\n" + getString(R.string.addressNameUser) + " : " + userLoginNow.getAddress()
                                + "\n" + getString(R.string.phoneNameUser) + " : " + userLoginNow.getUserPhone()
                                + "\n" + getString(R.string.notificationEditItem) + " " + nameItem;
                        Notification notification = new Notification(0, 1 + "",
                                userLoginNow.getAccountName(), userLoginNow.getAddress(), userLoginNow.getUserPhone(),
                                dayEdit + "/" + monthEdit + "/" + yearEdit, contentNotification);
                        sqlLiteHelper.addNotification(notification);
                        Toast.makeText(getContext(), getString(R.string.notificationAcceptEditItem), Toast.LENGTH_SHORT).show();
                        mainActivity.getFragment(ManagerItemSellByMeFragment.newInstance());
                    }
                }
            }
        });
        return editItemSellByMeBinding.getRoot();
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
    }

    public String getSideCategory() {
        return sideCategory;
    }

    public void setSideCategory(String sideCategory) {
        this.sideCategory = sideCategory;
    }

    public String getUrlImgAvatar() {
        return urlImgAvatar;
    }

    public void setUrlImgAvatar(String urlImgAvatar) {
        this.urlImgAvatar = urlImgAvatar;
    }
}
