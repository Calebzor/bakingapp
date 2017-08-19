package hu.tvarga.bakingapp.dataaccess.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import hu.tvarga.bakingapp.dataaccess.objects.RecepyWithIngredientsAndSteps;

@Dao
public interface RecepyWithIngredientsAndStepsDao {

	@Query("SELECT * from recepy")
	List<RecepyWithIngredientsAndSteps> loadAllRecepyWithIngredientsAndSteps();
}
