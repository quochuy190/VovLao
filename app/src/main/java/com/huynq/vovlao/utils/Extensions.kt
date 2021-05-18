package com.vbeeon.iotdbs.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.Html
import android.text.Spanned
import android.view.View
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.huynq.vovlao.R
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit


fun Activity.openFragment(
    fragment: Fragment,
    addToBackStack: Boolean
) {
    this as AppCompatActivity
    supportFragmentManager.beginTransaction()
        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_in_left)
        .apply {
        add(R.id.frameLayout, fragment)
        if (addToBackStack) addToBackStack(fragment::class.java.simpleName)
        commit()
    }
}

fun Activity.replaceFragment(
    fragment: Fragment,
    addToBackStack: Boolean
) {
    this as AppCompatActivity
    supportFragmentManager.beginTransaction()
        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_in_left)
        .apply {
            replace(R.id.frameLayout, fragment)
            if (addToBackStack) addToBackStack(fragment::class.java.simpleName)
            commit()
        }
}


inline fun <reified T : Any> Activity.launchActivityForResult(
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}) {

    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivityForResult(intent, requestCode, options)
    } else {
        startActivityForResult(intent, requestCode)
    }
}

inline fun <reified T : Any> Context.launchActivity(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}) {

    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivity(intent, options)
    } else {
        startActivity(intent)
    }
}

inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)

fun View.setOnSafeClickListener(
    onSafeClick: (View) -> Unit
) {
    setOnClickListener(SafeClickListener { v ->
        onSafeClick(v)
    })
}

fun View.setOnSafeClickListener(
    interval: Int,
    onSafeClick: (View) -> Unit
) {
    setOnClickListener(SafeClickListener(interval, {v->
        onSafeClick(v)
    }))
}

inline fun View.handleFocusChange(context: Context, noinline onViewFocused:() -> Unit){
    this.setOnFocusChangeListener { v, gainFocus ->
        if (gainFocus) {
            val anim = AnimationUtils.loadAnimation(context, R.anim.scale_in_tv)
            anim.fillAfter = true
            this.startAnimation(anim)

            ViewCompat.setElevation(this, 1F)
            onViewFocused.invoke()
        } else {
            val anim = AnimationUtils.loadAnimation(context, R.anim.scale_out_tv)
            anim.fillAfter = true
            this.startAnimation(anim)

            ViewCompat.setElevation(this, 0F)
        }
    }
}

inline fun View.gone(){
    this.visibility = View.GONE
}

inline fun View.visible(){
    this.visibility = View.VISIBLE
}

fun Context.showAlertDialog(title: String?, msg: String?, positiveLabel: String? = null, positiveClick: DialogInterface.OnClickListener? = null,
                            negativeLabel: String? = null, negativeClick: DialogInterface.OnClickListener? = null, isCancel: Boolean = true): AlertDialog {
    val dialog = AlertDialog.Builder(this)
    title?.let { dialog.setTitle(it) }
    msg?.let { dialog.setMessage(it) }
    positiveLabel?.let { dialog.setPositiveButton(it, positiveClick) }
    negativeLabel?.let { dialog.setNegativeButton(it, negativeClick) }
    dialog.setCancelable(isCancel)
    val alertDialog = dialog.create()
    alertDialog.show()
    return alertDialog
}

fun setTextHTML(html: String): Spanned
{
    val result: Spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(html)
    }
    return result
}

fun <T> Observable<T>.observerOnDatabase(block: (T) -> Unit): Observable<T> {
    return this.observeOn(Schedulers.computation())
        .doOnNext(block)
        .observeOn(Schedulers.io())
}

@RequiresApi(Build.VERSION_CODES.M)
@SuppressLint("MissingPermission")
fun getUUID(context: Context): String? {
    var deviceId = ""
    deviceId = Settings.Secure.getString(
        context?.getContentResolver(),
        Settings.Secure.ANDROID_ID
    )
    return deviceId.toString()
}