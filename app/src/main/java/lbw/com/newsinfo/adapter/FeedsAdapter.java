package lbw.com.newsinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import lbw.com.newsinfo.R;

/**
 * Created by lbw on 2015/2/9.
 */
public class FeedsAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    public FeedsAdapter(Context context){
        mContext=context;
        mInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return 30;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return mInflater.inflate(R.layout.listitem_feed,null);
    }
}
