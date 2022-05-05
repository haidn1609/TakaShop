package View.UserFragment.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.example.myproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import AllListForder.AllKeyLocal;
import AllListForder.Object.User;
import de.hdodenhof.circleimageview.CircleImageView;
import support_functions.SqlLiteHelper;

public class AdapterRCVManagerUser extends RecyclerView.Adapter<AdapterRCVManagerUser.ViewHolder> implements AllKeyLocal {
    List<User> userList;
    SqlLiteHelper sqlLiteHelper;
    Context mContext;
    RCVManagerUserSetClickListener rcvManagerUserSetClickListener;

    public AdapterRCVManagerUser(Context mContext) {
        this.mContext = mContext;
    }

    public void setRcvManagerUserSetClickListener(RCVManagerUserSetClickListener rcvManagerUserSetClickListener) {
        this.rcvManagerUserSetClickListener = rcvManagerUserSetClickListener;
    }

    public void setDataUserList(List<User> dataUserList) {
        this.userList = dataUserList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_manager_user, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userList.get(position);
        sqlLiteHelper = new SqlLiteHelper(mContext);
        if (user.getSourceAvatar().equals(SOURCE_AVATAR_FROM_URL)) {
            if (user.getAvatar().equals(NONE_AVATAR)) {
                Picasso.get().load("https://i.imgur.com/TJSfIkU.png")
                        .placeholder(R.drawable.dont_loading_img)
                        .error(R.drawable.dont_loading_img)
                        .into(holder.circleImageView);
            } else {
                Picasso.get().load(user.getAvatar())
                        .placeholder(R.drawable.dont_loading_img)
                        .error(R.drawable.dont_loading_img)
                        .into(holder.circleImageView);
            }
        } else if (user.getSourceAvatar().equals(SOURCE_AVATAR_FROM_STORAGE)) {
            if (user.getAvatar().equals(NONE_AVATAR)) {
                Picasso.get().load("https://i.imgur.com/TJSfIkU.png")
                        .placeholder(R.drawable.dont_loading_img)
                        .error(R.drawable.dont_loading_img)
                        .into(holder.circleImageView);
            } else {
                Uri uri = Uri.parse(user.getAvatar());
                Picasso.get().load(uri)
                        .placeholder(R.drawable.dont_loading_img)
                        .error(R.drawable.dont_loading_img)
                        .into(holder.circleImageView);
            }
        }
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.linearLayout);
        holder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                super.onOpen(layout);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userList.remove(position);
                int id = user.getIdUser();
                sqlLiteHelper.delUser(id);
                Toast.makeText(mContext, mContext.getString(R.string.delete_complete), Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
                rcvManagerUserSetClickListener.onDeleteUserClickListener();
            }
        });
        holder.btnSetType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, v);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.set_type_user, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.setAdmin: {
                                user.setAccountType(TYPE_ADMIN);
                                sqlLiteHelper.editUserTable(user);
                                Toast.makeText(mContext, mContext.getString(R.string.edit_type_complete), Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                                rcvManagerUserSetClickListener.onEditTypeClickListener();
                                break;
                            }
                            case R.id.setSeller: {
                                user.setAccountType(TYPE_SELLER);
                                sqlLiteHelper.editUserTable(user);
                                Toast.makeText(mContext, mContext.getString(R.string.edit_type_complete), Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                                rcvManagerUserSetClickListener.onEditTypeClickListener();
                                break;
                            }
                            case R.id.setNormal: {
                                user.setAccountType(TYPE_NORMAL);
                                sqlLiteHelper.editUserTable(user);
                                Toast.makeText(mContext, mContext.getString(R.string.edit_type_complete), Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                                rcvManagerUserSetClickListener.onEditTypeClickListener();
                                break;
                            }
                            case R.id.setTradeMark: {
                                user.setAccountType(TYPE_TRADEMARK);
                                sqlLiteHelper.editUserTable(user);
                                Toast.makeText(mContext, mContext.getString(R.string.edit_type_complete), Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                                rcvManagerUserSetClickListener.onEditTypeClickListener();
                                break;
                            }
                        }
                        return false;
                    }
                });
                popupMenu.show();

            }
        });
        holder.tvAccName.setText(": " + user.getAccountName());
        holder.tvAccPass.setText(": " + user.getAccountPass());
        holder.tvFistNameUser.setText(": " + user.getUserFistName());
        holder.tvLastNameUser.setText(": " + user.getUserLastName());
        holder.tvEmail.setText(": " + user.getUserEmail());
        holder.tvPhone.setText(": " + user.getUserPhone());
        holder.tvAddress.setText(": " + user.getAddress());
        holder.tvType.setText(": " + user.getAccountType());
    }

    @Override
    public int getItemCount() {
        if (userList == null || userList.isEmpty()) {
            return 0;
        }
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CircleImageView circleImageView;
        private final TextView tvAccName;
        private final TextView tvAccPass;
        private final TextView tvFistNameUser;
        private final TextView tvLastNameUser;
        private final TextView tvEmail;
        private final TextView tvPhone;
        private final TextView tvAddress;
        private final TextView tvType;
        private final SwipeLayout swipeLayout;
        private final Button btnDelete;
        private final LinearLayout linearLayout;
        Button btnSetType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.img_managerUser_avatar);
            tvAccName = itemView.findViewById(R.id.manager_user_tv_name_account);
            tvAccPass = itemView.findViewById(R.id.manager_user_tv_pass_account);
            tvFistNameUser = itemView.findViewById(R.id.manager_user_tv_FirstName_User);
            tvLastNameUser = itemView.findViewById(R.id.manager_user_tv_LastName_User);
            tvEmail = itemView.findViewById(R.id.manager_user_tv_Email_account);
            tvPhone = itemView.findViewById(R.id.manager_user_tv_phone_account);
            tvAddress = itemView.findViewById(R.id.manager_user_tv_address_account);
            tvType = itemView.findViewById(R.id.manager_user_tv_type_account);
            swipeLayout = itemView.findViewById(R.id.swipeLayout);
            btnDelete = itemView.findViewById(R.id.btn_delete_manager_user);
            btnSetType = itemView.findViewById(R.id.btn_edit_type_manager_user);
            linearLayout = itemView.findViewById(R.id.layoutDown);
        }
    }
}
