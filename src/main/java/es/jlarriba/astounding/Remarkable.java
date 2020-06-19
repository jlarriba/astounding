package es.jlarriba.astounding;

import es.jlarriba.jrmapi.Jrmapi;
import es.jlarriba.jrmapi.model.Document;
import java.util.ArrayList;
import java.util.List;

public class Remarkable {
    
    private static Remarkable remarkable;
    
    private Jrmapi api;
    
    private List<Document> docs;
    
    private Remarkable(){}
    
    public static Remarkable getInstance() {
        if (remarkable == null) {
            remarkable = new Remarkable();
            return remarkable;
        } else {
            return remarkable;
        }
    }
    
    public void init() {
        remarkable.setApi(new Jrmapi());
        remarkable.setDocs(remarkable.getApi().listDocs());
    }

    public Jrmapi getApi() {
        return api;
    }

    public void setApi(Jrmapi api) {
        this.api = api;
    }

    public List<Document> getDocs() {
        return docs;
    }

    public void setDocs(List<Document> docs) {
        this.docs = docs;
    }

    public Document getDocById(String id) {
        for (var doc:this.getDocs()) {
            if (doc.getID().equals(id)) {
                return doc;
            }
        }
        return null;
    }

    public List<Document> loadEbooksByDirectory(String dirId) {
        List<Document> docsByDir = new ArrayList<>();
        for (var doc:this.getDocs()) {
            if (doc.getParent().equals(dirId) && doc.getType().equals("DocumentType")) {
                docsByDir.add(doc);
            }
        }
        return docsByDir;
    }

    public List<Document> loadCollectionsByDirectory(String dirId) {
        List<Document> collsByDir = new ArrayList<>();
        for (var doc:this.getDocs()) {
            if (doc.getType().equals("CollectionType") && doc.getParent().equals(dirId)) {
                collsByDir.add(doc);
            }
        }
        return collsByDir;
    }
}
