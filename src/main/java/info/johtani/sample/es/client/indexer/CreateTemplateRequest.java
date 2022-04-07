package info.johtani.sample.es.client.indexer;

import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch.indices.IndexSettings;
import co.elastic.clients.elasticsearch.indices.PutIndexTemplateRequest;
import co.elastic.clients.elasticsearch.indices.put_index_template.IndexTemplateMapping;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class CreateTemplateRequest {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PutIndexTemplateRequest buildEsRequest() throws IOException {
        PutIndexTemplateRequest.Builder reqBuilder = new PutIndexTemplateRequest.Builder();
        reqBuilder.name(name);

        List<String> indexPattern = new ArrayList<>();
        indexPattern.add("wikipedia-*");
        reqBuilder.indexPatterns(indexPattern);

        // sample index settings/mappings from https://github.com/johtani/wiki-json-loader/blob/master/sample/elasticsearch/index_schema.json
        // settings
        IndexSettings settings = buildSettings();

        // mappings
        TypeMapping mappings = buildMappings();

        IndexTemplateMapping.Builder templateBuilder = new IndexTemplateMapping.Builder();
        templateBuilder.settings(settings);
        templateBuilder.mappings(mappings);
        reqBuilder.template(templateBuilder.build());
        return reqBuilder.build();
    }

    // Builderを利用するサンプル
    private IndexSettings buildSettings() {
        IndexSettings.Builder builder = new IndexSettings.Builder();
        builder.numberOfReplicas("0");
        builder.numberOfShards("3");
        builder.refreshInterval(Time.of(t -> t.time("0")));
        return builder.build();
    }

    // of()メソッドを利用するサンプル
    private TypeMapping buildMappings() {
        // After v7.12.2
        //return TypeMapping.of(mappings -> mappings.withJson(WikipediaMappings.mappingJson));
        // Until v7.12.1
        return TypeMapping.of(mappings ->
                mappings.properties("id", p -> p.keyword(v -> v))
                        .properties("revision_id", p -> p.keyword(v -> v))
                        .properties("timestamp", p -> p.date(v -> v))
                        .properties("title", p -> p.text(v -> v
                                .analyzer("kuromoji")
                                .fields("keyword", fp -> fp.keyword(fv -> fv
                                        .ignoreAbove(256)
                                ))))
                        .properties("headings", p -> p.text(v -> v
                                .analyzer("kuromoji")
                                .fields("keyword", fp -> fp.keyword(fv -> fv
                                        .ignoreAbove(256)))))
                        .properties("categories", p -> p.keyword(v -> v))
                        .properties("contents", p -> p.text(v -> v
                                .analyzer("kuromoji")
                                .fields("keyword", fp -> fp.keyword(fv -> fv
                                        .ignoreAbove(256)))))
                        .properties("images", p -> p.nested(v -> v
                                .properties("taget", np -> np.keyword(nv -> nv))
                                .properties("target_type", np -> np.keyword(nv -> nv))
                                .properties("text", np -> np.text(nv -> nv
                                        .analyzer("kuromoji")
                                        .fields("keyword", kp -> kp.keyword(kv -> kv
                                                .ignoreAbove(256)))))
                                .properties("link_target", np -> np.keyword(nv -> nv))
                        ))
                        .properties("links", p -> p.nested(v -> v
                                .properties("text", np -> np.text(nv -> nv
                                        .analyzer("kuromoji")
                                        .fields("keyword", kp -> kp.keyword(kv -> kv
                                                .ignoreAbove(256)))))
                                .properties("link_target", np -> np.keyword(nv -> nv))
                        ))
        );
    }
}

/**
 * After v7.12.2
 * See https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/7.17/loading-json.html
 */
class WikipediaMappings {


    static Reader mappingJson = new StringReader("{\n" +
            "      \"properties\" : {\n" +
            "        \"id\" : {\n" +
            "          \"type\" : \"keyword\"\n" +
            "        },\n" +
            "        \"revision_id\": {\n" +
            "          \"type\": \"keyword\"\n" +
            "        },\n" +
            "        \"timestamp\": {\n" +
            "          \"type\": \"date\"\n" +
            "        },\n" +
            "        \"title\" : {\n" +
            "          \"type\" : \"text\",\n" +
            "          \"analyzer\": \"kuromoji\",\n" +
            "          \"fields\" : {\n" +
            "            \"keyword\" : {\n" +
            "              \"type\" : \"keyword\",\n" +
            "              \"ignore_above\" : 256\n" +
            "            }\n" +
            "          }\n" +
            "        },\n" +
            "        \"headings\" : {\n" +
            "          \"type\" : \"text\",\n" +
            "          \"analyzer\": \"kuromoji\",\n" +
            "          \"fields\" : {\n" +
            "            \"keyword\" : {\n" +
            "              \"type\" : \"keyword\",\n" +
            "              \"ignore_above\" : 256\n" +
            "            }\n" +
            "          }\n" +
            "        },\n" +
            "        \"categories\" : {\n" +
            "          \"type\" : \"keyword\"\n" +
            "        },\n" +
            "        \"contents\" : {\n" +
            "          \"type\" : \"text\",\n" +
            "          \"analyzer\": \"kuromoji\",\n" +
            "          \"fields\" : {\n" +
            "            \"keyword\" : {\n" +
            "              \"type\" : \"keyword\",\n" +
            "              \"ignore_above\" : 256\n" +
            "            }\n" +
            "          }\n" +
            "        },\n" +
            "        \"images\": {\n" +
            "          \"type\": \"nested\",\n" +
            "           \"properties\": {\n" +
            "             \"taget\": {\n" +
            "               \"type\": \"keyword\"\n" +
            "             },\n" +
            "             \"target_type\": {\n" +
            "               \"type\": \"keyword\"\n" +
            "             },\n" +
            "             \"text\": {\n" +
            "               \"type\": \"nested\",\n" +
            "               \"properties\": {\n" +
            "                 \"text\": {\n" +
            "                   \"type\" : \"text\",\n" +
            "                   \"analyzer\": \"kuromoji\",\n" +
            "                   \"fields\" : {\n" +
            "                     \"keyword\" : {\n" +
            "                       \"type\" : \"keyword\",\n" +
            "                       \"ignore_above\" : 256\n" +
            "                     }\n" +
            "                   }\n" +
            "                 },\n" +
            "                 \"link_target\": {\n" +
            "                   \"type\": \"keyword\"\n" +
            "                 }\n" +
            "               }\n" +
            "             }\n" +
            "           }\n" +
            "        },\n" +
            "        \"links\": {\n" +
            "          \"type\": \"nested\",\n" +
            "          \"properties\": {\n" +
            "            \"text\": {\n" +
            "              \"type\" : \"text\",\n" +
            "              \"analyzer\": \"kuromoji\",\n" +
            "              \"fields\" : {\n" +
            "                \"keyword\" : {\n" +
            "                  \"type\" : \"keyword\",\n" +
            "                  \"ignore_above\" : 256\n" +
            "                }\n" +
            "              }\n" +
            "            },\n" +
            "            \"link_target\": {\n" +
            "              \"type\": \"keyword\"\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    }");
}

