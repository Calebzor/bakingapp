package hu.tvarga.bakingapp.di;

import dagger.Component;
import hu.tvarga.bakingapp.di.androidinjectors.ServiceModule;
import hu.tvarga.bakingapp.di.scopes.ApplicationScope;
import hu.tvarga.bakingapp.widget.WidgetService;

@Component(modules = ServiceModule.class)
@ApplicationScope
public interface WidgetServiceComponent {

	void inject(WidgetService widgetService);
}
