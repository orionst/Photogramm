package com.orionst.mymaterialdesignapp.domain.model.entity.netstore;

public class QueryResult {
    private NetImage[] hits;

    public NetImage[] getImages() {
        return hits;
    }

    public void setImages(NetImage[] hits) {
        this.hits = hits;
    }
}
