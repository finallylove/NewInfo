package lbw.com.newsinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import org.w3c.dom.Text;

import java.util.ArrayList;

import lbw.com.newsinfo.MyApp;
import lbw.com.newsinfo.R;
import lbw.com.newsinfo.entity.NewsEntity;

/**
 * Created by lbw on 2015/2/9.
 */
public class FeedsAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<NewsEntity> mList;
    private static final String ERROR_IMAGE = ImageDownloader.Scheme.DRAWABLE.wrap("R.drawable.icon_load_fail");

    public FeedsAdapter(Context context, ArrayList<NewsEntity> list) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView title;
        ImageView newImage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        NewsEntity newsEntity = mList.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.listitem_feed, null);
            holder.title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.newImage = (ImageView) convertView.findViewById(R.id.iv_new);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(newsEntity.title);
        if (newsEntity.images == null || newsEntity.images.get(0).trim().length() == 0)
            ImageLoader.getInstance().displayImage(ERROR_IMAGE, holder.newImage);
        else
            ImageLoader.getInstance().displayImage(newsEntity.images.get(0), holder.newImage);
        return convertView;
    }
}
