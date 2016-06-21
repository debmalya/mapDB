/**
 * Copyright 2015-2016 Debmalya Jash
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package xodus;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import jetbrains.exodus.ByteIterable;
import jetbrains.exodus.bindings.StringBinding;
import jetbrains.exodus.env.Environment;
import jetbrains.exodus.env.EnvironmentConfig;
import jetbrains.exodus.env.Environments;
import jetbrains.exodus.env.Store;
import jetbrains.exodus.env.StoreConfig;
import jetbrains.exodus.env.Transaction;
import jetbrains.exodus.env.TransactionalComputable;
import jetbrains.exodus.env.TransactionalExecutable;

/**
 * @author debmalyajash
 *
 */
public class EnvironmentWrapper {
	/**
	 * To log messages.
	 */
	private static final Logger LOGGER = Logger.getLogger(EnvironmentWrapper.class);
	/**
	 * opens existing database or creates the new one in the directory passed as
	 * a parameter. Each environment should have different directories. All
	 * environment data will be stored in ./src/resource/.myAppData This will
	 * open environment with default configuration.
	 */
	private Environment env;

	private static final Object Lock = new Object();
	/**
	 * 
	 */
	private Environment envWithoutGC;

	public Environment getEnv() {
		return env;
	}

	/**
	 * A transactional closure is used as the simplest way to manage
	 * transactions and updates within a transaction.Once you get a Store
	 * object, you can put values by keys in it and get values by keys from it.
	 * On the Environment layer, all data is binary and untyped, and it is
	 * represented by ByteIterable instances. ByteIterable is a kind of byte
	 * array or Iterable<Byte>. Prepare the data and proceed with a closure to
	 * put it into the store:
	 * 
	 * @param storeName
	 *            name of the store where key value pairs will be kept.
	 * @return newly created store.
	 */
	public Store createStore(final String storeName) {
		final Store store = env.computeInTransaction(new TransactionalComputable<Store>() {
			@Override
			public Store compute(@NotNull final Transaction txn) {
				return env.openStore(storeName, StoreConfig.WITHOUT_DUPLICATES, txn);
			}
		});
		return store;
	}

	/**
	 * Default constructor
	 * 
	 * @throws Exception
	 *             if any exception occurs while creating environment instances.
	 */
	public EnvironmentWrapper() throws Exception {
		try {
			synchronized (Lock) {
				if (env == null) {
					env = Environments.newInstance("./src/test/resource/environment1/.myAppData");
					LOGGER.debug("Default environment initialized.");
				}
				if (envWithoutGC == null) {
					envWithoutGC = Environments.newInstance("./src/test/resource/environment2/.myAppDataWithoutGC",
							new EnvironmentConfig().setGcEnabled(false));
					LOGGER.debug("Environment without garbage collection initialized.");
				}
			}
		} catch (Throwable th) {
			LOGGER.error(th.getMessage(), th);
			throw new Exception(th.getMessage(), th);
		}
	}

	/**
	 * To store key and value both are String.
	 * 
	 * @param key
	 *            to be stored.
	 * @param value
	 *            to be stored.
	 * @return true if stored successfully
	 * @throws Exception
	 *             - if any exception occurs during storing.
	 */
	public boolean storeKeyValue(final String keyToStore, final String valueToStore, final Store store)
			throws Exception {
		try {
			final ByteIterable key = StringBinding.stringToEntry(keyToStore);
			final ByteIterable value = StringBinding.stringToEntry(valueToStore);

			env.executeInTransaction(new TransactionalExecutable() {
				@Override
				public void execute(@NotNull final Transaction txn) {
					store.put(txn, key, value);
				}
			});
			return true;
		} catch (Throwable th) {
			throw new Exception(th.getMessage(), th);
		}
	}

	/**
	 * Close environment.
	 * 
	 * @throws Exception
	 */
	public void closeEnvrionment() throws Exception {
		try {
			if (env.isOpen()) {
				env.close();
			}

			if (envWithoutGC.isOpen()) {
				envWithoutGC.close();
			}

		} catch (Throwable th) {
			LOGGER.error(th.getMessage(), th);
			throw new Exception(th);
		}
	}
}
