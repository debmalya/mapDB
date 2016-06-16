package db;

import org.mapdb.DB;
import org.mapdb.DBMaker;

public class TransactionEnabledFileDB {

	private DB db = DBMaker.fileDB("transaction.db").transactionEnable().make();
	
	private static TransactionEnabledFileDB instance;
	
	static {
		instance = new TransactionEnabledFileDB();
	}
	
	private TransactionEnabledFileDB(){
		
	}
	
	public static TransactionEnabledFileDB getInstance() {
		return instance;
	}
}
