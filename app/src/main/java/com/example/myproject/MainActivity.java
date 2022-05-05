package com.example.myproject;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.androidstudy.networkmanager.Monitor;
import com.androidstudy.networkmanager.Tovuti;
import com.example.myproject.databinding.ActivityMainBinding;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import AllListForder.AllKeyLocal;
import AllListForder.AllList;
import AllListForder.Object.InfoLogin;
import AllListForder.Object.ItemSell;
import AllListForder.Object.User;
import View.CategoryFragment.ShowListCategoryFragment;
import View.HomeFragment.HomeFragment;
import View.NotificationFragment.NotificationFragment;
import View.UserFragment.UserFragment;
import View.showItemFragment.OrderItemBuyFragment;
import support_functions.SqlLiteHelper;

public class MainActivity extends AppCompatActivity implements AllList, AllKeyLocal, View.OnClickListener, LocationListener {
    private ActivityMainBinding mainBinding;
    private ItemSell itemSell;
    private String local;
    private String MainLocal;
    private String typeCategory;
    private SqlLiteHelper sqlLiteHelper;
    private String viTri;
    private Geocoder geocoder;
    private LocationManager locationManager;
    private double latitude;
    private double longitude;
    private List<Address> addressList;
    private final ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {

        }

        @Override
        public void closeMenu() {

        }
    };
    private ResideMenu resideMenu;
    private ResideMenuItem homeItem;
    private ResideMenuItem categoryItem;
    //private ResideMenuItem newsFeedItem;
    private ResideMenuItem notificationItem;
    private ResideMenuItem userItem;
    private User userLoginNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        getFragment(HomeFragment.newInstance());
        sqlLiteHelper = new SqlLiteHelper(this);

        geocoder = new Geocoder(this, Locale.ENGLISH);
        addressList = new ArrayList<>();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.INTERNET}, 200);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, this);

        Tovuti.from(this).monitor(new Monitor.ConnectivityListener() {
            @Override
            public void onConnectivityChanged(int connectionType, boolean isConnected, boolean isFast) {
                if (isConnected) {
                    Intent getIntend = getIntent();
                    Boolean checkInternet = getIntend.getBooleanExtra("CheckInternet", true);
                    if (checkInternet == false) {
                        Toast.makeText(MainActivity.this, "Kiem tra lai ket noi", Toast.LENGTH_LONG).show();
                    }
                    setUpMenu();
                    mainBinding.imgIconBuyInMain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            InfoLogin infoLogin = INFO_LOGIN_LIST.get(INFO_LOGIN_LIST.size() - 1);
                            if (infoLogin.getInfoLogin().equals(USER_LOGIN)) {
                                setVisibleSearchBar(false);
                                getFragment(OrderItemBuyFragment.newInstance());
                            } else {
                                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                                        .setTitle(getString(R.string.dialogTile))
                                        .setMessage(getString(R.string.notifyCheckLogin))
                                        .setPositiveButton(getString(R.string.Login), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        }).setNegativeButton(getString(R.string.cancelNotify), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        }).create();
                                alertDialog.show();
                            }

                        }
                    });
                } else {
                    AlertDialog checkInternetAnalog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle(getString(R.string.dialogTile))
                            .setMessage(getString(R.string.notifyCheckInternet))
                            .setPositiveButton(getString(R.string.returnConnect), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

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
        });
    }

    private void setUpMenu() {
        resideMenu = new ResideMenu(this);
        //SCREEN_STATE_ON
        resideMenu.setSwipeDirectionDisable(ResideMenu.SCREEN_STATE_ON);
        resideMenu.setSwipeDirectionDisable(ResideMenu.SCREEN_STATE_OFF);
        resideMenu.setBackground(R.drawable.menu_backgroud);
        resideMenu.attachToActivity(this);
        resideMenu.setScaleValue(0.6f);

        resideMenu.setMenuListener(menuListener);

        homeItem = new ResideMenuItem(this, R.drawable.homeimg, getString(R.string.function_home));
        categoryItem = new ResideMenuItem(this, R.drawable.categoryimg, getString(R.string.function_Category));
        //newsFeedItem = new ResideMenuItem(this, R.drawable.newsimg, getString(R.string.function_new));
        notificationItem = new ResideMenuItem(this, R.drawable.notificationimg, getString(R.string.function_Notification));
        userItem = new ResideMenuItem(this, R.drawable.userimg, getString(R.string.function_user));

        homeItem.setOnClickListener(this);
        categoryItem.setOnClickListener(this);
        //newsFeedItem.setOnClickListener(this);
        notificationItem.setOnClickListener(this);
        userItem.setOnClickListener(this);

        resideMenu.addMenuItem(homeItem, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(categoryItem, ResideMenu.DIRECTION_LEFT);
        //resideMenu.addMenuItem(newsFeedItem, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(notificationItem, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(userItem, ResideMenu.DIRECTION_LEFT);

        mainBinding.btnBarMenuInMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Tovuti.from(this).stop();
    }

    public User getUserLoginNow() {
        return userLoginNow;
    }

    public void setUserLoginNow(User userLoginNow) {
        this.userLoginNow = userLoginNow;
    }

    public String getMainLocal() {
        return MainLocal;
    }

    public void setMainLocal(String mainLocal) {
        MainLocal = mainLocal;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public ItemSell getItemSell() {
        return itemSell;
    }

    public void setItemSell(ItemSell itemSell) {
        this.itemSell = itemSell;
    }

    public EditText getEt() {
        return mainBinding.etSearchItemInMain;
    }

    public String getTypeCategory() {
        return typeCategory;
    }

    public void setTypeCategory(String typeCategory) {
        this.typeCategory = typeCategory;
    }

    public void getFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, fragment).commit();
    }

    public void setVisibleSearchBar(boolean visibleSearchBar) {
        if (visibleSearchBar) {
            mainBinding.mainSearchBar.setVisibility(View.VISIBLE);
        } else {
            mainBinding.mainSearchBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == homeItem) {
            mainBinding.mainSearchBar.setVisibility(View.VISIBLE);
            getFragment(HomeFragment.newInstance());
        } else if (v == categoryItem) {
            mainBinding.mainSearchBar.setVisibility(View.VISIBLE);
            setLocal(TOY_AND_MOM);
            setTypeCategory(TYPE_MAIN);
            getFragment(ShowListCategoryFragment.newInstance());
        }
//        else if (v == newsFeedItem) {
//            mainBinding.mainSearchBar.setVisibility(View.VISIBLE);
//            getFragment(NewsFeedFragment.newInstance());
//        }
        else if (v == notificationItem) {
            mainBinding.mainSearchBar.setVisibility(View.VISIBLE);
            getFragment(NotificationFragment.newInstance());
        } else if (v == userItem) {
            mainBinding.mainSearchBar.setVisibility(View.VISIBLE);
            getFragment(UserFragment.newInstance());
        }
        resideMenu.closeMenu();
    }

    public void addLoginInfo(InfoLogin infoLogin) {
        sqlLiteHelper.addCheckLoginTable(infoLogin);
        List<InfoLogin> infoLoginList = sqlLiteHelper.getAllListCheckLogin();
        INFO_LOGIN_LIST.clear();
        INFO_LOGIN_LIST.addAll(infoLoginList);
    }

    public String getVitri() {
        return viTri;
    }

    public void setVitri(String vitri) {
        this.viTri = vitri;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        try {
            addressList = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setVitri(addressList.get(0).getAddressLine(0));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }


}