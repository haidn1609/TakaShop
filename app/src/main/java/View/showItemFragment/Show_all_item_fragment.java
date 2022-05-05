package View.showItemFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.LoginActivity;
import com.example.myproject.MainActivity;
import com.example.myproject.R;
import com.example.myproject.databinding.ShowAllListItemFragmentBinding;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import AllListForder.AllKeyLocal;
import AllListForder.AllList;
import AllListForder.Object.InfoLogin;
import AllListForder.Object.ItemSell;
import AllListForder.Object.MainAdsImg;
import AllListForder.Object.User;
import View.CategoryFragment.ShowListCategoryFragment;
import View.HomeFragment.Adapter.OnItemRCVClickListener;
import View.HomeFragment.HomeFragment;
import View.showItemFragment.Adapter.AdapterRCVShowAllItem;

public class Show_all_item_fragment extends Fragment implements AllList, AllKeyLocal, ModelShowItem {
    private int currentPage = 1;
    private ShowAllListItemFragmentBinding showAllListItemFragmentBinding;
    private MainActivity mainActivity;
    private AdapterRCVShowAllItem adapterRCVShowAllItem;
    private int totalPage;
    private List<ItemSell> MainListItemShow;
    private ShowItemPresenter showItemPresenter;

    public static Show_all_item_fragment newInstance() {
        Bundle args = new Bundle();
        Show_all_item_fragment fragment = new Show_all_item_fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        showAllListItemFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.show_all_list_item_fragment, container, false);
        mainActivity = (MainActivity) getActivity();
        mainActivity.setVisibleSearchBar(false);
        MainListItemShow = new ArrayList<>();
        showItemPresenter = new ShowItemPresenter(this);
        for (int i = 0; i < MAIN_ADS_IMG_LIST.size(); i++) {
            MainAdsImg mainAdsImg = MAIN_ADS_IMG_LIST.get(i);
            flipPerImage(mainAdsImg.getUrlIMG());
        }

        adapterRCVShowAllItem = new AdapterRCVShowAllItem();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getBaseContext(), 3, RecyclerView.VERTICAL, false);
        showAllListItemFragmentBinding.rcvShowAllItem.setLayoutManager(gridLayoutManager);
        adapterRCVShowAllItem.setItemClickListener(new OnItemRCVClickListener() {
            @Override
            public void onItemClick(ItemSell itemSell) {
                if (mainActivity.getMainLocal().equals(LOCAL_HOME)) {
                    mainActivity.setMainLocal(SHOW_ALL_ITEM);
                } else if (mainActivity.getMainLocal().equals(LOCAL_CATEGORY)) {
                    mainActivity.setMainLocal(LOCAL_CATEGORY);
                }
                mainActivity.setItemSell(itemSell);
                mainActivity.getFragment(ShowItemDetailFragment.newInstance());
            }
        });

        showItemPresenter.ShowItem(mainActivity.getLocal());
        showAllListItemFragmentBinding.tvCurrentTotalPage.setText(currentPage + "/" + totalPage);

        showAllListItemFragmentBinding.backPageInShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage == 1) {
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.dont_loading), Toast.LENGTH_SHORT).show();
                } else if (currentPage > 1) {
                    currentPage -= 1;
                    setData(MainListItemShow, currentPage);
                    showAllListItemFragmentBinding.tvCurrentTotalPage.setText(currentPage + "/" + totalPage);
                }
            }
        });
        showAllListItemFragmentBinding.nextPageInShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage == totalPage) {
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.dont_loading), Toast.LENGTH_SHORT).show();
                } else if (currentPage < totalPage) {
                    currentPage += 1;
                    setData(MainListItemShow, currentPage);
                    showAllListItemFragmentBinding.tvCurrentTotalPage.setText(currentPage + "/" + totalPage);
                }
            }
        });

        showAllListItemFragmentBinding.imgIconBackInShowAllItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity.getMainLocal().equals(LOCAL_HOME)) {
                    mainActivity.getFragment(HomeFragment.newInstance());
                    mainActivity.setVisibleSearchBar(true);
                } else if (mainActivity.getMainLocal().equals(LOCAL_CATEGORY)) {
                    mainActivity.getFragment(ShowListCategoryFragment.newInstance());
                    mainActivity.setVisibleSearchBar(true);
                }
            }
        });
        showAllListItemFragmentBinding.iconSearchInShowAllItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setVisibleSearchBar(true);
                showAllListItemFragmentBinding.showAllTileBar.setVisibility(View.GONE);
            }
        });
        showAllListItemFragmentBinding.rcvShowAllItem.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mainActivity.setVisibleSearchBar(false);
                showAllListItemFragmentBinding.showAllTileBar.setVisibility(View.VISIBLE);
                return false;
            }
        });
        showAllListItemFragmentBinding.svShowAllItem.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mainActivity.setVisibleSearchBar(false);
                showAllListItemFragmentBinding.showAllTileBar.setVisibility(View.VISIBLE);
                return false;
            }
        });
