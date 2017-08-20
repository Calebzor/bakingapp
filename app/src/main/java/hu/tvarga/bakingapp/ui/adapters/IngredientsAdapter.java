package hu.tvarga.bakingapp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import hu.tvarga.bakingapp.R;
import hu.tvarga.bakingapp.dataaccess.objects.Ingredient;
import hu.tvarga.bakingapp.dataaccess.objects.RecepyWithIngredientsAndSteps;

public class IngredientsAdapter
		extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {

	private final Context context;
	private RecepyWithIngredientsAndSteps recepy;

	public IngredientsAdapter(Context context) {
		this.context = context;
	}

	public void setRecepy(RecepyWithIngredientsAndSteps recepy) {
		this.recepy = recepy;
	}

	@Override
	public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new IngredientViewHolder(
				LayoutInflater.from(context).inflate(R.layout.ingredient_list_item, parent, false));
	}

	@Override
	public void onBindViewHolder(IngredientViewHolder holder, int position) {
		Ingredient ingredient = recepy.ingredients.get(position);
		holder.onBind(ingredient.ingredient, ingredient.quantity, ingredient.measure);
	}

	@Override
	public int getItemCount() {
		if (recepy.ingredients == null || recepy.ingredients.isEmpty()) {
			return 0;
		}

		return recepy.ingredients.size();
	}

	public class IngredientViewHolder extends ButterKnifeViewHolder {

		@BindView(R.id.ingredient)
		TextView ingredient;

		@BindView(R.id.quantity)
		TextView quantity;

		@BindView(R.id.measure)
		TextView measure;

		public IngredientViewHolder(View itemView) {
			super(itemView);
		}

		void onBind(String ingredient, String quantity, String measure) {
			this.ingredient.setText(ingredient);
			this.quantity.setText(quantity);
			this.measure.setText(measure);
		}
	}
}
