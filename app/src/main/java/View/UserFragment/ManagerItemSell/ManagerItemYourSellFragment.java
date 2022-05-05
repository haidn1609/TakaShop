package View.UserFragment.ManagerItemSell;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.MainActivity;
import com.example.myproject.R;
import com.example.myproject.databinding.ManagerItemYourSellBinding;

import java.util.ArrayList;
import java.util.List;

import AllListForder.Object.ItemBuy;
import AllListForder.Object.User;
import View.UserFragment.Adapter.AdapterRcvItemYourSell;
import support_functions.SqlLiteHelper;

public class ManagerItemYourSellFragment extends Fragment {
    private ManagerItemYourSellBinding managerItemYourSellBinding;
    private SqlLiteHelper sqlLiteHelper;
    private MainActivity mainActivity;
    private AdapterRcvItemYourSell adapterRcvItemYourSell;
    private List<ItemBuy> showListItemBuy;
    private int totalPage;
    private int currentPage = 1;

    public static ManagerItemYourSellFragment newInstance() {

        Bundle args = new Bundle();

        ManagerItemYourSellFragment fragment = new ManagerItemYourSellFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        managerItemYourSellBinding = DataBindingUtil.inflate(inflater, R.layout.manager_item_your_sell, container, false);
        mainActivity = (MainActivity) getActivity();
        sqlLiteHelper = new SqlLiteHelper(getContext());
        showListItemBuy = new ArrayList<>();
        setShowListItemBuy(sqlLiteHelper.getAllListItemBuy());
        totalPage = getTotalPage(showListItemBuy);
        managerItemYourSellBinding.tvCurrentTotalPageManagerItemYourSell.setText(currentPage + "/" + totalPage);
//RCV
        adapterRcvItemYourSell = new AdapterRcvItemYourSell();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        managerItemYourSellBinding.rcvManagerItemYourSell.setLayoutManager(layoutManager);
        setData(showListItemBuy, currentPage);

        managerItemYourSellBinding.backPageInManagerItemYourSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage == 1) {
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.dont_loading), Toast.LENGTH_SHORT).show();
                } else if (currentPage > 1) {
                    currentPage -= 1;
                    setData(showListItemBuy, currentPage);
                    managerItemYourSellBinding.tvCurrentTotalPageManagerItemYourSell.setText(currentPage + "/" + totalPage);
                }
            }
        });
        managerItemYourSellBinding.nextPageInManagerItemYourSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage == totalPage) {
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.dont_loading), Toast.LENGTH_SHORT).show();
                } else if (currentPage < totalPage) {
                    currentPage += 1;
                    setData(showListItemBuy, currentPage);
                    managerItemYourSellBinding.tvCurrentTotalPageManagerItemYourSell.setText(currentPage + "/" + totalPage);
                }
            }
        });
        managerItemYourSellBinding.imgBackInManagerItemYourSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.getFragment(ManagerItemSellFragment.newInstance());
            }
        });
//Search
        managerItemYourSellBinding.refeshListItemYourSellManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setShowListItemBuy(sqlLiteHelper.getAllListItemBuy());
                totalPage = getTotalPage(showListItemBuy);
                currentPage = 1;
                setData(showListItemBuy, currentPage);
                managerItemYourSellBinding.tvCurrentTotalPageManagerItemYourSell.setText(currentPage + "/" + totalPage);
            }
        });
        managerItemYourSellBinding.iconSearchItemYourSellName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameItemBuy = managerItemYourSellBinding.etSearchItemYourSellName.getText().toString().trim().toLowerCase();
                if (nameItemBuy == null || nameItemBuy.isEmpty()) {
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.dont_find_itemBuy), Toast.LENGTH_SHORT).show();
                    setShowListItemBuy(sqlLiteHelper.getAllListItemBuy());
                    totalPage = getTotalPage(showListItemBuy);
                    currentPage = 1;
                    setData(showListItemBuy, currentPage);
                    managerItemYourSellBinding.tvCurrentTotalPageManagerItemYourSell.setText(currentPage + "/" + totalPage);
                    managerItemYourSellBinding.etSearchItemYourSellName.setText("");
                } else {
                    List<ItemBuy> itemBuyByMeFindList = new ArrayList<>();
                    for (int i = 0; i < showListItemBuy.size(); i++) {
                        ItemBuy itemBuy = showListItemBuy.get(i);
                        if (itemBuy.getItemName().trim().toLowerCase().indexOf(nameItemBuy) > -1
                                || itemBuy.getTimeBuy().trim().toLowerCase().indexOf(nameItemBuy) > -1) {
                            itemBuyByMeFindList.add(itemBuy);
                        } else continue;
                    }
                    if (itemBuyByMeFindList == null || itemBuyByMeFindList.isEmpty()) {
                        Toast.makeText(getActivity().getBaseContext(), getString(R.string.dont_find_itemBuy), Toast.LENGTH_SHORT).show();
                        managerItemYourSellBinding.etSearchItemYourSellName.setText("");
                    } else {
                        setShowListItemBuy(itemBuyByMeFindList);
                        totalPage = getTotalPage(showListItemBuy);
                        currentPage = 1;
                        setData(showListItemBuy, currentPage);
                        managerItemYourSellBinding.tvCurrentTotalPageManagerItemYourSell.setText(currentPage + "/" + totalPage);
                        managerItemYourSellBinding.etSearchItemYourSellName.setText("");
                    }
                }
            }
        });

        return managerItemYourSellBinding.getRoot();
    }

    private void setData(List<ItemBuy> itemBuyList, int currentPage) {
        adapterRcvItemYourSell.setDataItemBuyList(getListItemBuy(itemBuyList, currentPage));
        managerItemYourSellBinding.rcvManagerItemYourSell.setAdapter(adapterRcvItemYourSell);
    }

    private List<ItemBuy> getListItemBuy(List<ItemBuy> itemBuyList, int currentPage) {
        List<ItemBuy> itemBuyList1 = new ArrayList<>();
        if (itemBuyList.size() <= currentPage * 15) {
            for (int i = (currentPage - 1) * 15; i < itemBuyList.size(); i++) {
                itemBuyList1.add(itemBuyList.get(i));
            }
        } else if (itemBuyList.size() > currentPage * 15) {
            for (int i = (currentPage - 1) * 15; i < (currentPage - 1) * 15 + 15; i++) {
                itemBuyList1.add(itemBuyList.get(i));
            }
        }
        return itemBuyList1;
    }

    private int getTotalPage(List<ItemBuy> itemBuyList) {
        int total = 1;
        if (itemBuyList.size() <= 15) {
            total = 1;
        } else if (itemBuyList.size() > 15 && itemBuyList.size() % 15 == 0) {
            total = itemBuyList.size() / 15;
        } else if (itemBuyList.size() > 15 && itemBuyList.size() % 15 != 0) {
            total = (itemBuyList.size() / 15) + 1;
        }
        return total;
    }

    private void setShowListItemBuy(List<ItemBuy> showListItemSells) {
        List<ItemBuy> itemSellsByMe = new ArrayList<>();
        User userLoginNow = mainActivity.getUserLoginNow();
        for (int i = 0; i < showListItemSells.size(); i++) {
            ItemBuy itemBuy = showListItemSells.get(i);
            if (itemBuy.getNameAccountSell().equals(userLoginNow.getAccountName())) {
                itemSellsByMe.add(itemBuy);
            }
        }
        this.showListItemBuy = itemSellsByMe;
    }
}
