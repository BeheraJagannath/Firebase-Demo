package com.example.loginfirebasedemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.Navigation.NavigationActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelId = "notification_channel"
const val  channelName= "com.example.loginfirebasedemo"
class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        if (message.getNotification()!=null){
            GenerateNotification(message .notification!!.title!!,message.notification!!.body!!)
        }

    }

    fun getRomoteView(title:String , message:String):RemoteViews{
        var remotview = RemoteViews("com.example.loginfirebasedemo",R.layout.notification_item)
        remotview .setTextViewText(R.id.notification_title,title)
        remotview .setTextViewText(R.id.notification_subtitle,message)
        remotview .setImageViewResource(R.id.imag,R.drawable.ic_launcher_foreground)

        return remotview
    }

fun GenerateNotification(title:String , message:String) {
    val  intent = Intent(this,NavigationActivity::class.java);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

    val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT)

    var  builder :NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setAutoCancel(true)
        .setVibrate(longArrayOf(1000,1000,1000,1000))
        .setLocalOnly(true)
        .setContentIntent(pendingIntent)


    builder = builder .setContent(getRomoteView(title,message))

    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
        val notificationchannel = NotificationChannel(channelId, channelName,NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(notificationchannel)
    }
    notificationManager.notify(0 ,builder.build())



}

}
