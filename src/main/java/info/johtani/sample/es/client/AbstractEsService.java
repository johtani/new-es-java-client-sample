package info.johtani.sample.es.client;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

public class AbstractEsService {

    protected ElasticsearchClient client;

    // クライアントの初期化
    public void init() {
        this.init("192.168.1.240");
    }
    public void init(String hostname) {
        client = new ElasticsearchClient(
                new RestClientTransport(
                        RestClient.builder(
                                new HttpHost(hostname, 9200, "http")
                        ).build(),
                        new JacksonJsonpMapper()
                )
        );
    }
}
