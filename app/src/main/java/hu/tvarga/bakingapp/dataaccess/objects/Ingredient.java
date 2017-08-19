package hu.tvarga.bakingapp.dataaccess.objects;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(primaryKeys = {"ingredient", "recepyId"},
		foreignKeys = @ForeignKey(entity = Recepy.class, parentColumns = "id",
				childColumns = "recepyId", onDelete = CASCADE))
public class Ingredient implements Serializable {

	private static final long serialVersionUID = 8688072108919790956L;

	@SerializedName("quantity")
	@Expose
	public String quantity;
	@SerializedName("measure")
	@Expose
	public String measure;
	@SerializedName("ingredient")
	@Expose
	public String ingredient;

	public Integer recepyId;

	@Override
	public String toString() {
		return "Ingredient{" + "quantity='" + quantity + '\'' + ", measure='" + measure + '\'' +
				", ingredient='" + ingredient + '\'' + ", recepyId=" + recepyId + '}';
	}
}
