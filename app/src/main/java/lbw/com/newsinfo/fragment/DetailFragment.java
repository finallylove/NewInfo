package lbw.com.newsinfo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lbw.com.newsinfo.BaseFragment;
import lbw.com.newsinfo.R;
import lbw.com.newsinfo.net.HttpApi;
import lbw.com.newsinfo.view.MultiSwipeRefreshLayout;

/**
 * Created by lbw on 2015/2/12.
 */
public class DetailFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    private static DetailFragment mDetailFragment;
    private Context mContext;
    private LayoutInflater mInflater;
    private long mNewsId;

    @InjectView(R.id.web_view)
    WebView mWebView;
    @InjectView(R.id.web_view_refresh)
    MultiSwipeRefreshLayout mSwipeRefreshLayout;

    public static DetailFragment getInstanceDetail(long id) {
        if (mDetailFragment == null) {
            mDetailFragment = new DetailFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        mDetailFragment.setArguments(bundle);
        return mDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Bundle bundle = getArguments();
            mNewsId = bundle.getLong("id");
        } else {
            mNewsId = savedInstanceState.getLong("id");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("id", mNewsId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = mInflater.inflate(R.layout.fragment_detail, null);
        ButterKnife.inject(this, contentView);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSaveFormData(false);
        mWebView.loadUrl(String.format(HttpApi.NEWS_DETAIL, String.valueOf(mNewsId)));
        mWebView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        return contentView;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return false;
    }

    @Override
    public void onRefresh() {
        mWebView.reload();
    }
}
