package com.vempower.eezyclinic.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.vempower.eezyclinic.R

/**
 * Created by Satish on 11/15/2017.
 */
class SpalshActivity : AbstractFragmentActivity() {

    private val SPLASH_DISPLAY_LENGTH = 3000
    // private var intent: Intent? = null
    //private var intent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spalsh)
        init();
    }

    private fun init() {

        Handler().postDelayed({
           // var intent: Intent? = null
            //intent = Intent(this, AppTourActivity::class.java);
       /*     when(SharedPreferenceUtils.getBooleanValueFromSharedPrefarence(Constants.IS_WATCH_APP_TOUR_KEY,false))
            {
                true->intent = Intent(this, HomeActivity::class.java);

                false ->intent = Intent(this, AppTourActivity::class.java);

            }*/
            val intent = Intent(this, AppTourActivity::class.java)

            this.startActivity(intent)
            this.finish()
        }, SPLASH_DISPLAY_LENGTH.toLong())

    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}