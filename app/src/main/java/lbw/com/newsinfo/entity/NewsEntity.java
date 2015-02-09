package lbw.com.newsinfo.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by lbw on 2015/2/9.
 */
public class NewsEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public String title;
    public String share_url;
    public String ga_prefix;
    public ArrayList<String> images;
    public int type;
    public long id;

    public boolean subscribed;
    public String theme_name;
    public int theme_id;

    //已读、未读标识
    public boolean is_read = false;

    //是否仅仅是个tag
    public boolean isTag = false;

    @Override
    public String toString() {
        return "title="+title;
    }
}

