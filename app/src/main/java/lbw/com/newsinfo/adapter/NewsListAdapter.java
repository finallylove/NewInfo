package lbw.com.newsinfo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhaarman.listviewanimations.itemmanipulation.expandablelistitem.ExpandableListItemAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import lbw.com.newsinfo.R;
import lbw.com.newsinfo.entity.NewsEntity;

/**
 * Created by lbw on 2015/2/11.
 */
public class NewsListAdapter extends ExpandableListItemAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<NewsEntity> mList;
    private static final String ERROR_IMAGE = ImageDownloader.Scheme.DRAWABLE.wrap("R.drawable.icon_load_fail");

    public NewsListAdapter(@NonNull Context context, ArrayList<NewsEntity> list) {
        super(context, list);
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mList = list;
    }

    @NonNull
    @Override
    public View getTitleView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView=new TextView(mContext);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setText(mList.get(position).title);
        return textView;
    }

    @NonNull
    @Override
    public View getContentView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
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

    private class ViewHolder {
        TextView title;
        ImageView newImage;
    }
}
