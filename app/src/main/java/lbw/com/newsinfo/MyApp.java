package lbw.com.newsinfo;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by sf on 2015/2/2.
 */
public class MyApp extends Application {

    private static Context sContext;
    @Override
    public void onCreate() {
        super.onCreate();
        sContext=getApplicationContext();
        initImageLoader(getApplicationContext());
    }

    private static void initImageLoader(Context context){
        ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(
                context).threadPoolSize(Thread.NORM_PRIORITY-1)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50*1024*1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }

    public static Context getContext(){
        return sContext;
    }
}
