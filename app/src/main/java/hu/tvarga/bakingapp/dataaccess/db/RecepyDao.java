package hu.tvarga.bakingapp.dataaccess.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import hu.tvarga.bakingapp.dataaccess.objects.Recepy;

@Dao
public interface RecepyDao {

	@Query("SELECT * FROM recepy")
	List<Recepy> getAll();

	@Query("SELECT * FROM recepy WHERE id IN (:recepyIds)")
	List<Recepy> loadAllByIds(int[] recepyIds);

	@Insert
	void insertAll(Recepy... recepies);

	@Delete
	void delete(Recepy recepy);
}
