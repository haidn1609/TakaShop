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

public class AdapterRcvAcceptItem extends RecyclerView.Adapter<AdapterRcvAcceptItem.ViewHolder> {
    private final Context mContex;
    private List<ItemSell> itemSellList;
    private SqlLiteHelper sqlLiteHelper;
    private RCVAcceptItemClickListener rcvAcceptItemClickListener;

    public AdapterRcvAcceptItem(Context mContex) {
        this.mContex = mContex;
    }

    public void setRcvAcceptItemClickListener(RCVAcceptItemClickListener rcvAcceptItemClickListener) {
        this.rcvAcceptItemClickListener = rcvAcceptItemClickListener;
    }

    public void setDataAcceptItem(List<ItemSell> itemSellList) {
        this.itemSellList = itemSellList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_rcv_manager_accept_item, parent, false);
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
                sqlLiteHelper.delItemSellPending(id);
                Toast.makeText(mContex, mContex.getString(R.string.delete_complete), Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
                rcvAcceptItemClickListener.btnDeleteOnClick(itemSell);
            }
        });
        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlLiteHelper.addItemSell(itemSell);
                itemSellList.remove(position);
                int id = itemSell.getIdItemSell();
                sqlLiteHelper.delItemSellPending(id);
                Toast.makeText(mContex, mContex.getString(R.string.addComplete), Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
                rcvAcceptItemClickListener.btnAcceptOnClick(itemSell);
            }
        });
        Picasso.get().load(itemSell.getAvatarItemSell().get(0).getUrlImg()).error(R.drawable.dont_loading_img).into(holder.avatarItemAccept);

        holder.tvNameItemAccept.setText(" " + itemSell.getNameItemSell());
        holder.tvPriceItemAccept.setText(" " + myFormatter.format(itemSell.getPriceDontSale()) + " đ");
        if (itemSell.getSale().equals("yes")) {
            holder.tvSaleItemAccept.setText(" " + mContex.getString(R.string.acceptDoneSale));
            holder.tvPriceSaleItemAccept.setVisibility(View.VISIBLE);
            holder.tvPriceSaleItemAccept.setText(" " + myFormatter.format(itemSell.getPriceSale()) + " đ");
        } else {
            holder.tvSaleItemAccept.setText(" " + mContex.getString(R.string.acceptDontSale));
            holder.tvPriceSaleItemAccept.setVisibility(View.GONE);
        }
        holder.tvCharacteristicsAccept.setText("\n" + itemSell.getCharacteristics());
    }

    @Override
    public int getItemCount() {
        if (itemSellList == null || itemSellList.isEmpty()) {
            return 0;
        }
        return itemSellList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarItemAccept;
        TextView tvNameItemAccept, tvPriceItemAccept, tvSaleItemAccept, tvPriceSaleItemAccept, tvCharacteristicsAccept, tvMainCate, tvSideCate;
        LinearLayout layoutChange;
        SwipeLayout swipeLayout;
        Button btnDelete, btnAccept;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            swipeLayout = itemView.findViewById(R.id.swipeLayoutAcceptItem);
            layoutChange = itemView.findViewById(R.id.layoutDownManagerAcceptItem);
            avatarItemAccept = itemView.findViewById(R.id.img_avatar_acceptItem);
            tvNameItemAccept = itemView.findViewById(R.id.tv_name_acceptItem);
            tvPriceItemAccept = itemView.findViewById(R.id.tv_priceOnce_acceptItem);
            tvSaleItemAccept = itemView.findViewById(R.id.tv_sale_acceptItem);
            tvPriceSaleItemAccept = itemView.findViewById(R.id.tv_sale_price_acceptItem);
            tvCharacteristicsAccept = itemView.findViewById(R.id.tv_characteristics_acceptItem);
//            tvMainCate = itemView.findViewById(R.id.tv_mainCategory_acceptItem);
//            tvSideCate = itemView.findViewById(R.id.tv_side_cate_acceptItem);
            btnDelete = itemView.findViewById(R.id.btn_delete_AcceptItem);
            btnAccept = itemView.findViewById(R.id.btn_AcceptItem);
        }
    }
}
