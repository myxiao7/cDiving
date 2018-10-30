package com.cdiving.cdiving.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cdiving.cdiving.R;
import com.cdiving.cdiving.base.BaseActivity;
import com.cdiving.cdiving.entity.RegisterStatus;
import com.cdiving.cdiving.http.RetrofitFactory;
import com.cdiving.cdiving.utils.RegexUtils;
import com.cdiving.cdiving.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.cdiving.cdiving.login.LoginActivity.REGISTER_REQUEST_CODE;

/**
 * 注册
 *
 * @author zhanghao
 * @date 2018-10-19.
 */

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.tvBack)
    TextView tvBack;
    @BindView(R.id.editEmail)
    EditText editEmail;
    @BindView(R.id.editAccount)
    EditText editAccount;
    @BindView(R.id.editPassword)
    EditText editPassword;
    @BindView(R.id.btnRegister)
    TextView btnRegister;
    @BindView(R.id.tvLogin)
    TextView tvLogin;
    @BindView(R.id.tvCompany)
    TextView tvCompany;
    @BindView(R.id.tvConnectUs)
    TextView tvConnectUs;

    public static void start(Activity context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivityForResult(intent, REGISTER_REQUEST_CODE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    private void register() {
        final String email = editEmail.getText().toString();
        final String account = editAccount.getText().toString();
        final String password = editPassword.getText().toString();
        if (!RegexUtils.isEmail(email)) {
            ToastUtil.showShort(R.string.email_hint);
            return;
        }
        if (TextUtils.isEmpty(account)) {
            ToastUtil.showShort(R.string.account_hint);
            return;
        }
        if (password.length() < 6 || password.length() > 15) {
            ToastUtil.showShort(R.string.register_password_hint);
            return;
        }

        RetrofitFactory.getUserApi()
                .register("register_email", "api", "Oauth", email, account, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<RegisterStatus>bindToLifecycle())
                .subscribe(new Observer<RegisterStatus>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RegisterStatus registerStatus) {
                        ToastUtil.showShort(registerStatus.getMsg());
                        if (registerStatus.getStatus() == 1) {
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putString("account", email);
                            bundle.putString("password", password);
                            intent.putExtra("info", bundle);
                            setResult(RESULT_OK, intent);
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

    @OnClick({R.id.tvBack, R.id.btnRegister, R.id.tvLogin, R.id.tvCompany, R.id.tvConnectUs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvBack:
                finish();
                break;
            case R.id.btnRegister:
                register();
                break;
            case R.id.tvLogin:
                LoginActivity.start(activity);
                break;
            case R.id.tvCompany:
                break;
            case R.id.tvConnectUs:
                break;
            default:
                break;
        }
    }
}
