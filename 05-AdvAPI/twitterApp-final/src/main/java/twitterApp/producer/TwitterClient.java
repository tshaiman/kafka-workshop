package twitterApp.producer;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class TwitterClient {

    static Config config = ConfigFactory.load();

    public static Client createTwitterClient(BlockingQueue<String> msgQueue,String... elements){
        /** Set up your blocking queues: Be sure to size these properly based on expected TPS of your stream */

        /** Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth) */
        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
        // Optional: set up some followings and track terms
        List<String> terms = Lists.newArrayList(elements);
        hosebirdEndpoint.trackTerms(terms);


        String ckey = config.getString("twitter.consumer-key");
        String cSecret = config.getString("twitter.consumer-secret");
        String aToken = config.getString("twitter.access-token");
        String aSecret = config.getString("twitter.access-secret");

        // These secrets should be read from a config file
        Authentication hosebirdAuth = new OAuth1(ckey, cSecret,aToken,aSecret);


        ClientBuilder builder = new ClientBuilder()
                .name("Hosebird-Client-01")    // optional: mainly for the logs
                .hosts(hosebirdHosts)
                .authentication(hosebirdAuth)
                .endpoint(hosebirdEndpoint)
                .processor(new StringDelimitedProcessor(msgQueue));


        Client hosebirdClient = builder.build();
        // Attempts to establish a connection.
        hosebirdClient.connect();

        return hosebirdClient;
    }
}
