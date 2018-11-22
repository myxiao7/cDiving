package com.cdiving.cdiving.rongyun;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdiving.cdiving.R;
import com.cdiving.cdiving.adapter.FollowAdapter;
import com.cdiving.cdiving.base.BaseFragment;
import com.cdiving.cdiving.entity.Follow;
import com.cdiving.cdiving.http.RetrofitFactory;
import com.cdiving.cdiving.ui.DatumActivity;
import com.cdiving.cdiving.utils.db.DbUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;

/**
 * @author zhanghao
 * @date 2018-11-09.
 */

public class FollowFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private FollowAdapter adapter;
    private List<Follow> list;
    public static FollowFragment newInstance(){
        FollowFragment followFragment = new FollowFragment();
        return followFragment;
    }
    @Override
    protected View setLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_follow, container, false);
    }

    @Override
    protected void bindView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void init() {
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new FollowAdapter(list, activity);
        recyclerView.setAdapter(adapter);
    }

    private void getFollowList() {
        RetrofitFactory.getUserApi()
                .getFollowList("user_following", "api", "User", DbUtil.getUserInfo().getOauth_token(),
                        DbUtil.getUserInfo().getOauth_token_secret(), "0")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Follow>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Follow> follows) {
                        swipeRefreshLayout.setRefreshing(false);
                        list.clear();
                        list.addAll(follows);
                        adapter.notifyDataSetChanged();
                        DbUtil.insertFollowList(follows);
                    }

                    @Override
                    public void onError(Throwable e) {
                        List<Follow> follows = DbUtil.getFollowList();
                        list.clear();
                        list.addAll(follows);
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void initEvent() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                RongIM.getInstance().startPrivateChat(activity, list.get(position).getUid(), list.get(position).getUname());
                DatumActivity.start(activity, list.get(position).getUid());
            }
        });
        adapter.setEnableLoadMore(false);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFollowList();
            }
        });
    }

    @Override
    protected void lazyLoad() {
        getFollowList();
    }


}
