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
import com.example.myproject.databinding.ManagerAcceptItemBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import AllListForder.Object.ItemSell;
import AllListForder.Object.Notification;
import AllListForder.Object.User;
import View.UserFragment.Adapter.AdapterRcvAcceptItem;
import View.UserFragment.Adapter.RCVAcceptItemClickListener;
import support_functions.SqlLiteHelper;

public class AcceptItemSell extends Fragment {
    private ManagerAcceptItemBinding managerAcceptItemBinding;
    private MainActivity mainActivity;
    private SqlLiteHelper sqlLiteHelper;
    private int totalPage;
    private int currentPage = 1;
    private List<ItemSell> showListItemSell;
    private AdapterRcvAcceptItem adapterRcvAcceptItem;

    public static AcceptItemSell newInstance() {

        Bundle args = new Bundle();

        AcceptItemSell fragment = new AcceptItemSell();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        managerAcceptItemBinding = DataBindingUtil.inflate(inflater, R.layout.manager_accept_item, container, false);
        mainActivity = (MainActivity) getActivity();
        sqlLiteHelper = new SqlLiteHelper(getContext());
        showListItemSell = new ArrayList<>();
        setShowListItemBuy(sqlLiteHelper.getAllListItemSellPending());
        totalPage = getTotalPage(showListItemSell);
        managerAcceptItemBinding.tvCurrentTotalPageManagerAcceptItemSell.setText(currentPage + "/" + totalPage);
//RCV
        adapterRcvAcceptItem = new AdapterRcvAcceptItem(getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        managerAcceptItemBinding.rcvManagerAcceptItemSell.setLayoutManager(layoutManager);
        setData(showListItemSell, currentPage);
        adapterRcvAcceptItem.setRcvAcceptItemClickListener(new RCVAcceptItemClickListener() {
            @Override
            public void btnDeleteOnClick(ItemSell itemSell) {
                setShowListItemBuy(sqlLiteHelper.getAllListItemSellPending());
                totalPage = getTotalPage(showListItemSell);
                currentPage = 1;
                String notificationContent = getActivity().getString(R.string.item) + " " + itemSell.getNameItemSell() + " " + getActivity().getString(R.string.notificationDontAccepItem);
                String addressUserReceive = "";
                String nameUserReceive = "";
                String phoneUserReceive = "";
                int idUserReceive = itemSell.getIdUserSell();
                List<User> userList = sqlLiteHelper.getAllListUser();
                for (int i = 0; i < userList.size(); i++) {
                    User user = userList.get(i);
                    if (idUserReceive == user.getIdUser()) {
                        addressUserReceive = user.getAddress();
                        phoneUserReceive = user.getUserPhone();
                        return;
                    } else continue;
                }
                Calendar calendar = Calendar.getInstance();
                int dayAdd = calendar.get(Calendar.DAY_OF_MONTH);
                int monthAdd = calendar.get(Calendar.MONTH) + 1;
                int yearAdd = calendar.get(Calendar.YEAR);
                Notification notification = new Notification(0, itemSell.getIdUserSell() + "", getActivity().getString(R.string.typeAdmin),
                        addressUserReceive, phoneUserReceive, dayAdd + "/" + monthAdd + "/" + yearAdd, notificationContent);
                sqlLiteHelper.addNotification(notification);
                setData(showListItemSell, currentPage);
                managerAcceptItemBinding.tvCurrentTotalPageManagerAcceptItemSell.setText(currentPage + "/" + totalPage);
            }

            @Override
            public void btnAcceptOnClick(ItemSell itemSell) {
                setShowListItemBuy(sqlLiteHelper.getAllListItemSellPending());
                totalPage = getTotalPage(showListItemSell);
                currentPage = 1;
                String notificationContent = getActivity().getString(R.string.item) + " " + itemSell.getNameItemSell() + " " + getActivity().getString(R.string.notificationAccepItem);
                String addressUserReceive = "";
                String nameUserReceive = "";
                String phoneUserReceive = "";
                int idUserReceive = itemSell.getIdUserSell();
                List<User> userList = sqlLiteHelper.getAllListUser();
                for (int i = 0; i < userList.size(); i++) {
                    User user = userList.get(i);
                    if (idUserReceive == user.getIdUser()) {
                        addressUserReceive = user.getAddress();
                        phoneUserReceive = user.getUserPhone();
                        return;
                    } else continue;
                }
                Calendar calendar = Calendar.getInstance();
                int dayAdd = calendar.get(Calendar.DAY_OF_MONTH);
                int monthAdd = calendar.get(Calendar.MONTH) + 1;
                int yearAdd = calendar.get(Calendar.YEAR);
                Notification notification = new Notification(0, itemSell.getIdUserSell() + "", getActivity().getString(R.string.typeAdmin),
                        addressUserReceive, phoneUserReceive, dayAdd + "/" + monthAdd + "/" + yearAdd, notificationContent);
                sqlLiteHelper.addNotification(notification);
                setData(showListItemSell, currentPage);
                managerAcceptItemBinding.tvCurrentTotalPageManagerAcceptItemSell.setText(currentPage + "/" + totalPage);
            }
        });

        managerAcceptItemBinding.backPageInManagerAcceptItemSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage == 1) {
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.dont_loading), Toast.LENGTH_SHORT).show();
                } else if (currentPage > 1) {
                    currentPage -= 1;
                    setData(showListItemSell, currentPage);
                    managerAcceptItemBinding.tvCurrentTotalPageManagerAcceptItemSell.setText(currentPage + "/" + totalPage);
                }
            }
        });
        managerAcceptItemBinding.nextPageInManagerAcceptItemSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage == totalPage) {
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.dont_loading), Toast.LENGTH_SHORT).show();
                } else if (currentPage < totalPage) {
                    currentPage += 1;
                    setData(showListItemSell, currentPage);
                    managerAcceptItemBinding.tvCurrentTotalPageManagerAcceptItemSell.setText(currentPage + "/" + totalPage);
                }
            }
        });
        managerAcceptItemBinding.imgBackInManagerAcceptItemSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.getFragment(ManagerItemSellFragment.newInstance());
            }
        });
