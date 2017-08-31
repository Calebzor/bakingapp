package hu.tvarga.bakingapp.di.androidinjectors;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import hu.tvarga.bakingapp.widget.WidgetService;

@Module
public class ServiceModule {

	WidgetService widgetService;

	public ServiceModule(WidgetService widgetService) {
		this.widgetService = widgetService;
	}

	@Provides
	WidgetService provideWidgetService() {
		return widgetService;
	}

	@Provides
	Context provideContext() {
		return widgetService.getApplicationContext();
	}

}
