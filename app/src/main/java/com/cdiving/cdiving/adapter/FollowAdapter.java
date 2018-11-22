package com.cdiving.cdiving.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cdiving.cdiving.R;
import com.cdiving.cdiving.entity.Follow;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author zhanghao
 * @date 2018-11-09.
 */

public class FollowAdapter extends BaseQuickAdapter<Follow, BaseViewHolder>{
    private Context mContext;


    public FollowAdapter(@Nullable List<Follow> data, Context context) {
        super(R.layout.list_item_follow, data);
        this.mContext = context;
    }
    @Override
    protected void convert(BaseViewHolder helper, Follow item) {
        ImageView ivIcon = helper.getView(R.id.ivIcon);
        RequestOptions requestOptions = RequestOptions.circleCropTransform().error(R.mipmap.ic_default);
        Glide.with(mContext).load(item.getAvatar_middle()).apply(requestOptions).into(ivIcon);
        helper.setText(R.id.tvName, item.getUname().trim());
    }
}
