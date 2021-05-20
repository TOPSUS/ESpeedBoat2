package id.alin.espeedboat.MyHelper;

import android.content.Context;
import android.content.SharedPreferences;

import id.alin.espeedboat.MySharedPref.Config;

public class LogoutDataCleaner {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public LogoutDataCleaner(Context context) {
        this.context = context;
    }

    public void clearSharedPreferences(){
        sharedPreferences = context.getSharedPreferences(Config.ESPEED_STORAGE,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.remove(Config.USER_TOKEN);
        editor.remove(Config.USER_FOTO);
        editor.remove(Config.USER_JENIS_KELAMIN);
        editor.remove(Config.USER_NOHP);
        editor.remove(Config.USER_EMAIL);
        editor.remove(Config.USER_PIN);
        editor.remove(Config.USER_CHAT_ID);
        editor.remove(Config.USER_ALAMAT);
        editor.remove(Config.USER_NAMA);
        editor.remove(Config.USER_ID);
        editor.apply();
    }
}
