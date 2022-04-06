package info.johtani.sample.es.client.search;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;

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
                .match(
                        q -> q
                                .field(getFieldName())
                                .query(getSearchText())
                ).build();

        SearchRequest esRequest = SearchRequest.of(
                r -> r.query(query)
                // TODO aggs
                // TODO sort option

        );
        return esRequest;
    }

    public String printQuery() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.fieldName).append(":").append(this.searchText);
        return builder.toString();
    }
}
