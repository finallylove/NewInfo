package lbw.com.newsinfo.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import lbw.com.newsinfo.BaseActivity;
import lbw.com.newsinfo.R;
import lbw.com.newsinfo.fragment.DetailFragment;

public class DetailActivity extends BaseActivity {

    private DetailFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        long id = getIntent().getLongExtra("id", 0);
        mFragment = DetailFragment.getInstanceDetail(id);
        replaceFragment(R.id.detail, mFragment);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mFragment.onKeyDown(keyCode, event))
            return true;
        return super.onKeyDown(keyCode, event);
    }
}
