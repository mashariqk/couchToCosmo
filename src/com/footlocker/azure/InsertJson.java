package com.footlocker.azure;

import com.microsoft.azure.documentdb.DocumentClient;
import com.microsoft.azure.documentdb.DocumentClientException;

import java.io.IOException;
import java.util.List;

public class InsertJson implements Runnable{

    private DocumentClient client;
    private String databaseName;
    private String collectionName;
    List<String> fileList;
    ConnectToCouch couch;
    private static int counter = 0;

    public InsertJson(DocumentClient client, String databaseName, String collectionName, List<String> fileList, ConnectToCouch couch) {
        this.client = client;
        this.databaseName = databaseName;
        this.collectionName = collectionName;
        this.fileList = fileList;
        this.couch = couch;
    }

    @Override
    public void run() {
        try {
            for (String url:this.fileList){
                GeneralUtils.createRowDocumentIfNotExists(this.client, this.databaseName, this.collectionName, (Row) GeneralUtils.parseJson(couch.getHttpResponse(url).getBytes(), Row.class));
            }
            System.out.println(" Finished Thread "+ ++counter);
        } catch (DocumentClientException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
