package hu.tvarga.bakingapp.dataaccess.objects;

import android.arch.persistence.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(primaryKeys = {"id", "recepyId"})
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

}
