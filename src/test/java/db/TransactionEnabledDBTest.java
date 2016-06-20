package db;

import org.junit.Assert;
import org.junit.Test;
import org.mapdb.DB;

public class TransactionEnabledDBTest {

	@Test
	public void testCreateTransactionEnbledDB() {
		TransactionEnabledDB teDB = new TransactionEnabledDB();
		DB myDB = teDB.createTransactionEnbledDB("durable.db");
		Assert.assertNotNull(myDB);

	}

}
