package info.johtani.sample.es.client.search.ltr;

import info.johtani.sample.es.client.Logger;
public class SampleClient {


    // Run with hello-ltr demo's Elasticsearch server
    // See https://github.com/o19s/hello-ltr how to start demo server
    public static void main(String[] args) {
        EsService service = new EsService();
        service.init("localhost");
        EsSearchRequest request = new EsSearchRequest("title", "batman");

        EsSearchResult result = service.search(request);
        result.printResults();

        Logger.log("finish searching");
        System.exit(0);
    }
}
