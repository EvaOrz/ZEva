package cn.com.zwwl.bayuwen.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

/**
 * Created by lousx on 2017/2/23.
 */
@com.bumptech.glide.annotation.GlideModule
public final class TGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(final Context context, GlideBuilder glideBuilder) {
        int diskCacheSize = 1024 * 1024 * 30;//最多可以缓存多少字节的数据
        int memorySize = (int) (Runtime.getRuntime().maxMemory()) / 8;  // 取1/8最大内存作为最大缓存
        glideBuilder.setDiskCache(
                new ExternalCacheDiskCacheFactory(context, "zwwlapp", diskCacheSize));
        // 自定义内存和图片池大小
        glideBuilder.setMemoryCache(new LruResourceCache(memorySize));
        glideBuilder.setBitmapPool(new LruBitmapPool(memorySize));
    }


    /**
     * 为App注册一个自定义的String类型的BaseGlideUrlLoader
     *
     * @param context
     * @param registry
     */

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        super.registerComponents(context, glide, registry);
        registry.append(String.class, InputStream.class,new CustomBaseGlideUrlLoader.Factory());
    }

    /**
     * 清单解析的开启
     *
     * 这里不开启，避免添加相同的modules两次
     * @return
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
