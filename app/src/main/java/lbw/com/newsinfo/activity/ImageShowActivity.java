package lbw.com.newsinfo.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import lbw.com.newsinfo.BaseActivity;
import lbw.com.newsinfo.R;
import lbw.com.newsinfo.fragment.DetailFragment;
import lbw.com.newsinfo.fragment.ImageShowFragment;

public class ImageShowActivity extends BaseActivity {

    private ImageShowFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);
        String imageUrl = getIntent().getStringExtra("image_url");
        mFragment = ImageShowFragment.getInstanceDetail(imageUrl);
        replaceFragment(R.id.image_show, mFragment);
    }

}
