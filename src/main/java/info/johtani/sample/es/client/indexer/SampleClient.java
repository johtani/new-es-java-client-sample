package info.johtani.sample.es.client.indexer;

import info.johtani.sample.es.client.Logger;

import java.io.IOException;

public class SampleClient {

    public static void main(String[] args) {

        EsIndexService service = new EsIndexService();
        service.init();
        String newIndexName = "wikipedia-new-index";
        String oldIndexName = "wikipedia-old-index";
        String aliasName = "wikipedia-current";

        // add index template
        Logger.log("Put index template...");
        CreateTemplateRequest tReq = new CreateTemplateRequest();
        tReq.setName("wikipedia-index-template");
        CreateTemplateResult indexTemplateResult = service.createIndexTemplate(tReq);
        if (indexTemplateResult.isError()) {
            System.exit(1);
        }

        // check index existence new
        Logger.log("Check existence of index");
        try {
            boolean exist = service.existsIndex(newIndexName);
            if (exist) {
                Logger.log("Already exist ["+newIndexName+"]...");
                System.exit(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("Something wrong...");
            System.exit(1);
        }

        // bulk index with template
        Logger.log("Bulk indexing sample documents");
        try {
            IndexResult indexResult = service.bulkIndex(DummyWikipediaData.sampleDataList(), newIndexName);
            if (indexResult.isError()) {
                System.exit(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("Something wrong...");
            System.exit(1);
        }

        // check index existence new and old
        Logger.log("Check existence of new and old indices");
        try {
            boolean exist = service.existsIndex(newIndexName);
            if (!exist) {
                Logger.log("Does not exist ["+newIndexName+"]...");
                System.exit(1);
            }
            exist = service.existsIndex(oldIndexName);
            if (!exist) {
                Logger.log("Does not exist ["+oldIndexName+"]...");
                System.exit(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("Something wrong...");
            System.exit(1);
        }

        // check count new and old index
        Logger.log("Counting new index...");
        long newCount = -1;
        try {
            newCount = service.countDocs(newIndexName);
            Logger.log(newIndexName + " has " + newCount + " docs");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Logger.log("Counting new index...");
        long oldCount = -1;
        try {
            oldCount = service.countDocs(oldIndexName);
            Logger.log(oldIndexName + " has " + oldCount + " docs");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (oldCount != newCount) {
            Logger.log("Warn counts are difference.");
            System.exit(1);
        }


        // switch alias
        AliasResult aliasResult = service.switchAlias(aliasName, oldIndexName, newIndexName);
        if (aliasResult.isError()) {
            Logger.log("Switching alias has problems...");
            System.exit(1);
        }
        // finish
        Logger.log("Success all operations!");
        Logger.log("Cleaning up...");

        System.exit(0);

    }
}
