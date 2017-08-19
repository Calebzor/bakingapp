package hu.tvarga.bakingapp;

import org.junit.Test;

import java.util.List;

import hu.tvarga.bakingapp.data.BakingData;
import hu.tvarga.bakingapp.dataaccess.objects.Ingredient;
import hu.tvarga.bakingapp.dataaccess.objects.RecepyWithIngredientsAndSteps;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class ObjectSerializationTest {

	@Test
	public void serializationTest() {
		List<RecepyWithIngredientsAndSteps> recepyWithIngredientsAndStepsList =
				BakingData.getRecepies(BakingData.JSON);

		assertNotNull(recepyWithIngredientsAndStepsList);
		assertFalse("Not an empty list is expected", recepyWithIngredientsAndStepsList.isEmpty());
		RecepyWithIngredientsAndSteps recepyWithIngredientsAndSteps =
				recepyWithIngredientsAndStepsList.get(1);
		assertNotNull(recepyWithIngredientsAndSteps);
		List<Ingredient> ingredients = recepyWithIngredientsAndSteps.ingredients;
		assertNotNull(ingredients);
		assertFalse("Not an empty list is expected", ingredients.isEmpty());

	}
}
