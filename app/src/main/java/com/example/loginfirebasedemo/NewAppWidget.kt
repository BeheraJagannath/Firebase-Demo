package com.example.loginfirebasedemo

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
class NewAppWidget : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)

        }
    }

    override fun onEnabled(context: Context) {

    }
    override fun onDisabled(context: Context) {

    }
}
internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
    val widgetText = context.getString(R.string.app_name)
    val views = RemoteViews(context.packageName, R.layout.new_app)
    views.setTextViewText(R.id.appwidget_text, widgetText)

    val  intent = Intent(context, SplashActivity::class.java);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    val pending = PendingIntent.getActivity(context, 0, intent, 0)
    views.setOnClickPendingIntent(R.id.appwidget_rel,pending)

    appWidgetManager.updateAppWidget(appWidgetId, views)
}