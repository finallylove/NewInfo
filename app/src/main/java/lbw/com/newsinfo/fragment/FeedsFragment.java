package lbw.com.newsinfo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.android.volley.Cache;
import com.android.volley.GsonRequest;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.DiskBasedCache;
import com.google.gson.Gson;
import com.nhaarman.listviewanimations.appearance.AnimationAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lbw.com.newsinfo.BaseFragment;
import lbw.com.newsinfo.R;
import lbw.com.newsinfo.activity.DetailActivity;
import lbw.com.newsinfo.adapter.CardsAnimationAdapter;
import lbw.com.newsinfo.adapter.FeedsAdapter;
import lbw.com.newsinfo.adapter.NewsListAdapter;
import lbw.com.newsinfo.entity.NewsEntity;
import lbw.com.newsinfo.net.HttpApi;
import lbw.com.newsinfo.view.MultiSwipeRefreshLayout;
import lbw.com.newsinfo.view.OnLoadNextListener;
import lbw.com.newsinfo.view.PageStaggeredGridView;

/**
 * Created by lbw in 2015.2.19
 */
public class FeedsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, OnLoadNextListener, MultiSwipeRefreshLayout.CanChildScrollUpCallback, AdapterView.OnItemClickListener {
    public static final String EXTRA_TITLE = "extra_title";

    Context mContext;
    private final Gson mGson = new Gson();

    @InjectView(R.id.swipe_container)
    MultiSwipeRefreshLayout mSwipeRefreshLayout;
    @InjectView(R.id.material_listview)
    PageStaggeredGridView mGridView;

    FeedsAdapter mAdapter;
    AnimationAdapter mAnimationAdapter;

    public ArrayList<NewsEntity> mList;
    public String next;

    public static FeedsFragment newInstance(String title) {
        FeedsFragment fragment = new FeedsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        View contentView = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.inject(this, contentView);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setCanChildScrollUpCallback(this);
        loadData("0");
        mGridView.setLoadNextListener(this);
        mGridView.setOnItemClickListener(this);
        return contentView;
    }

    @Override
    public void onRefresh() {
        loadData("0");
    }

    @Override
    public void onLoadNext() {
        loadData(next);
    }

    private void loadData(String next) {
        if ("0".equals(next)) {
            if (!mSwipeRefreshLayout.isRefreshing())
                mSwipeRefreshLayout.setRefreshing(true);
            executeRequest(new GsonRequest(HttpApi.NEWS_LAST, FeedRequestData.class, responseListener(next), errorListener()));
        } else {
            executeRequest(new GsonRequest(String.format(HttpApi.NEWS_LIST, next), FeedRequestData.class, responseListener(next), errorListener()));
        }
    }

    private Response.Listener<FeedRequestData> responseListener(String page) {
        final boolean isRefreshFromTop = ("0".equals(page));
        return new Response.Listener<FeedRequestData>() {

            @Override
            public void onResponse(FeedRequestData response) {
                if (isRefreshFromTop) {
                    mSwipeRefreshLayout.setRefreshing(!isRefreshFromTop);
                    mList = response.stories;
                } else {
                    mList.addAll(response.stories);
                }
                next = response.date;
                if (mList != null && mList.size() > 0)
                    refreshNewsData(isRefreshFromTop);
            }
        };
    }

    public void refreshNewsData(boolean isRefreshFromTop) {
        if (mAdapter == null || isRefreshFromTop) {
            mAdapter = new FeedsAdapter(mContext, mList);
            mAnimationAdapter = new CardsAnimationAdapter(mAdapter);
            mAnimationAdapter.setAbsListView(mGridView);
            mGridView.setAdapter(mAnimationAdapter);
        } else {
            mAnimationAdapter.notifyDataSetChanged();
        }
        mGridView.setRefresh(false);
    }

    @Override
    protected Response.ErrorListener errorListener() {

        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mGridView.setRefresh(true);
                    DiskBasedCache cache = new DiskBasedCache(new File(mContext.getCacheDir(), "volley"));
                    cache.initialize();
                    Cache.Entry entry = cache.get(HttpApi.NEWS_LAST);
                    if (entry != null) {
                        FeedRequestData data = mGson.fromJson(new String(entry.data), FeedRequestData.class);
                        mList = data.stories;
                        refreshNewsData(true);
                    }
                } else {
                    //加载更多Error
                }
            }
        };
    }

    @Override
    public boolean canSwipeRefreshChildScrollUp() {
        return mGridView.getDistanceToTop() != 0;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(mContext, DetailActivity.class).putExtra("id", mList.get(position).id));
    }


    public static class FeedRequestData {
        public ArrayList<NewsEntity> stories;
        public String date;
    }

}
