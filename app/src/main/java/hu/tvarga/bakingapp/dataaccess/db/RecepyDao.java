package hu.tvarga.bakingapp.dataaccess.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import hu.tvarga.bakingapp.dataaccess.objects.Recepy;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface RecepyDao {

	@Query("SELECT * FROM recepy")
	List<Recepy> getAll();

	@Query("SELECT * FROM recepy WHERE id IN (:recepyIds)")
	List<Recepy> loadAllByIds(int[] recepyIds);

	@Insert(onConflict = REPLACE)
	void insertAll(Recepy... recepies);

	@Delete
	void delete(Recepy recepy);

	@Query("DELETE FROM recepy")
	void delete();
}
