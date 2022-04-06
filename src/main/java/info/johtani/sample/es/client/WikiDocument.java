package info.johtani.sample.es.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

//Jacksonを利用したJSONデシリアライズなので、必要でない項目の処理などが別途必要
@JsonIgnoreProperties(ignoreUnknown=true)
public class WikiDocument {

    private String id;
    private String title;
    private List<String> categories;
    private List<String> headings;
    private String revision_id;

    // JacksonでのJSONからインスタンス化するために必要なので追加
    public WikiDocument() {}

    public WikiDocument(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getHeadings() {
        return headings;
    }

    public void setHeadings(List<String> headings) {
        this.headings = headings;
    }

    public String getRevision_id() {
        return revision_id;
    }

    public void setRevision_id(String revision_id) {
        this.revision_id = revision_id;
    }

    public void printDoc() {
        Logger.log(" title: ["+this.title+"]");
        //TODO
    }
}
