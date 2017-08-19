package hu.tvarga.bakingapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import hu.tvarga.bakingapp.data.BakingData;
import hu.tvarga.bakingapp.dataaccess.db.AppDatabase;
import hu.tvarga.bakingapp.dataaccess.db.IngredientDao;
import hu.tvarga.bakingapp.dataaccess.db.RecepyDao;
import hu.tvarga.bakingapp.dataaccess.db.RecepyWithIngredientsAndStepsDao;
import hu.tvarga.bakingapp.dataaccess.db.StepDao;
import hu.tvarga.bakingapp.dataaccess.objects.Ingredient;
import hu.tvarga.bakingapp.dataaccess.objects.Recepy;
import hu.tvarga.bakingapp.dataaccess.objects.RecepyWithIngredientsAndSteps;
import hu.tvarga.bakingapp.dataaccess.objects.Step;

import static hu.tvarga.bakingapp.dataaccess.db.RecepyFromNetworkDbHelper.addRecepiesToDb;
import static hu.tvarga.bakingapp.dataaccess.db.RecepyFromNetworkDbHelper.deleteDbContent;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class SimpleEntityReadWriteTest {

	List<RecepyWithIngredientsAndSteps> recepyWithIngredientsAndStepsList = BakingData.getRecepies(
			BakingData.JSON);

	private AppDatabase db;

	private RecepyDao recepyDao;
	private IngredientDao ingredientDao;
	private StepDao stepDao;
	private RecepyWithIngredientsAndStepsDao recepyWithIngredientsAndStepsDao;

	@Before
	public void createDb() {
		Context context = InstrumentationRegistry.getTargetContext();
		db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
		recepyDao = db.recepyDao();
		ingredientDao = db.ingredientDao();
		stepDao = db.stepDao();
		recepyWithIngredientsAndStepsDao = db.recepyWithIngredientsAndStepsDao();
	}

	@After
	public void closeDb() throws IOException {
		db.close();
	}

	@Test
	public void writeRecepiesAndReadThemBack() throws Exception {
		recepyDao.insertAll(recepyWithIngredientsAndStepsList
				.toArray(new Recepy[recepyWithIngredientsAndStepsList.size()]));
		List<Recepy> all = recepyDao.getAll();
		assertNotNull(all);
		assertThat(recepyWithIngredientsAndStepsList.size(), is(all.size()));
	}

	@Test
	public void writeRecepiesAndVerifyIngredients() throws Exception {
		addRecepiesToDb(recepyWithIngredientsAndStepsList, db);

		List<Recepy> allRecepies = recepyDao.getAll();
		assertNotNull(allRecepies);
		assertThat(recepyWithIngredientsAndStepsList.size(), is(allRecepies.size()));

		int[] recepyIds = new int[allRecepies.size()];
		for (int i = 0; i < allRecepies.size(); i++) {
			recepyIds[i] = allRecepies.get(i).id;
		}

		List<Ingredient> allIngredients = ingredientDao.loadAllByRecepyId(recepyIds);
		assertNotNull(allIngredients);

		List<Step> allSteps = stepDao.loadAllByRecepyId(recepyIds);
		assertNotNull(allSteps);
	}

	@Test
	public void writeRecepiesAndReadThemBackAsIs() {
		addRecepiesToDb(recepyWithIngredientsAndStepsList, db);

		List<RecepyWithIngredientsAndSteps> recepies =
				recepyWithIngredientsAndStepsDao.loadAllRecepyWithIngredientsAndSteps();
		assertNotNull(recepies);
		assertThat(recepyWithIngredientsAndStepsList.size(), is(recepies.size()));
		assertThat(recepyWithIngredientsAndStepsList.get(0).steps.get(0).videoURL, is(recepies.get(
				0).steps.get(0).videoURL));
	}

	@Test
	public void writingAllTwice() {
		addRecepiesToDb(recepyWithIngredientsAndStepsList, db);
		addRecepiesToDb(recepyWithIngredientsAndStepsList, db);
	}

	@Test
	public void writingAllTwiceWithDelete() {
		addRecepiesToDb(recepyWithIngredientsAndStepsList, db);

		deleteDbContent(db);

		addRecepiesToDb(recepyWithIngredientsAndStepsList, db);
	}

}
