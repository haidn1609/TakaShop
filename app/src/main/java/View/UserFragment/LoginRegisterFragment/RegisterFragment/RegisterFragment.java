package View.UserFragment.LoginRegisterFragment.RegisterFragment;

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
import com.example.myproject.R;
import com.example.myproject.databinding.RegisterFragmentBinding;

import java.util.List;
import java.util.regex.Pattern;

import AllListForder.AllKeyLocal;
import AllListForder.AllList;
import AllListForder.Object.User;
import View.UserFragment.LoginRegisterFragment.LoginFragment.LoginFragment;
import support_functions.SqlLiteHelper;


public class RegisterFragment extends Fragment implements AllKeyLocal, AllList {
    RegisterFragmentBinding registerFragmentBinding;
    LoginActivity loginActivity;
    SqlLiteHelper sqlLiteHelper;

    public static RegisterFragment newInstance() {

        Bundle args = new Bundle();

        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        registerFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.register_fragment, container, false);
        loginActivity = (LoginActivity) getActivity();
        sqlLiteHelper = new SqlLiteHelper(getContext());

        registerFragmentBinding.btnBackRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginActivity.getFragment(LoginFragment.newInstance());
            }
        });

        registerFragmentBinding.getLocationUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vitri = loginActivity.getVitri();
                registerFragmentBinding.etAddressUser.setText(vitri);
            }
        });
        registerFragmentBinding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkNameUser = false;
                boolean checkAccountName = false;
                boolean checkAccountPass = false;
                boolean checkAddress = false;
                boolean checkPhone = false;
                String firstName = registerFragmentBinding.etFirstNameRegister.getText().toString();
                String lastName = registerFragmentBinding.etLastNameRegister.getText().toString();
                String accountName = registerFragmentBinding.etAccountRegister.getText().toString();
                String accountPass = registerFragmentBinding.etPassRegister.getText().toString();
                String userPhone = registerFragmentBinding.etPhoneRegister.getText().toString();
                String addressUser = registerFragmentBinding.etAddressUser.getText().toString();
                String sex = null;
                if (registerFragmentBinding.checkMale.isChecked()) {
                    sex = SEX_MALE;
                } else if (registerFragmentBinding.checkFemale.isChecked()) {
                    sex = SEX_FEMALE;
                }
                if (firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty()) {
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.notification_dont_name_user), Toast.LENGTH_SHORT).show();
                    return;
                } else checkNameUser = true;
                if (accountName.isEmpty() || accountName == null) {
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.notification_dont_name_account), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    List<User> userList = sqlLiteHelper.getAllListUser();
                    for (int i = 0; i < userList.size(); i++) {
                        User checkUser = userList.get(i);
                        if (accountName.toLowerCase().equals(checkUser.getAccountName().toLowerCase())) {
                            Toast.makeText(getActivity().getBaseContext(), getString(R.string.notification_have_name_account), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        checkAccountName = true;
                    }
                }
                if (userPhone.isEmpty() || userPhone == null) {
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.notification_dont_phone_account), Toast.LENGTH_SHORT).show();
                    return;
                } else checkPhone = true;
                checkAccountPass = Pattern.matches(regexPass, accountPass);
                if (!checkAccountPass) {
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.notification_dont_pass_account), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (addressUser.isEmpty() || addressUser == null) {
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.notification_dont_address_account), Toast.LENGTH_SHORT).show();
                    return;
                } else checkAddress = true;
                if (checkNameUser && checkAccountName && checkAccountPass && checkAddress && checkPhone) {
                    User user = new User(0, TYPE_NORMAL, accountName, accountPass, firstName, lastName,
                            "", userPhone, addressUser, sex, SOURCE_AVATAR_FROM_URL, NONE_AVATAR);
                    USER_LIST.add(user);
                    sqlLiteHelper.addUserTable(user);
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.register_complete), Toast.LENGTH_SHORT).show();
                    loginActivity.getFragment(LoginFragment.newInstance());
                }
            }
        });
        return registerFragmentBinding.getRoot();
    }


}
