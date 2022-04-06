package info.johtani.sample.es.client.search;


import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
import info.johtani.sample.es.client.Logger;
import info.johtani.sample.es.client.WikiDocument;

import java.util.ArrayList;
import java.util.List;

public class EsSearchResult {

    private long totalHits = 0;
    private List<WikiDocument> results;

    public void fromEsResponse(SearchResponse<WikiDocument> response) {
        HitsMetadata<WikiDocument> hits = response.hits();

        // count
        TotalHits totalHits = hits.total();
        if (totalHits.relation() != TotalHitsRelation.Eq) Logger.log(" +++ Not Exact count!!");
        this.totalHits = totalHits.value();
        this.results = new ArrayList<>();


        // convert res to result
        for (Hit<WikiDocument> hit : hits.hits()) {
            results.add(convertDoc(hit));
        }

        // convert facet to result
        //TODO
    }

    private WikiDocument convertDoc(Hit<WikiDocument> hit) {
        // TODO 特殊処理をしているような場合にどうなるかはチェックが必要
        return hit.source();
    }

    public void printResults() {
        //TODO
        Logger.log("--- print result ---");
        Logger.log("hit count: " + this.totalHits);
        int count = 0;
        for (WikiDocument result : this.results) {
            count++;
            Logger.log("doc[" + count +"]");
            result.printDoc();
        }
    }
}
