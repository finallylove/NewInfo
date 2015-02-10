package lbw.com.newsinfo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;

/**
 * Created by lbw on 2015/2/10.
 */
import com.etsy.android.grid.StaggeredGridView;

public class PageStaggeredGridView extends StaggeredGridView implements AbsListView.OnScrollListener {

    private OnLoadNextListener mLoadNextListener;
    private boolean isRefresh=false;

    public PageStaggeredGridView(Context context) {
        super(context);
        init();
    }

    public PageStaggeredGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PageStaggeredGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setOnScrollListener(this);
    }

    public void setLoadNextListener(OnLoadNextListener listener) {
        mLoadNextListener = listener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (isRefresh) {
            return;
        }
        if (firstVisibleItem + visibleItemCount >= totalItemCount
                && totalItemCount != 0
                && totalItemCount != getHeaderViewsCount()
                + getFooterViewsCount() && mLoadNextListener != null) {
            mLoadNextListener.onLoadNext();
            isRefresh=true;
        }
    }

    public void setRefresh(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }
}
