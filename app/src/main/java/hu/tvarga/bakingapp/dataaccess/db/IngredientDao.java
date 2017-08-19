package hu.tvarga.bakingapp.dataaccess.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import hu.tvarga.bakingapp.dataaccess.objects.Ingredient;

@Dao
public interface IngredientDao {

	@Query("SELECT * FROM ingredient WHERE recepyId IN (:recepyIds)")
	List<Ingredient> loadAllByRecepyId(int[] recepyIds);

	@Insert
	void insertAll(Ingredient... steps);

	@Delete
	void delete(Ingredient step);
}
