package lbw.com.newsinfo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.joanzapata.android.QuickAdapter;
import com.nhaarman.listviewanimations.appearance.AnimationAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lbw.com.newsinfo.BaseFragment;
import lbw.com.newsinfo.R;
import lbw.com.newsinfo.adapter.CardsAnimationAdapter;
import lbw.com.newsinfo.adapter.FeedsAdapter;
import lbw.com.newsinfo.view.MultiSwipeRefreshLayout;

/**
 * Created by lbw in 2015.2.19
 */
public class FeedsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final String EXTRA_TITLE = "extra_title";

    Context mContext;
    @InjectView(R.id.swipe_container)
    MultiSwipeRefreshLayout mSwipeRefreshLayout;
    @InjectView(R.id.material_listview)
    StaggeredGridView mGridView;

    FeedsAdapter mAdapter;

    public static FeedsFragment newInstance(String title) {
        FeedsFragment fragment = new FeedsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext=getActivity();
        View contentView = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.inject(this, contentView);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mAdapter=new FeedsAdapter(mContext);
        AnimationAdapter animationAdapter=new CardsAnimationAdapter(mAdapter);
        animationAdapter.setAbsListView(mGridView);
        mGridView.setAdapter(animationAdapter);



        return contentView;
    }

    @Override
    public void onRefresh() {

    }

}
