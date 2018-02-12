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
        AssetManager assetManager = AssetManager.class.newInstance();
        Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
        method.invoke(assetManager, Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "plugin.skin");
        Resources pluginResources = new Resources(assetManager,
                nativeResources.getDisplayMetrics(),
                nativeResources.getConfiguration());

        int drawableId = pluginResources.getIdentifier("ic_launcher", "drawable",
                "rc.loveq.skinplugin");
        mIvImage.setImageDrawable(pluginResources.getDrawable(drawableId));
    }
}
