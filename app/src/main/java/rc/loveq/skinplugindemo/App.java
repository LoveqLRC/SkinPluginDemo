package rc.loveq.skinplugindemo;

import android.app.Application;

import rc.loveq.framelibrary.skin.SkinManager;

/**
 * Author：Rc
 * 0n 2018/2/18 11:30
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getInstance().init(this);
    }
}
