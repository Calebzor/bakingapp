package hu.tvarga.bakingapp.dataaccess.objects;

import android.arch.persistence.room.Relation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecepyWithIngredientsAndSteps extends Recepy {

	@SerializedName("ingredients")
	@Expose
	@Relation(parentColumn = "id", entityColumn = "recepyId")
	public List<Ingredient> ingredients;
	@SerializedName("steps")
	@Expose
	@Relation(parentColumn = "id", entityColumn = "recepyId")
	public List<Step> steps;

	@Override
	public String toString() {
		return "RecepyWithIngredientsAndSteps{" + "ingredients=" + ingredients + ", steps=" +
				steps + "} " + super.toString();
	}
}
