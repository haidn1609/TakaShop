package View.UserFragment.LoginRegisterFragment.LoginFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.myproject.LoginActivity;
import com.example.myproject.MainActivity;
import com.example.myproject.R;
import com.example.myproject.databinding.LoginFragmentBinding;

import java.util.List;

import AllListForder.AllKeyLocal;
import AllListForder.AllList;
import AllListForder.Object.InfoLogin;
import AllListForder.Object.User;
import View.UserFragment.LoginRegisterFragment.RegisterFragment.RegisterFragment;
import support_functions.SqlLiteHelper;


public class LoginFragment extends Fragment implements AllList, AllKeyLocal {
    LoginFragmentBinding loginFragmentBinding;
    LoginActivity loginActivity;
    SqlLiteHelper sqlLiteHelper;

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loginFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false);
        loginActivity = (LoginActivity) getActivity();
        sqlLiteHelper = new SqlLiteHelper(getContext());
        loginFragmentBinding.btnBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        loginFragmentBinding.tvLogReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginActivity.getFragment(RegisterFragment.newInstance());
            }
        });

        loginFragmentBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkLogin = false;
                String accountName = loginFragmentBinding.etLoginAccount.getText().toString();
                String accountPass = loginFragmentBinding.etLoginPassword.getText().toString();
                for (int i = 0; i < USER_LIST.size(); i++) {
                    User user = USER_LIST.get(i);
                    if (accountName.toLowerCase().equals(user.getAccountName().toLowerCase())) {
                        checkLogin = accountPass.equals(user.getAccountPass());
                        break;
                    }
                }
                if (checkLogin) {
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.LoginComplete), Toast.LENGTH_SHORT).show();
                    sqlLiteHelper.addCheckLoginTable(new InfoLogin(0, accountName, USER_LOGIN));
                    List<InfoLogin> infoLoginList = sqlLiteHelper.getAllListCheckLogin();
                    INFO_LOGIN_LIST.clear();
                    INFO_LOGIN_LIST.addAll(infoLoginList);
                    Intent intent = new Intent(getActivity().getBaseContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.LoginFalse), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return loginFragmentBinding.getRoot();
    }

}