//        search
        managerAcceptItemBinding.refeshListAcceptItemSellManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setShowListItemBuy(sqlLiteHelper.getAllListItemSellPending());
                totalPage = getTotalPage(showListItemSell);
                currentPage = 1;
                setData(showListItemSell, currentPage);
                managerAcceptItemBinding.tvCurrentTotalPageManagerAcceptItemSell.setText(currentPage + "/" + totalPage);
            }
        });
        managerAcceptItemBinding.iconSearchAcceptItemSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameItemBuy = managerAcceptItemBinding.etSearchAcceptItemSell.getText().toString().trim().toLowerCase();
                if (nameItemBuy == null || nameItemBuy.isEmpty()) {
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.dont_find_itemBuy), Toast.LENGTH_SHORT).show();
                    setShowListItemBuy(sqlLiteHelper.getAllListItemSell());
                    totalPage = getTotalPage(showListItemSell);
                    currentPage = 1;
                    setData(showListItemSell, currentPage);
                    managerAcceptItemBinding.tvCurrentTotalPageManagerAcceptItemSell.setText(currentPage + "/" + totalPage);
                    managerAcceptItemBinding.etSearchAcceptItemSell.setText("");
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
                        managerAcceptItemBinding.etSearchAcceptItemSell.setText("");
                    } else {
                        setShowListItemBuy(itemSellsByMeFindList);
                        totalPage = getTotalPage(showListItemSell);
                        currentPage = 1;
                        setData(showListItemSell, currentPage);
                        managerAcceptItemBinding.tvCurrentTotalPageManagerAcceptItemSell.setText(currentPage + "/" + totalPage);
                        managerAcceptItemBinding.etSearchAcceptItemSell.setText("");
                    }
                }
            }
        });
        return managerAcceptItemBinding.getRoot();
    }

    private void setData(List<ItemSell> itemSellList, int currentPage) {
        adapterRcvAcceptItem.setDataAcceptItem(getListItemSell(itemSellList, currentPage));
        managerAcceptItemBinding.rcvManagerAcceptItemSell.setAdapter(adapterRcvAcceptItem);
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
        this.showListItemSell = showListItemSells;
    }
}
