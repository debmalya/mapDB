package db;

import org.mapdb.DB;
import org.mapdb.DBMaker;

public class TransactionEnabledDB {
	
	public DB createTransactionEnbledDB(final String dbName) {
		return DBMaker.fileDB(dbName).transactionEnable().closeOnJvmShutdown().make();
	}

}
