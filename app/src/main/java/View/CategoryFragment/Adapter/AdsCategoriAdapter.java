package View.CategoryFragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.myproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdsCategoriAdapter extends PagerAdapter {

    List<Integer> listImgAds;
    Context context;

    public AdsCategoriAdapter(List<Integer> listImgAds, Context context) {
        this.listImgAds = listImgAds;
        this.context = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_ads_category, container, false);
        ImageView imageView;
        imageView = view.findViewById(R.id.img_ads_category);

        int id = listImgAds.get(position);
        Picasso.get().load(id)
                .placeholder(R.drawable.dont_loading_img)
                .error(R.drawable.dont_loading_img)
                .into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if (listImgAds != null) {
            return listImgAds.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
