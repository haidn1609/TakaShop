package View.showItemFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.MainActivity;
import com.example.myproject.R;
import com.example.myproject.databinding.OrderItemFragmentBinding;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import AllListForder.AllKeyLocal;
import AllListForder.AllList;
import AllListForder.Object.ItemBuy;
import View.HomeFragment.HomeFragment;
import View.showItemFragment.Adapter.AdapterRCVOrderItemBuy;
import support_functions.SqlLiteHelper;

public class OrderItemBuyFragment extends Fragment implements AllList, AllKeyLocal {
    private OrderItemFragmentBinding orderItemFragmentBinding;
    private MainActivity mainActivity;
    private AdapterRCVOrderItemBuy adapterRCVOrderItemBuy;
    private SqlLiteHelper sqlLiteHelper;

    public static OrderItemBuyFragment newInstance() {

        Bundle args = new Bundle();

        OrderItemBuyFragment fragment = new OrderItemBuyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        orderItemFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.order_item_fragment, container, false);
        mainActivity = (MainActivity) getActivity();
        sqlLiteHelper = new SqlLiteHelper(getContext());
        final DecimalFormatSymbols syms = new DecimalFormatSymbols();
        syms.setGroupingSeparator(',');
        DecimalFormat myFormatter = new DecimalFormat("###,###,###,###,###,###", syms);

        orderItemFragmentBinding.imgBackInOrderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.getFragment(HomeFragment.newInstance());
                mainActivity.setVisibleSearchBar(true);
            }
        });
//        RCV
        adapterRCVOrderItemBuy = new AdapterRCVOrderItemBuy(getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getBaseContext(), RecyclerView.VERTICAL, false);
        orderItemFragmentBinding.rcvOrderListItemBuy.setLayoutManager(layoutManager);
        setData(ITEM_ORDER_LIST);

//     Buy
        orderItemFragmentBinding.btnBuyAllOrderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalItem = 0;
                int totalPrice = 0;
                for (int i = 0; i < ITEM_ORDER_LIST.size(); i++) {
                    ItemBuy itemBuy = ITEM_ORDER_LIST.get(i);
                    totalPrice += itemBuy.getTotalItemPrice();
                    totalItem += itemBuy.getPurchased();
                }
                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setTitle(getString(R.string.dialogTile))
                        .setMessage(getString(R.string.notification_1) + " " + totalItem + " " + getString(R.string.notification_2) + " " + myFormatter.format(totalPrice) + " đ")
                        .setPositiveButton(getString(R.string.btnBuy), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (int i = 0; i < ITEM_ORDER_LIST.size(); i++) {
                                    ItemBuy itemBuy = ITEM_ORDER_LIST.get(i);
                                    sqlLiteHelper.addItemBuy(itemBuy);
                                }
                                Toast.makeText(getActivity().getBaseContext(), getString(R.string.buyComplete), Toast.LENGTH_SHORT).show();
                                ITEM_ORDER_LIST.clear();
                                mainActivity.getFragment(HomeFragment.newInstance());
                                mainActivity.setVisibleSearchBar(true);
                            }
                        }).setNegativeButton(getString(R.string.cancelNotify), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create();
                alertDialog.show();
            }
        });
        return orderItemFragmentBinding.getRoot();
    }

    private void setData(List<ItemBuy> itemBuyList) {
        adapterRCVOrderItemBuy.setDataOrderList(itemBuyList);
        orderItemFragmentBinding.rcvOrderListItemBuy.setAdapter(adapterRCVOrderItemBuy);
    }
}
