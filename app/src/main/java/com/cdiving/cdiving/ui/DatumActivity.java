package com.cdiving.cdiving.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
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
import com.cdiving.cdiving.R;
import com.cdiving.cdiving.base.BaseActivity;
import com.cdiving.cdiving.entity.CompanyAddress;
import com.cdiving.cdiving.entity.CompanyInfo;
import com.cdiving.cdiving.http.RetrofitFactory;
import com.cdiving.cdiving.utils.MobileUtils;
import com.cdiving.cdiving.utils.ToastUtil;
import com.cdiving.cdiving.utils.db.DbUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;

/**
 * 资料
 *
 * @author zhanghao
 * @date 2018-11-19.
 */

public class DatumActivity extends BaseActivity {
    MapView mMapView = null;
    @BindView(R.id.map)
    MapView map;
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
    @BindView(R.id.btnChats)
    TextView btnChats;
    @BindView(R.id.tvBack)
    TextView tvBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    private AMap mAMap;
    private Marker mSelectMarker;
    private String userId;
    private CompanyInfo mSelectInfo;//登录

    public static void start(Context context, String userId) {
        Intent intent = new Intent(context, DatumActivity.class);
        intent.putExtra("userId", userId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datum);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.datum);
        tvBack.setText(R.string.back);
        //获取地图控件引用
        mMapView = findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        if (mAMap == null) {
            mAMap = mMapView.getMap();
        }
        userId = this.getIntent().getStringExtra("userId");
        getCompanyDetail2(userId);
    }

    /**
     * 获取公司详情（已登录情况）
     *
     * @param userId
     */
    private void getCompanyDetail2(final String userId) {
        RetrofitFactory.getUserApi()
                .getUserInfo("showCompanyInfo", "api", "User",
                        DbUtil.getUserInfo().getOauth_token(), DbUtil.getUserInfo().getOauth_token_secret(),
                        userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CompanyInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(CompanyInfo companyInfo) {
                        companyInfo.setIs_follow(companyInfo.getFollow_state().getFollowing());
                        DbUtil.UpdateCompanyDetail2(companyInfo, userId);
                        mSelectInfo = companyInfo;
                        tvCompanyName.setText(companyInfo.getUname());
                        if (companyInfo.getTel().equals(companyInfo.getEmail())) {
                            if (companyInfo.getEmail().contains("@")) {
                                tvCompanyMobile.setText("");
                                tvCompanyEmail.setText(companyInfo.getEmail());
                            } else {
                                tvCompanyMobile.setText(companyInfo.getTel());
                                tvCompanyEmail.setText("");
                            }
                        } else {
                            tvCompanyMobile.setText(companyInfo.getTel());
                            tvCompanyEmail.setText(companyInfo.getEmail());
                        }
                        tvCompanyAddress.setText(companyInfo.getAddress());
                        tvCompanyWebSite.setText(companyInfo.getWebsite());

                        CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new
                                CameraPosition(new LatLng(Double.parseDouble(companyInfo.getLatitude()), Double.parseDouble(companyInfo.getLongitude())
                        ), 3, 0, 0));
                        mAMap.moveCamera(mCameraUpdate);
                        MarkerOptions markerOption = new MarkerOptions();
                        markerOption.position(new LatLng(Double.valueOf(companyInfo
                                .getLatitude()),
                                Double.valueOf(companyInfo.getLongitude())));
                                /*markerOption.title("");
                                markerOption.anchor(0.5f, 0.81f);//设置锚点
                                markerOption.infoWindowEnable(false);*/

                        markerOption.icon(createMarkerIconNor());
                        Marker addMarker = mAMap.addMarker(markerOption);
                        addMarker.setObject(companyInfo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        CompanyInfo companyInfo = DbUtil.getCompanyDetail2(userId);
                        if (companyInfo == null) {
                            return;
                        }
                        mSelectInfo = companyInfo;
                        tvCompanyName.setText(companyInfo.getUname());
                        if (companyInfo.getTel().equals(companyInfo.getEmail())) {
                            if (companyInfo.getEmail().contains("@")) {
                                tvCompanyMobile.setText("");
                                tvCompanyEmail.setText(companyInfo.getEmail());
                            } else {
                                tvCompanyMobile.setText(companyInfo.getTel());
                                tvCompanyEmail.setText("");
                            }
                        } else {
                            tvCompanyMobile.setText(companyInfo.getTel());
                            tvCompanyEmail.setText(companyInfo.getEmail());
                        }
                        tvCompanyAddress.setText(companyInfo.getAddress());
                        tvCompanyWebSite.setText(companyInfo.getWebsite());
                        CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new
                                CameraPosition(new LatLng(Double.parseDouble(companyInfo.getLatitude()), Double.parseDouble(companyInfo.getLongitude())
                        ), 3, 0, 0));
                        mAMap.moveCamera(mCameraUpdate);
                        MarkerOptions markerOption = new MarkerOptions();
                        markerOption.position(new LatLng(Double.valueOf(companyInfo
                                .getLatitude()),
                                Double.valueOf(companyInfo.getLongitude())));
                                /*markerOption.title("");
                                markerOption.anchor(0.5f, 0.81f);//设置锚点
                                markerOption.infoWindowEnable(false);*/

                        markerOption.icon(createMarkerIconNor());
                        Marker addMarker = mAMap.addMarker(markerOption);
                        addMarker.setObject(companyInfo);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @OnClick({R.id.tvCompanyMobile, R.id.tvCompanyEmail, R.id.tvCompanyWebSite, R.id.btnChats, R.id.tvBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvCompanyMobile:
                showCallDialog();
                break;
            case R.id.tvCompanyEmail:
                sendEmail();
                break;
            case R.id.tvCompanyWebSite:
                break;
            case R.id.tvBack:
                finish();
                break;
            case R.id.btnChats:
                if (mSelectInfo != null) {
                    if (mSelectInfo.getIs_member() == 1) {
                        final CompanyAddress companyAddress = (CompanyAddress) mSelectMarker.getObject();
                        RongIM.getInstance().startPrivateChat(activity, companyAddress.getUid(), mSelectInfo.getUname());
                    } else {
                        showContactDialog();
                    }
                }

                break;
        }
    }

    /**
     * 联系人对话框
     */
    private void showContactDialog() {
        new MaterialDialog
                .Builder(activity)
                .title(R.string.contact_title)
                .items(R.array.contact_tab)
                .itemsColor(getResources().getColor(R.color.blue))
                .titleGravity(GravityEnum.CENTER)
                .itemsGravity(GravityEnum.CENTER)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        switch (position) {
                            case 0:
                                showCallDialog();
                                break;
                            case 1:
                                sendEmail();
                                break;
                            case 2:
                                break;
                            default:
                                break;
                        }
                    }
                })
                .show();
    }

    /**
     * 打电话对话框
     */
    private void showCallDialog() {
        if (DbUtil.getUserInfo().getIsLogin()) {
            if (!TextUtils.isEmpty(mSelectInfo.getTel())) {
                new MaterialDialog
                        .Builder(activity)
                        .title(R.string.call)
                        .inputType(InputType.TYPE_CLASS_PHONE)
                        .input(getResources().getString(R.string.call_hint), mSelectInfo.getTel(), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {

                            }
                        }).positiveText(R.string.call)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                String phone = dialog.getInputEditText().getText().toString();
                                if (phone.contains("F")) {
                                    phone = phone.substring(0, phone.indexOf("F"));
                                }
                                ToastUtil.showShort(phone);
                                if (!TextUtils.isEmpty(phone)) {
                                    MobileUtils.call(activity, phone);
                                }
                            }
                        }).negativeText(R.string.cancel)
                        .show();
            }
        }
    }

    /**
     * 发送邮件
     */
    private void sendEmail() {
        if (DbUtil.getUserInfo().getIsLogin()) {
            if (!TextUtils.isEmpty(mSelectInfo.getEmail())) {
                Uri uri = Uri.parse("mailto:" + mSelectInfo.getEmail());
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra(Intent.EXTRA_SUBJECT, "cdiving"); // 主题
                intent.putExtra(Intent.EXTRA_TEXT, "cdiving"); // 正文
                startActivity(Intent.createChooser(intent, "请选择邮件类应用"));
            }
        }
    }

    /**
     * Marker图标
     *
     * @return
     */
    private BitmapDescriptor createMarkerIconNor() {
        return BitmapDescriptorFactory.fromBitmap
                (BitmapFactory.decodeResource
                        (getResources(), R
                                .mipmap
                                .ic_location_normal2));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
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

}
