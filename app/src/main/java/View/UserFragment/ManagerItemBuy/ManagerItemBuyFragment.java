package View.UserFragment.ManagerItemBuy;

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
import com.example.myproject.databinding.ManagerItemBuyBinding;

import java.util.ArrayList;
import java.util.List;

import AllListForder.Object.ItemBuy;
import AllListForder.Object.User;
import View.UserFragment.Adapter.AdapterManagerItemBuy;
import View.UserFragment.UserFragment;
import support_functions.SqlLiteHelper;

public class ManagerItemBuyFragment extends Fragment {
    private MainActivity mainActivity;
    private ManagerItemBuyBinding managerItemBuyBinding;
    private SqlLiteHelper sqlLiteHelper;
    private List<ItemBuy> showListItemBuy;
    private int totalPage;
    private int currentPage = 1;
    private AdapterManagerItemBuy adapterManagerItemBuy;

    public static ManagerItemBuyFragment newInstance() {

        Bundle args = new Bundle();

        ManagerItemBuyFragment fragment = new ManagerItemBuyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        managerItemBuyBinding = DataBindingUtil.inflate(inflater, R.layout.manager_item_buy, container, false);
        mainActivity = (MainActivity) getActivity();
        sqlLiteHelper = new SqlLiteHelper(getContext());
        showListItemBuy = new ArrayList<>();
        setShowListItemBuy(sqlLiteHelper.getAllListItemBuy());
        totalPage = getTotalPage(showListItemBuy);
        managerItemBuyBinding.tvCurrentTotalPageManagerItemBuy.setText(currentPage + "/" + totalPage);
//        Rcv
        adapterManagerItemBuy = new AdapterManagerItemBuy(getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getBaseContext(), RecyclerView.VERTICAL, false);
        managerItemBuyBinding.rcvManagerItemBuy.setLayoutManager(layoutManager);
        setData(showListItemBuy, currentPage);

        managerItemBuyBinding.backPageInManagerItemBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage == 1) {
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.dont_loading), Toast.LENGTH_SHORT).show();
                } else if (currentPage > 1) {
                    currentPage -= 1;
                    setData(showListItemBuy, currentPage);
                    managerItemBuyBinding.tvCurrentTotalPageManagerItemBuy.setText(currentPage + "/" + totalPage);
                }
            }
        });
        managerItemBuyBinding.nextPageInManagerItemBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage == totalPage) {
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.dont_loading), Toast.LENGTH_SHORT).show();
                } else if (currentPage < totalPage) {
                    currentPage += 1;
                    setData(showListItemBuy, currentPage);
                    managerItemBuyBinding.tvCurrentTotalPageManagerItemBuy.setText(currentPage + "/" + totalPage);
                }
            }
        });
        managerItemBuyBinding.imgBackInManagerItemBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.getFragment(UserFragment.newInstance());
            }
        });

//        Search Item
        managerItemBuyBinding.refeshListItemBuyManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setShowListItemBuy(sqlLiteHelper.getAllListItemBuy());
                totalPage = getTotalPage(showListItemBuy);
                currentPage = 1;
                setData(showListItemBuy, currentPage);
                managerItemBuyBinding.tvCurrentTotalPageManagerItemBuy.setText(currentPage + "/" + totalPage);
            }
        });
        managerItemBuyBinding.iconSearchItemBuyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameItemBuy = managerItemBuyBinding.etSearchItemBuyName.getText().toString().trim().toLowerCase();
                if (nameItemBuy == null || nameItemBuy.isEmpty()) {
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.dont_find_itemBuy), Toast.LENGTH_SHORT).show();
                    setShowListItemBuy(sqlLiteHelper.getAllListItemBuy());
                    totalPage = getTotalPage(showListItemBuy);
                    currentPage = 1;
                    setData(showListItemBuy, currentPage);
                    managerItemBuyBinding.tvCurrentTotalPageManagerItemBuy.setText(currentPage + "/" + totalPage);
                    managerItemBuyBinding.etSearchItemBuyName.setText("");
                } else {
                    List<ItemBuy> itemBuyFindList = new ArrayList<>();
                    for (int i = 0; i < showListItemBuy.size(); i++) {
                        ItemBuy itemBuy = showListItemBuy.get(i);
                        if (itemBuy.getItemName().trim().toLowerCase().indexOf(nameItemBuy) > -1
                                || itemBuy.getTimeBuy().trim().toLowerCase().indexOf(nameItemBuy) > -1) {
                            itemBuyFindList.add(itemBuy);
                        } else continue;
                    }
                    if (itemBuyFindList == null || itemBuyFindList.isEmpty()) {
                        Toast.makeText(getActivity().getBaseContext(), getString(R.string.dont_find_itemBuy), Toast.LENGTH_SHORT).show();
                        managerItemBuyBinding.etSearchItemBuyName.setText("");
                    } else {
                        setShowListItemBuy(itemBuyFindList);
                        totalPage = getTotalPage(showListItemBuy);
                        currentPage = 1;
                        setData(showListItemBuy, currentPage);
                        managerItemBuyBinding.tvCurrentTotalPageManagerItemBuy.setText(currentPage + "/" + totalPage);
                        managerItemBuyBinding.etSearchItemBuyName.setText("");
                    }
                }
            }
        });
        return managerItemBuyBinding.getRoot();
    }

    private void setData(List<ItemBuy> itemBuyList, int currentPage) {
        adapterManagerItemBuy.setDataItemBuyList(getListItemBuy(itemBuyList, currentPage));
        managerItemBuyBinding.rcvManagerItemBuy.setAdapter(adapterManagerItemBuy);
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

    private void setShowListItemBuy(List<ItemBuy> showListItemBuy) {
        List<ItemBuy> itemBuysForMe = new ArrayList<>();
        User userLoginNow = mainActivity.getUserLoginNow();
        for (int i = 0; i < showListItemBuy.size(); i++) {
            ItemBuy itemBuy = showListItemBuy.get(i);
            if (itemBuy.getNameAccountBuy().equals(userLoginNow.getAccountName())) {
                itemBuysForMe.add(itemBuy);
            }
        }
        this.showListItemBuy = itemBuysForMe;
    }

}
