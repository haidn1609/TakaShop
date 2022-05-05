package View.showItemFragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.example.myproject.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import AllListForder.Object.ItemBuy;

public class AdapterRCVOrderItemBuy extends RecyclerView.Adapter<AdapterRCVOrderItemBuy.ViewHolder> {
    private final Context mContext;
    private List<ItemBuy> itemBuyList;

    public AdapterRCVOrderItemBuy(Context mContext) {
        this.mContext = mContext;
    }

    public void setDataOrderList(List<ItemBuy> itemBuyList) {
        this.itemBuyList = itemBuyList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_order_list_buy, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemBuy itemBuy = itemBuyList.get(position);
        final DecimalFormatSymbols syms = new DecimalFormatSymbols();
        syms.setGroupingSeparator(',');
        DecimalFormat myFormatter = new DecimalFormat("###,###,###,###,###,###", syms);

        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.relativeLayout);
        holder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                super.onOpen(layout);
            }
        });
        holder.btnDelItemOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemBuyList.remove(position);
                Toast.makeText(mContext, "Delete", Toast.LENGTH_SHORT).show();
                setDataOrderList(itemBuyList);
            }
        });
        Picasso.get().load(itemBuy.getAvatarItem())
                .placeholder(R.drawable.dont_loading_img)
                .error(R.drawable.dont_loading_img)
                .into(holder.avatarItem);
        holder.itemName.setText(itemBuy.getItemName());
        holder.priceOnceItem.setText(myFormatter.format(itemBuy.getPriceOnceItem()) + " đ");
        int purchase = itemBuy.getPurchased();
        int totalPrice = itemBuy.getPriceOnceItem() * purchase;
        itemBuy.setTotalItemPrice(totalPrice);
        holder.totalPriceItem.setText(myFormatter.format(totalPrice) + " đ");
        holder.etPurchaseItem.setText(purchase + "");
        holder.increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int purchase = itemBuy.getPurchased();
                purchase += 1;
                itemBuy.setPurchased(purchase);
                int totalPrice = itemBuy.getPriceOnceItem() * purchase;
                holder.totalPriceItem.setText(myFormatter.format(totalPrice) + " đ");
                itemBuy.setTotalItemPrice(totalPrice);
                holder.etPurchaseItem.setText(purchase + "");
            }
        });
        holder.reductionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int purchase = itemBuy.getPurchased();
                if (purchase > 1) {
                    purchase -= 1;
                    itemBuy.setPurchased(purchase);
                    int totalPrice = itemBuy.getPriceOnceItem() * purchase;
                    holder.totalPriceItem.setText(myFormatter.format(totalPrice) + " đ");
                    itemBuy.setTotalItemPrice(totalPrice);
                    holder.etPurchaseItem.setText(purchase + "");
                } else {
                    itemBuyList.remove(position);
                    setDataOrderList(itemBuyList);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (itemBuyList.isEmpty() || itemBuyList == null) {
            return 0;
        }
        return itemBuyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView avatarItem;
        private final ImageView reductionBtn;
        private final ImageView increaseBtn;
        private final TextView itemName;
        private final TextView priceOnceItem;
        private final TextView totalPriceItem;
        private final EditText etPurchaseItem;
        private final Button btnDelItemOrder;
        SwipeLayout swipeLayout;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarItem = itemView.findViewById(R.id.img_avatar_item_buy_order);
            itemName = itemView.findViewById(R.id.tv_order_name_item_buy);
            priceOnceItem = itemView.findViewById(R.id.tv_order_priceOnce_item_buy);
            etPurchaseItem = itemView.findViewById(R.id.et_purchase_item_order);
            reductionBtn = itemView.findViewById(R.id.reduction_purchase_item_order);
            increaseBtn = itemView.findViewById(R.id.increase_purchase_item_order);
            swipeLayout = itemView.findViewById(R.id.swipeLayoutOrder);
            relativeLayout = itemView.findViewById(R.id.layoutDownOrderList);
            btnDelItemOrder = itemView.findViewById(R.id.btn_delete_item_buy_order);
            totalPriceItem = itemView.findViewById(R.id.tv_order_totalPrice_item_buy);
        }
    }
}
