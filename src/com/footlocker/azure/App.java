package com.footlocker.azure;


import com.microsoft.azure.documentdb.ConnectionPolicy;
import com.microsoft.azure.documentdb.ConsistencyLevel;
import com.microsoft.azure.documentdb.DocumentClient;
import com.microsoft.azure.documentdb.DocumentClientException;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class App {

    public static void main(String[] args) throws IOException, DocumentClientException {
        String propertyFileLocation;
        if (args != null && args.length > 0) {
            propertyFileLocation = args[0];
        } else {
            propertyFileLocation = "runtime.properties";
        }
        Properties properties = new Properties();
        properties.load(new FileInputStream(propertyFileLocation));
        ConnectToCouch couch = new ConnectToCouch();
        String uri = properties.getProperty("uri");
        int noOfThreads = Integer.parseInt(properties.getProperty("threads"));
        int totalLineCount = Math.toIntExact(Files.lines(Paths.get(properties.getProperty("data"))).count());
        int fullRange = totalLineCount / noOfThreads;
        String serviceEndpoint = properties.getProperty("serviceEndpoint");
        String masterKey = properties.getProperty("masterKey");
        String databaseName = properties.getProperty("databaseName");
        String collectionName = properties.getProperty("collectionName");
        DocumentClient client = new DocumentClient(serviceEndpoint, masterKey, new ConnectionPolicy(), ConsistencyLevel.Session);
        Row row = (Row) GeneralUtils.parseJson(couch.getHttpResponse(uri).getBytes(), Row.class);
        GeneralUtils.createDatabaseIfNotExists(client, databaseName);
        GeneralUtils.createDocumentCollectionIfNotExists(client, databaseName, collectionName);
        GeneralUtils.createRowDocumentIfNotExists(client, databaseName, collectionName, row);
        client.close();
    }
}
