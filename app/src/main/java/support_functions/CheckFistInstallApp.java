package support_functions;

import android.content.Context;
import android.content.SharedPreferences;

public class CheckFistInstallApp {
    private static final String FistInstall = "FirstInstall";
    private final Context mContext;

    public CheckFistInstallApp(Context mContex) {
        this.mContext = mContex;
    }

    public void putBooleanValue(String key, boolean check) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(FistInstall, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, check);
        editor.apply();
    }

    public boolean getBooleanValue(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(FistInstall, 0);
        return sharedPreferences.getBoolean(key, false);
    }
}
