package com.example.myproject;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

import AllListForder.AllKeyLocal;
import AllListForder.AllList;
import AllListForder.Object.EventInHome;
import AllListForder.Object.InfoLogin;
import AllListForder.Object.ItemSell;
import AllListForder.Object.MainAdsImg;
import AllListForder.Object.MainCategory;
import AllListForder.Object.SideCategory;
import AllListForder.Object.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import support_functions.CheckFistInstallApp;
import support_functions.CheckInternet;
import support_functions.GetApiSP;
import support_functions.GetOBJAPI;
import support_functions.SqlLiteHelper;

public class LoadingScreen extends AppCompatActivity implements AllList, AllKeyLocal {
    private final int Request_Permission_Code = 10;
    private static final String keyFistInstal = "KEY_FIST_INSTAL";
    private SqlLiteHelper sqlLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, Request_Permission_Code);
        sqlLiteHelper = new SqlLiteHelper(this);
        loadData();


    }

    private void loadData() {
        if (CheckInternet.checkInterNet(this)) {
            //network enabled
            //new GetData().execute();
            loadingData();
        } else {
            //network disable
            AlertDialog checkInternetAnalog = new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.dialogTile))
                    .setMessage(getString(R.string.notifyCheckInternet))
                    .setPositiveButton(getString(R.string.returnConnect), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            loadData();
                        }
                    })
                    .setNegativeButton(getString(R.string.cancelNotify), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            System.exit(0);
                        }
                    }).create();
            checkInternetAnalog.show();
        }
    }

    private void setListCategory() {
        List<SideCategory> sideCategoryList0 = new ArrayList<>();
        sideCategoryList0.add(new SideCategory(0, 0, TA_LOT, getString(R.string.taLot), "https://i.imgur.com/tAj97IH.png"));
        sideCategoryList0.add(new SideCategory(1, 0, SUA_BOT, getString(R.string.suaBot), "https://i.imgur.com/Ps8Bgif.png"));
        sideCategoryList0.add(new SideCategory(2, 0, DO_CHOI, getString(R.string.doChoi), "https://i.imgur.com/Oif1vUx.png"));
        sideCategoryList0.add(new SideCategory(3, 0, QUAN_AO_TRE_EM, getString(R.string.quanAoTreEm), "https://i.imgur.com/qKAJLnx.png"));
        MAIN_CATEGORY_LIST.add(new MainCategory(0, TOY_AND_MOM, getString(R.string.toy_and_mom), "https://i.imgur.com/CAqQVk3.png", sideCategoryList0));
        List<SideCategory> sideCategoryList1 = new ArrayList<>();
        sideCategoryList1.add(new SideCategory(4, 1, DIEN_THOAI, getString(R.string.dienThoai), "https://i.imgur.com/uzmsdxl.png"));
        sideCategoryList1.add(new SideCategory(5, 1, MAY_TINH_BANG, getString(R.string.mayTinhBang), "https://i.imgur.com/3idWCbN.png"));
        MAIN_CATEGORY_LIST.add(new MainCategory(1, PHONE_AND_TABLET, getString(R.string.phone_and_tablet), "https://i.imgur.com/eku81jT.png", sideCategoryList1));
        List<SideCategory> sideCategoryList2 = new ArrayList<>();
        sideCategoryList2.add(new SideCategory(6, 2, LIPSTICK, getString(R.string.lipstick), "https://i.imgur.com/Wla6TRd.png"));
        sideCategoryList2.add(new SideCategory(7, 2, NUOC_HOA, getString(R.string.nuocHoa), "https://i.imgur.com/N4N6mOf.png"));
        MAIN_CATEGORY_LIST.add(new MainCategory(2, COSMETIC, getString(R.string.cosmetic), "https://i.imgur.com/SO1xlUk.png", sideCategoryList2));
        List<SideCategory> sideCategoryList3 = new ArrayList<>();
        sideCategoryList3.add(new SideCategory(8, 3, LAM_MAT, getString(R.string.lamMat), "https://i.imgur.com/UDLG2mr.png"));
        sideCategoryList3.add(new SideCategory(9, 3, NAU_AN, getString(R.string.nauAn), "https://i.imgur.com/zhpfnna.png"));
        sideCategoryList3.add(new SideCategory(10, 3, GIA_DUNG, getString(R.string.giaDung), "https://i.imgur.com/O05akzG.png"));
        MAIN_CATEGORY_LIST.add(new MainCategory(3, ELECTRIC_APPLIANCES, getString(R.string.electric_appliances), "https://i.imgur.com/Hm3meHM.png", sideCategoryList3));
        List<SideCategory> sideCategoryList4 = new ArrayList<>();
        sideCategoryList4.add(new SideCategory(11, 4, AO_NU, getString(R.string.aoNu), "https://i.imgur.com/nSylJ87.png"));
        sideCategoryList4.add(new SideCategory(12, 4, QUAN_NU, getString(R.string.quanNu), "https://i.imgur.com/ArIvdw9.png"));
        sideCategoryList4.add(new SideCategory(13, 4, VAY_NU, getString(R.string.vayNu), "https://i.imgur.com/pUhp8zx.png"));
        MAIN_CATEGORY_LIST.add(new MainCategory(4, WOMEN_FASHION, getString(R.string.women_fashion), "https://i.imgur.com/jWsiAaI.png", sideCategoryList4));
        List<SideCategory> sideCategoryList5 = new ArrayList<>();
        sideCategoryList5.add(new SideCategory(14, 5, AO_NAM, getString(R.string.aoNam), "https://i.imgur.com/pCTD7WE.png"));
        sideCategoryList5.add(new SideCategory(15, 5, QUAN_NAM, getString(R.string.quanNam), "https://i.imgur.com/L1auLma.png"));
        MAIN_CATEGORY_LIST.add(new MainCategory(5, MEN_FASHION, getString(R.string.men_fashion), "https://i.imgur.com/SFGKWFI.png", sideCategoryList5));
        List<SideCategory> sideCategoryList6 = new ArrayList<>();
        sideCategoryList6.add(new SideCategory(16, 6, TRANG_SUC, getString(R.string.trangSuc), "https://i.imgur.com/pbWZoga.png"));
        sideCategoryList6.add(new SideCategory(17, 6, BALO, getString(R.string.balo), "https://i.imgur.com/lrd90rb.png"));
        sideCategoryList6.add(new SideCategory(18, 6, TUI_XACH, getString(R.string.tuiXach), "https://i.imgur.com/nL2UCy4.png"));
        MAIN_CATEGORY_LIST.add(new MainCategory(6, JEWELRY, getString(R.string.jewelry), "https://i.imgur.com/Vp7qmin.png", sideCategoryList6));
        List<SideCategory> sideCategoryList7 = new ArrayList<>();
        sideCategoryList7.add(new SideCategory(19, 7, LAPTOP, getString(R.string.laptop), "https://i.imgur.com/iI1A4xC.png"));
        sideCategoryList7.add(new SideCategory(20, 7, PC, getString(R.string.pc), "https://i.imgur.com/2wHtOIZ.png"));
        sideCategoryList7.add(new SideCategory(21, 7, PHU_KIEN, getString(R.string.phuKien), "https://i.imgur.com/s5D9gMN.png"));
        MAIN_CATEGORY_LIST.add(new MainCategory(7, LAPTOP_PC, getString(R.string.laptop_pc), "https://i.imgur.com/7Vv1ESt.png", sideCategoryList7));
        List<SideCategory> sideCategoryList8 = new ArrayList<>();
        sideCategoryList8.add(new SideCategory(22, 8, OTO, getString(R.string.oto), "https://i.imgur.com/mLMON10.png"));
        sideCategoryList8.add(new SideCategory(23, 8, XE_MAY, getString(R.string.xeMay), "https://i.imgur.com/djFBx2Z.png"));
        MAIN_CATEGORY_LIST.add(new MainCategory(8, CAR_MOTO, getString(R.string.car_moto), "https://i.imgur.com/iGvJFob.png", sideCategoryList8));
        List<SideCategory> sideCategoryList9 = new ArrayList<>();
        sideCategoryList9.add(new SideCategory(24, 9, THUC_PHAM, getString(R.string.foods), "https://i.imgur.com/QfF9s92.png"));
        sideCategoryList9.add(new SideCategory(25, 9, NUOC_UONG, getString(R.string.drinks), "https://i.imgur.com/47CUdNJ.png"));
        MAIN_CATEGORY_LIST.add(new MainCategory(9, FOODS_AND_DRINK, getString(R.string.food_drink), "https://i.imgur.com/dTncThD.png", sideCategoryList9));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Request_Permission_Code) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadingData() {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://demo8357538.mockable.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetApiSP getApiSP = retrofit.create(GetApiSP.class);
        final CheckFistInstallApp checkFistInstallApp = new CheckFistInstallApp(LoadingScreen.this);

        Call<List<ItemSell>> callListItemSell = getApiSP.ITEM_SELLS_CALL();
        Call<List<EventInHome>> callListEventInHome = getApiSP.EVENT_IN_HOME_CALL();
        Call<List<MainAdsImg>> callListMainAdsInHome = getApiSP.MAIN_ADS_IN_HOME_CALL();
        Call<List<User>> callListUser = getApiSP.USER_LIST_CALL();

        setListCategory();
        if (checkFistInstallApp.getBooleanValue(keyFistInstal)) {
            List<User> userList1 = sqlLiteHelper.getAllListUser();
            USER_LIST.addAll(userList1);
            List<InfoLogin> infoLoginList = sqlLiteHelper.getAllListCheckLogin();
            INFO_LOGIN_LIST.addAll(infoLoginList);
        } else {
            sqlLiteHelper.addCheckLoginTable(new InfoLogin(0, NONE_USER, USER_LOGOUT));
            List<InfoLogin> infoLoginList = sqlLiteHelper.getAllListCheckLogin();
            INFO_LOGIN_LIST.addAll(infoLoginList);
            callListUser.clone().enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getBaseContext(), "Code" + response.code(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    List<User> userList = response.body();
                    for (int i = 0; i < userList.size(); i++) {
                        sqlLiteHelper.addUserTable(userList.get(i));
                    }
                    List<User> userList1 = sqlLiteHelper.getAllListUser();
                    USER_LIST.addAll(userList1);

                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    Toast.makeText(getBaseContext(), "False", Toast.LENGTH_SHORT).show();
                }
            });
            checkFistInstallApp.putBooleanValue(keyFistInstal, true);

        }
        GetOBJAPI.getOBJEventInHome(callListEventInHome, LoadingScreen.this);
        GetOBJAPI.getOBJMainAdsImg(callListMainAdsInHome, LoadingScreen.this);
        GetOBJAPI.getOBJItemSell(callListItemSell, LoadingScreen.this, intent);
    }

}