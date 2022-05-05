package View.HomeFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.MainActivity;
import com.example.myproject.R;
import com.example.myproject.databinding.HomeFragmentBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import AllListForder.AllKeyLocal;
import AllListForder.AllList;
import AllListForder.Object.EventInHome;
import AllListForder.Object.ItemSell;
import AllListForder.Object.MainAdsImg;
import View.HomeFragment.Adapter.AdaperRCVItemShowInHome;
import View.HomeFragment.Adapter.AdapterEventHome;
import View.HomeFragment.Adapter.AdapterRCVItemSaleInDay;
import View.HomeFragment.Adapter.AdapterRCVItemYourMayLike;
import View.HomeFragment.Adapter.OnEventHomeRCVClickListener;
import View.HomeFragment.Adapter.OnItemRCVClickListener;
import View.showItemFragment.ShowItemDetailFragment;
import View.showItemFragment.Show_all_item_fragment;
import support_functions.Classify_item_list;


public class HomeFragment extends Fragment implements AllList, AllKeyLocal {

    HomeFragmentBinding homeFragmentBinding;
    private AdapterEventHome adapterEventHome;
    private AdapterRCVItemSaleInDay adapterRCVItemSaleInDay;
    private AdapterRCVItemYourMayLike adapterRCVItemYourMayLike;
    private AdaperRCVItemShowInHome adaperRCVItemShowInHome;
    private MainActivity mainActivity;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(args);
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
        mainActivity = (MainActivity) getActivity();
        mainActivity.setMainLocal(LOCAL_HOME);
        mainActivity.setLocal(HOT_DEAL_ITEM);
        homeFragmentBinding.showAllItemSaleInDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setMainLocal(LOCAL_HOME);
                mainActivity.setLocal(SALE_IN_HOME);
                mainActivity.getFragment(Show_all_item_fragment.newInstance());
            }
        });
        homeFragmentBinding.showAllItemYouMayLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setMainLocal(LOCAL_HOME);
                mainActivity.setLocal(YOU_MAY_LIKE);
                mainActivity.getFragment(Show_all_item_fragment.newInstance());
            }
        });

        for (int i = 0; i < MAIN_ADS_IMG_LIST.size(); i++) {
            MainAdsImg mainAdsImg = MAIN_ADS_IMG_LIST.get(i);
            flipPerImage(mainAdsImg.getUrlIMG());
        }
