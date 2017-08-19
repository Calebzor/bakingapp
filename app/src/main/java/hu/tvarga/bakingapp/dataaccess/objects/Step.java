package hu.tvarga.bakingapp.dataaccess.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step implements Parcelable {

	@SerializedName("id")
	@Expose
	public Integer id;
	@SerializedName("shortDescription")
	@Expose
	public String shortDescription;
	@SerializedName("description")
	@Expose
	public String description;
	@SerializedName("videoURL")
	@Expose
	public String videoURL;
	@SerializedName("thumbnailURL")
	@Expose
	public String thumbnailURL;
	public final static Parcelable.Creator<Step> CREATOR = new Creator<Step>() {

		@SuppressWarnings({"unchecked"})
		public Step createFromParcel(Parcel in) {
			Step instance = new Step();
			instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
			instance.shortDescription = ((String) in.readValue((String.class.getClassLoader())));
			instance.description = ((String) in.readValue((String.class.getClassLoader())));
			instance.videoURL = ((String) in.readValue((String.class.getClassLoader())));
			instance.thumbnailURL = ((String) in.readValue((String.class.getClassLoader())));
			return instance;
		}

		public Step[] newArray(int size) {
			return (new Step[size]);
		}

	};

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(id);
		dest.writeValue(shortDescription);
		dest.writeValue(description);
		dest.writeValue(videoURL);
		dest.writeValue(thumbnailURL);
	}

	public int describeContents() {
		return 0;
	}

}
