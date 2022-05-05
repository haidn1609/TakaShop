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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.MainActivity;
import com.example.myproject.R;
import com.example.myproject.databinding.ManagerItemSellByMeBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import AllListForder.Object.ItemSell;
import AllListForder.Object.User;
import View.UserFragment.Adapter.AdapterRCVManagerItemSellByMe;
import View.UserFragment.Adapter.RCVManagerItemSellsByMeClickListener;
import support_functions.SqlLiteHelper;

public class ManagerItemSellByMeFragment extends Fragment {
    private ManagerItemSellByMeBinding managerItemSellByMeBinding;
    private SqlLiteHelper sqlLiteHelper;
    private MainActivity mainActivity;
    private int totalPage;
    private int currentPage = 1;
    private AdapterRCVManagerItemSellByMe adapterRCVManagerItemSellByMe;
    private List<ItemSell> showListItemSell;

    public static ManagerItemSellByMeFragment newInstance() {

        Bundle args = new Bundle();

        ManagerItemSellByMeFragment fragment = new ManagerItemSellByMeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        managerItemSellByMeBinding = DataBindingUtil.inflate(inflater, R.layout.manager_item_sell_by_me, container, false);
        mainActivity = (MainActivity) getActivity();
        sqlLiteHelper = new SqlLiteHelper(getContext());
        showListItemSell = new ArrayList<>();
        totalPage = getTotalPage(sqlLiteHelper.getAllListItemSell());
        setShowListItemBuy(sqlLiteHelper.getAllListItemSell());
        managerItemSellByMeBinding.tvCurrentTotalPageManagerItemSellByMe.setText(currentPage + "/" + totalPage);
//        RCV
        adapterRCVManagerItemSellByMe = new AdapterRCVManagerItemSellByMe(getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        managerItemSellByMeBinding.rcvManagerItemSellByMe.setLayoutManager(layoutManager);
        setData(showListItemSell, currentPage);
        adapterRCVManagerItemSellByMe.setRcvManagerItemSellsByMeClickListener(new RCVManagerItemSellsByMeClickListener() {
            @Override
            public void btnDeleteClickListener() {
                setShowListItemBuy(sqlLiteHelper.getAllListItemSell());
                totalPage = getTotalPage(showListItemSell);
                currentPage = 1;
                setData(showListItemSell, currentPage);
                managerItemSellByMeBinding.tvCurrentTotalPageManagerItemSellByMe.setText(currentPage + "/" + totalPage);
            }

            @Override
            public void btnEditClickListener(ItemSell itemSell) {
                mainActivity.setItemSell(itemSell);
                mainActivity.getFragment(EditItemSellByMe.newInstance());
            }
        });

        managerItemSellByMeBinding.backPageInManagerItemSellByMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage == 1) {
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.dont_loading), Toast.LENGTH_SHORT).show();
                } else if (currentPage > 1) {
                    currentPage -= 1;
                    setData(showListItemSell, currentPage);
                    managerItemSellByMeBinding.tvCurrentTotalPageManagerItemSellByMe.setText(currentPage + "/" + totalPage);
                }
            }
        });
        managerItemSellByMeBinding.nextPageInManagerItemSellByMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage == totalPage) {
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.dont_loading), Toast.LENGTH_SHORT).show();
                } else if (currentPage < totalPage) {
                    currentPage += 1;
                    setData(showListItemSell, currentPage);
                    managerItemSellByMeBinding.tvCurrentTotalPageManagerItemSellByMe.setText(currentPage + "/" + totalPage);
                }
            }
        });
        managerItemSellByMeBinding.imgBackInManagerItemSellByMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.getFragment(ManagerItemSellFragment.newInstance());
            }
        });
