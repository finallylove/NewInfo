package lbw.com.newsinfo.manager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import lbw.com.newsinfo.MyApp;

/**
 * Created by sf on 2015/2/2.
 */
public class RequestManager {
    public static RequestQueue sRequestQueue = Volley.newRequestQueue(MyApp.getContext());

    private RequestManager(){

    }

    public static void addRequest(Request<?> request,Object tag){
        if(tag!=null){
            request.setTag(tag);
        }
        sRequestQueue.add(request);
    }

    public static void cancelAll(Object tag){
        sRequestQueue.cancelAll(tag);
    }
}
