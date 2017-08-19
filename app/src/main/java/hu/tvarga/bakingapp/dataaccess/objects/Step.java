package hu.tvarga.bakingapp.dataaccess.objects;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(primaryKeys = {"id", "recepyId"},
		foreignKeys = @ForeignKey(entity = Recepy.class, parentColumns = "id",
				childColumns = "recepyId", onDelete = CASCADE))
public class Step implements Serializable {

	private static final long serialVersionUID = 2923780284996128776L;

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
	public Integer recepyId;

	@Override
	public String toString() {
		return "Step{" + "id=" + id + ", shortDescription='" + shortDescription + '\'' +
				", description='" + description + '\'' + ", videoURL='" + videoURL + '\'' +
				", thumbnailURL='" + thumbnailURL + '\'' + ", recepyId=" + recepyId + '}';
	}
}
