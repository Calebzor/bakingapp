package hu.tvarga.bakingapp.dataaccess.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import hu.tvarga.bakingapp.dataaccess.objects.Ingredient;
import hu.tvarga.bakingapp.dataaccess.objects.Recepy;
import hu.tvarga.bakingapp.dataaccess.objects.Step;

@Database(entities = {Recepy.class, Ingredient.class, Step.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

	public abstract RecepyDao recepyDao();
	public abstract IngredientDao ingredientDao();
	public abstract StepDao stepDao();
	public abstract RecepyWithIngredientsAndStepsDao recepyWithIngredientsAndStepsDao();
}