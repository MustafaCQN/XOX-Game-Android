package com.mustafacqn.xoxgame;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.huawei.hmf.tasks.Task;
import com.huawei.hms.support.hwid.HuaweiIdAuthManager;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper;
import com.huawei.hms.support.hwid.result.AuthHuaweiId;
import com.huawei.hms.support.hwid.service.HuaweiIdAuthService;
import com.mustafacqn.xoxgame.achievements.Achievements;

public class Opening extends AppCompatActivity {

    private final int SIGN_IN_INTENT = 1002;
    private final int ACHIEVEMENTS_INTENT = 1003;
    public HuaweiIdAuthService mAuthManager;
    public HuaweiIdAuthParams mAuthParam;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        initSignin();
    }

    public void initSignin() {
        mAuthParam = new HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM_GAME)
                .setIdToken()
                .createParams();
        mAuthManager = HuaweiIdAuthManager.getService(this, mAuthParam);
    }

    public void startGameClickHandler(View view) {
        startActivityForResult(mAuthManager.getSignInIntent(), SIGN_IN_INTENT);
    }

    public void achievementsClickHandler(View view) {
        startActivityForResult(mAuthManager.getSignInIntent(), ACHIEVEMENTS_INTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_INTENT){
            Task<AuthHuaweiId> authHuaweiIdTask = HuaweiIdAuthManager.parseAuthResultFromIntent(data);
            if (authHuaweiIdTask.isSuccessful()){
                AuthHuaweiId huaweiAccount = authHuaweiIdTask.getResult();
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("hw_account", huaweiAccount);
                startActivity(intent);
            }
        }else if (requestCode == ACHIEVEMENTS_INTENT) {
            Task<AuthHuaweiId> authHuaweiIdTask = HuaweiIdAuthManager.parseAuthResultFromIntent(data);
            if (authHuaweiIdTask.isSuccessful()){
                AuthHuaweiId huaweiAccount = authHuaweiIdTask.getResult();
                Intent intent = new Intent(this, Achievements.class);
                intent.putExtra("hw_account", huaweiAccount);
                startActivity(intent);
            }
        }
    }
}
