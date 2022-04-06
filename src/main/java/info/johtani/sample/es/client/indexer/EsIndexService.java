package info.johtani.sample.es.client.indexer;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.CountRequest;
import co.elastic.clients.elasticsearch.core.CountResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.indices.*;
import co.elastic.clients.elasticsearch.indices.update_aliases.Action;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.johtani.sample.es.client.AbstractEsService;
import info.johtani.sample.es.client.Logger;
import info.johtani.sample.es.client.WikiDocument;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class EsIndexService extends AbstractEsService {

    // JSONにして設定する必要があるため
    private static String toJsonString(WikiDocument doc) {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(doc);
        } catch (JsonProcessingException e) {
            Logger.log("to json error...");
            e.printStackTrace();
        }
        return json;
    }

    public CreateTemplateResult createIndexTemplate(CreateTemplateRequest req) {
        CreateTemplateResult result = new CreateTemplateResult();
        try {
            PutIndexTemplateRequest request = req.buildEsRequest();
            PutIndexTemplateResponse response = client.indices().putIndexTemplate(request);
            result.setError(!response.acknowledged());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public IndexResult bulkIndex(List<WikiDocument> docs, String indexName) throws IOException{
        IndexResult result = new IndexResult();
        BulkRequest request = BulkRequest.of(f -> f.operations(
                docs.stream().map(
                        doc -> BulkOperation.of(
                                b -> b.index(io -> io.index(indexName).id(doc.getId()).document(doc))
                        )
                ).collect(Collectors.toList()))
                .refresh(Refresh.True)
        );

        try {
            BulkResponse res = client.bulk(request);
            // FIXME error check
            result.setError(res.errors());
            if (result.isError()) {
                Logger.log("Bulk indexing has some failures");
                // Easy way to write a combined message
                Logger.log(res.items().stream().filter(f -> f.error() != null).map(f -> f.error().reason()).toString());
            } else {
                Logger.log("Bulk indexing success!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            // FIXME log errors
            Logger.log(e.getMessage());
            throw e;
        }

        return result;
    }

    public AliasResult switchAlias(String name, String oldIndex, String newIndex) {
        AliasResult result = new AliasResult();
        UpdateAliasesRequest request = UpdateAliasesRequest.of(f -> f
                .actions(Action.of(af -> af.add(v -> v.alias(name).index(newIndex))))
                .actions(Action.of(af -> af.remove(v -> v.alias(name).index(oldIndex))))
        );
        try {
            UpdateAliasesResponse res = client.indices().updateAliases(request);
            result.setError(!res.acknowledged());
            if (result.isError() == false) {
                Logger.log("switch alias request was failed. alias:\"" + name +
                        "\", add_index: \"" + newIndex + "\", old_index:\"" + oldIndex + "\"");
            }
        } catch (ElasticsearchException ee) {
            //Runtime Exception
            Logger.log(ee.getMessage());
            ee.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean existsIndex(String indexName) throws IOException {
        ExistsRequest request = ExistsRequest.of(f -> f.index(indexName));
        return client.indices().exists(request).value();
    }

    public DeleteResult deleteIndex(String indexName) throws IOException {
        DeleteResult result = new DeleteResult();
        DeleteIndexRequest request = DeleteIndexRequest.of(f -> f.index(indexName));
        try {
            DeleteIndexResponse response = client.indices().delete(request);
            result.setError(!response.acknowledged());
            if (result.isError() == false) {
                Logger.log("delete \"" + indexName + "\" index failed.");
            }
        } catch (ElasticsearchException ee) {
            //Runtime Exception
            Logger.log(ee.getMessage());
            ee.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }

    public long countDocs(String indexName) throws IOException {
        long count = -1;
        CountRequest request = CountRequest.of(f -> f.index(indexName));
        try {
            CountResponse res = client.count(request);
            count = res.count();
        } catch (ElasticsearchException ee) {
            //Runtime Exception
            Logger.log(ee.getMessage());
            ee.printStackTrace();
        } catch (IOException e) {
            // TODO 分かりやすくするためにトレース出しているだけ
            e.printStackTrace();
            throw e;
        }
        return count;
    }
}
