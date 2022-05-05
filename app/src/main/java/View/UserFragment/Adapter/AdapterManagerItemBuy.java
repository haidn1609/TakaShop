package View.UserFragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import AllListForder.Object.ItemBuy;
import support_functions.SqlLiteHelper;

public class AdapterManagerItemBuy extends RecyclerView.Adapter<AdapterManagerItemBuy.ViewHolder> {
    List<ItemBuy> itemBuyList;
    SqlLiteHelper sqlLiteHelper;
    Context mContext;

    public AdapterManagerItemBuy(Context mContext) {
        this.mContext = mContext;
    }

    public void setDataItemBuyList(List<ItemBuy> itemBuyList) {
        this.itemBuyList = itemBuyList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_manager_item_buy, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemBuy itemBuy = itemBuyList.get(position);
        sqlLiteHelper = new SqlLiteHelper(mContext);
        final DecimalFormatSymbols syms = new DecimalFormatSymbols();
        syms.setGroupingSeparator(',');
        DecimalFormat myFormatter = new DecimalFormat("###,###,###,###,###,###", syms);

        Picasso.get().load(itemBuy.getAvatarItem())
                .placeholder(R.drawable.dont_loading_img).error(R.drawable.dont_loading_img)
                .into(holder.itemBuyAvatar);
        holder.tvNameItemBuy.setText(" " + itemBuy.getItemName());
        holder.tvPriceOnceItem.setText(" " + myFormatter.format(itemBuy.getPriceOnceItem()) + " đ");
        holder.tvPurchaseItem.setText(" " + itemBuy.getPurchased());
        holder.tvTotalPriceItem.setText(" " + myFormatter.format(itemBuy.getTotalItemPrice()));
        holder.tvDayBuy.setText(" " + itemBuy.getTimeBuy());
    }

    @Override
    public int getItemCount() {
        if (itemBuyList == null || itemBuyList.isEmpty()) {
            return 0;
        }
        return itemBuyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView itemBuyAvatar;
        private final TextView tvNameItemBuy;
        private final TextView tvPriceOnceItem;
        private final TextView tvPurchaseItem;
        private final TextView tvTotalPriceItem;
        private final TextView tvDayBuy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemBuyAvatar = itemView.findViewById(R.id.img_avatar_item_buy_manager_item_buy);
            tvNameItemBuy = itemView.findViewById(R.id.tv_manager_item_buy_name_item_buy);
            tvPriceOnceItem = itemView.findViewById(R.id.tv_manager_item_buy_priceOnce_item_buy);
            tvPurchaseItem = itemView.findViewById(R.id.tv_manager_item_buy_purchase);
            tvTotalPriceItem = itemView.findViewById(R.id.tv_manager_item_buy_totalPrice_item_buy);
            tvDayBuy = itemView.findViewById(R.id.tv_manager_item_buy_day_buy);
        }
    }
}
