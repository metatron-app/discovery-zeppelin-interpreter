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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
public class MetisClient {

  private MetisClientSetting settings;

  public MetisClient(MetisClientSetting settings) {
    this.settings = settings;
  }

  public MetisResponse execute() throws IOException {
    return null;
  }

  /**
   * load data
   *
   * @param spark    SparkSession
   * @param dataType datasources(/api/datasources/-/data), dashboards(/api/dashboards/-/data),
   *                 charts(/api/widgets/-/data)
   * @param dataId   unique-id
   */
  public Dataset<Row> loadData(SparkSession spark, String dataType, String dataId) throws IOException, URISyntaxException {
    URI uri = new URIBuilder().setScheme("http").setHost(this.settings.getHost()).setPort(this.settings.getPort())
                              .setPath(getUriPathByType(dataType) + dataId + "/data").build();

    return executeDruidAPI(spark, uri);
  }

  /**
   * load data
   *
   * @param spark    SparkSession
   * @param dataType datasources(/api/datasources/-/data), dashboards(/api/dashboards/-/data),
   *                 charts(/api/widgets/-/data)
   * @param dataId   unique-id
   * @param limit    limit of dataset
   */
  public Dataset<Row> loadData(SparkSession spark, String dataType, String dataId, String limit) throws IOException, URISyntaxException {
    URI uri = new URIBuilder().setScheme("http").setHost(this.settings.getHost()).setPort(this.settings.getPort())
                              .setPath(getUriPathByType(dataType) + dataId + "/data").setParameter("limit", limit).build();

    return executeDruidAPI(spark, uri);
  }

  /**
   * query to Druid
   */
  private Dataset<Row> executeDruidAPI(SparkSession spark, URI uri) throws IOException {
    HttpClient client = HttpClientBuilder.create().build();
    HttpPost request = new HttpPost(uri);
    request.addHeader("Accept", "application/json");
    request.addHeader("Content-Type", "application/json");

    HttpResponse response = client.execute(request);
    HttpEntity entity = response.getEntity();
    if (entity != null) {
      return makeDataset(spark, entity.getContent());
    } else {
      throw new RuntimeException("Fail to load data.");
    }
  }

  /**
   * convert result to Dataset of Spark
   */
  private Dataset<Row> makeDataset(SparkSession spark, InputStream io) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    List<String> valueElements = new ArrayList();
    JsonNode nodes = mapper.readTree(io);
    Iterator<JsonNode> iterator = nodes.elements();
    while (iterator.hasNext()) {
      JsonNode node = (JsonNode) iterator.next();
      valueElements.add(node.toString());
    }
    return spark.read().json(spark.createDataset(valueElements, Encoders.STRING()));
  }

  /**
   *
   * @param dataType
   * @return
   */
  private String getUriPathByType(String dataType) {
    String uriPath;
    if (StringUtils.isNotEmpty(dataType)) {
      if ("datasources".equals(dataType)) {
        uriPath = "/api/datasources/";
      } else if ("dashboards".equals(dataType)) {
        uriPath = "/api/dashboards/";
      } else if ("charts".equals(dataType)) {
        uriPath = "/api/widgets/";
      } else {
        throw new RuntimeException("Unsupported data type - " + dataType);
      }
    } else {
      throw new RuntimeException("Invalid data type - " + dataType);
    }
    return uriPath;
  }

}
