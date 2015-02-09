package lbw.com.newsinfo.net;

/**
 * Created by lbw on 2015/2/9.
 */
public class HttpApi {
    private static final String HOST = "http://news-at.zhihu.com/api/3/news";

    public static final String NEWS_LIST = HOST + "/before/%1$s";

    public static final String NEWS_LAST = HOST + "/latest";

    public static final String NEWS_DETAIL = HOST + "/%1$s";


}
