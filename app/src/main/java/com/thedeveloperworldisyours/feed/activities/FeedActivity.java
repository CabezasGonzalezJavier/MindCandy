package com.thedeveloperworldisyours.feed.activities;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.thedeveloperworldisyours.feed.R;
import com.thedeveloperworldisyours.feed.adapters.ImageAdapter;
import com.thedeveloperworldisyours.feed.listeners.InfiniteScrollListener;
import com.thedeveloperworldisyours.feed.models.Feed;
import com.thedeveloperworldisyours.feed.utils.Constants;
import com.thedeveloperworldisyours.feed.utils.Utils;
import com.thedeveloperworldisyours.feed.webservices.Client;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class FeedActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<String> mList;
    private GridView mGridview;
    private boolean mFinishScroll = false;
    private ImageAdapter mAdapter;
    private ProgressDialog mProgressDialog;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        mProgressDialog = new ProgressDialog(FeedActivity.this);
        mList = new ArrayList<String>();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_feed_swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);


        mGridview = (GridView) findViewById(R.id.activity_feed_gridview);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mGridview.setNumColumns(1);
        } else {
            mGridview.setNumColumns(3);
        }


        mGridview.setOnScrollListener(new InfiniteScrollListener(5) {
            @Override
            public void loadMore(int page, int totalItemsCount) {
                mProgressDialog.show();
                getInfo();
                mFinishScroll = true;
            }
        });


        getInfo();

    }

    public void getInfo() {
        if (Utils.isOnline(FeedActivity.this)) {
            getFeed();
        }else{
            Toast.makeText(FeedActivity.this,R.string.check_internet,Toast.LENGTH_LONG).show();
        }
    }

    public void getList(Feed feed) {

        for (int i = 0; i < feed.getItems().size(); i++) {
            mList.add(feed.getItems().get(i).getMedia().getM());
            Log.v("mlist", mList.size() + "__");
        }

    }

    public void buildList() {


        if (!mFinishScroll) {
            mAdapter = new ImageAdapter(FeedActivity.this, mList);
            mGridview.setAdapter(mAdapter);

        } else {
            mAdapter.notifyDataSetChanged();
            mFinishScroll = false;
            mProgressDialog.dismiss();
        }
    }

    public void getFeed() {
        Callback<Feed> callback = new Callback<Feed>() {
            @Override
            public void success(Feed feed, Response response) {
                getList(feed);
                buildList();
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Toast.makeText(FeedActivity.this,R.string.data_failed,Toast.LENGTH_LONG).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        };
        Client.initRestAdapter().getQuestions(Constants.NOJSONCALLBACK, Constants.FORMAT, callback);
    }


    @Override
    public void onRefresh() {
        getInfo();
    }
}
