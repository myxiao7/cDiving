package com.cdiving.cdiving;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.cdiving.cdiving.base.BaseActivity;
import com.cdiving.cdiving.entity.CompanyInfo;
import com.cdiving.cdiving.entity.CompanyResult;
import com.cdiving.cdiving.entity.RongYunToken;
import com.cdiving.cdiving.http.RetrofitFactory;
import com.cdiving.cdiving.im.SealConst;
import com.cdiving.cdiving.im.SealUserInfoManager;
import com.cdiving.cdiving.im.ui.activity.NewFriendListActivity;
import com.cdiving.cdiving.login.LoginActivity;
import com.cdiving.cdiving.rongyun.TabActivity;
import com.cdiving.cdiving.utils.DialogUtil;
import com.cdiving.cdiving.utils.ScreenUtil;
import com.cdiving.cdiving.utils.ToastUtil;
import com.cdiving.cdiving.utils.db.DbUtil;
import com.yinglan.scrolllayout.ScrollLayout;
import com.yinglan.scrolllayout.content.ContentScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ContactNotificationMessage;

/**
 * @author zhanghao
 * @date
 */
public class MainActivity extends BaseActivity {
    MapView mMapView = null;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnTab)
    Button btnTab;
    @BindView(R.id.btnChat)
    Button btnChat;
    @BindView(R.id.map)
    MapView map;
    @BindView(R.id.linFoot)
    LinearLayout linFoot;
    @BindView(R.id.scroll_down_layout)
    ScrollLayout mScrollLayout;
    @BindView(R.id.root)
    RelativeLayout root;
    @BindView(R.id.re)
    RelativeLayout re;

    private AMap mAMap;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;

    private List<CompanyResult.CompanyAddress> list;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    private ScrollLayout.OnScrollChangedListener mOnScrollChangedListener = new ScrollLayout.OnScrollChangedListener() {
        @Override
        public void onScrollProgressChanged(float currentProgress) {
            if (currentProgress >= 0) {
                float precent = 255 * currentProgress;
                if (precent > 255) {
                    precent = 255;
                } else if (precent < 0) {
                    precent = 0;
                }
                mScrollLayout.getBackground().setAlpha(255 - (int) precent);
            }
            if (linFoot.getVisibility() == View.VISIBLE)
                linFoot.setVisibility(View.GONE);
        }

        @Override
        public void onScrollFinished(ScrollLayout.Status currentStatus) {
            if (currentStatus.equals(ScrollLayout.Status.EXIT)) {
                linFoot.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onChildScroll(int top) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        editor = sp.edit();
        initView();
        getCompanyList();
        getRongYunToken();
        initLocation();
        //获取地图控件引用
        mMapView = findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        if (mAMap == null) {
            mAMap = mMapView.getMap();
        }
    }

    private void initView() {
        /*listView.addHeaderView(LayoutInflater.from(activity).inflate(R.layout.layout_header_company, null));
        listView.setAdapter(new ListviewAdapter(this));
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        re.measure(w, h);
        final int height = re.getMeasuredHeight();
        int width = re.getMeasuredWidth();
        ToastUtil.showLong("" + height + " " + width);*/
        /**设置 setting*/
        mScrollLayout.setMinOffset(ScreenUtil.dip2px(this, 48));
//        mScrollLayout.setMaxOffset((int) (ScreenUtil.getScreenHeight(this) * 0.5));
        mScrollLayout.setMaxOffset(ScreenUtil.dip2px(this, 200));

        mScrollLayout.setExitOffset(ScreenUtil.dip2px(this, 48));
        mScrollLayout.setIsSupportExit(true);
        mScrollLayout.setAllowHorizontalScroll(true);
        mScrollLayout.setOnScrollChangedListener(mOnScrollChangedListener);
        mScrollLayout.setToExit();

        mScrollLayout.getBackground().setAlpha(0);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollLayout.scrollToExit();
            }
        });

        linFoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollLayout.setToOpen();
            }
        });
    }

    protected void initData() {
        final Conversation.ConversationType[] conversationTypes = {
                Conversation.ConversationType.PRIVATE,
                Conversation.ConversationType.GROUP, Conversation.ConversationType.SYSTEM,
                Conversation.ConversationType.PUBLIC_SERVICE, Conversation.ConversationType.APP_PUBLIC_SERVICE
        };

//        RongIM.getInstance().addUnReadMessageCountChangedObserver(this, conversationTypes);
        getConversationPush();// 获取 push 的 id 和 target
        getPushMessage();
    }

    private void getConversationPush() {
        if (getIntent() != null && getIntent().hasExtra("PUSH_CONVERSATIONTYPE") && getIntent().hasExtra("PUSH_TARGETID")) {

            final String conversationType = getIntent().getStringExtra("PUSH_CONVERSATIONTYPE");
            final String targetId = getIntent().getStringExtra("PUSH_TARGETID");


            RongIM.getInstance().getConversation(Conversation.ConversationType.valueOf(conversationType), targetId, new RongIMClient.ResultCallback<Conversation>() {
                @Override
                public void onSuccess(Conversation conversation) {

                    if (conversation != null) {

                        if (conversation.getLatestMessage() instanceof ContactNotificationMessage) { //好友消息的push
                            startActivity(new Intent(MainActivity.this, NewFriendListActivity.class));
                        } else {
                            Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon().appendPath("conversation")
                                    .appendPath(conversationType).appendQueryParameter("targetId", targetId).build();
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    }
                }

                @Override
                public void onError(RongIMClient.ErrorCode e) {

                }
            });
        }
    }

    /**
     * 得到不落地 push 消息
     */
    private void getPushMessage() {
        Intent intent = getIntent();
        if (intent != null && intent.getData() != null && intent.getData().getScheme().equals("rong")) {
            String path = intent.getData().getPath();
            if (path.contains("push_message")) {
                SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
                String cacheToken = sharedPreferences.getString("loginToken", "");
                if (TextUtils.isEmpty(cacheToken)) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                } else {
                    if (!RongIM.getInstance().getCurrentConnectionStatus().equals(RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED)) {
                        DialogUtil.showProgress(activity);
                        RongIM.connect(cacheToken, new RongIMClient.ConnectCallback() {
                            @Override
                            public void onTokenIncorrect() {
                                DialogUtil.stopProgress(activity);
                            }

                            @Override
                            public void onSuccess(String s) {
                                DialogUtil.stopProgress(activity);
                            }

                            @Override
                            public void onError(RongIMClient.ErrorCode e) {
                                DialogUtil.stopProgress(activity);
                            }
                        });
                    }
                }
            }
        }
    }

    private void initLocation() {
        mLocationClient = new AMapLocationClient(getApplicationContext());
        mLocationOption = new AMapLocationClientOption();
        //设置返回地址信息，默认为true
        mLocationOption.setNeedAddress(true);
        //设置定位监听
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(final AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
//                        ToastUtil.showShort(aMapLocation.getCity());
                    } else {
//                        ToastUtil.showShort(aMapLocation.getErrorCode() + aMapLocation.getErrorInfo());
//                        ToastUtil.showShort("定位失败");
                    }
                }
            }
        });
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(1000 * 60 * 60);
        //设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
    }


    private void getRongYunToken() {
        if (!DbUtil.getUserInfo().getIsLogin()) {
            return;
        }
        RetrofitFactory.getUserApi()
                .getRongYunToken("getIMToken", "api", "Oauth",
                        DbUtil.getUserInfo().getOauth_token(), DbUtil.getUserInfo().getOauth_token_secret(),
                        DbUtil.getUserInfo().getUname(), DbUtil.getUserInfo().getUid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RongYunToken>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(RongYunToken rongYunToken) {
                        connect(rongYunToken.getToken());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void connect(final String token) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            /**
             * Token 错误。可以从下面两点检查
             * 1.Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             * 2.Token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            @Override
            public void onTokenIncorrect() {

            }

            /**
             * 连接融云成功
             * @param userid 当前 token 对应的用户 id
             */
            @Override
            public void onSuccess(String userid) {
//                ToastUtil.showShort(token);
                editor.putString(SealConst.SEALTALK_LOGIN_ID, userid);
                editor.commit();
                SealUserInfoManager.getInstance().openDB();
                getUserInfo(userid);
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }


    private void getUserInfo(final String userId) {
        RetrofitFactory.getUserApi()
                .getUserInfo("showCompanyInfo", "api", "User",
                        DbUtil.getUserInfo().getOauth_token(), DbUtil.getUserInfo().getOauth_token_secret(),
                        DbUtil.getUserInfo().getUid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CompanyInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(CompanyInfo userInfo) {
                        String nickName = userInfo.getUname();
                        String portraitUri = userInfo.getAvatar_middle();
                        editor.putString(SealConst.SEALTALK_LOGIN_NAME, nickName);
                        editor.putString(SealConst.SEALTALK_LOGING_PORTRAIT, portraitUri);
                        editor.commit();
                        RongIM.getInstance().refreshUserInfoCache(new UserInfo(userId, nickName, Uri.parse(portraitUri)));
                        //不继续在login界面同步好友,群组,群组成员信息
                        SealUserInfoManager.getInstance().getAllUserInfo();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getCompanyList() {
        RetrofitFactory.getUserApi()
                .getCompanyAddress()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CompanyResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(CompanyResult companyResult) {
                        if (companyResult.getStatus() == 1) {
                            list = new ArrayList<>();
                            list.addAll(companyResult.getResults());
                            for (CompanyResult.CompanyAddress address : list) {
                                MarkerOptions markerOption = new MarkerOptions();
                                markerOption.position(new LatLng(Double.valueOf(address
                                        .getLatitude()),
                                        Double.valueOf(address.getLongitude())));
                                markerOption.title("");
                                markerOption.anchor(0.5f, 0.81f);//设置锚点
                                markerOption.infoWindowEnable(true);

                                markerOption.icon(BitmapDescriptorFactory.fromBitmap
                                        (BitmapFactory.decodeResource
                                                (getResources(), R
                                                        .mipmap
                                                        .ic_location)));
                                Marker addMarker = mAMap.addMarker(markerOption);
                                addMarker.setObject(address);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLocationClient.startLocation();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mLocationClient.isStarted()) {
            mLocationClient.stopLocation();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        mLocationClient.onDestroy();
        mLocationClient = null;
        mLocationOption = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @OnClick({R.id.btnLogin, R.id.btnTab, R.id.btnChat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                LoginActivity.start(activity);
                break;
            case R.id.btnTab:
                TabActivity.start(activity);
                break;
            case R.id.btnChat:
                RongIM.getInstance().startPrivateChat(activity, "628", "标题");
                break;
        }
    }
}
