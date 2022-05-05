package View.showItemFragment.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import AllListForder.Object.AvatarURL;

public class AdapterRCVShowListImgDetailItem extends RecyclerView.Adapter<AdapterRCVShowListImgDetailItem.ViewHolder> {
    private List<AvatarURL> listUrlImg;
    private ShowImgItemDetailClick showImgItemDetailClick;

    public void setClick(ShowImgItemDetailClick showImgItemDetailClick){
        this.showImgItemDetailClick = showImgItemDetailClick;
    }
    public void SetDataAdapter(List<AvatarURL> listUrlImg) {
        this.listUrlImg = listUrlImg;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_show_list_img_detail_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String UrlImg = listUrlImg.get(position).getUrlImg();
        Picasso.get().load(UrlImg)
                .placeholder(R.drawable.dont_loading_img)
                .error(R.drawable.dont_loading_img)
                .into(holder.imgItemDetail);

        holder.imgItemDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImgItemDetailClick.onClick(UrlImg);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listUrlImg.isEmpty() || listUrlImg == null) {
            return 0;
        } else
            return listUrlImg.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItemDetail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItemDetail = itemView.findViewById(R.id.img_RCV_item_detail);
        }
    }
}