//        Search
        managerItemSellByMeBinding.refeshListItemSellByMeManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setShowListItemBuy(sqlLiteHelper.getAllListItemSell());
                totalPage = getTotalPage(showListItemSell);
                currentPage = 1;
                setData(showListItemSell, currentPage);
                managerItemSellByMeBinding.tvCurrentTotalPageManagerItemSellByMe.setText(currentPage + "/" + totalPage);
            }
        });
        managerItemSellByMeBinding.iconSearchItemSellByMeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameItemBuy = managerItemSellByMeBinding.etSearchItemSellByMeName.getText().toString().trim().toLowerCase();
                if (nameItemBuy == null || nameItemBuy.isEmpty()) {
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.dont_find_itemBuy), Toast.LENGTH_SHORT).show();
                    setShowListItemBuy(sqlLiteHelper.getAllListItemSell());
                    totalPage = getTotalPage(showListItemSell);
                    currentPage = 1;
                    setData(showListItemSell, currentPage);
                    managerItemSellByMeBinding.tvCurrentTotalPageManagerItemSellByMe.setText(currentPage + "/" + totalPage);
                    managerItemSellByMeBinding.etSearchItemSellByMeName.setText("");
                } else {
                    List<ItemSell> itemSellsByMeFindList = new ArrayList<>();
                    for (int i = 0; i < showListItemSell.size(); i++) {
                        ItemSell itemSell = showListItemSell.get(i);
                        if (itemSell.getNameItemSell().trim().toLowerCase().indexOf(nameItemBuy) > -1
                                || itemSell.getDaySell().trim().toLowerCase().indexOf(nameItemBuy) > -1) {
                            itemSellsByMeFindList.add(itemSell);
                        } else continue;
                    }
                    if (itemSellsByMeFindList == null || itemSellsByMeFindList.isEmpty()) {
                        Toast.makeText(getActivity().getBaseContext(), getString(R.string.dont_find_itemBuy), Toast.LENGTH_SHORT).show();
                        managerItemSellByMeBinding.etSearchItemSellByMeName.setText("");
                    } else {
                        setShowListItemBuy(itemSellsByMeFindList);
                        totalPage = getTotalPage(showListItemSell);
                        currentPage = 1;
                        setData(showListItemSell, currentPage);
                        managerItemSellByMeBinding.tvCurrentTotalPageManagerItemSellByMe.setText(currentPage + "/" + totalPage);
                        managerItemSellByMeBinding.etSearchItemSellByMeName.setText("");
                    }
                }
            }
        });
