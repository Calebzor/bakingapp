package hu.tvarga.bakingapp.dataaccess.objects;

import android.arch.persistence.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(primaryKeys = {"ingredient", "recepyId"})
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
}
