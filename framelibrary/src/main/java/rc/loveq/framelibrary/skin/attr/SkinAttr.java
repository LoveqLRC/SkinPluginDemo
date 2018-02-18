package rc.loveq.framelibrary.skin.attr;

import android.view.View;

/**
 * Author：Rc
 * 0n 2018/2/18 08:05
 */

public class SkinAttr {
    private String mResName;
    private SkinType mSkinType;

    public SkinAttr(String resName, SkinType skinType) {
        mResName = resName;
        mSkinType = skinType;
    }

    public void skin(View view) {
        mSkinType.skin(view, mResName);
    }
}
