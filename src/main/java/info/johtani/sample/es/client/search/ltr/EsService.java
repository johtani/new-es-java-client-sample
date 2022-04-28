package info.johtani.sample.es.client.search.ltr;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import info.johtani.sample.es.client.AbstractEsService;
import info.johtani.sample.es.client.Logger;
import org.elasticsearch.client.RequestOptions;

import java.io.IOException;

public class EsService extends AbstractEsService {

    public EsSearchResult search(EsSearchRequest request) {
        EsSearchResult result = new EsSearchResult();

        try {
            SearchResponse<TMDBDocument> response = client.search(
                    request.buildEsSearchResult(),
                    TMDBDocument.class
            );
            result.fromEsResponse(response);
        } catch (ElasticsearchException ex) {
            // isValidResponseメソッドの代わりの処理がここに当たる
            // ステータスなどで問題があった場合はここに来る
            Logger.log("??? status: " + ex.status());
            Logger.log("??? type: " + ex.error().type());
            // そのほかの情報
            // Endpoint名
            Logger.log("??? endpoint: " + ex.endpointId());
            // あるかな？
            Logger.log("??? reason" + ex.response().error().reason());
        } catch (IOException e) {
            Logger.log("");
            e.printStackTrace();
        }

        return result;
    }
}
