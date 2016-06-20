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

import jetbrains.exodus.env.Environment;
import jetbrains.exodus.env.EnvironmentConfig;
import jetbrains.exodus.env.Environments;

/**
 * @author debmalyajash
 *
 */
public class EnvironmentWrapper {
	/**
	 * opens existing database or creates the new one in the directory passed as
	 * a parameter. Each environment should have different directories. All
	 * environment data will be stored in ./src/resource/.myAppData This will
	 * open environment with default configuration.
	 */
	private Environment env = Environments.newInstance("./src/resource/.myAppData");

	/**
	 * 
	 */
	private Environment envWithoutGC = Environments.newInstance("./src/test/resource/.myAppDataWithoutGC",
			new EnvironmentConfig().setGcEnabled(false));

	public Environment getEnv() {
		return env;
	}

}
