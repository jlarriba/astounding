/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jlarriba.astounding;

import es.jlarriba.jrmapi.Jrmapi;
import es.jlarriba.jrmapi.model.Document;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author jlarriba
 */
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
    
    /**
     * @return the api
     */
    public Jrmapi getApi() {
        return api;
    }

    /**
     * @param api the api to set
     */
    public void setApi(Jrmapi api) {
        this.api = api;
    }

    /**
     * @return the docs
     */
    public List<Document> getDocs() {
        return docs;
    }

    /**
     * @param docs the docs to set
     */
    public void setDocs(List<Document> docs) {
        this.docs = docs;
    }
    
    public ObservableList<Document> loadEbooksByDirectory(String dirId) {
        List<Document> docsByDir = new ArrayList<>();
        for (var doc:this.getDocs()) {
            if (doc.getParent().equals(dirId)) {
                System.out.println("Adding doc to list " + doc.getVissibleName());
                docsByDir.add(doc);
            }
        }
        return FXCollections.observableList(docsByDir);
    }
}
