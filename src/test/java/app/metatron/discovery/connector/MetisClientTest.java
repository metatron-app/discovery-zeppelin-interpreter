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

import org.junit.Test;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 *
 */
public class MetisClientTest {

  @Test
  public void loadDataTest() {
    MetisClientSetting setting = new MetisClientSetting();
    setting.setting("host", "localhost");
    setting.setting("port", "8180");

    MetisClient client = new MetisClient(setting);
    SparkSession spark = SparkSession.builder().master("local").appName("testMetisClient").getOrCreate();
    try {
      Dataset<Row> res = client.loadData(spark, "charts", "397f1137-dd93-4f2a-abe5-db98ca59c13e");
      res.show();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    } finally {
      spark.stop();
    }
  }
}
