package rc.loveq.framelibrary.skin;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rc.loveq.framelibrary.skin.attr.SkinView;
import rc.loveq.framelibrary.skin.callback.ISkinChangeListener;
import rc.loveq.framelibrary.skin.config.SkinConfig;
import rc.loveq.framelibrary.skin.config.SkinPreUtils;

/**
 * Author：Rc
 * 0n 2018/2/18 07:56
 */

public class SkinManager {
    private static SkinManager mInstance;
    private Context mContext;
    private SkinResource mSkinResource;

    private Map<ISkinChangeListener, List<SkinView>>
            mSkinViews = new HashMap<>();

    static {
        mInstance = new SkinManager();
    }

    public static SkinManager getInstance() {
        return mInstance;
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();
        // 每一次打开应用都会到这里来，防止皮肤被任意删除，做一些措施
        String currentSkinPath = SkinPreUtils.getInstance(context).getSkinPath();

        File file = new File(currentSkinPath);
        if (!file.exists()) {
            // 不存在，清空皮肤
            SkinPreUtils.getInstance(context).clearSkinInfo();
            return;
        }
        //是否能获取到包名
        String packageName = context.getPackageManager().getPackageArchiveInfo(currentSkinPath,
                PackageManager.GET_ACTIVITIES).packageName;

        if (TextUtils.isEmpty(packageName)) {
            SkinPreUtils.getInstance(context).clearSkinInfo();
            return;
        }

        //校验签名

        mSkinResource = new SkinResource(mContext, currentSkinPath);

    }

    /**
     * 加载皮肤
     *
     * @param skinPath
     * @return
     */
    public int loadSkin(String skinPath) {
        File file = new File(skinPath);
        if (!file.exists()) {
            return SkinConfig.SKIN_FILE_NOEXSIST;
        }
        String packageName = mContext.getPackageManager().getPackageArchiveInfo(
                skinPath, PackageManager.GET_ACTIVITIES
        ).packageName;

        if (TextUtils.isEmpty(packageName)) {
            return SkinConfig.SKIN_FILE_ERROR;
        }
        //当前皮肤如果一样不要换
        String currentSkinPath = SkinPreUtils.getInstance(mContext).getSkinPath();
        if (skinPath.equals(currentSkinPath)) {
            return SkinConfig.SKIN_CHANGE_NOTHING;
        }
        // 校验签名
        mSkinResource = new SkinResource(mContext, skinPath);

        changeSkin();

        saveSkinStatus(skinPath);
        return SkinConfig.SKIN_CHANGE_SUCCESS;
    }

    /**
     * 改变皮肤
     */
    private void changeSkin() {
        Set<ISkinChangeListener> listeners = mSkinViews.keySet();
        for (ISkinChangeListener listener : listeners) {
            List<SkinView> views = mSkinViews.get(listener);
            for (SkinView view : views) {
                view.skin();
            }
            listener.changeSkin(mSkinResource);
        }
    }

    private void saveSkinStatus(String path) {
        SkinPreUtils.getInstance(mContext).saveSkinPath(path);
    }

    /**
     * 恢复默认
     * @return
     */
    public int restoreDefault() {
        // 判断当前有没有皮肤，没有皮肤就不要执行任何方法
        String currentSkinPath = SkinPreUtils.getInstance(mContext).getSkinPath();

        if(TextUtils.isEmpty(currentSkinPath)){
            return SkinConfig.SKIN_CHANGE_NOTHING;
        }

        // 当前手机运行的app的路径apk路径
        String skinPath = mContext.getPackageResourcePath();
        // 初始化资源管理
        mSkinResource = new SkinResource(mContext,skinPath);

        // 改变皮肤
        changeSkin();

        // 把皮肤信息清空
        SkinPreUtils.getInstance(mContext).clearSkinInfo();

        return SkinConfig.SKIN_CHANGE_SUCCESS;
    }

    /**
     * 获取SkinView通过activity
     * @param activity
     * @return
     */
    public List<SkinView> getSkinViews(Activity activity) {
        return mSkinViews.get(activity);
    }

    /**
     * 注册
     * @param skinChangeListener
     * @param skinViews
     */
    public void register(ISkinChangeListener skinChangeListener, List<SkinView> skinViews) {
        mSkinViews.put(skinChangeListener,skinViews);
    }

    /**
     * 获取当前的皮肤资源
     * @return
     */
    public SkinResource getSkinResource() {
        return mSkinResource;
    }

    /**
     * 检测要不要换肤
     * @param skinView
     */
    public void checkChangeSkin(SkinView skinView) {
        // 如果当前有皮肤，也就是保存了皮肤路径，就换一下皮肤
        String currentSkinPath = SkinPreUtils.getInstance(mContext).getSkinPath();

        if(!TextUtils.isEmpty(currentSkinPath)) {
            // 切换一下
            skinView.skin();
        }
    }

    /**
     * 防止内存泄露
     */
    public void unregister(ISkinChangeListener skinChangeListener) {
        mSkinViews.remove(skinChangeListener);
    }

}
