package hu.tvarga.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import hu.tvarga.bakingapp.R;
import hu.tvarga.bakingapp.dataaccess.db.DbFactory;
import hu.tvarga.bakingapp.dataaccess.objects.Ingredient;
import hu.tvarga.bakingapp.dataaccess.objects.RecepyWithIngredientsAndSteps;
import hu.tvarga.bakingapp.di.DaggerWidgetServiceComponent;
import hu.tvarga.bakingapp.di.androidinjectors.ServiceModule;
import timber.log.Timber;

import static hu.tvarga.bakingapp.utilties.DispatchQueueHelper.runInBackgroundThread;
import static hu.tvarga.bakingapp.widget.WidgetProvider.ACTION_UPDATE;

public class WidgetService extends RemoteViewsService {

	@Inject
	DbFactory dbFactory;

	@Override
	public void onCreate() {
		super.onCreate();
		DaggerWidgetServiceComponent.builder().serviceModule(new ServiceModule(this)).build()
				.inject(this);
	}

	@Override
	public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
		return new BakingAppWidgetViewsFactory(getApplicationContext());
	}

	public class BakingAppWidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory {

		private List<RecepyWithIngredientsAndSteps> recepies = new ArrayList<>();
		private Context context;

		public BakingAppWidgetViewsFactory(Context applicationContext) {
			context = applicationContext;
		}

		@Override
		public void onCreate() {
			Timber.d("BakingAppWidgetViewsFactory#onCreate");
			loadData(true);
		}

		@Override
		public void onDataSetChanged() {
			Timber.d("BakingAppWidgetViewsFactory#onDataSetChanged");
			loadData(false);
		}

		@Override
		public void onDestroy() {
			Timber.d("BakingAppWidgetViewsFactory#onDestroy");
		}

		@Override
		public int getCount() {
			return recepies.size();
		}

		@Override
		public RemoteViews getViewAt(int position) {
			RecepyWithIngredientsAndSteps recepyWithIngredientsAndSteps = recepies.get(position);
			if (recepyWithIngredientsAndSteps == null) {
				return null;
			}
			RemoteViews row = new RemoteViews(context.getPackageName(), R.layout.recepy_list_item);
			row.setTextViewText(R.id.text, recepyWithIngredientsAndSteps.name);
			StringBuilder sb = new StringBuilder("");
			for (Ingredient ingredient : recepyWithIngredientsAndSteps.ingredients) {
				if (ingredient.ingredient != null) {
					sb.append(ingredient.ingredient).append("\n");
				}
				if (ingredient.quantity != null && ingredient.measure != null) {
					sb.append(ingredient.quantity).append(" ").append(ingredient.measure);
				}
			}
			String ingredient = sb.toString();
			row.setTextViewText(R.id.ingredients, ingredient);

			return row;
		}

		@Override
		public RemoteViews getLoadingView() {
			return null;
		}

		@Override
		public int getViewTypeCount() {
			return 1;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		public void loadData(final boolean shouldBroadcastUpdate) {
			runInBackgroundThread(new Runnable() {
				@Override
				public void run() {
					List<RecepyWithIngredientsAndSteps> recepyWithIngredientsAndStepses =
							dbFactory.getDb().recepyWithIngredientsAndStepsDao()
									.loadAllRecepyWithIngredientsAndSteps();
					if (recepyWithIngredientsAndStepses != null) {
						recepies.clear();
						recepies.addAll(recepyWithIngredientsAndStepses);
					}
					if (shouldBroadcastUpdate) {
						Intent dataUpdatedIntent = new Intent(ACTION_UPDATE);
						context.sendBroadcast(dataUpdatedIntent);
					}
				}
			});

		}
	}
}
