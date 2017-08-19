package hu.tvarga.bakingapp.dataaccess.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ingredient implements Parcelable {

	@SerializedName("quantity")
	@Expose
	public String quantity;
	@SerializedName("measure")
	@Expose
	public String measure;
	@SerializedName("ingredient")
	@Expose
	public String ingredient;
	public final static Parcelable.Creator<Ingredient> CREATOR = new Creator<Ingredient>() {

		@SuppressWarnings({"unchecked"})
		public Ingredient createFromParcel(Parcel in) {
			Ingredient instance = new Ingredient();
			instance.quantity = ((String) in.readValue((String.class.getClassLoader())));
			instance.measure = ((String) in.readValue((String.class.getClassLoader())));
			instance.ingredient = ((String) in.readValue((String.class.getClassLoader())));
			return instance;
		}

		public Ingredient[] newArray(int size) {
			return (new Ingredient[size]);
		}

	};

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(quantity);
		dest.writeValue(measure);
		dest.writeValue(ingredient);
	}

	public int describeContents() {
		return 0;
	}

}
