package lbw.com.newsinfo.fragment;

import android.app.Activity;
import android.content.Context;
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
import android.widget.ImageView;

import com.android.volley.GsonRequest;
import com.android.volley.Response;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lbw.com.newsinfo.BaseFragment;
import lbw.com.newsinfo.R;
import lbw.com.newsinfo.entity.NewsDetailEntity;
import lbw.com.newsinfo.net.HttpApi;
import lbw.com.newsinfo.view.MultiSwipeRefreshLayout;

/**
 * Created by lbw on 2015/2/12.
 */
public class ImageShowFragment extends BaseFragment{
    private static ImageShowFragment sImageShowFragment;
    private Context mContext;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;
    private String imageUrl;

    @InjectView(R.id.image_detail)
    ImageView mImageView;

    public static ImageShowFragment getInstanceDetail(String ImageUrl) {
        if (sImageShowFragment == null) {
            sImageShowFragment = new ImageShowFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString("image_url", ImageUrl);
        sImageShowFragment.setArguments(bundle);
        return sImageShowFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Bundle bundle = getArguments();
            imageUrl = bundle.getString("image_url");
        } else {
            imageUrl = savedInstanceState.getString("image_url");
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("image_url",imageUrl);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = mInflater.inflate(R.layout.fragment_image_show, null);
        ButterKnife.inject(this, contentView);
        mImageLoader=ImageLoader.getInstance();
        mImageLoader.displayImage(imageUrl,mImageView);
        return contentView;
    }
}

