package info.johtani.sample.es.client.search.ltr;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
import info.johtani.sample.es.client.Logger;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class EsSearchResult {

    private long totalHits = 0;
    private List<TMDBDocument> results;

    public void fromEsResponse(SearchResponse<TMDBDocument> response) {
        HitsMetadata<TMDBDocument> hits = response.hits();

        // count
        TotalHits totalHits = hits.total();
        if (totalHits.relation() != TotalHitsRelation.Eq) Logger.log(" +++ Not Exact count!!");
        this.totalHits = totalHits.value();
        this.results = new ArrayList<>();

        // convert res to result
        for (Hit<TMDBDocument> hit : hits.hits()) {
            results.add(convertDoc(hit));
        }

        // convert facet to result
        //TODO
    }

    private TMDBDocument convertDoc(Hit<TMDBDocument> hit) {
        return hit.source();
    }

    public void printResults() {
        //TODO
        Logger.log("--- print result ---");
        Logger.log("hit count: " + this.totalHits);
        int count = 0;
        if (this.results != null) {
            for (TMDBDocument result : this.results) {
                count++;
                Logger.log("doc[" + count + "]");
                result.printDoc();
            }
        } else {
            Logger.log("results is null");
        }
    }
}
