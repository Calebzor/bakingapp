package hu.tvarga.bakingapp.dataaccess.db;

import java.util.ArrayList;
import java.util.List;

import hu.tvarga.bakingapp.dataaccess.objects.Ingredient;
import hu.tvarga.bakingapp.dataaccess.objects.Recepy;
import hu.tvarga.bakingapp.dataaccess.objects.RecepyWithIngredientsAndSteps;
import hu.tvarga.bakingapp.dataaccess.objects.Step;

public class RecepyFromNetworkDbHelper {

	public static void addRecepiesToDb(
			List<RecepyWithIngredientsAndSteps> recepyWithIngredientsAndStepsList, AppDatabase db) {
		List<Ingredient> ingredientList = new ArrayList<>();
		List<Step> stepList = new ArrayList<>();
		for (RecepyWithIngredientsAndSteps recepyWithIngredientsAndSteps : recepyWithIngredientsAndStepsList) {
			for (Ingredient ingredient : recepyWithIngredientsAndSteps.ingredients) {
				ingredient.recepyId = recepyWithIngredientsAndSteps.id;
				ingredientList.add(ingredient);
			}
			for (Step step : recepyWithIngredientsAndSteps.steps) {
				step.recepyId = recepyWithIngredientsAndSteps.id;
				stepList.add(step);
			}
		}

		if (recepyWithIngredientsAndStepsList.isEmpty()) {
			return;
		}

		RecepyDao recepyDao = db.recepyDao();
		recepyDao.insertAll(recepyWithIngredientsAndStepsList
				.toArray(new Recepy[recepyWithIngredientsAndStepsList.size()]));

		if (!ingredientList.isEmpty()) {
			IngredientDao ingredientDao = db.ingredientDao();
			ingredientDao.insertAll(ingredientList.toArray(new Ingredient[ingredientList.size()]));
		}

		if (!stepList.isEmpty()) {
			StepDao stepDao = db.stepDao();
			stepDao.insertAll(stepList.toArray(new Step[stepList.size()]));
		}
	}

	public static void deleteDbContent(AppDatabase db) {
		RecepyDao recepyDao = db.recepyDao();
		recepyDao.delete();
	}

}
