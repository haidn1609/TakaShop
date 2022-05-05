package View.showItemFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.LoginActivity;
import com.example.myproject.MainActivity;
import com.example.myproject.R;
import com.example.myproject.databinding.ShowItemDetailFragmentBinding;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.List;

import AllListForder.AllKeyLocal;
import AllListForder.AllList;
import AllListForder.Object.AvatarURL;
import AllListForder.Object.InfoLogin;
import AllListForder.Object.ItemBuy;
import AllListForder.Object.ItemSell;
import AllListForder.Object.User;
import View.HomeFragment.HomeFragment;
import View.showItemFragment.Adapter.AdapterRCVShowListImgDetailItem;
import View.showItemFragment.Adapter.ShowImgItemDetailClick;
import support_functions.Classify_item_list;
import support_functions.SqlLiteHelper;

public class ShowItemDetailFragment extends Fragment implements AllList, AllKeyLocal {

    private ShowItemDetailFragmentBinding showItemDetailBinding;
    private MainActivity mainActivity;
    private AdapterRCVShowListImgDetailItem adapterRCVShowListImgDetailItem;
    private SqlLiteHelper sqlLiteHelper;

    public static ShowItemDetailFragment newInstance() {
        Bundle args = new Bundle();
        ShowItemDetailFragment fragment = new ShowItemDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        showItemDetailBinding = DataBindingUtil.inflate(inflater, R.layout.show_item_detail_fragment, container, false);
        mainActivity = (MainActivity) getActivity();
        sqlLiteHelper = new SqlLiteHelper(getContext());
        final DecimalFormatSymbols syms = new DecimalFormatSymbols();
        syms.setGroupingSeparator(',');
        DecimalFormat myFormatter = new DecimalFormat("###,###,###,###,###,###", syms);
        ItemSell itemSell = mainActivity.getItemSell();
        List<AvatarURL> listUrlImgae = itemSell.getAvatarItemSell();
        mainActivity.setVisibleSearchBar(false);
        int totalItem = itemSell.getTotalItem() - itemSell.getItemSoldInMonth();

        showItemDetailBinding.tvShowDetailNameItem.setText(itemSell.getNameItemSell());
        showItemDetailBinding.tvTotalItem.setText(" " + totalItem + " " + getString(R.string.item));
        showItemDetailBinding.tvTotalItemSold.setText(" " + itemSell.getTotalItemSold() + " " + getString(R.string.item));
        showItemDetailBinding.tvTrademark.setText(" " + itemSell.getTrademark());
        showItemDetailBinding.tvUserSellName.setText(" " + USER_LIST.get(itemSell.getIdUserSell() - 1).getAccountName());

        Picasso.get().load(listUrlImgae.get(0).getUrlImg())
                .placeholder(R.drawable.dont_loading_img)
                .error(R.drawable.dont_loading_img)
                .into(showItemDetailBinding.showImgDetailItem);

        adapterRCVShowListImgDetailItem = new AdapterRCVShowListImgDetailItem();
        adapterRCVShowListImgDetailItem.SetDataAdapter(listUrlImgae);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getBaseContext(), RecyclerView.VERTICAL, false);
        showItemDetailBinding.rcvListImgDetailItem.setAdapter(adapterRCVShowListImgDetailItem);
        showItemDetailBinding.rcvListImgDetailItem.setLayoutManager(layoutManager);
        adapterRCVShowListImgDetailItem.setClick(new ShowImgItemDetailClick() {
            @Override
            public void onClick(String linkUrl) {
                Picasso.get().load(linkUrl)
                        .placeholder(R.drawable.dont_loading_img)
                        .error(R.drawable.dont_loading_img)
                        .into(showItemDetailBinding.showImgDetailItem);
            }
        });

