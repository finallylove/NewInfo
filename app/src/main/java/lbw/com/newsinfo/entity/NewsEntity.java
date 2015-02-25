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
    public String ga_prefix;
    public ArrayList<String> images;
    public int type;
    public long id;

    @Override
    public String toString() {
        return "title="+title;
    }
}

