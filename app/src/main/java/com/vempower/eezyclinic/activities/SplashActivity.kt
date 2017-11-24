package com.vempower.eezyclinic.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import com.vempower.eezyclinic.R
import com.vempower.eezyclinic.utils.Constants
import com.vempower.eezyclinic.utils.SharedPreferenceUtils

/**
 * Created by Satish on 11/15/2017.
 */
class SpalshActivity : AbstractFragmentActivity() {

    private val SPLASH_DISPLAY_LENGTH = 2000
    // private var intent: Intent? = null
    //private var intent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spalsh)
        init();
    }

    private fun init() {

        Handler().postDelayed({
            var intent: Intent? = null
            //SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY, loginAPI.getAccessToken())

            //intent = Intent(this, AppTourActivity::class.java);
            when(!TextUtils.isEmpty(SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY,null)))
            {
                true->intent = Intent(this, HomeActivity::class.java);

                false ->intent = Intent(this, AppTourActivity::class.java);

            }
            //val intent = Intent(this, AppTourActivity::class.java)

            this.startActivity(intent)
            this.finish()
        }, SPLASH_DISPLAY_LENGTH.toLong())

    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}