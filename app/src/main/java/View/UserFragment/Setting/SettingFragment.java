package View.UserFragment.Setting;

import android.content.Intent;
import android.net.Uri;
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

import com.example.myproject.MainActivity;
import com.example.myproject.R;
import com.example.myproject.databinding.SettingUserFragmentBinding;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.regex.Pattern;

import AllListForder.AllKeyLocal;
import AllListForder.AllList;
import AllListForder.Object.InfoLogin;
import AllListForder.Object.User;
import View.HomeFragment.HomeFragment;
import View.UserFragment.UserFragment;
import support_functions.SqlLiteHelper;

import static android.app.Activity.RESULT_OK;

public class SettingFragment extends Fragment implements AllList, AllKeyLocal {
    SettingUserFragmentBinding settingUserFragmentBinding;
    SqlLiteHelper sqlLiteHelper;
    MainActivity mainActivity;
    private boolean checkAvatar = false;
    private boolean checkNameUser = false;
    private boolean checkAddress = false;
    private boolean checkPhone = false;
    private String pathImg;
    private String sourceImg;

    public static SettingFragment newInstance() {

        Bundle args = new Bundle();

        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        settingUserFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.setting_user_fragment, container, false);
        sqlLiteHelper = new SqlLiteHelper(getContext());
        mainActivity = (MainActivity) getActivity();


        InfoLogin infoLogin = INFO_LOGIN_LIST.get(INFO_LOGIN_LIST.size() - 1);
        String accountLoginName = infoLogin.getNameUserLogin();
        User userLoginNow = getUserLogin(accountLoginName);

        settingUserFragmentBinding.layoutEditProfile.setVisibility(View.GONE);
        settingUserFragmentBinding.layoutEditPass.setVisibility(View.GONE);

