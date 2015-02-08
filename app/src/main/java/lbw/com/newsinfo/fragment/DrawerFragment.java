package lbw.com.newsinfo.fragment;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import lbw.com.newsinfo.BaseFragment;
import lbw.com.newsinfo.MyApp;
import lbw.com.newsinfo.R;
import lbw.com.newsinfo.activity.MainActivity;
import lbw.com.newsinfo.adapter.DrawerAdapter;
import lbw.com.newsinfo.entity.Items;

/**
 * Created by lbw on 2015/2/6.
 */
public class DrawerFragment extends BaseFragment {
    private String[] mDrawerTitles;
    private TypedArray mDrawerIcons;
    private ArrayList<Items> drawerItems;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDrawerTitles = getResources().getStringArray(R.array.drawer_titles);
        mDrawerIcons = getResources().obtainTypedArray(R.array.drawer_icons);
        drawerItems = new ArrayList<>();
        for (int i = 0; i < mDrawerTitles.length; i++) {
            drawerItems.add(new Items(mDrawerTitles[i], mDrawerIcons.getResourceId(i, -(i + 1))));
        }
        ListView mListView = (ListView) inflater.inflate(R.layout.fragment_drawer, null);
        mListView.setAdapter(new DrawerAdapter(MyApp.getContext(), drawerItems));
        mListView.setOnItemClickListener(new DrawerItemClickListener());
        mListView.setItemChecked(0, true);
        return mListView;
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            ((MainActivity)getActivity()).setNewsData(mDrawerTitles[position]);
        }
    }
}
