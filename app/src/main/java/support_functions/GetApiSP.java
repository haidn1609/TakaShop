package support_functions;

import java.util.List;

import AllListForder.Object.EventInHome;
import AllListForder.Object.ItemSell;
import AllListForder.Object.MainAdsImg;
import AllListForder.Object.MainCategory;
import AllListForder.Object.User;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetApiSP {

    @GET("DemoSanPham")
    Call<List<ItemSell>> ITEM_SELLS_CALL();

    @GET("demoHomeEvent")
    Call<List<EventInHome>> EVENT_IN_HOME_CALL();

    @GET("DemoHomeAds")
    Call<List<MainAdsImg>> MAIN_ADS_IN_HOME_CALL();

    @GET("DemoUserAccount")
    Call<List<User>> USER_LIST_CALL();
}
