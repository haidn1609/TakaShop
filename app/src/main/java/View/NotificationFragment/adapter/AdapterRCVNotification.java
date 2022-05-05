package View.NotificationFragment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.R;

import java.util.List;

import AllListForder.Object.Notification;

public class AdapterRCVNotification extends RecyclerView.Adapter<AdapterRCVNotification.ViewHolder> {
    private List<Notification> notificationList;
    private ShowNotification showNotification;
    public void setDataNotification(List<Notification> notificationList) {
        this.notificationList = notificationList;
        notifyDataSetChanged();
    }
    public void setClickNotification(ShowNotification showNotification){
        this.showNotification = showNotification;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_rcv_tile_notification, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Notification notification = notificationList.get(position);

        holder.tvNameUserSend.setText(notification.getNameUserSend());
        holder.tvDaysend.setText(notification.getDaySendNotification());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification.onNotificationClick(notification.getContentMail());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (notificationList.isEmpty() || notificationList == null) {
            return 0;
        } else
            return notificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameUserSend, tvDaysend;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.layout_notification_tile);
            tvNameUserSend = itemView.findViewById(R.id.notification_user_send_name);
            tvDaysend = itemView.findViewById(R.id.notification_day_send);
        }
    }
}
