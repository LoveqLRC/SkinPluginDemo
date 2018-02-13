package rc.loveq.skinplugindemo;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    public ImageView mIvImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIvImage = findViewById(R.id.iv_image);
    }

    public void changeSkin(View view) throws Exception {
        Resources nativeResources = getResources();
        //创建AssetManager，但是不能直接new所以只能通过反射
        AssetManager assetManager = AssetManager.class.newInstance();
        // 反射获取addAssetPath方法
        Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
        // 皮肤包的路径:  本地sdcard/plugin.skin
        // 反射调用addAssetPath方法
        method.invoke(assetManager, Environment.getExternalStorageDirectory().getAbsoluteFile()
                + File.separator + "plugin.skin");
        // 创建皮肤的Resources对象
        Resources pluginResources = new Resources(assetManager,
                nativeResources.getDisplayMetrics(),
                nativeResources.getConfiguration());
        // 通过资源名称,类型，包名获取Id
        int drawableId = pluginResources.getIdentifier("plugin_img", "drawable",
                "rc.loveq.skinplugin");
        // 设置背景
        mIvImage.setImageDrawable(pluginResources.getDrawable(drawableId));
    }
}
