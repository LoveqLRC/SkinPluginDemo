package rc.loveq.skinplugindemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

import rc.loveq.framelibrary.BaseSkinActivity;
import rc.loveq.framelibrary.skin.SkinManager;

public class SkinActivity extends BaseSkinActivity {

    public ImageView mIvSkin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);
        mIvSkin = findViewById(R.id.iv_skin);
    }

    public void changeSkin(View view) {
        String skinPath = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator + "red.skin";
        int status = SkinManager.getInstance().loadSkin(skinPath);
    }

    public void restoreToDefault(View view) {
        SkinManager.getInstance().restoreDefault();
    }

    public void startSkinActivity(View view) {
        Intent intent = new Intent(this, SkinActivity.class);
        startActivity(intent);

    }
}
