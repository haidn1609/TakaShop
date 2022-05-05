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

import AllListForder.Object.MainCategory;
import View.HomeFragment.Adapter.OnItemRCVClickListener;

public class MainCategoryRCVAdapter extends RecyclerView.Adapter<MainCategoryRCVAdapter.ViewHolder> {

    List<MainCategory> mainCategoryList;
    private OnMainCategoryClickListener onMainCategoryRCVClickListener;

    public void setDataAdapter(List<MainCategory> mainCategoryList) {
        this.mainCategoryList = mainCategoryList;
        notifyDataSetChanged();
    }

    public void setMainCategoryClick(OnMainCategoryClickListener onMainCategoryRCVClickListener) {
        this.onMainCategoryRCVClickListener = onMainCategoryRCVClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_main_category, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MainCategory mainCategory = mainCategoryList.get(position);

        holder.tvMainCategoryName.setText(mainCategory.getNameCategory());
        Picasso.get().load(mainCategory.getUrlImgMainCategory())
                .placeholder(R.drawable.dont_loading_img)
                .error(R.drawable.dont_loading_img)
                .into(holder.imgMainCategory);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMainCategoryRCVClickListener.onMainCategoryClick(mainCategoryList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mainCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMainCategory;
        TextView tvMainCategoryName;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMainCategory = itemView.findViewById(R.id.img_item_main_category);
            tvMainCategoryName = itemView.findViewById(R.id.tv_name_item_main_category);
            linearLayout = itemView.findViewById(R.id.layout_main_category);
        }
    }
}
