package org.lmy.open.utillibrary.imageload.cache;

import com.nostra13.universalimageloader.cache.disc.impl.BaseDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;

import org.lmy.open.utillibrary.imageload.base.BaseLoadImageConfigure;
import org.lmy.open.utillibrary.path.PathUtil;

import java.io.File;

/**
 * @author lmy
 * @ClassName: DiskCache
 * @Package: org.lmy.open.utillibrary.imageload.cache
 * @Date: 2018/07/31
 */
public final class DiskCache extends BaseDiskCache {
    /**
     * 图片加载配置
     */
    private BaseLoadImageConfigure mImageConfigure;

    private DiskCache(File cacheDir) {
        super(cacheDir);
    }

    private DiskCache(File cacheDir, File reserveCacheDir) {
        super(cacheDir, reserveCacheDir);
    }

    public DiskCache(FileNameGenerator fileNameGenerator, BaseLoadImageConfigure configure) {
        this(new File(PathUtil.getInstance().getImageCachePath()), new File(PathUtil.getInstance().getCachePath()), fileNameGenerator);
        mImageConfigure = configure;
    }

    private DiskCache(File cacheDir, File reserveCacheDir, FileNameGenerator fileNameGenerator) {
        super(cacheDir, reserveCacheDir, fileNameGenerator);
    }

    @Override
    protected File getFile(String imageUri) {
        String fileName = fileNameGenerator.generate(imageUri);
        File dir = new File(mImageConfigure.getCachePath(imageUri));
        if (!cacheDir.exists() && !cacheDir.mkdirs()) {
            boolean isHaveReserveCacheDir = reserveCacheDir != null && (reserveCacheDir.exists() || reserveCacheDir.mkdirs());
            if (isHaveReserveCacheDir) {
                dir = reserveCacheDir;
            }
        }
        return new File(dir, fileName);
    }
}
