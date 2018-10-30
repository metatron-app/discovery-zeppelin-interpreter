/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specic language governing permissions and
 * limitations under the License.
 */

package app.metatron.discovery.connector;

import java.util.Properties;

/**
 *
 */
public class MetisClientSetting {

  private Properties properties;

  public MetisClientSetting() {
    this.properties = new Properties();
  }

  public MetisClientSetting setting(String propertyName, String propertyValue) {
    this.properties.put(propertyName, propertyValue);
    return this;
  }

  public String getSetting(String propertyName) {
    return this.properties.getProperty(propertyName);
  }

  public String getDefaultSetting(String propertyName, String defaultValue) {
    return this.properties.getProperty(propertyName, defaultValue);
  }

  public int getPort() {
    return Integer.parseInt(getDefaultSetting("port", "8180"));
  }

  public String getHost() {
    return getDefaultSetting("host", "localhost");
  }
}
