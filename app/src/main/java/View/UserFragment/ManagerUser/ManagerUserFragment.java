package View.UserFragment.ManagerUser;

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
import com.example.myproject.databinding.ManagerUserFragmentBinding;

import java.util.ArrayList;
import java.util.List;

import AllListForder.AllKeyLocal;
import AllListForder.AllList;
import AllListForder.Object.User;
import View.UserFragment.Adapter.AdapterRCVManagerUser;
import View.UserFragment.Adapter.RCVManagerUserSetClickListener;
import View.UserFragment.UserFragment;
import support_functions.SqlLiteHelper;

public class ManagerUserFragment extends Fragment implements AllKeyLocal, AllList {
    private ManagerUserFragmentBinding managerUserFragmentBinding;
    private SqlLiteHelper sqlLiteHelper;
    private AdapterRCVManagerUser adapterRCVManagerUser;
    private MainActivity mainActivity;
    private int currentPage = 1;
    private int totalPage;
    private List<User> showListNow;

    public static ManagerUserFragment newInstance() {

        Bundle args = new Bundle();

        ManagerUserFragment fragment = new ManagerUserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        managerUserFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.manager_user_fragment, container, false);
        sqlLiteHelper = new SqlLiteHelper(getContext());
        showListNow = new ArrayList<>();
        //List<User> userList = sqlLiteHelper.getAllListUser();

        setShowListNow(sqlLiteHelper.getAllListUser());
        totalPage = getTotalPage(showListNow);
        managerUserFragmentBinding.tvCurrentTotalPageManagerUser.setText(currentPage + "/" + totalPage);
        mainActivity = (MainActivity) getActivity();
//RCV
        adapterRCVManagerUser = new AdapterRCVManagerUser(getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getBaseContext(), RecyclerView.VERTICAL, false);
        managerUserFragmentBinding.rcvManagerUserSell.setLayoutManager(layoutManager);
        setData(showListNow, currentPage);
        adapterRCVManagerUser.setRcvManagerUserSetClickListener(new RCVManagerUserSetClickListener() {
            @Override
            public void onDeleteUserClickListener() {
                setShowListNow(sqlLiteHelper.getAllListUser());
                totalPage = getTotalPage(showListNow);
                currentPage = 1;
                setData(showListNow, currentPage);
                managerUserFragmentBinding.tvCurrentTotalPageManagerUser.setText(currentPage + "/" + totalPage);
            }

            @Override
            public void onEditTypeClickListener() {
                setShowListNow(sqlLiteHelper.getAllListUser());
                setData(showListNow, currentPage);
                managerUserFragmentBinding.tvCurrentTotalPageManagerUser.setText(currentPage + "/" + totalPage);
            }
        });
        managerUserFragmentBinding.backPageInManagerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage == 1) {
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.dont_loading), Toast.LENGTH_SHORT).show();
                } else if (currentPage > 1) {
                    currentPage -= 1;
                    setData(showListNow, currentPage);
                    managerUserFragmentBinding.tvCurrentTotalPageManagerUser.setText(currentPage + "/" + totalPage);
                }
            }
        });
        managerUserFragmentBinding.nextPageInManagerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage == totalPage) {
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.dont_loading), Toast.LENGTH_SHORT).show();
                } else if (currentPage < totalPage) {
                    currentPage += 1;
                    setData(showListNow, currentPage);
                    managerUserFragmentBinding.tvCurrentTotalPageManagerUser.setText(currentPage + "/" + totalPage);
                }
            }
        });

        managerUserFragmentBinding.imgBackInManagerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.getFragment(UserFragment.newInstance());
            }
        });

//        search user
        managerUserFragmentBinding.iconSearchAccountName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountNameSearch = managerUserFragmentBinding.etSearchAccountName.getText().toString().toLowerCase();
                if (accountNameSearch == null || accountNameSearch.trim().isEmpty()) {
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.dont_find_user), Toast.LENGTH_SHORT).show();
                } else {
                    List<User> allUser = sqlLiteHelper.getAllListUser();
                    List<User> userFind = new ArrayList<>();
                    for (int i = 0; i < allUser.size(); i++) {
                        User userCheck = allUser.get(i);
                        if (userCheck.getAccountName().toLowerCase().indexOf(accountNameSearch) > -1
                                || userCheck.getAccountType().toLowerCase().indexOf(accountNameSearch) > -1) {
                            userFind.add(userCheck);
                        } else continue;
                    }
                    if (userFind == null || userFind.isEmpty()) {
                        Toast.makeText(getActivity().getBaseContext(), getString(R.string.dont_find_user), Toast.LENGTH_SHORT).show();
                    } else {
                        setShowListNow(userFind);
                        totalPage = getTotalPage(showListNow);
                        currentPage = 1;
                        setData(showListNow, currentPage);
                        managerUserFragmentBinding.tvCurrentTotalPageManagerUser.setText(currentPage + "/" + totalPage);
                    }
                }
            }
        });
        managerUserFragmentBinding.refeshListUserManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setShowListNow(sqlLiteHelper.getAllListUser());
                totalPage = getTotalPage(showListNow);
                currentPage = 1;
                setData(showListNow, currentPage);
                managerUserFragmentBinding.tvCurrentTotalPageManagerUser.setText(currentPage + "/" + totalPage);
            }
        });
        return managerUserFragmentBinding.getRoot();
    }

    public void setShowListNow(List<User> showListNow) {
        this.showListNow = showListNow;
    }

    private int getTotalPage(List<User> userList) {
        int total = 1;
        if (userList.size() <= 15) {
            total = 1;
        } else if (userList.size() > 15 && userList.size() % 15 == 0) {
            total = userList.size() / 15;
        } else if (userList.size() > 15 && userList.size() % 15 != 0) {
            total = (userList.size() / 15) + 1;
        }
        return total;
    }

    private void setData(List<User> userList, int currentPage) {
        adapterRCVManagerUser.setDataUserList(getListUser(userList, currentPage));
        managerUserFragmentBinding.rcvManagerUserSell.setAdapter(adapterRCVManagerUser);
    }

    private List<User> getListUser(List<User> userList, int currentPage) {
        List<User> userList1 = new ArrayList<>();
        if (userList.size() <= currentPage * 15) {
            for (int i = (currentPage - 1) * 15; i < userList.size(); i++) {
                userList1.add(userList.get(i));
            }
        } else if (userList.size() > currentPage * 15) {
            for (int i = (currentPage - 1) * 15; i < (currentPage - 1) * 15 + 15; i++) {
                userList1.add(userList.get(i));
            }
        }
        return userList1;
    }
}
