package com.example.vkapifriends.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.vkapifriends.presenters.LoginPresenter
import com.example.vkapifriends.views.LoginView
import com.github.rahatarmanahmed.cpv.CircularProgressView
import com.example.vkapifriends.R
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk
import com.vk.sdk.util.VKUtil


class LoginActivity : MvpAppCompatActivity(), LoginView {
//    private val tagger: String = LoginActivity::class.java.simpleName

    @InjectPresenter
    lateinit var loginPresenter: LoginPresenter

    private lateinit var mTxtLoginWelcome: TextView
    private lateinit var mCpvWait: CircularProgressView
    private lateinit var mBtnEnter: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTxtLoginWelcome = findViewById(R.id.txt_login_welcome)
        mBtnEnter = findViewById(R.id.btn_login_enter)
        mCpvWait = findViewById(R.id.cpv_login)

        mBtnEnter.setOnClickListener {
            //loginPresenter.login(isSuccess = true)
            VKSdk.login(this@LoginActivity, VKScope.FRIENDS)
        }

//        val fingerprints = VKUtil.getCertificateFingerprint(this, this.packageName)
//        if(fingerprints != null)
//            Log.e(tagger, "fingerprint ${fingerprints!![0]}")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(!loginPresenter.loginVk(requestCode = requestCode, resultCode = resultCode, data = data)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun startLoading() {
        mBtnEnter.visibility = View.GONE
        mCpvWait.visibility = View.VISIBLE
    }

    override fun endLoading() {
        mBtnEnter.visibility = View.VISIBLE
        mCpvWait.visibility = View.GONE
    }

    override fun showError(textResource: Int) {
        Toast.makeText(applicationContext, getString(textResource), Toast.LENGTH_SHORT).show()
    }

    override fun openFriends() {
        startActivity(Intent(applicationContext, FriendsActivity::class.java))
    }
}
