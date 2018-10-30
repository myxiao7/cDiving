package com.cdiving.cdiving.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cdiving.cdiving.MainActivity;
import com.cdiving.cdiving.R;
import com.cdiving.cdiving.base.BaseActivity;
import com.cdiving.cdiving.entity.UserInfo;
import com.cdiving.cdiving.http.RetrofitFactory;
import com.cdiving.cdiving.utils.EncryptUtils;
import com.cdiving.cdiving.utils.RegexUtils;
import com.cdiving.cdiving.utils.ToastUtil;
import com.cdiving.cdiving.utils.db.DbUtil;

import org.greenrobot.greendao.DbUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * @author zhanghao
 * @date 2018-10-19.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.tvBack)
    TextView tvBack;
    @BindView(R.id.editAccount)
    EditText editAccount;
    @BindView(R.id.editPassword)
    EditText editPassword;
    @BindView(R.id.btnLogin)
    TextView btnLogin;
    @BindView(R.id.tvRegister)
    TextView tvRegister;
    @BindView(R.id.tvConnectUs)
    TextView tvConnectUs;

    private static final String TAG = "LoginActivity";

    public static final int REGISTER_REQUEST_CODE = 154;
    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tvBack, R.id.btnLogin, R.id.tvRegister, R.id.tvConnectUs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvBack:
                break;
            case R.id.btnLogin:
                login();
                break;
            case R.id.tvRegister:
                RegisterActivity.start(activity);
                break;
            case R.id.tvConnectUs:
                break;
            default:
                break;
        }
    }

    private void login(){
        String account = editAccount.getText().toString();
        String password = editPassword.getText().toString();
        if(TextUtils.isEmpty(account)){
            ToastUtil.showShort(R.string.login_account_hint);
            return;
        }
        if(TextUtils.isEmpty(password)){
            ToastUtil.showShort(R.string.login_password_hint);
            return;
        }

        RetrofitFactory.getUserApi()
                .login("authorize_new", "api", "Oauth", account, EncryptUtils.encryptMD5ToString(password).toLowerCase())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<UserInfo>bindToLifecycle())
                .subscribe(new Observer<UserInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                        if(userInfo.getCode() != 0){
                            ToastUtil.showShort(userInfo.getMessage());
                        }else{
                            userInfo.setIsLogin(true);
                            DbUtil.updateUserInfo(userInfo);
                            finish();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REGISTER_REQUEST_CODE && resultCode == RESULT_OK){
            Bundle bundle = data.getBundleExtra("info");
            editAccount.setText(bundle.getString("account"));
            editPassword.setText(bundle.getString("password"));
        }
    }
}