//        sort
        Comparator<ItemSell> comparatorHotItem = new Comparator<ItemSell>() {
            @Override
            public int compare(ItemSell o1, ItemSell o2) {
                return o1.getItemSoldInMonth() > o2.getItemSoldInMonth() ? -1 : 1;
            }
        };
        Comparator<ItemSell> comparatorLowPrice = new Comparator<ItemSell>() {
            @Override
            public int compare(ItemSell o1, ItemSell o2) {
                return o1.getPriceSale() < o2.getPriceSale() ? -1 : 1;
            }
        };
        Comparator<ItemSell> comparatorHightPrice = new Comparator<ItemSell>() {
            @Override
            public int compare(ItemSell o1, ItemSell o2) {
                return o1.getPriceSale() < o2.getPriceSale() ? 1 : -1;
            }
        };
        Comparator<ItemSell> comparatorDate = new Comparator<ItemSell>() {
            @Override
            public int compare(ItemSell o1, ItemSell o2) {
                Date dateO1 = null;
                Date dateO2 = null;
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    dateO1 = sdf.parse(o1.getDaySell());
                    dateO2 = sdf.parse(o2.getDaySell());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar calendarO1 = Calendar.getInstance();
                calendarO1.setTime(dateO1);
                int ngayO1 = calendarO1.get(Calendar.DAY_OF_MONTH);
                int thangO1 = calendarO1.get(Calendar.MONTH);
                int namO1 = calendarO1.get(Calendar.YEAR);
                Calendar calendarO2 = Calendar.getInstance();
                calendarO2.setTime(dateO2);
                int ngayO2 = calendarO1.get(Calendar.DAY_OF_MONTH);
                int thangO2 = calendarO1.get(Calendar.MONTH);
                int namO2 = calendarO1.get(Calendar.YEAR);
                return namO1 > namO2 ? -1 : namO1 < namO2 ? 1 : thangO1 > thangO2 ? -1 : thangO1 < thangO2 ? 1 : ngayO1 > ngayO2 ? -1 : 1;
            }
        };
        showAllListItemFragmentBinding.popupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getActivity().getBaseContext(), v);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.sort_item_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.newItemMenu: {
                                showAllListItemFragmentBinding.popupMenu.setText(getString(R.string.newItem));
                                Collections.sort(MainListItemShow, comparatorDate);
                                currentPage = 1;
                                setData(MainListItemShow, currentPage);
                                showAllListItemFragmentBinding.tvCurrentTotalPage.setText(currentPage + "/" + totalPage);
                                break;
                            }
                            case R.id.hotItemMenu: {
                                showAllListItemFragmentBinding.popupMenu.setText(getString(R.string.hotItem));
                                Collections.sort(MainListItemShow, comparatorHotItem);
                                currentPage = 1;
                                setData(MainListItemShow, currentPage);
                                showAllListItemFragmentBinding.tvCurrentTotalPage.setText(currentPage + "/" + totalPage);
                                break;
                            }
                            case R.id.lowPriceMenu: {
                                showAllListItemFragmentBinding.popupMenu.setText(getString(R.string.lowPrice));
                                Collections.sort(MainListItemShow, comparatorLowPrice);
                                currentPage = 1;
                                setData(MainListItemShow, currentPage);
                                showAllListItemFragmentBinding.tvCurrentTotalPage.setText(currentPage + "/" + totalPage);
                                break;
                            }
                            case R.id.hightPriceMenu: {
                                showAllListItemFragmentBinding.popupMenu.setText(getString(R.string.hightPrice));
                                Collections.sort(MainListItemShow, comparatorHightPrice);
                                currentPage = 1;
                                setData(MainListItemShow, currentPage);
                                showAllListItemFragmentBinding.tvCurrentTotalPage.setText(currentPage + "/" + totalPage);
                                break;
                            }

                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
