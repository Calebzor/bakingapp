package hu.tvarga.bakingapp;

import org.junit.Test;

import java.util.List;

import hu.tvarga.bakingapp.data.BakingData;
import hu.tvarga.bakingapp.dataaccess.objects.Recepy;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class ObjectSerializationTest {

	@Test
	public void serializationTest() {
		List<Recepy> recepyList = BakingData.getRecepies(BakingData.JSON);

		assertNotNull(recepyList);
		assertFalse("Not an empty list is expected", recepyList.isEmpty());
	}
}
