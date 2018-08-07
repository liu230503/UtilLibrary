package org.lmy.open.utillibrary.imageload;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.ArrayMap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.lmy.open.utillibrary.MyResource;
import org.lmy.open.utillibrary.UtilApplication;
import org.lmy.open.utillibrary.imageload.base.BaseLoadImageConfigure;

import java.util.Map;

/**********************************************************************
 *
 *
 * @类名 LoadImageConfigure
 * @包名 org.lmy.open.utillibrary.ImageLoad
 * @author lmy
 * @创建日期 2018/3/5
 ***********************************************************************/
final class LoadImageConfigure extends BaseLoadImageConfigure {

    @Override
    protected Map<Integer, String> getPathMap() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return new ArrayMap<>();
        }else {
            return new ArrayMap<Integer, String>();
        }
    }

    @Override
    public DisplayImageOptions getOrCreateOptions(int type) {
        DisplayImageOptions options = mOptionsMap.get(type);
        if (options != null) {
            return options;
        }
        switch (type) {
            default:
                options = new DisplayImageOptions.Builder()
                        .showImageForEmptyUri(MyResource.getIdByName(UtilApplication.getInstance().getContext(), "mipmap", "picture_error"))
                        .showImageOnFail(MyResource.getIdByName(UtilApplication.getInstance().getContext(), "mipmap", "picture_error"))
                        .showImageOnLoading(MyResource.getIdByName(UtilApplication.getInstance().getContext(), "mipmap", "picture_error"))
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                        .bitmapConfig(Bitmap.Config.ARGB_4444)
                        .build();

                break;
        }
        return options;
    }

    @Override
    protected int getMaxImageWidthForMemoryCache() {
        return 480;
    }

    @Override
    protected int getMaxImageHeightForMemoryCache() {
        return 800;
    }

    @Override
    protected int getMaxImageWidthForDiskCache() {
        return 480;
    }

    @Override
    protected int getMaxImageHeightForDiskCache() {
        return 800;
    }

    @Override
    protected int getMaxSizeForMemoryCache() {
        return 2 * 1024 * 1024;
    }

    @Override
    public int getAvailableMemoryPercent() {
        return 20;
    }

    @Override
    public int getMaxFileCountForDiskCache() {
        return 200;
    }
}
