package hu.tvarga.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import hu.tvarga.bakingapp.R;
import hu.tvarga.bakingapp.ui.main.MainActivity;

public class WidgetProvider extends AppWidgetProvider {

	public static final String ACTION_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE";

	@Override
	public void onReceive(Context context, Intent intent) {
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		String action = intent.getAction();
		if (ACTION_UPDATE.equals(action)) {
			int[] appWidgetIDs = appWidgetManager.getAppWidgetIds(
					new ComponentName(context, getClass()));
			appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIDs, R.id.recepyList);
		}
		super.onReceive(context, intent);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		Intent intent = new Intent(context, WidgetService.class);
		intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
		RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget);
		rv.setRemoteAdapter(R.id.recepyList, intent);
		rv.setEmptyView(R.id.recepyList, R.id.defaultMessage);

		Intent openAppIntent = new Intent(context, MainActivity.class);
		PendingIntent openAppPendingIntent = PendingIntent.getActivity(context, 0, openAppIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		rv.setOnClickPendingIntent(R.id.title, openAppPendingIntent);
		rv.setOnClickPendingIntent(R.id.defaultMessage, openAppPendingIntent);
		rv.setOnClickPendingIntent(R.id.widget_layout_parent, openAppPendingIntent);

		appWidgetManager.updateAppWidget(appWidgetIds, rv);

		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
}
