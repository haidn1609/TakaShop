package View.UserFragment.Adapter;

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

public class AdapterRcvItemYourSell extends RecyclerView.Adapter<AdapterRcvItemYourSell.ViewHolder> {
    private List<ItemBuy> itemBuyList;

    public void setDataItemBuyList(List<ItemBuy> itemBuyList) {
        this.itemBuyList = itemBuyList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_rcv_manager_item_your_sell, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemBuy itemBuy = itemBuyList.get(position);

        final DecimalFormatSymbols syms = new DecimalFormatSymbols();
        syms.setGroupingSeparator(',');
        DecimalFormat myFormatter = new DecimalFormat("###,###,###,###,###,###", syms);

        Picasso.get().load(itemBuy.getAvatarItem())
                .placeholder(R.drawable.dont_loading_img).error(R.drawable.dont_loading_img)
                .into(holder.itemYourSellAvatar);
        holder.tvNameItemYourSell.setText(" " + itemBuy.getItemName());
        holder.tvPriceOnceItemYourSell.setText(" " + myFormatter.format(itemBuy.getPriceOnceItem()) + " đ");
        holder.tvPurchaseItemYourSell.setText(" " + itemBuy.getPurchased());
        holder.tvTotalPriceItemYourSell.setText(" " + myFormatter.format(itemBuy.getTotalItemPrice()));
        holder.tvDaySell.setText(" " + itemBuy.getTimeBuy());
        holder.tvNameUserBuy.setText(" " + itemBuy.getNameAccountBuy());
    }

    @Override
    public int getItemCount() {
        if (itemBuyList == null || itemBuyList.isEmpty()) {
            return 0;
        }
        return itemBuyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemYourSellAvatar;
        TextView tvNameItemYourSell;
        TextView tvPriceOnceItemYourSell;
        TextView tvPurchaseItemYourSell;
        TextView tvTotalPriceItemYourSell;
        TextView tvDaySell;
        TextView tvNameUserBuy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemYourSellAvatar = itemView.findViewById(R.id.img_avatar_item_your_sell_manager_item_sell);
            tvNameItemYourSell = itemView.findViewById(R.id.tv_manager_item_your_sell_name_item_sell);
            tvPriceOnceItemYourSell = itemView.findViewById(R.id.tv_manager_item_your_sell_priceOnce_item_sell);
            tvPurchaseItemYourSell = itemView.findViewById(R.id.tv_manager_item_your_sell_purchase);
            tvTotalPriceItemYourSell = itemView.findViewById(R.id.tv_manager_item_your_sell_totalPrice_item_sell);
            tvDaySell = itemView.findViewById(R.id.tv_manager_item_your_sell_day_sell);
            tvNameUserBuy = itemView.findViewById(R.id.tv_manager_item_your_sell_name_user_buy);
        }
    }
}
