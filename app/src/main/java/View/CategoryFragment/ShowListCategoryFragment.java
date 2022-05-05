package View.CategoryFragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.MainActivity;
import com.example.myproject.R;
import com.example.myproject.databinding.ListCategoryFragmentBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import AllListForder.AllKeyLocal;
import AllListForder.AllList;
import AllListForder.Object.MainCategory;
import AllListForder.Object.SideCategory;
import View.CategoryFragment.Adapter.AdsCategoriAdapter;
import View.CategoryFragment.Adapter.MainCategoryRCVAdapter;
import View.CategoryFragment.Adapter.OnMainCategoryClickListener;
import View.CategoryFragment.Adapter.OnSideCategoryClickListener;
import View.CategoryFragment.Adapter.SideCategoryRCVAdapter;
import View.showItemFragment.Show_all_item_fragment;
import support_functions.Classify_item_list;


public class ShowListCategoryFragment extends Fragment implements AllList, AllKeyLocal {

    ListCategoryFragmentBinding listCategoryFragmentBinding;
    private List<Integer> listItemAdsCategory;
    private AdsCategoriAdapter adsCategoriAdapter;
    private MainCategoryRCVAdapter mainCategoryRCVAdapter;
    private SideCategoryRCVAdapter sideCategoryRCVAdapter;
    private Timer timer;
    private MainActivity mainActivity;

    public static ShowListCategoryFragment newInstance() {

        Bundle args = new Bundle();

        ShowListCategoryFragment fragment = new ShowListCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listCategoryFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.list_category_fragment, container, false);
        mainActivity = (MainActivity) getActivity();

        mainActivity.setLocal(ITEM_FROM_CATEGORY);
        Classify_item_list.getItemInCategory(mainActivity.getTypeCategory(), TOY_AND_MOM);

        listItemAdsCategory = new ArrayList<>();
        listItemAdsCategory.add(R.drawable.ads_in_category_1);
        listItemAdsCategory.add(R.drawable.ads_in_category_2);
        listItemAdsCategory.add(R.drawable.ads_in_category_3);

//set Main category
        mainCategoryRCVAdapter = new MainCategoryRCVAdapter();
        mainCategoryRCVAdapter.setDataAdapter(MAIN_CATEGORY_LIST);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        listCategoryFragmentBinding.rcvMainCategory.setLayoutManager(layoutManager);
        listCategoryFragmentBinding.rcvMainCategory.setAdapter(mainCategoryRCVAdapter);
        mainCategoryRCVAdapter.setMainCategoryClick(new OnMainCategoryClickListener() {
            @Override
            public void onMainCategoryClick(MainCategory mainCategory) {
                mainActivity.setTypeCategory(TYPE_MAIN);
                mainActivity.setLocal(ITEM_FROM_CATEGORY);
                Classify_item_list.getItemInCategory(mainActivity.getTypeCategory(), mainCategory.getCodeIdCategory());
                sideCategoryRCVAdapter.setDataAdaper(mainCategory.getSideCategoryList());
            }
        });
//set Side category
        sideCategoryRCVAdapter = new SideCategoryRCVAdapter();
        List<SideCategory> sideCategoryList = MAIN_CATEGORY_LIST.get(0).getSideCategoryList();
        sideCategoryRCVAdapter.setDataAdaper(sideCategoryList);
        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getActivity(), 3, RecyclerView.VERTICAL, false);
        listCategoryFragmentBinding.rcvSideCategory.setLayoutManager(layoutManager1);
        listCategoryFragmentBinding.rcvSideCategory.setAdapter(sideCategoryRCVAdapter);
        sideCategoryRCVAdapter.setOnSideCategoryClickListener(new OnSideCategoryClickListener() {
            @Override
            public void onSideCategoryClick(SideCategory sideCategory) {
                mainActivity.setMainLocal(LOCAL_CATEGORY);
                mainActivity.setTypeCategory(TYPE_SIDE);
                mainActivity.setLocal(ITEM_FROM_CATEGORY);
                Classify_item_list.getItemInCategory(mainActivity.getTypeCategory(), sideCategory.getCodeId());
                mainActivity.getFragment(Show_all_item_fragment.newInstance());
            }
        });
//set Ads
        adsCategoriAdapter = new AdsCategoriAdapter(listItemAdsCategory, getActivity().getBaseContext());
        listCategoryFragmentBinding.pagerAdsSideCategory.setAdapter(adsCategoriAdapter);
        listCategoryFragmentBinding.CIAdsSideCategory.setViewPager(listCategoryFragmentBinding.pagerAdsSideCategory);
        adsCategoriAdapter.registerDataSetObserver(listCategoryFragmentBinding.CIAdsSideCategory.getDataSetObserver());
        autoSlide();
//set Click button
        listCategoryFragmentBinding.showAllItemInCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setMainLocal(LOCAL_CATEGORY);
                mainActivity.setLocal(ALL_ITEM);
                mainActivity.getFragment(Show_all_item_fragment.newInstance());
            }
        });
        listCategoryFragmentBinding.showAllItemMainCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setMainLocal(LOCAL_CATEGORY);
                mainActivity.setLocal(ITEM_FROM_CATEGORY);
                mainActivity.getFragment(Show_all_item_fragment.newInstance());
            }
        });

        return listCategoryFragmentBinding.getRoot();
    }

    private void autoSlide() {
        if (listItemAdsCategory == null || listItemAdsCategory.isEmpty() || listCategoryFragmentBinding.pagerAdsSideCategory == null) {
            return;
        }
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = listCategoryFragmentBinding.pagerAdsSideCategory.getCurrentItem();
                        int totalItem = listItemAdsCategory.size() - 1;
                        if (currentItem < totalItem) {
                            currentItem++;
                            listCategoryFragmentBinding.pagerAdsSideCategory.setCurrentItem(currentItem);
                        } else {
                            listCategoryFragmentBinding.pagerAdsSideCategory.setCurrentItem(0);
                        }
                    }
                });
            }
        }, 500, 3000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
