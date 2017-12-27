package com.vempower.stashdealcustomer.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.rey.material.app.DialogFragment
import com.rey.material.app.SimpleDialog
import com.vempower.eezyclinic.R
import com.vempower.eezyclinic.adapters.AccountListAdapter
import com.vempower.eezyclinic.application.MyApplication
import com.vempower.eezyclinic.core.AdapterItem
import com.vempower.eezyclinic.interfaces.AccountListDialogInterface
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface
import com.vempower.eezyclinic.utils.Utils
import com.vempower.eezyclinic.views.MyTextViewRR
import java.util.*

/**
 * Created by Satishk on 8/10/2017.
 */

abstract class AbstractActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.setCurrentActivityContext(this)

        //FirebaseApp.initializeApp(this)
        //FirebaseCrash.report(Exception("My first Android non-fatal error"));
    }

    override fun onStart() {
        super.onStart()
        MyApplication.setCurrentActivityContext(this)
    }

    override fun onRestart() {
        super.onRestart()
        MyApplication.setCurrentActivityContext(this)
    }

    override fun onResume() {
        super.onResume()
        MyApplication.setCurrentActivityContext(this)
    }


    protected fun showToastMessage(id: Int) {
        if (id <= 0) {
            return
        }
        showToastMessage(Utils.getStringFromResources(id))
        //Log.i(AbstractActivity.class.getName(), message);
    }

    protected fun showToastMessage(message: String?) {
        if (message == null) {
            return
        }
        Log.i(AbstractActivity::class.java.name, message)
        Utils.showToastMsg(this, message)
    }


    fun showAlertDialog(title: String, msg: String, isFinish: Boolean) {
        showAlertDialog(title, msg, isFinish, null)
    }

    /*private fun test(intents: Array<Intent>) {

    }*/

    protected fun showAlertDialog(title: String, msg: String, isFinish: Boolean, intents: Array<Intent>) {

        val alertDialog = AlertDialog.Builder(MyApplication.getCurrentActivityContext())

        // Setting Dialog Title
        alertDialog.setTitle(if (TextUtils.isEmpty(title)) "" else title)

        // Setting Dialog Message
        alertDialog.setMessage(msg)

        // setting Dialog cancelable to false 9010864578
        alertDialog.setCancelable(false)

        // On pressing Settings button
        alertDialog.setPositiveButton(Utils.getStringFromResources(R.string.ok_label)
        ) { dialog, which ->
            dialog.dismiss()


            if (intents != null) {
                startActivities(intents)
            }
            if (isFinish) {
                finish()
            }
        }


        alertDialog.show()

    }



    protected fun showAlertDialog(title: String, msg: String, isFinish: Boolean, intent: Intent?) {

        val alertDialog = AlertDialog.Builder(MyApplication.getCurrentActivityContext())

        // Setting Dialog Title
        alertDialog.setTitle(if (TextUtils.isEmpty(title)) "" else title)

        // Setting Dialog Message
        alertDialog.setMessage(msg)

        // setting Dialog cancelable to false 9010864578
        alertDialog.setCancelable(false)

        // On pressing Settings button
        alertDialog.setPositiveButton(Utils.getStringFromResources(R.string.ok_label)) { dialog, which ->
            dialog.dismiss()


            if (intent != null) {
                startActivity(intent)
            }
            if (isFinish) {
                finish()
            }
        }


        alertDialog.show()

    }
    protected fun showMyAlertDialog(title: String, message: String,buttonName: String, isFinish: Boolean) {
        val builder = object : SimpleDialog.Builder(R.style.SimpleDialogLight) {
            override fun onPositiveActionClicked(fragment: DialogFragment) {
                //Toast.makeText(mActivity, "Agreed", Toast.LENGTH_SHORT).show();
                super.onPositiveActionClicked(fragment)

                if(isFinish)
                {
                    finish();
                }

                //dialogInterface?.retryClick()

            }

            /*override fun onNegativeActionClicked(fragment: DialogFragment) {
                //Toast.makeText(mActivity, "Disagreed", Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment)
                dialogInterface?.onCloseClick()
            }*/
        }

        (builder as SimpleDialog.Builder).message(message)
                .title(title)
                .positiveAction(buttonName)
               // .negativeAction("Close")

        val fragment = DialogFragment.newInstance(builder)
        //fragment.show(this.fragmentManager,"")
        fragment?.show(supportFragmentManager, null)
    }

    protected fun showMyDialog(title: String, message: String, positiveButtonName: String, dialogInterface: ApiErrorDialogInterface?) {
        val builder = object : SimpleDialog.Builder(R.style.SimpleDialogLight) {
            override fun onPositiveActionClicked(fragment: DialogFragment) {
                //Toast.makeText(mActivity, "Agreed", Toast.LENGTH_SHORT).show();
                super.onPositiveActionClicked(fragment)
                dialogInterface?.retryClick()
            }

            /*@Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                //Toast.makeText(mActivity, "Disagreed", Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment);
                if(dialogInterface!=null) {
                    dialogInterface.onCloseClick();
                }
            }*/
        }

        (builder as SimpleDialog.Builder).message(message)
                .title(title)
                .positiveAction(positiveButtonName)
        // .negativeAction("Close");
        val fragment = DialogFragment.newInstance(builder)
        fragment.isCancelable = false
        fragment.show(supportFragmentManager, null)
    }


    protected fun showMyDialog(title: String, message: String, dialogInterface: ApiErrorDialogInterface?) {
        val builder = object : SimpleDialog.Builder(R.style.SimpleDialogLight) {
            override fun onPositiveActionClicked(fragment: DialogFragment) {
                //Toast.makeText(mActivity, "Agreed", Toast.LENGTH_SHORT).show();
                super.onPositiveActionClicked(fragment)

                dialogInterface?.retryClick()

            }

            override fun onNegativeActionClicked(fragment: DialogFragment) {
                //Toast.makeText(mActivity, "Disagreed", Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment)
                dialogInterface?.onCloseClick()
            }
        }

        (builder as SimpleDialog.Builder).message(message)
                .title(title)
                .positiveAction(Utils.getStringFromResources(R.string.retry_lbl))
                .negativeAction(Utils.getStringFromResources(R.string.close_lbl))//"Close")


        val fragment = DialogFragment.newInstance(builder)
        //fragment.show(this.fragmentManager,"")
        fragment?.show(supportFragmentManager, null)
    }

    protected fun showMyDialog(title: String, message: String,  positiveBtnName: String="Retry",negativeBtnName: String="Close", dialogInterface: ApiErrorDialogInterface?) {
        val builder = object : SimpleDialog.Builder(R.style.SimpleDialogLight) {
            override fun onPositiveActionClicked(fragment: DialogFragment) {
                //Toast.makeText(mActivity, "Agreed", Toast.LENGTH_SHORT).show();
                super.onPositiveActionClicked(fragment)

                dialogInterface?.retryClick()

            }

            override fun onNegativeActionClicked(fragment: DialogFragment) {
                //Toast.makeText(mActivity, "Disagreed", Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment)
                dialogInterface?.onCloseClick()
            }
        }


        (builder as SimpleDialog.Builder).message(message)
                .title(title)
                .positiveAction(positiveBtnName)
                .negativeAction(negativeBtnName)

        val fragment = DialogFragment.newInstance(builder)
        //fragment.show(this.fragmentManager,"")
        fragment?.show(supportFragmentManager, null)
    }
    protected fun showMyDialog(title: String, message: String,isFinish: Boolean) {
        showMyDialog(title, message,"Ok",isFinish, null)

    }
    protected fun showMyDialog(title: String, message: String,isFinish: Boolean, intent: Intent) {
       showMyDialog(title, message,"Ok",isFinish, intent)

        }
        protected fun showMyDialog(title: String, message: String, buttonName: String="Ok",isFinish: Boolean, intent: Intent?=null) {
        val builder = object : SimpleDialog.Builder(R.style.SimpleDialogLight) {
            override fun onPositiveActionClicked(fragment: DialogFragment) {
                //Toast.makeText(mActivity, "Agreed", Toast.LENGTH_SHORT).show();
                super.onPositiveActionClicked(fragment)
                if (intent != null) {
                    startActivity(intent)
                }
                if (isFinish) {
                    finish()
                }
                //dialogInterface?.retryClick()

            }

            /*override fun onNegativeActionClicked(fragment: DialogFragment) {
                //Toast.makeText(mActivity, "Disagreed", Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment)
                dialogInterface?.onCloseClick()
            }*/
        }

        (builder as SimpleDialog.Builder).message(message)
                .title(title)
                .positiveAction(buttonName)
        // .negativeAction("Close")

        val fragment = DialogFragment.newInstance(builder)
        //fragment.show(this.fragmentManager,"")
        fragment?.show(supportFragmentManager, null)
    }

    protected fun showMyDialog(title: String, message: String,isFinish: Boolean, intents: Array<Intent>) {
        val builder = object : SimpleDialog.Builder(R.style.SimpleDialogLight) {
            override fun onPositiveActionClicked(fragment: DialogFragment) {
                //Toast.makeText(mActivity, "Agreed", Toast.LENGTH_SHORT).show();
                super.onPositiveActionClicked(fragment)
                if (intents != null) {
                    startActivities(intents)
                }
                if (isFinish) {
                    finish()
                }
                //dialogInterface?.retryClick()

            }

            /*override fun onNegativeActionClicked(fragment: DialogFragment) {
                //Toast.makeText(mActivity, "Disagreed", Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment)
                dialogInterface?.onCloseClick()
            }*/
        }

        (builder as SimpleDialog.Builder).message(message)
                .title(title)
                .positiveAction("Ok")
               // .negativeAction("Close")

        val fragment = DialogFragment.newInstance(builder)
        //fragment.show(this.fragmentManager,"")
        fragment?.show(supportFragmentManager, null)
    }

    protected fun showMyDialog1(title: String, message: String,  positiveBtnName: String="Ok", dialogInterface: ApiErrorDialogInterface?) {
        val builder = object : SimpleDialog.Builder(R.style.SimpleDialogLight) {
            override fun onPositiveActionClicked(fragment: DialogFragment) {
                //Toast.makeText(mActivity, "Agreed", Toast.LENGTH_SHORT).show();
                super.onPositiveActionClicked(fragment)

                dialogInterface?.retryClick()

            }

         /*   override fun onNegativeActionClicked(fragment: DialogFragment) {
                //Toast.makeText(mActivity, "Disagreed", Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment)
                dialogInterface?.onCloseClick()
            }*/
        }


        (builder as SimpleDialog.Builder).message(message)
                .title(title)
                .positiveAction(positiveBtnName)
                //.negativeAction(negativeBtnName)

        val fragment = DialogFragment.newInstance(builder)
        //fragment.show(this.fragmentManager,"")
        fragment?.show(supportFragmentManager, null)
    }



    @SuppressLint("StringFormatInvalid")
    protected fun showAccountListDialog (isFromEmail:Boolean, accountList :List<AdapterItem>, accountListener: AccountListDialogInterface?) {
        val alertDialog = AlertDialog.Builder(MyApplication.getCurrentActivityContext())
        val view = layoutInflater.inflate(R.layout.account_list_dialog_layout, null)
        //mail Id / mobile number
        val recylerview = view.findViewById<android.support.v7.widget.RecyclerView>(R.id.dialog_recylerview)
        var adapter1 = AccountListAdapter(this, accountList)
        recylerview.adapter = adapter1
        recylerview.layoutManager= LinearLayoutManager(this)
        var isFrom="";
        when(isFromEmail)
        {
            true ->isFrom="mail Id";
            false ->isFrom="mobile number";
        }

        val header_text=view.findViewById<MyTextViewRR>(R.id.header_text)

        val headerText = resources.getString(R.string.account_list_dialog_header_msg, isFrom)
        header_text.setText(headerText)


        alertDialog.setView(view)

        /*val tv= view.findViewById<TextView>(R.id.text_1)
        tv.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                showToastMessage("Now click view")
            }})*/

        // Setting Dialog Title
        // setting Dialog cancelable to false 9010864578
        alertDialog.setCancelable(false)

        // On pressing Settings button
        alertDialog.setPositiveButton(Utils.getStringFromResources(R.string.ok_label)
        ) { dialog, which ->
            // dialog.dismiss()
            //TODO something
           // showToastMessage("Selected item :" + adapter1.mSelectedItem);
            if(accountListener!=null)
            {
                accountListener.okClick(adapter1.mSelectedItem)
            }

        }


            alertDialog.setNegativeButton(Utils.getStringFromResources(R.string.cancel_lbl)) { dialog, which ->
                dialog.dismiss()

            }


            alertDialog.show()
            // builder.contentView(R.layout.radio_view_item)


        }






    /*protected fun callTawkChatWebView() {
        val intent = Intent(MyApplication.getCurrentActivityContext(), StashdealWebviewActivity::class.java)
        intent.putExtra(StashdealWebviewActivity.URL_KEY, Constants.TAWK_CHAT_URL)
        MyApplication.getCurrentActivityContext().startActivity(intent)
    }*/



    private var keyBoardHide: InputMethodManager?=null;

    fun hideKeyBord(view: View?) {
        if (view != null) {
            if (keyBoardHide == null) {
                keyBoardHide = MyApplication.getCurrentActivityContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                // keyBoardHide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,
                // 0);
            }
            if (keyBoardHide != null && keyBoardHide!!.isActive()) {
                // to hide keyboard
                //if (view != null) {
                    keyBoardHide?.hideSoftInputFromWindow(view.windowToken, 0)
               // }
            }
        }
    }

}
