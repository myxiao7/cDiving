package com.cdiving.cdiving;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.cdiving.cdiving.base.BaseActivity;
import com.cdiving.cdiving.entity.CompanyAddress;
import com.cdiving.cdiving.entity.CompanyIndexInfo;
import com.cdiving.cdiving.entity.CompanyInfo;
import com.cdiving.cdiving.entity.CompanyResult;
import com.cdiving.cdiving.entity.RongYunToken;
import com.cdiving.cdiving.entity.UserInfo;
import com.cdiving.cdiving.http.RetrofitFactory;
import com.cdiving.cdiving.im.SealConst;
import com.cdiving.cdiving.im.SealUserInfoManager;
import com.cdiving.cdiving.im.ui.activity.NewFriendListActivity;
import com.cdiving.cdiving.login.LoginActivity;
import com.cdiving.cdiving.rongyun.TabActivity;
import com.cdiving.cdiving.ui.UserCenterActivity;
import com.cdiving.cdiving.utils.DialogUtil;
import com.cdiving.cdiving.utils.ToastUtil;
import com.cdiving.cdiving.utils.db.DbUtil;
import com.cdiving.cdiving.views.SlideBottomLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
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
    @BindView(R.id.ivChat)
    ImageView ivChat;
    @BindView(R.id.tvChat)
    TextView tvChat;
    @BindView(R.id.ivContacts)
    ImageView ivContacts;
    @BindView(R.id.tvContacts)
    TextView tvContacts;
    @BindView(R.id.ivCollection)
    ImageView ivCollection;
    @BindView(R.id.handle)
    LinearLayout handle;
    @BindView(R.id.tvCompanyName)
    TextView tvCompanyName;
    @BindView(R.id.tvCompanyMobile)
    TextView tvCompanyMobile;
    @BindView(R.id.tvCompanyEmail)
    TextView tvCompanyEmail;
    @BindView(R.id.tvCompanyAddress)
    TextView tvCompanyAddress;
    @BindView(R.id.tvCompanyWebSite)
    TextView tvCompanyWebSite;
    @BindView(R.id.slideLayout)
    SlideBottomLayout slideLayout;
    @BindView(R.id.root)
    RelativeLayout root;
    @BindView(R.id.btnChats)
    TextView btnChats;
    @BindView(R.id.tvUserCenter)
    TextView tvUserCenter;
    @BindView(R.id.rvTitle)
    RelativeLayout rvTitle;
    @BindView(R.id.ivChat2)
    ImageView ivChat2;
    @BindView(R.id.tvChat2)
    TextView tvChat2;
    @BindView(R.id.ivContacts2)
    ImageView ivContacts2;
    @BindView(R.id.tvContacts2)
    TextView tvContacts2;
    @BindView(R.id.linTitle)
    LinearLayout linTitle;

    private AMap mAMap;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;

    private List<CompanyAddress> list;
    private Marker mSelectMarker;

    private static final String TAG = "MainActivity";

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        editor = sp.edit();
        if(!DbUtil.getUserInfo().getIsLogin()){
            LoginActivity.start(activity);
        }
        getRongYunToken();
        //获取地图控件引用
        mMapView = findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        if (mAMap == null) {
            mAMap = mMapView.getMap();
            initLocation();
        }
        getCompanyList();
        mAMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                final CompanyAddress companyAddress = (CompanyAddress) marker.getObject();
                if(mSelectMarker != null){
                    final CompanyAddress mSelectAddress = (CompanyAddress) mSelectMarker.getObject();
                    if(!companyAddress.getLatitude().equals(mSelectAddress.getLatitude()) && !companyAddress.getLongitude().equals(mSelectAddress.getLongitude())){
                        marker.setIcon(createMarkerIconNor(true));
                        mSelectMarker.setIcon(createMarkerIconNor(false));
                    }
                }else{
                    marker.setIcon(createMarkerIconNor(true));
                }
                //                marker.showInfoWindow();
                mSelectMarker = marker;
                linTitle.setVisibility(View.GONE);
                slideLayout.setVisibility(View.VISIBLE);
                ToastUtil.showLong("" + companyAddress.getUid());
                getCompanyInfo(companyAddress.getUid());

                return true;
            }
        });
    }

    private void initView() {

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
                        CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new
                                CameraPosition(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()
                        ), 3, 0, 0));
                        mAMap.moveCamera(mCameraUpdate);
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
                    public void onNext(CompanyInfo companyInfo) {
                        String nickName = companyInfo.getUname();
                        String portraitUri = companyInfo.getAvatar_middle();
                        String email = companyInfo.getEmail();
                        UserInfo userInfo = DbUtil.getUserInfo();
                        userInfo.setUname(nickName);
                        userInfo.setEmail(email);
                        userInfo.setPortrait(portraitUri);
                        DbUtil.updateUserInfo(userInfo);

                        editor.putString(SealConst.SEALTALK_LOGIN_NAME, nickName);
                        editor.putString(SealConst.SEALTALK_LOGING_PORTRAIT, portraitUri);
                        editor.commit();
                        RongIM.getInstance().refreshUserInfoCache(new io.rong.imlib.model.UserInfo(userId, nickName, Uri.parse(portraitUri)));
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
                            mAMap.clear();
                            list = new ArrayList<>();
                            list.addAll(companyResult.getResults());
                            DbUtil.insertCompanyList(list);
                            for (CompanyAddress address : list) {
                                MarkerOptions markerOption = new MarkerOptions();
                                markerOption.position(new LatLng(Double.valueOf(address
                                        .getLatitude()),
                                        Double.valueOf(address.getLongitude())));
                                /*markerOption.title("");
                                markerOption.anchor(0.5f, 0.81f);//设置锚点
                                markerOption.infoWindowEnable(false);*/

                                markerOption.icon(createMarkerIconNor(false));
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

    private void getCompanyInfo(String userId) {
        RetrofitFactory.getUserApi()
                .getCompanyInfo(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CompanyIndexInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(CompanyIndexInfo companyIndexInfo) {
                        if (companyIndexInfo.getStatus() == 1) {
                            CompanyIndexInfo.ResultsBean resultsBean = companyIndexInfo.getResults().get(0);
                            tvCompanyName.setText(resultsBean.getUname());
                            tvCompanyMobile.setText(resultsBean.getTel());
                            tvCompanyEmail.setText(resultsBean.getEmail());
                            tvCompanyAddress.setText(resultsBean.getAddress());
                            tvCompanyWebSite.setText(resultsBean.getWebSite());
                            if (!slideLayout.arriveTop()) {
                                slideLayout.show();
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

    /**
     * Marker图标
     * @param selector
     * @return
     */
    private BitmapDescriptor createMarkerIconNor(boolean selector) {
        if (selector) {
            return BitmapDescriptorFactory.fromBitmap
                    (BitmapFactory.decodeResource
                            (getResources(), R
                                    .mipmap
                                    .ic_location_selector));
        } else {
            return BitmapDescriptorFactory.fromBitmap
                    (BitmapFactory.decodeResource
                            (getResources(), R
                                    .mipmap
                                    .ic_location_normal));
        }
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


    @OnClick({R.id.btnLogin, R.id.btnTab, R.id.btnChat, R.id.ivChat2, R.id.tvChat2, R.id.ivContacts2, R.id.tvContacts2, R.id.ivChat, R.id.tvChat, R.id.ivContacts, R.id.tvContacts, R.id.ivCollection, R.id.tvCompanyMobile, R.id.tvCompanyEmail, R.id.btnChats, R.id.tvUserCenter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                LoginActivity.start(activity);
                break;
            case R.id.btnTab:
//                TabActivity.start(activity);
                break;
            case R.id.btnChat:
                RongIM.getInstance().startPrivateChat(activity, "628", "标题");
                break;
            case R.id.ivChat2:
                startChatOrContact(0);
                break;
            case R.id.tvChat2:
                startChatOrContact(0);
                break;
            case R.id.ivContacts2:
                startChatOrContact(1);
                break;
            case R.id.tvContacts2:
                startChatOrContact(1);
                break;
            case R.id.ivChat:
                startChatOrContact(0);
                break;
            case R.id.tvChat:
                startChatOrContact(0);
                break;
            case R.id.ivContacts:
                startChatOrContact(1);
                break;
            case R.id.tvContacts:
                startChatOrContact(1);
                break;
            case R.id.ivCollection:
                break;

            case R.id.tvCompanyMobile:
                ToastUtil.showShort("call");
                break;
            case R.id.tvCompanyEmail:
                ToastUtil.showShort("eMail");
                break;

            case R.id.btnChats:
                final CompanyAddress companyAddress = (CompanyAddress) mSelectMarker.getObject();
                RongIM.getInstance().startPrivateChat(activity, companyAddress.getUid(), "标题");
                break;
            case R.id.tvUserCenter:
                UserCenterActivity.start(activity);
                break;
        }
    }

    private void startChatOrContact(int type) {
        if (DbUtil.getUserInfo().getIsLogin()) {
            TabActivity.start(activity, type);
        } else {
            LoginActivity.start(activity);
        }
    }
}
