package hu.tvarga.bakingapp.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import hu.tvarga.bakingapp.R;
import hu.tvarga.bakingapp.dataaccess.objects.RecepyWithIngredientsAndSteps;
import hu.tvarga.bakingapp.ui.detail.DetailActivity;
import hu.tvarga.bakingapp.utilties.GsonHelper;

import static hu.tvarga.bakingapp.dataaccess.preferences.Preferences.getSharedPreferences;

public class MainCardAdapter extends RecyclerView.Adapter<MainCardAdapter.MainCardViewHolder> {

	public static final String RECEPY_EXTRA_KEY = "RECEPY_EXTRA_KEY";
	private final Context context;
	private final List<RecepyWithIngredientsAndSteps> recepies;

	public MainCardAdapter(Context context, List<RecepyWithIngredientsAndSteps> recepies) {
		this.context = context;
		this.recepies = recepies;
	}

	@Override
	public int getItemCount() {
		return recepies == null ? 0 : recepies.size();
	}

	@Override
	public MainCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new MainCardViewHolder(
				LayoutInflater.from(context).inflate(R.layout.recepy_card_item, parent, false));
	}

	@Override
	public void onBindViewHolder(MainCardViewHolder holder, int position) {
		holder.onBind(recepies.get(position));
	}

	public class MainCardViewHolder extends ButterKnifeViewHolder {

		@BindView(R.id.recepyTitle)
		TextView title;

		@BindView(R.id.recepyImage)
		ImageView imageView;

		public MainCardViewHolder(View itemView) {
			super(itemView);
		}

		void onBind(final RecepyWithIngredientsAndSteps recepy) {
			title.setText(recepy.name);
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					SharedPreferences sharedPreferences = getSharedPreferences(context);
					sharedPreferences.edit().putString(RECEPY_EXTRA_KEY,
							GsonHelper.getGson().toJson(recepy)).apply();
					Intent intent = new Intent(context, DetailActivity.class);
					context.startActivity(intent);
				}
			});
		}
	}
}
