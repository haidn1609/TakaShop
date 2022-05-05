package View.UserFragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import AllListForder.Object.ItemSell;
import support_functions.SqlLiteHelper;

public class AdapterRCVManagerItemSellByMe extends RecyclerView.Adapter<AdapterRCVManagerItemSellByMe.ViewHolder> {
    private final Context mContex;
    private List<ItemSell> itemSellList;
    private SqlLiteHelper sqlLiteHelper;
    private RCVManagerItemSellsByMeClickListener rcvManagerItemSellsByMeClickListener;

    public AdapterRCVManagerItemSellByMe(Context mContex) {
        this.mContex = mContex;
    }

    public void setRcvManagerItemSellsByMeClickListener(RCVManagerItemSellsByMeClickListener rcvManagerItemSellsByMeClickListener) {
        this.rcvManagerItemSellsByMeClickListener = rcvManagerItemSellsByMeClickListener;
    }

    public void setDataItemSellList(List<ItemSell> itemSellList) {
        this.itemSellList = itemSellList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_rcv_manager_item_sell_by_me, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemSell itemSell = itemSellList.get(position);
        sqlLiteHelper = new SqlLiteHelper(mContex);
        final DecimalFormatSymbols syms = new DecimalFormatSymbols();
        syms.setGroupingSeparator(',');
        DecimalFormat myFormatter = new DecimalFormat("###,###,###,###,###,###", syms);

        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.layoutChange);
        holder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                super.onOpen(layout);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemSellList.remove(position);
                int id = itemSell.getIdItemSell();
                sqlLiteHelper.delItemSell(id);
                Toast.makeText(mContex, mContex.getString(R.string.delete_complete), Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
                rcvManagerItemSellsByMeClickListener.btnDeleteClickListener();
            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rcvManagerItemSellsByMeClickListener.btnEditClickListener(itemSell);
            }
        });
        Picasso.get().load(itemSell.getAvatarItemSell().get(0).getUrlImg()).error(R.drawable.dont_loading_img).into(holder.avatarItem);
        holder.tvNameItem.setText(" " + itemSell.getNameItemSell());
        holder.tvPriceItem.setText(" " + myFormatter.format(itemSell.getPriceSale()) + " đ");
        holder.tvInventoryItem.setText(" " + itemSell.getTotalItem() + " " + mContex.getString(R.string.item));
        holder.tvTotalItemSold.setText(" " + itemSell.getTotalItemSold() + " " + mContex.getString(R.string.item));
        holder.tvItemSoldInMonth.setText(" " + itemSell.getItemSoldInMonth() + " " + mContex.getString(R.string.item));
    }

    @Override
    public int getItemCount() {
        if (itemSellList == null || itemSellList.isEmpty()) {
            return 0;
        }
        return itemSellList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarItem;
        TextView tvNameItem, tvPriceItem, tvTotalItemSold, tvInventoryItem, tvItemSoldInMonth;
        LinearLayout layoutChange;
        SwipeLayout swipeLayout;
        Button btnDelete, btnEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            swipeLayout = itemView.findViewById(R.id.swipeLayoutItemSellByMe);
            layoutChange = itemView.findViewById(R.id.layoutDownManagerItemSellByMe);
            avatarItem = itemView.findViewById(R.id.img_avatar_item_sell_by_me);
            tvNameItem = itemView.findViewById(R.id.tv_name_item_sell_by_me);
            tvPriceItem = itemView.findViewById(R.id.tv_priceOnce_item_sell_by_me);
            tvTotalItemSold = itemView.findViewById(R.id.tv_total_sold_item_sell_by_me);
            tvInventoryItem = itemView.findViewById(R.id.tv_total_item_now_item_sell_by_me);
            tvItemSoldInMonth = itemView.findViewById(R.id.tv_total_sold_in_month_item_sell_by_me);
            btnDelete = itemView.findViewById(R.id.btn_delete_manager_item_sell_by_me);
            btnEdit = itemView.findViewById(R.id.btn_edit_item_manager_item_sell_by_me);
        }
    }
}
