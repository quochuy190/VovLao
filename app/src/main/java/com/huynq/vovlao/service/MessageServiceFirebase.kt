package com.huynq.vovlao.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.huynq.vovlao.R
import timber.log.Timber

// File MessageServiceFirebase
// @project OfficeApp
// @author minhhoang on 23-04-2021
class MessageServiceFirebase : FirebaseMessagingService() {
    private val ID_CHANNEL = "VovLao"
    private val CHANNEL_NAME = "VovLao"


    override fun onNewToken(p0: String) {
        super.onNewToken(p0)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val data = remoteMessage.data
        Timber.e(""+data)
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val builder = setNotificationBuilder(this, manager)

    }

    private fun setNotificationBuilder(
        context: Context,
        manager: NotificationManager
    ): NotificationCompat.Builder {
        val builder: NotificationCompat.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(ID_CHANNEL, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            channel.description = CHANNEL_NAME
            channel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            channel.lightColor = Color.parseColor("#ffffff")
            channel.enableLights(true)
            channel.enableVibration(true)
            manager.createNotificationChannel(channel)
            builder = NotificationCompat.Builder(context, ID_CHANNEL)
        } else {
            builder = NotificationCompat.Builder(context)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.color = ContextCompat.getColor(context, android.R.color.transparent)
        }
        builder.setAutoCancel(true)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
            .setSmallIcon(R.mipmap.ic_launcher)
        return builder
    }
}
