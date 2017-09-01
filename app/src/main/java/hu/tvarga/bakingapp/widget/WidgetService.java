package hu.tvarga.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import javax.inject.Inject;

import hu.tvarga.bakingapp.R;
import hu.tvarga.bakingapp.dataaccess.db.DbFactory;
import hu.tvarga.bakingapp.dataaccess.objects.Ingredient;
import hu.tvarga.bakingapp.dataaccess.objects.RecepyWithIngredientsAndSteps;
import hu.tvarga.bakingapp.dataaccess.preferences.Preferences;
import hu.tvarga.bakingapp.di.DaggerWidgetServiceComponent;
import hu.tvarga.bakingapp.di.androidinjectors.ServiceModule;
import timber.log.Timber;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static hu.tvarga.bakingapp.ui.detail.DetailActivity.FAVORITE_RECEPY_INDEX;
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

		private RecepyWithIngredientsAndSteps recepyWithIngredientAndSteps;
		private Context context;
		private int favoritesIndex = -1;

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
			return 1;
		}

		@Override
		public RemoteViews getViewAt(int position) {
			RemoteViews row = new RemoteViews(context.getPackageName(), R.layout.recepy_list_item);
			if (favoritesIndex == -1 || recepyWithIngredientAndSteps == null) {
				row.setViewVisibility(R.id.listItemContainer, GONE);
				row.setViewVisibility(R.id.defaultMessage, VISIBLE);
				return row;
			}
			row.setViewVisibility(R.id.listItemContainer, VISIBLE);
			row.setViewVisibility(R.id.defaultMessage, GONE);
			row.setTextViewText(R.id.text, recepyWithIngredientAndSteps.name);
			StringBuilder sb = new StringBuilder("");
			for (Ingredient ingredient : recepyWithIngredientAndSteps.ingredients) {
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
			SharedPreferences sharedPreferences = Preferences.getSharedPreferences(context);
			favoritesIndex = sharedPreferences.getInt(FAVORITE_RECEPY_INDEX, -1);
			runInBackgroundThread(new Runnable() {
				@Override
				public void run() {
					recepyWithIngredientAndSteps =
							dbFactory.getDb().recepyWithIngredientsAndStepsDao()
									.loadRecepyWithIngredientsAndSteps(favoritesIndex);
					if (shouldBroadcastUpdate) {
						Intent dataUpdatedIntent = new Intent(ACTION_UPDATE);
						context.sendBroadcast(dataUpdatedIntent);
					}
				}
			});

		}
	}
}
