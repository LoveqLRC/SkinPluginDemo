package rc.loveq.framelibrary.skin.attr;

import android.view.View;

import java.util.List;

/**
 * Author：Rc
 * 0n 2018/2/18 08:04
 */

public class SkinView {
    private View mView;
    private List<SkinAttr> mSkinAttrs;

    public SkinView(View view, List<SkinAttr> skinAttrs) {
        mView = view;
        mSkinAttrs = skinAttrs;
    }

    public void skin() {
        for (SkinAttr attr : mSkinAttrs) {
            attr.skin(mView);
        }
    }
}
