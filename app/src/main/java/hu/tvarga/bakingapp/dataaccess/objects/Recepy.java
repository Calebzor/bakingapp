package hu.tvarga.bakingapp.dataaccess.objects;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class Recepy implements Serializable {

	private static final long serialVersionUID = 6336230927017750606L;

	@SerializedName("id")
	@Expose
	@PrimaryKey
	public Integer id;
	@SerializedName("name")
	@Expose
	public String name;
	@SerializedName("servings")
	@Expose
	public Integer servings;
	@SerializedName("image")
	@Expose
	public String image;

	@Override
	public String toString() {
		return "Recepy{" + "id=" + id + ", name='" + name + '\'' + ", servings=" + servings +
				", image='" + image + '\'' + '}';
	}
}
