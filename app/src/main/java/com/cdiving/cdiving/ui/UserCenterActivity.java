package com.cdiving.cdiving.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cdiving.cdiving.R;
import com.cdiving.cdiving.base.BaseActivity;
import com.cdiving.cdiving.entity.CompanyInfo;
import com.cdiving.cdiving.entity.UserInfo;
import com.cdiving.cdiving.http.RetrofitFactory;
import com.cdiving.cdiving.im.SealConst;
import com.cdiving.cdiving.im.SealUserInfoManager;
import com.cdiving.cdiving.utils.db.DbUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 个人中心
 *
 * @author zhanghao
 * @date 2018-11-01.
 */

public class UserCenterActivity extends BaseActivity {

    @BindView(R.id.tvBack)
    TextView tvBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.rvTitle)
    RelativeLayout rvTitle;
    @BindView(R.id.ivPortrait)
    ImageView ivPortrait;
    @BindView(R.id.linPortrait)
    LinearLayout linPortrait;
    @BindView(R.id.tvAccount)
    TextView tvAccount;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.linConnectUs)
    LinearLayout linConnectUs;
    @BindView(R.id.btnLogout)
    TextView btnLogout;

    private List<LocalMedia> selectList;

    public static void start(Context context) {
        Intent intent = new Intent(context, UserCenterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.setting);
        initData();
        getUserInfo();
    }

    private void initData() {
        UserInfo userInfo = DbUtil.getUserInfo();
        RequestOptions requestOptions = RequestOptions.centerCropTransform().placeholder(R.mipmap.ic_chat).error(R.mipmap.ic_chat);
        Glide.with(activity).load(userInfo).apply(requestOptions).into(ivPortrait);
        tvAccount.setText(userInfo.getUname());
        tvEmail.setText(userInfo.getEmail());
    }
    /**
     * 选择头像
     */
    private void openGallery() {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())
                .isCamera(true)
                .maxSelectNum(1)
                .previewImage(true)
                .compress(true)
                .compressGrade(Luban.CUSTOM_GEAR)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    private void getUserInfo() {
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
     * 上传头像
     */
    private void uploadImage(File file){
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("avatar", file.getName(), requestBody);
      /*  RetrofitFactory.getUserApi()
                .uploadImage(part)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<BaseResult<UploadImage>>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseDataObserver<UploadImage>(activity) {
                    @Override
                    protected void onSuccess(UploadImage data) {
                        icon = data.getUrl();
                        RequestOptions requestOptions = RequestOptions.circleCropTransform().error(R.mipmap.ic_icon);
                        Glide.with(activity).load(icon).apply(requestOptions).into(ivIcon);
                        //清除图片处理缓存
                        PictureFileUtils.deleteCacheDirFile(activity);
                    }
                });*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AppCompatActivity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    for (int i = 0; i < selectList.size(); i++) {
                        File file = new File(selectList.get(i).getCompressPath());
                        uploadImage(file);
                    }
                    break;
                    default:
                        break;
            }
        }

    }

    @OnClick({R.id.tvBack, R.id.linPortrait, R.id.linConnectUs, R.id.btnLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvBack:
                finish();
                break;
            case R.id.linPortrait:
                openGallery();
                break;
            case R.id.linConnectUs:
                break;
            case R.id.btnLogout:
                break;
        }
    }
}