        if (itemSell.getSale().equals("yes")) {
            showItemDetailBinding.tvShowDetailPriceItemSell.setText(myFormatter.format(itemSell.getPriceSale()) + " đ");
            showItemDetailBinding.tvShowDetailSalePercent.setVisibility(View.VISIBLE);
            showItemDetailBinding.tvShowDetailPriceDontSale.setVisibility(View.VISIBLE);
            showItemDetailBinding.tvShowDetailPriceDontSale.setText(myFormatter.format(itemSell.getPriceDontSale()) + " đ");
            showItemDetailBinding.tvShowDetailSalePercent.setText("-" + itemSell.getSalePercent() + "%");
        } else {
            showItemDetailBinding.tvShowDetailPriceItemSell.setText(myFormatter.format(itemSell.getPriceDontSale()) + " đ");
            showItemDetailBinding.tvShowDetailSalePercent.setVisibility(View.GONE);
            showItemDetailBinding.tvShowDetailPriceDontSale.setVisibility(View.GONE);
        }
        showItemDetailBinding.tvShowDetailCharacteristics.setText(itemSell.getCharacteristics());
        showItemDetailBinding.imgIconBackInShowDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity.getMainLocal().equals(LOCAL_HOME)) {
                    mainActivity.getFragment(HomeFragment.newInstance());
                    mainActivity.setVisibleSearchBar(true);
                } else if (mainActivity.getMainLocal().equals(SHOW_ALL_ITEM)) {
                    mainActivity.setMainLocal(LOCAL_HOME);
                    mainActivity.getFragment(Show_all_item_fragment.newInstance());
                    mainActivity.setVisibleSearchBar(true);
                } else if (mainActivity.getMainLocal().equals(LOCAL_CATEGORY)) {
                    mainActivity.setMainLocal(LOCAL_CATEGORY);
                    mainActivity.getFragment(Show_all_item_fragment.newInstance());
                    mainActivity.setVisibleSearchBar(true);
                }
            }
        });
//        BUY_ITEM
        showItemDetailBinding.btnBuyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoLogin infoLogin = INFO_LOGIN_LIST.get(INFO_LOGIN_LIST.size() - 1);
                if (infoLogin.getInfoLogin().equals(USER_LOGIN)) {
                    Calendar calendar = Calendar.getInstance();
                    int id = 1;
                    int idItem = itemSell.getIdItemSell();
                    int priceOnceItem = itemSell.getPriceSale();
                    int purchased = 1;
                    int TotalItemPrice = priceOnceItem * purchased;
                    int idUserSell = itemSell.getIdUserSell();
                    String userSellName = null;
                    for (int i = 0; i < USER_LIST.size(); i++) {
                        User user = USER_LIST.get(i);
                        if (idUserSell == user.getIdUser()) {
                            userSellName = user.getAccountName();
                            break;
                        }
                    }
                    String userBuyName = infoLogin.getNameUserLogin();
                    String itemName = itemSell.getNameItemSell();
                    String avatarItem = itemSell.getAvatarItemSell().get(0).getUrlImg();
                    String dayBuy = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
                    ItemBuy itemBuy = new ItemBuy(id, idItem, priceOnceItem, TotalItemPrice, purchased, userSellName, userBuyName, itemName, avatarItem, dayBuy);
                    ITEM_ORDER_LIST.add(itemBuy);
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.addComplete), Toast.LENGTH_LONG).show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle(getString(R.string.dialogTile))
                            .setMessage(getString(R.string.notifyCheckLogin))
                            .setPositiveButton(getString(R.string.Login), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(getActivity().getBaseContext(), LoginActivity.class);
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
//        Click_show_list_item_in_user
        showItemDetailBinding.tvTrademark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tradeMark = itemSell.getTrademark();
                mainActivity.setMainLocal(LOCAL_HOME);
                mainActivity.setLocal(ITEM_FROM_USER);
                Classify_item_list.getItemInUser(TYPE_TRADEMARK, tradeMark);
                mainActivity.getFragment(Show_all_item_fragment.newInstance());
            }
        });
        showItemDetailBinding.tvUserSellName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idUserSell = itemSell.getIdUserSell() + "";
                mainActivity.setMainLocal(LOCAL_HOME);
                mainActivity.setLocal(ITEM_FROM_USER);
                Classify_item_list.getItemInUser(TYPE_SELLER, idUserSell);
                mainActivity.getFragment(Show_all_item_fragment.newInstance());
            }
        });
//        SEARCH
        showItemDetailBinding.iconSearchInShowDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setVisibleSearchBar(true);
                showItemDetailBinding.detailTileBar.setVisibility(View.GONE);
                showItemDetailBinding.svShowDetail.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        mainActivity.setVisibleSearchBar(false);
                        showItemDetailBinding.detailTileBar.setVisibility(View.VISIBLE);
                        return false;
                    }
                });
            }
        });
        showItemDetailBinding.iconBuyInShowDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoLogin infoLogin = INFO_LOGIN_LIST.get(INFO_LOGIN_LIST.size() - 1);
                if (infoLogin.getInfoLogin().equals(USER_LOGIN)) {
                    mainActivity.getFragment(OrderItemBuyFragment.newInstance());
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle(getString(R.string.dialogTile))
                            .setMessage(getString(R.string.notifyCheckLogin))
                            .setPositiveButton(getString(R.string.Login), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(getActivity().getBaseContext(), LoginActivity.class);
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
        return showItemDetailBinding.getRoot();
    }
}