//event home
        adapterEventHome = new AdapterEventHome(EVENT_IN_HOME_LIST, getActivity().getBaseContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getBaseContext(), RecyclerView.VERTICAL, false);
        homeFragmentBinding.rcvHomeEventShow.setAdapter(adapterEventHome);
        homeFragmentBinding.rcvHomeEventShow.setLayoutManager(layoutManager);
        adapterEventHome.setOnEventHomeRCVClickListener(new OnEventHomeRCVClickListener() {
            @Override
            public void onEventClick(EventInHome eventInHome) {
                mainActivity.setMainLocal(LOCAL_HOME);
                mainActivity.setLocal(ITEM_FROM_EVENT_IN_HOME);
                Classify_item_list.getItemInEvent(eventInHome.getCodeEvent());
                mainActivity.getFragment(Show_all_item_fragment.newInstance());
            }
        });

        adapterRCVItemSaleInDay = new AdapterRCVItemSaleInDay();
        adapterRCVItemSaleInDay.setData(getList(ITEM_SALE_IN_DAY_LIST));
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity().getBaseContext(), RecyclerView.HORIZONTAL, false);
        homeFragmentBinding.rcvItemSaleInDay.setLayoutManager(layoutManager1);
        homeFragmentBinding.rcvItemSaleInDay.setAdapter(adapterRCVItemSaleInDay);
        adapterRCVItemSaleInDay.setItemClickListener(new OnItemRCVClickListener() {
            @Override
            public void onItemClick(ItemSell itemSell) {
                mainActivity.setMainLocal(LOCAL_HOME);
                mainActivity.setItemSell(itemSell);
                mainActivity.getFragment(ShowItemDetailFragment.newInstance());
            }
        });

        adapterRCVItemYourMayLike = new AdapterRCVItemYourMayLike();
        adapterRCVItemYourMayLike.setData(getList(ITEM_YOUR_MAY_LIKE_LIST));
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getActivity().getBaseContext(), RecyclerView.HORIZONTAL, false);
        homeFragmentBinding.rcvItemYouMayLike.setLayoutManager(layoutManager2);
        homeFragmentBinding.rcvItemYouMayLike.setAdapter(adapterRCVItemYourMayLike);
        adapterRCVItemYourMayLike.setItemClickListener(new OnItemRCVClickListener() {
            @Override
            public void onItemClick(ItemSell itemSell) {
                mainActivity.setMainLocal(LOCAL_HOME);
                mainActivity.setItemSell(itemSell);
                mainActivity.getFragment(ShowItemDetailFragment.newInstance());
            }
        });

        adaperRCVItemShowInHome = new AdaperRCVItemShowInHome();
        adaperRCVItemShowInHome.setData(getList(ITEM_HOT_DEAL));
        RecyclerView.LayoutManager layoutManager3 = new GridLayoutManager(getActivity().getBaseContext(), 3, RecyclerView.VERTICAL, false);
        homeFragmentBinding.rcvShowListSpItemInHome.setLayoutManager(layoutManager3);
        homeFragmentBinding.rcvShowListSpItemInHome.setAdapter(adaperRCVItemShowInHome);
        adaperRCVItemShowInHome.setItemClickListener(new OnItemRCVClickListener() {
            @Override
            public void onItemClick(ItemSell itemSell) {
                mainActivity.setMainLocal(LOCAL_HOME);
                mainActivity.setItemSell(itemSell);
                mainActivity.getFragment(ShowItemDetailFragment.newInstance());
            }
        });

        homeFragmentBinding.tvHotDealInHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setMainLocal(LOCAL_HOME);
                mainActivity.setLocal(HOT_DEAL_ITEM);
                adaperRCVItemShowInHome.setData(getList(ITEM_HOT_DEAL));
                homeFragmentBinding.rcvShowListSpItemInHome.setAdapter(adaperRCVItemShowInHome);
            }
        });
        homeFragmentBinding.tvBestPriceInHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setMainLocal(LOCAL_HOME);
                mainActivity.setLocal(BEST_PRICE_ITEM);
                adaperRCVItemShowInHome.setData(getList(ITEM_BEST_PRICE));
                homeFragmentBinding.rcvShowListSpItemInHome.setAdapter(adaperRCVItemShowInHome);
            }
        });
        homeFragmentBinding.tvNewItemInHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setMainLocal(LOCAL_HOME);
                mainActivity.setLocal(NEW_ITEM);
                adaperRCVItemShowInHome.setData(getList(ITEM_NEW));
                homeFragmentBinding.rcvShowListSpItemInHome.setAdapter(adaperRCVItemShowInHome);
            }
        });
        homeFragmentBinding.btnShowMoreInHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.getFragment(Show_all_item_fragment.newInstance());
            }
        });
        return homeFragmentBinding.getRoot();
    }

    public void flipPerImage(String urlImage) {
        ImageView imageView = new ImageView(getContext());
        Picasso.get().load(urlImage)
                .placeholder(R.drawable.dont_loading_img)
                .error(R.drawable.dont_loading_img)
                .into(imageView);
        homeFragmentBinding.VfMainAdsHomeFragment.addView(imageView);
        homeFragmentBinding.VfMainAdsHomeFragment.setFlipInterval(5000);
        homeFragmentBinding.VfMainAdsHomeFragment.setAutoStart(true);

        homeFragmentBinding.VfMainAdsHomeFragment.setInAnimation(getContext(), android.R.anim.slide_in_left);
        homeFragmentBinding.VfMainAdsHomeFragment.setOutAnimation(getContext(), android.R.anim.slide_out_right);
    }

    private List<ItemSell> getList(List<ItemSell> itemSells) {
        List<ItemSell> itemSellList = new ArrayList<>();
        if (itemSells.size() <= 15) {
            for (int i = 0; i < itemSells.size(); i++) {
                itemSellList.add(itemSells.get(i));
            }
        } else if (itemSells.size() > 15) {
            for (int i = 0; i < 15; i++) {
                itemSellList.add(itemSells.get(i));
            }
        }
        return itemSellList;
    }

}
