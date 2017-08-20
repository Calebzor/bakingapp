package hu.tvarga.bakingapp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import hu.tvarga.bakingapp.R;
import hu.tvarga.bakingapp.dataaccess.objects.RecepyWithIngredientsAndSteps;
import hu.tvarga.bakingapp.dataaccess.objects.Step;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailBaseViewHolder> {

	private final Context context;
	private RecepyWithIngredientsAndSteps recepy;

	public DetailAdapter(Context context) {
		this.context = context;
	}

	public void setRecepy(RecepyWithIngredientsAndSteps recepy) {
		this.recepy = recepy;
	}

	@Override
	public DetailBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new DetailBaseViewHolder(
				LayoutInflater.from(context).inflate(R.layout.detail_list_item, parent, false));
	}

	@Override
	public void onBindViewHolder(DetailBaseViewHolder holder, int position) {
		if (position == 0) {
			holder.onBind(context.getString(R.string.ingredients), null);
		}
		else {
			Step step = recepy.steps.get(position - 1);
			holder.onBind(step.id + ". " + step.shortDescription, step);
		}
	}

	@Override
	public int getItemCount() {
		int itemCount = 0;
		if (recepy.ingredients != null) {
			itemCount++;
		}
		itemCount = itemCount + (recepy.steps == null ? 0 : recepy.steps.size());
		return itemCount;
	}

	public class DetailBaseViewHolder extends ButterKnifeViewHolder {

		@BindView(R.id.title)
		TextView title;

		public DetailBaseViewHolder(View itemView) {
			super(itemView);
		}

		void onBind(String title, final Step step) {
			this.title.setText(title);
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					((DetailItemClickAction) context).onItemClick(step);
				}
			});
		}
	}

	public interface DetailItemClickAction {

		/**
		 * Step being null means the ingredients list item was clicked
		 *
		 * @param step the step that was clicked, null means, it's not a step, but the ingredients
		 */
		void onItemClick(Step step);
	}

}
