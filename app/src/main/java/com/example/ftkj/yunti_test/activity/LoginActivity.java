package com.example.ftkj.yunti_test.activity;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ftkj.yunti_test.MainActivity;
import com.example.ftkj.yunti_test.R;
import com.example.ftkj.yunti_test.base.BaseActivity;
import com.example.ftkj.yunti_test.utils.SharedPreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.edit_account)
    EditText mEditAccount;
    @BindView(R.id.edit_pwd)
    EditText mEditPwd;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    private SharedPreferencesUtil mSharedPreferencesUtil;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        setHasStatusBar(false);
        Observable.combineLatest(initEditTextObserver(mEditAccount), initEditTextObserver(mEditPwd), new BiFunction<String, String, Boolean>() {
            @Override
            public Boolean apply(String account, String password) throws Exception {
                return isAccount(account) && isPassword(password);
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean verify) throws Exception {
                Log.d("AAA", "accept: " + verify);
                mBtnLogin.setEnabled(verify);
            }
        });
        mEditAccount.setText(mSharedPreferencesUtil.getString("account",""));
        mEditPwd.setText(mSharedPreferencesUtil.getString("password",""));
    }

    private boolean isPassword(String password) {
        return password.length() >= 6;
    }

    private boolean isAccount(String account) {
        return account.length() >= 5;
    }

    private Observable<String> initEditTextObserver(final EditText editText) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> e) throws Exception {
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        e.onNext(s.toString());
                    }
                });
            }
        });
    }

    @Override
    protected void initData() {
        mSharedPreferencesUtil = SharedPreferencesUtil.getInstance();
    }

    @Override
    protected void initListener() {

    }


    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        String account = mEditAccount.getText().toString().trim();
        String password = mEditPwd.getText().toString().trim();
        if ("admin".equals(account) && "123456".equals(password)){
            mSharedPreferencesUtil.putString("account",account);
            mSharedPreferencesUtil.putString("password",password);
            Toast.makeText(this, "欢迎您，"+ account, Toast.LENGTH_SHORT).show();
            go(MainActivity.class);
            finish();
        }else {
            showToast("请检查账号密码后登陆");
        }


    }
}
