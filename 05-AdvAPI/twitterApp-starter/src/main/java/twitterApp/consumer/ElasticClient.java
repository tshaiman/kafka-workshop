package twitterApp.consumer;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

public class ElasticClient {

    static Config config = ConfigFactory.load();

    public static RestHighLevelClient createClient(){
        String hostname = config.getString("elastic.hostname");
        String username = config.getString("elastic.username");
        String password = config.getString("elastic.password");


        final CredentialsProvider credProvider = new BasicCredentialsProvider();

        credProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials(username,password));

        RestClientBuilder builder = RestClient.builder(
                new HttpHost(hostname,443,"https"))
                .setHttpClientConfigCallback(httpAsyncClientBuilder ->
                        httpAsyncClientBuilder.setDefaultCredentialsProvider(credProvider));

        return new RestHighLevelClient(builder);
    }
}
