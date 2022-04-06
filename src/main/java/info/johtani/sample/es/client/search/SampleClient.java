package info.johtani.sample.es.client.search;

import info.johtani.sample.es.client.Logger;

public class SampleClient {

    public static void main(String[] args) {
        EsService service = new EsService();
        service.init();
        EsSearchRequest request = new EsSearchRequest("title", "コシヒカリ");
        Logger.log("starting search: " + request.printQuery());

        EsSearchResult result = service.search(request);
        result.printResults();

        Logger.log("finish searching");
        System.exit(0);
    }

}