        settingUserFragmentBinding.logoutAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlLiteHelper.addCheckLoginTable(new InfoLogin(0, NONE_USER, USER_LOGOUT));
                List<InfoLogin> infoLoginList = sqlLiteHelper.getAllListCheckLogin();
                INFO_LOGIN_LIST.clear();
                INFO_LOGIN_LIST.addAll(infoLoginList);
                mainActivity.getFragment(HomeFragment.newInstance());
            }
        });
        settingUserFragmentBinding.backSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.getFragment(UserFragment.newInstance());
            }
        });

        settingUserFragmentBinding.editAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingUserFragmentBinding.layoutEditProfile.setVisibility(View.VISIBLE);
                settingUserFragmentBinding.layoutEditPass.setVisibility(View.GONE);
                editProfile(userLoginNow);
            }
        });

        settingUserFragmentBinding.editPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingUserFragmentBinding.layoutEditProfile.setVisibility(View.GONE);
                settingUserFragmentBinding.layoutEditPass.setVisibility(View.VISIBLE);
                editPass(userLoginNow);
            }
        });
        return settingUserFragmentBinding.getRoot();
    }

    private void editPass(User user) {
        settingUserFragmentBinding.btnConfirmEditPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkOldPass;
                boolean checkNewPass;
                boolean checkReNewPass;
                String oldPass = settingUserFragmentBinding.etInputOldPass.getText().toString();
                String newPass = settingUserFragmentBinding.etInputNewPass.getText().toString();
                String reNewPass = settingUserFragmentBinding.etReInputNewPass.getText().toString();
                checkOldPass = user.getAccountPass().equals(oldPass);
                if (!checkOldPass) {
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.old_pass_wrong), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    checkNewPass = Pattern.matches(regexPass, newPass);
                    if (!checkNewPass) {
                        Toast.makeText(getActivity().getBaseContext(), getString(R.string.notification_dont_pass_account), Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        checkReNewPass = newPass.equals(reNewPass);
                        if (!checkReNewPass) {
                            Toast.makeText(getActivity().getBaseContext(), getString(R.string.dont_mismatched), Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            user.setAccountPass(newPass);
                            sqlLiteHelper.editUserTable(user);
                            Toast.makeText(getActivity().getBaseContext(), getString(R.string.change_complete), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    private void editProfile(User user) {
        settingUserFragmentBinding.getURLIMG.setVisibility(View.GONE);
        settingUserFragmentBinding.etEditFirstName.setText(user.getUserFistName());
        settingUserFragmentBinding.etEditLastName.setText(user.getUserLastName());
        settingUserFragmentBinding.editPhoneUser.setText(user.getUserPhone());
        settingUserFragmentBinding.editEmailUser.setText(user.getUserEmail());
        settingUserFragmentBinding.etEditAddressUser.setText(user.getAddress());
        if (user.getSex().equals(SEX_MALE)) {
            settingUserFragmentBinding.editCheckMale.setChecked(true);
        } else if (user.getSex().equals(SEX_FEMALE)) {
            settingUserFragmentBinding.editCheckFemale.setChecked(true);
        }
        settingUserFragmentBinding.editGetLocationUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String viTri = mainActivity.getVitri();
                settingUserFragmentBinding.etEditAddressUser.setText(viTri);
            }
        });
        settingUserFragmentBinding.btnSelectEditAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getActivity().getBaseContext(), v);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.select_img_avatar, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.fromURL: {
                                checkAvatar = true;
                                sourceImg = SOURCE_AVATAR_FROM_URL;
                                settingUserFragmentBinding.getURLIMG.setVisibility(View.VISIBLE);
                                break;
                            }
                            case R.id.fromStorage: {
                                checkAvatar = true;
                                settingUserFragmentBinding.etInputUrlImage.setVisibility(View.GONE);
                                sourceImg = SOURCE_AVATAR_FROM_STORAGE;
                                Intent pickImg = new Intent(Intent.ACTION_GET_CONTENT);
                                pickImg.setType("*/*");
                                startActivityForResult(pickImg, 169);
                                break;
                            }
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        settingUserFragmentBinding.confirmUrlImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String linkUrl = settingUserFragmentBinding.etInputUrlImage.getText().toString();
                if (linkUrl.trim().isEmpty() || linkUrl == null) {
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.noneUrl), Toast.LENGTH_SHORT).show();
                } else {
                    pathImg = linkUrl;
                    Picasso.get().load(pathImg).error(R.drawable.dont_loading_img).into(settingUserFragmentBinding.imgEditAvatar);
                }
            }
        });
        settingUserFragmentBinding.btnConfirmEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = settingUserFragmentBinding.etEditFirstName.getText().toString();
                String lastName = settingUserFragmentBinding.etEditLastName.getText().toString();
                String userPhone = settingUserFragmentBinding.editPhoneUser.getText().toString();
                String userEmail = settingUserFragmentBinding.editEmailUser.getText().toString();
                String addressUser = settingUserFragmentBinding.etEditAddressUser.getText().toString();
                String avatar = settingUserFragmentBinding.etInputUrlImage.getText().toString();
                String sex = null;
                if (settingUserFragmentBinding.editCheckMale.isChecked()) {
                    sex = SEX_MALE;
                } else if (settingUserFragmentBinding.editCheckFemale.isChecked()) {
                    sex = SEX_FEMALE;
                }
                if (firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty()) {
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.notification_dont_name_user), Toast.LENGTH_SHORT).show();
                    return;
                } else checkNameUser = true;
                if (userPhone.isEmpty() || userPhone == null) {
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.notification_dont_phone_account), Toast.LENGTH_SHORT).show();
                    return;
                } else checkPhone = true;

                if (addressUser.isEmpty() || addressUser == null) {
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.notification_dont_address_account), Toast.LENGTH_SHORT).show();
                    return;
                } else checkAddress = true;
                if (sourceImg.equals(SOURCE_AVATAR_FROM_URL)) {
                    checkAvatar = !avatar.isEmpty() && !avatar.trim().isEmpty();
                }
                if (checkNameUser && checkPhone && checkAddress) {
                    if (checkAvatar == false) {
                        user.setUserFistName(firstName);
                        user.setUserLastName(lastName);
                        user.setUserEmail(userEmail);
                        user.setUserPhone(userPhone);
                        user.setAddress(addressUser);
                        sqlLiteHelper.editUserTable(user);
                        Toast.makeText(getActivity().getBaseContext(), getString(R.string.change_complete), Toast.LENGTH_SHORT).show();
                    } else if ((checkAvatar)) {
                        user.setUserFistName(firstName);
                        user.setUserLastName(lastName);
                        user.setUserEmail(userEmail);
                        user.setUserPhone(userPhone);
                        user.setAddress(addressUser);
                        user.setSourceAvatar(sourceImg);
                        user.setAvatar(pathImg);
                        sqlLiteHelper.editUserTable(user);
                        Toast.makeText(getActivity().getBaseContext(), getString(R.string.change_complete), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 169) {
            if (resultCode == RESULT_OK) {
                sourceImg = SOURCE_AVATAR_FROM_STORAGE;
                Uri uriSelectImg = data.getData();
                pathImg = uriSelectImg.toString();
                Picasso.get().load(Uri.parse(pathImg)).error(R.drawable.dont_loading_img).into(settingUserFragmentBinding.imgEditAvatar);
            }
        }
    }
}
