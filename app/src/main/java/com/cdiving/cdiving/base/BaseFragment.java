package com.cdiving.cdiving.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * @author zhanghao
 * @date 2018-10-23.
 */

public abstract class BaseFragment extends RxFragment {
    protected BaseActivity activity;
    protected View rootView;
    protected boolean isFirstLoad = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (BaseActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return setLayout(inflater, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        rootView = view;
        bindView(rootView);
        init();
        initEvent();
        if(isFirstLoad){
            lazyLoad();
            isFirstLoad = false;
        }

    }

    /**
     * 加载布局
     * @param inflater
     * @param container
     * @return
     */
    protected abstract View setLayout(LayoutInflater inflater, ViewGroup container);

    /**
     * 绑定View
     * @param view
     */
    protected abstract void bindView(View view);

    /**
     * 初始化
     */
    protected abstract void init();

    /**
     * 初始化事件
     */
    protected abstract void initEvent();

    /**
     * 加载数据
     */
    protected abstract void lazyLoad();
}