//        orderItem
        showAllListItemFragmentBinding.iconBuyInShowDetail.setOnClickListener(new View.OnClickListener() {
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
        return showAllListItemFragmentBinding.getRoot();
    }

    private void setUserSellInfoView() {
        User userSell = USER_LIST.get(ITEM_IN_USER.get(0).getIdUserSell() - 1);
        if (userSell.getSourceAvatar().equals(SOURCE_AVATAR_FROM_URL)) {
            if (userSell.getAvatar().equals(NONE_AVATAR)) {
                Picasso.get().load("https://i.imgur.com/TJSfIkU.png")
                        .placeholder(R.drawable.dont_loading_img)
                        .error(R.drawable.dont_loading_img)
                        .into(showAllListItemFragmentBinding.avatarUserSell);
            } else {
                Picasso.get().load(userSell.getAvatar())
                        .placeholder(R.drawable.dont_loading_img)
                        .error(R.drawable.dont_loading_img)
                        .into(showAllListItemFragmentBinding.avatarUserSell);
            }
        } else if (userSell.getSourceAvatar().equals(SOURCE_AVATAR_FROM_STORAGE)) {
            if (userSell.getAvatar().equals(NONE_AVATAR)) {
                Picasso.get().load("https://i.imgur.com/TJSfIkU.png")
                        .placeholder(R.drawable.dont_loading_img)
                        .error(R.drawable.dont_loading_img)
                        .into(showAllListItemFragmentBinding.avatarUserSell);
            } else {
                Uri uri = Uri.parse(userSell.getAvatar());
                Picasso.get().load(uri)
                        .error(R.drawable.dont_loading_img)
                        .into(showAllListItemFragmentBinding.avatarUserSell);
            }
            showAllListItemFragmentBinding.tvNameUserSellInAllItem.setText(userSell.getAccountName());
            showAllListItemFragmentBinding.tvAddressUserSellInAllItem.setText(userSell.getAddress());
            showAllListItemFragmentBinding.tvTotalItemUserSellInAllItem.setText(ITEM_IN_USER.size() + "");
            int totalSold = 0;
            for (int i = 0; i < ITEM_IN_USER.size(); i++) {
                ItemSell itemSell = ITEM_IN_USER.get(i);
                totalSold += itemSell.getTotalItemSold();
            }
            showAllListItemFragmentBinding.tvTotalSoldUserSellInAllItem.setText(totalSold + "");
        }
    }

    private void setMainList(List<ItemSell> itemSells) {
        MainListItemShow.clear();
        MainListItemShow.addAll(itemSells);
    }

    private void setData(List<ItemSell> itemSells, int currentPage) {
        adapterRCVShowAllItem.setDataAdapter(getListItem(itemSells, currentPage));
        showAllListItemFragmentBinding.rcvShowAllItem.setAdapter(adapterRCVShowAllItem);
    }

    private int getTotalPage(List<ItemSell> itemSells) {
        int total = 1;
        if (itemSells.size() <= 15) {
            total = 1;
        } else if (itemSells.size() > 15 && itemSells.size() % 15 == 0) {
            total = itemSells.size() / 15;
        } else if (itemSells.size() > 15 && itemSells.size() % 15 != 0) {
            total = (itemSells.size() / 15) + 1;
        }
        return total;
    }

    private List<ItemSell> getListItem(List<ItemSell> itemSells, int currentPage) {
        List<ItemSell> itemSellList = new ArrayList<>();
        if (itemSells.size() <= currentPage * 15) {
            for (int i = (currentPage - 1) * 15; i < itemSells.size(); i++) {
                itemSellList.add(itemSells.get(i));
            }
        } else if (itemSells.size() > currentPage * 15) {
            for (int i = (currentPage - 1) * 15; i < (currentPage - 1) * 15 + 15; i++) {
                itemSellList.add(itemSells.get(i));
            }
        }
        return itemSellList;
    }

    private void setViewInfo(int key) {
        switch (key) {
            case 0: {
                showAllListItemFragmentBinding.viewInfoAds.setVisibility(View.VISIBLE);
                showAllListItemFragmentBinding.viewInfoUserSell.setVisibility(View.GONE);
                break;
            }
            case 1: {
                showAllListItemFragmentBinding.viewInfoAds.setVisibility(View.GONE);
                showAllListItemFragmentBinding.viewInfoUserSell.setVisibility(View.VISIBLE);
                break;
            }
        }
    }

    private void flipPerImage(String urlImage) {
        ImageView imageView = new ImageView(getContext());
        Picasso.get().load(urlImage)
                .placeholder(R.drawable.dont_loading_img)
                .error(R.drawable.dont_loading_img)
                .into(imageView);
        showAllListItemFragmentBinding.VfMainAdsShowAllFragment.addView(imageView);
        showAllListItemFragmentBinding.VfMainAdsShowAllFragment.setFlipInterval(5000);
        showAllListItemFragmentBinding.VfMainAdsShowAllFragment.setAutoStart(true);

        showAllListItemFragmentBinding.VfMainAdsShowAllFragment.setInAnimation(getContext(), android.R.anim.slide_in_left);
        showAllListItemFragmentBinding.VfMainAdsShowAllFragment.setOutAnimation(getContext(), android.R.anim.slide_out_right);
    }

    @Override
    public void setListSaleInHome() {
        setViewInfo(0);
        setMainList(ITEM_SALE_IN_DAY_LIST);
        setData(MainListItemShow, currentPage);
        totalPage = getTotalPage(MainListItemShow);
    }

    @Override
    public void setListYouMayLike() {
        setViewInfo(0);
        setMainList(ITEM_YOUR_MAY_LIKE_LIST);
        setData(MainListItemShow, currentPage);
        totalPage = getTotalPage(MainListItemShow);
    }

    @Override
    public void setListHotDealItem() {
        setViewInfo(0);
        setMainList(ITEM_HOT_DEAL);
        setData(MainListItemShow, currentPage);
        totalPage = getTotalPage(MainListItemShow);
    }

    @Override
    public void setListBestPriceItem() {
        setViewInfo(0);
        setMainList(ITEM_BEST_PRICE);
        setData(MainListItemShow, currentPage);
        totalPage = getTotalPage(MainListItemShow);
    }

    @Override
    public void setListNewItem() {
        setViewInfo(0);
        setMainList(ITEM_NEW);
        setData(MainListItemShow, currentPage);
        totalPage = getTotalPage(MainListItemShow);
    }

    @Override
    public void setListAllItem() {
        setViewInfo(0);
        setMainList(ALL_ITEM_SELL_LIST);
        setData(MainListItemShow, currentPage);
        totalPage = getTotalPage(MainListItemShow);
    }

    @Override
    public void setListItemFromCategory() {
        setViewInfo(0);
        setMainList(ITEM_IN_CATEGORY);
        setData(MainListItemShow, currentPage);
        totalPage = getTotalPage(MainListItemShow);
    }

    @Override
    public void setListItemFromEventInHome() {
        setViewInfo(0);
        setMainList(ITEM_IN_EVENT);
        setData(MainListItemShow, currentPage);
        totalPage = getTotalPage(MainListItemShow);
    }

    @Override
    public void setListItemFromUser() {
        setViewInfo(1);
        setMainList(ITEM_IN_USER);
        setUserSellInfoView();
        setData(MainListItemShow, currentPage);
        totalPage = getTotalPage(MainListItemShow);
    }
}
