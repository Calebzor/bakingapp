package hu.tvarga.bakingapp.dataaccess.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recepy implements Parcelable {

	@SerializedName("id")
	@Expose
	public Integer id;
	@SerializedName("name")
	@Expose
	public String name;
	@SerializedName("ingredients")
	@Expose
	public List<Ingredient> ingredients = null;
	@SerializedName("steps")
	@Expose
	public List<Step> steps = null;
	@SerializedName("servings")
	@Expose
	public Integer servings;
	@SerializedName("image")
	@Expose
	public String image;
	public final static Parcelable.Creator<Recepy> CREATOR = new Creator<Recepy>() {

		@SuppressWarnings({"unchecked"})
		public Recepy createFromParcel(Parcel in) {
			Recepy instance = new Recepy();
			instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
			instance.name = ((String) in.readValue((String.class.getClassLoader())));
			in.readList(instance.ingredients, (Ingredient.class.getClassLoader()));
			in.readList(instance.steps, (Step.class.getClassLoader()));
			instance.servings = ((Integer) in.readValue((Integer.class.getClassLoader())));
			instance.image = ((String) in.readValue((String.class.getClassLoader())));
			return instance;
		}

		public Recepy[] newArray(int size) {
			return (new Recepy[size]);
		}

	};

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(id);
		dest.writeValue(name);
		dest.writeList(ingredients);
		dest.writeList(steps);
		dest.writeValue(servings);
		dest.writeValue(image);
	}

	public int describeContents() {
		return 0;
	}

}