//        Sort
        Comparator<ItemSell> comparatorSoldInMonth = new Comparator<ItemSell>() {
            @Override
            public int compare(ItemSell o1, ItemSell o2) {
                return o1.getItemSoldInMonth() > o2.getItemSoldInMonth() ? -1 : 1;
            }
        };
        Comparator<ItemSell> comparatorTotalSold = new Comparator<ItemSell>() {
            @Override
            public int compare(ItemSell o1, ItemSell o2) {
                return o1.getTotalItemSold() > o2.getTotalItemSold() ? -1 : 1;
            }
        };
        Comparator<ItemSell> comparatorInventory = new Comparator<ItemSell>() {
            @Override
            public int compare(ItemSell o1, ItemSell o2) {
                return o1.getTotalItem() > o2.getTotalItem() ? -1 : 1;
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
        managerItemSellByMeBinding.sortItemSellByMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getActivity().getBaseContext(), v);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.menu_sort_item_sell_by_me, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.time_me_sell: {
                                managerItemSellByMeBinding.sortItemSellByMe.setText(getString(R.string.day_me_sell));
                                Collections.sort(showListItemSell, comparatorDate);
                                currentPage = 1;
                                setData(showListItemSell, currentPage);
                                managerItemSellByMeBinding.tvCurrentTotalPageManagerItemSellByMe.setText(currentPage + "/" + totalPage);
                                break;
                            }
                            case R.id.total_item_sell: {
                                managerItemSellByMeBinding.sortItemSellByMe.setText(getString(R.string.total_item_sell));
                                Collections.sort(showListItemSell, comparatorTotalSold);
                                currentPage = 1;
                                setData(showListItemSell, currentPage);
                                managerItemSellByMeBinding.tvCurrentTotalPageManagerItemSellByMe.setText(currentPage + "/" + totalPage);
                                break;
                            }
                            case R.id.sold_in_month_item_sell: {
                                managerItemSellByMeBinding.sortItemSellByMe.setText(getString(R.string.sold_in_month));
                                Collections.sort(showListItemSell, comparatorSoldInMonth);
                                currentPage = 1;
                                setData(showListItemSell, currentPage);
                                managerItemSellByMeBinding.tvCurrentTotalPageManagerItemSellByMe.setText(currentPage + "/" + totalPage);
                                break;
                            }
                            case R.id.inventory_item: {
                                managerItemSellByMeBinding.sortItemSellByMe.setText(getString(R.string.inventory_item));
                                Collections.sort(showListItemSell, comparatorInventory);
                                currentPage = 1;
                                setData(showListItemSell, currentPage);
                                managerItemSellByMeBinding.tvCurrentTotalPageManagerItemSellByMe.setText(currentPage + "/" + totalPage);
                                break;
                            }
                            case R.id.lowPrice: {
                                managerItemSellByMeBinding.sortItemSellByMe.setText(getString(R.string.lowPrice));
                                Collections.sort(showListItemSell, comparatorLowPrice);
                                currentPage = 1;
                                setData(showListItemSell, currentPage);
                                managerItemSellByMeBinding.tvCurrentTotalPageManagerItemSellByMe.setText(currentPage + "/" + totalPage);
                                break;
                            }
                            case R.id.hightPrice: {
                                managerItemSellByMeBinding.sortItemSellByMe.setText(getString(R.string.hightPrice));
                                Collections.sort(showListItemSell, comparatorHightPrice);
                                currentPage = 1;
                                setData(showListItemSell, currentPage);
                                managerItemSellByMeBinding.tvCurrentTotalPageManagerItemSellByMe.setText(currentPage + "/" + totalPage);
                                break;
                            }
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        return managerItemSellByMeBinding.getRoot();
    }

    private void setData(List<ItemSell> itemSellList, int currentPage) {
        adapterRCVManagerItemSellByMe.setDataItemSellList(getListItemSell(itemSellList, currentPage));
        managerItemSellByMeBinding.rcvManagerItemSellByMe.setAdapter(adapterRCVManagerItemSellByMe);
    }

    private List<ItemSell> getListItemSell(List<ItemSell> itemSellList, int currentPage) {
        List<ItemSell> itemSelList1 = new ArrayList<>();
        if (itemSellList.size() <= currentPage * 15) {
            for (int i = (currentPage - 1) * 15; i < itemSellList.size(); i++) {
                itemSelList1.add(itemSellList.get(i));
            }
        } else if (itemSellList.size() > currentPage * 15) {
            for (int i = (currentPage - 1) * 15; i < (currentPage - 1) * 15 + 15; i++) {
                itemSelList1.add(itemSellList.get(i));
            }
        }
        return itemSelList1;
    }

    private int getTotalPage(List<ItemSell> itemSellList) {
        int total = 1;
        if (itemSellList.size() <= 15) {
            total = 1;
        } else if (itemSellList.size() > 15 && itemSellList.size() % 15 == 0) {
            total = itemSellList.size() / 15;
        } else if (itemSellList.size() > 15 && itemSellList.size() % 15 != 0) {
            total = (itemSellList.size() / 15) + 1;
        }
        return total;
    }

    private void setShowListItemBuy(List<ItemSell> showListItemSells) {
        List<ItemSell> itemSellsByMe = new ArrayList<>();
        User userLoginNow = mainActivity.getUserLoginNow();
        for (int i = 0; i < showListItemSells.size(); i++) {
            ItemSell itemSell = showListItemSells.get(i);
            if (itemSell.getIdUserSell() == userLoginNow.getIdUser()) {
                itemSellsByMe.add(itemSell);
            } else continue;
        }
        this.showListItemSell = itemSellsByMe;
    }
}
