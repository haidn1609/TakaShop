package View.UserFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.myproject.LoginActivity;
import com.example.myproject.MainActivity;
import com.example.myproject.R;
import com.example.myproject.databinding.UserFragmentBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

import AllListForder.AllKeyLocal;
import AllListForder.AllList;
import AllListForder.Object.InfoLogin;
import AllListForder.Object.User;
import View.UserFragment.ManagerItemBuy.ManagerItemBuyFragment;
import View.UserFragment.ManagerItemSell.ManagerItemSellFragment;
import View.UserFragment.ManagerUser.ManagerUserFragment;
import View.UserFragment.Setting.SettingFragment;
import support_functions.SqlLiteHelper;

public class UserFragment extends Fragment implements AllList, AllKeyLocal {
    UserFragmentBinding userFragmentBinding;
    SqlLiteHelper sqlLiteHelper;
    MainActivity mainActivity;

    public static UserFragment newInstance() {

        Bundle args = new Bundle();

        UserFragment fragment = new UserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        userFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.user_fragment, container, false);
        sqlLiteHelper = new SqlLiteHelper(getContext());
        mainActivity = (MainActivity) getActivity();

        InfoLogin infoLogin = INFO_LOGIN_LIST.get(INFO_LOGIN_LIST.size() - 1);
        if (infoLogin.getInfoLogin().equals(USER_LOGOUT)) {
            userFragmentBinding.tvLogReg.setVisibility(View.VISIBLE);
            userFragmentBinding.userAllChoice.setVisibility(View.GONE);
            userFragmentBinding.tvNameUser.setVisibility(View.GONE);
        } else if (infoLogin.getInfoLogin().equals(USER_LOGIN)) {
            userFragmentBinding.tvLogReg.setVisibility(View.GONE);
            String accountLoginName = infoLogin.getNameUserLogin();
            User userLoginNow = getUserLogin(accountLoginName);
            mainActivity.setUserLoginNow(userLoginNow);
            userFragmentBinding.userAllChoice.setVisibility(View.VISIBLE);
            userFragmentBinding.tvNameUser.setVisibility(View.VISIBLE);
            userFragmentBinding.tvNameUser.setText(userLoginNow.getAccountName());
            if (userLoginNow.getSourceAvatar().equals(SOURCE_AVATAR_FROM_URL)) {
                if (userLoginNow.getAvatar().equals(NONE_AVATAR)) {
                    Picasso.get().load("https://i.imgur.com/TJSfIkU.png")
                            .placeholder(R.drawable.dont_loading_img)
                            .error(R.drawable.dont_loading_img)
                            .into(userFragmentBinding.imgAvatarUser);
                } else {
                    Picasso.get().load(userLoginNow.getAvatar())
                            .placeholder(R.drawable.dont_loading_img)
                            .error(R.drawable.dont_loading_img)
                            .into(userFragmentBinding.imgAvatarUser);
                }
            } else if (userLoginNow.getSourceAvatar().equals(SOURCE_AVATAR_FROM_STORAGE)) {
                if (userLoginNow.getAvatar().equals(NONE_AVATAR)) {
                    Picasso.get().load("https://i.imgur.com/TJSfIkU.png")
                            .placeholder(R.drawable.dont_loading_img)
                            .error(R.drawable.dont_loading_img)
                            .into(userFragmentBinding.imgAvatarUser);
                } else {
                    Uri uri = Uri.parse(userLoginNow.getAvatar());
                    Picasso.get().load(uri)
                            .error(R.drawable.dont_loading_img)
                            .into(userFragmentBinding.imgAvatarUser);
                }
            }
            String typeAccount = userLoginNow.getAccountType();
            if (typeAccount.equals(TYPE_ADMIN)) {
                userFragmentBinding.managerItemSell.setVisibility(View.VISIBLE);
                userFragmentBinding.managerUser.setVisibility(View.VISIBLE);
            } else if (typeAccount.equals(TYPE_SELLER)) {
                userFragmentBinding.managerItemSell.setVisibility(View.VISIBLE);
                userFragmentBinding.managerUser.setVisibility(View.GONE);
            } else if (typeAccount.equals(TYPE_NORMAL) || typeAccount.equals(TYPE_TRADEMARK)) {
                userFragmentBinding.managerItemSell.setVisibility(View.GONE);
                userFragmentBinding.managerUser.setVisibility(View.GONE);
            }
        }

        userFragmentBinding.tvLogReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        userFragmentBinding.userSeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.getFragment(SettingFragment.newInstance());
            }
        });
        userFragmentBinding.managerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.getFragment(ManagerUserFragment.newInstance());
            }
        });
        userFragmentBinding.managerItemBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.getFragment(ManagerItemBuyFragment.newInstance());
            }
        });
        userFragmentBinding.managerItemSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.getFragment(ManagerItemSellFragment.newInstance());
            }
        });
        return userFragmentBinding.getRoot();
    }

    private User getUserLogin(String accountName) {
        User user = null;
        List<User> userList = sqlLiteHelper.getAllListUser();
        for (int i = 0; i < userList.size(); i++) {
            user = userList.get(i);
            if (accountName.toLowerCase().equals(user.getAccountName().toLowerCase())) {
                return user;
            }
        }
        return user;
    }
}
