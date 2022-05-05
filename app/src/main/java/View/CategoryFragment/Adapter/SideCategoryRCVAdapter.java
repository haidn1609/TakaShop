package View.CategoryFragment.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import AllListForder.Object.SideCategory;

public class SideCategoryRCVAdapter extends RecyclerView.Adapter<SideCategoryRCVAdapter.ViewHolder> {
    List<SideCategory> sideCategoryList;
    private OnSideCategoryClickListener onSideCategoryClickListener;

    public void setDataAdaper(List<SideCategory> sideCategoryList) {
        this.sideCategoryList = sideCategoryList;
        notifyDataSetChanged();
    }

    public void setOnSideCategoryClickListener(OnSideCategoryClickListener onSideCategoryClickListener) {
        this.onSideCategoryClickListener = onSideCategoryClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_side_category, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SideCategory sideCategory = sideCategoryList.get(position);
        Picasso.get().load(sideCategory.getSideCategoryAvatar())
                .placeholder(R.drawable.dont_loading_img)
                .error(R.drawable.dont_loading_img)
                .into(holder.imageView);

        holder.textView.setText(sideCategory.getSideCategoryName());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSideCategoryClickListener.onSideCategoryClick(sideCategoryList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (sideCategoryList == null || sideCategoryList.isEmpty()) {
            return 0;
        }
        return sideCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_item_side_category);
            textView = itemView.findViewById(R.id.tv_name_item_side_category);
            linearLayout = itemView.findViewById(R.id.layout_side_category);
        }
    }
}
