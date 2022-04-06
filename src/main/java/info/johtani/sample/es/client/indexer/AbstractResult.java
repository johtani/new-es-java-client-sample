package info.johtani.sample.es.client.indexer;

public class AbstractResult {
    private boolean error = true;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
