package hu.tvarga.bakingapp.dataaccess.db;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DbFactory {

	AppDatabase db;

	@Inject
	public DbFactory(Context context) {
		db = Room.databaseBuilder(context, AppDatabase.class, "database-name").build();
	}

	public AppDatabase getDb() {
		return db;
	}
}
