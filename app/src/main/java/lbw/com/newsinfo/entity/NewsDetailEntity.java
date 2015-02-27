package lbw.com.newsinfo.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class NewsDetailEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String body;
	public String image_source;
	public String title;
	public String image;
	public String share_url;
	public ArrayList<String> js;
	public int type;
	public String ga_prefix;
	public long id;
	public ArrayList<String> css;

    @Override
    public String toString() {
        return "NewsDetailEntity{" +
                "body='" + body + '\'' +
                ", image_source='" + image_source + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", share_url='" + share_url + '\'' +
                ", js=" + js +
                ", type=" + type +
                ", ga_prefix='" + ga_prefix + '\'' +
                ", id=" + id +
                ", css=" + css +
                '}';
    }
}