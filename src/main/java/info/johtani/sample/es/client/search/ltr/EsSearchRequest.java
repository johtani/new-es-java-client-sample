package info.johtani.sample.es.client.search.ltr;


import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.search.Rescore;
import co.elastic.clients.elasticsearch.core.search.RescoreQuery;

public class EsSearchRequest {

    //TODO support complex bool conditions
    private String searchText;

    //TODO support complex bool conditions
    private String fieldName;

    //TODO filter conditions

    //TODO sort condition

    public EsSearchRequest(String field, String searchText) {
        this.fieldName = field;
        this.searchText = searchText;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    // たぶん、builderとかにして外に出すのがよさそう
    public SearchRequest buildEsSearchResult() {
        Query query = new Query.Builder()
                .bool(
                        bool -> bool
                                .must(
                                    Query.of(
                                            q -> q.matchAll(f -> f))
                                )
                                .filter(
                                        Query.of(
                                            q -> q.match(
                                                    f -> f.field(fieldName).query(this.searchText)
                                            )
                                        )
                                )

                ).build();
        Rescore rescore = Rescore.of(
                r -> r
                    .windowSize(1000)
                    .query(
                            RescoreQuery.of(rq -> rq
                                            .query(Query.of(q -> q
                                                    ._custom("sltr", ltrQueryParams())
                                            ))
                                    )
                    )
        );
        SearchRequest esRequest = SearchRequest.of(r -> r
                .index("")
                .query(query)
                .rescore(rescore)
        );
        return esRequest;
    }

//    static Reader ltrQuery = new StringReader("\n{" +
//            "        \"sltr\": {\n" +
//            "          \"params\": {},\n" +
//            "          \"model\": \"latest\"\n" +
//            "        }}"
//
//    );

    private Map<String,Object> ltrQueryParams() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("params", new HashMap<String, Object>());
        params.put("model", "latest");
        return params;
    }
}
