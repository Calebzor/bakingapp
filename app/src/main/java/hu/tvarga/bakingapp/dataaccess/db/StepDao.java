package hu.tvarga.bakingapp.dataaccess.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import hu.tvarga.bakingapp.dataaccess.objects.Step;

@Dao
public interface StepDao {

	@Query("SELECT * FROM step WHERE recepyId IN (:recepyIds)")
	List<Step> loadAllByRecepyId(int[] recepyIds);

	@Insert
	void insertAll(Step... steps);

	@Delete
	void delete(Step step);
}
