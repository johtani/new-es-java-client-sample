package info.johtani.sample.es.client.indexer;

import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch.indices.IndexSettings;
import co.elastic.clients.elasticsearch.indices.PutIndexTemplateRequest;
import co.elastic.clients.elasticsearch.indices.put_index_template.IndexTemplateMapping;

import java.io.IOException;
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
        IndexSettings.Builder settings = buildSettings();

        // mappings
        TypeMapping.Builder mappings = buildMappings();

        IndexTemplateMapping.Builder templateBuilder = new IndexTemplateMapping.Builder();

        templateBuilder.settings(settings.build());
        templateBuilder.mappings(mappings.build());
        reqBuilder.template(templateBuilder.build());
        return reqBuilder.build();
    }

    private IndexSettings.Builder buildSettings() {
        IndexSettings.Builder builder = new IndexSettings.Builder();
        builder.numberOfReplicas("0");
        builder.numberOfShards("3");
        return builder;
    }

    private TypeMapping.Builder buildMappings(){
        TypeMapping.Builder builder = new TypeMapping.Builder();
        TypeMapping.of(mappings ->
                mappings.properties("id", p -> p.keyword(v -> v.name("id")))
                        .properties("revision_id", p -> p.keyword(v -> v.name("revision_id")))
                        .properties("timestamp", p-> p.date(v -> v.name("timestamp")))
                        .properties("title", p -> p.text(v -> v.name("title")
                                        .analyzer("kuromoji")
                                        .fields("keyword", fp -> fp.keyword(fv -> fv.name("keyword")
                                                        .ignoreAbove(256)
                                                ))))
                        .properties("headings", p -> p.text(v -> v.name("headings")
                                .analyzer("kuromoji")
                                .fields("keyword", fp -> fp.keyword(fv -> fv.name("keyword")
                                        .ignoreAbove(256)))))
                        .properties("categories", p -> p.keyword(v -> v.name("categories")))
                        .properties("contents", p -> p.text(v -> v.name("contents")
                                .analyzer("kuromoji")
                                .fields("keyword", fp -> fp.keyword(fv -> fv.name("keyword")
                                        .ignoreAbove(256)))))
                        .properties("images", p -> p.nested(v ->
                                v.properties("target", np -> np.keyword(nv -> nv.name("target")))
                                        .properties("target_type", np -> np.keyword(nv -> nv.name("keyword")))
                                        .properties("text", np -> np.nested(nv ->
                                                nv.properties("text", nnp -> nnp.text(nnv -> nnv.name("text")
                                                        .analyzer("kuromoji")
                                                        .fields("keyword", kp -> kp.keyword(kv -> kv.name("keyword")
                                                                .ignoreAbove(256)))))
                                                        .properties("link_target", nnp -> nnp.keyword(nnv -> nnv.name("keyword")))))))

                        .properties("links", p -> p.nested(v ->
                                v.properties("text", np -> np.nested(nv ->
                                        nv.properties("text", nnp -> nnp.text(nnv -> nnv.name("text")
                                                        .analyzer("kuromoji")
                                                        .fields("keyword", kp -> kp.keyword(kv -> kv.name("keyword")
                                                                .ignoreAbove(256)))))
                                                .properties("link_target", nnp -> nnp.keyword(nnv -> nnv.name("keyword")))))))
        );
        return builder;
    }

}

