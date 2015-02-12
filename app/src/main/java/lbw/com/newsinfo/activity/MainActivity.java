package lbw.com.newsinfo.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lbw.com.newsinfo.BaseActivity;
import lbw.com.newsinfo.R;
import lbw.com.newsinfo.fragment.DrawerFragment;
import lbw.com.newsinfo.fragment.FeedsFragment;
import lbw.com.newsinfo.view.MultiSwipeRefreshLayout;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    private static FragmentManager mManager;
    private FeedsFragment mContentFragment;

    private MultiSwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mTitle = mDrawerTitle = getTitle();
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                mToolbar,
                R.string.drawer_open,
                R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(mTitle);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
            }
        };

        getSupportActionBar().setTitle(mTitle);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setNewsData(getResources().getStringArray(R.array.drawer_titles)[0]);
        replaceFragment(R.id.left_drawer, new DrawerFragment());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    public void setNewsData(String title) {
        mDrawerLayout.closeDrawers();
        if (mTitle.equals(title))
            return;
        mTitle = title;
        mContentFragment = FeedsFragment.newInstance(title);
        replaceFragment(R.id.main_content, mContentFragment);
    }
}
