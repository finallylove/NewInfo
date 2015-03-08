package lbw.com.newsinfo.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.volley.GsonRequest;
import com.android.volley.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lbw.com.newsinfo.BaseFragment;
import lbw.com.newsinfo.R;
import lbw.com.newsinfo.activity.DetailActivity;
import lbw.com.newsinfo.activity.ImageShowActivity;
import lbw.com.newsinfo.entity.NewsDetailEntity;
import lbw.com.newsinfo.net.HttpApi;
import lbw.com.newsinfo.util.PhoneUtils;
import lbw.com.newsinfo.view.MultiSwipeRefreshLayout;

/**
 * Created by lbw on 2015/2/12.
 */
public class DetailFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static DetailFragment mDetailFragment;
    private Context mContext;
    private LayoutInflater mInflater;
    private long mNewsId;
    private NewsDetailEntity mNewsDetail;

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
        mWebView.addJavascriptInterface(new JavaScriptObject((Activity) mContext), "injectedObject");
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSaveFormData(false);

        executeRequest(new GsonRequest(String.format(HttpApi.NEW_DETAIL, mNewsId), NewsDetailEntity.class, responseListener(), errorListener()));
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
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
        executeRequest(new GsonRequest(String.format(HttpApi.NEW_DETAIL, mNewsId), NewsDetailEntity.class, responseListener(), errorListener()));
    }

    private Response.Listener<NewsDetailEntity> responseListener() {
        return new Response.Listener<NewsDetailEntity>() {
            @Override
            public void onResponse(NewsDetailEntity response) {
                mNewsDetail = response;
                if (mNewsDetail == null || mNewsDetail.body == null || mNewsDetail.body.length() == 0)
                    return;
                mWebView.loadDataWithBaseURL(null, modifyImgTag(mNewsDetail.body), "text/html", "UTF-8", null);
            }
        };
    }

    private String modifyImgTag(String html) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"img-wrap\">")
                .append("<img class=\"title-image\" src=\"").append(mNewsDetail.image)
                .append("\" alt=\"\">")
                .append("<div class=\"img-mask\"></div>");
        html = html.replace("<div class=\"img-place-holder\">", sb.toString());
        Document doc = Jsoup.parse(html);
        Elements es = doc.getElementsByTag("img");
        for (Element e : es) {
            if (!e.hasClass("avatar")) {
                e.attr("onclick", "injectedObject.openImage('" + e.attr("src") + "')");
                e.attr("width", "100%");
            }
        }

        es = doc.getElementsByClass("title-image");
        if (es != null && es.size() > 0) {
            es.get(0).attr("height", "40%");
            es = doc.getElementsByClass("headline-background-link");
            es.select(">div[class]").remove();
        }
        return doc.html();
    }

    public static class JavaScriptObject {

        private Activity mInstance;

        public JavaScriptObject(Activity instance) {
            mInstance = instance;
        }

        @JavascriptInterface
        public void openImage(String url) {
            Intent intent = new Intent(mInstance, ImageShowActivity.class);
            intent.putExtra("image_url", url);
            mInstance.startActivity(intent);
        }
    }

}

