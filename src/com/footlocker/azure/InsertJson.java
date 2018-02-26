package com.footlocker.azure;

import com.microsoft.azure.documentdb.DocumentClient;
import com.microsoft.azure.documentdb.DocumentClientException;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
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
        List<String> remainder = new ArrayList<>();
            for (String url:this.fileList){
                try {
                    GeneralUtils.createRowDocumentIfNotExists(this.client, this.databaseName, this.collectionName, (Row) GeneralUtils.parseJson(couch.getHttpResponse(url).getBytes(), Row.class));
                } catch (Exception e) {
                    remainder.add(url);
                    e.printStackTrace();
                }
            }
            while (true){
                if (CollectionUtils.isEmpty(remainder)){
                    break;
                }
                ArrayList<String> listForOperation = new ArrayList<>();
                listForOperation.addAll(remainder);
                remainder  = new ArrayList<>();
                for (String url:listForOperation){
                    try {
                        GeneralUtils.createRowDocumentIfNotExists(this.client, this.databaseName, this.collectionName, (Row) GeneralUtils.parseJson(couch.getHttpResponse(url).getBytes(), Row.class));
                    } catch (Exception e) {
                        remainder.add(url);
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(" Finished Thread "+ ++counter);
    }
}
