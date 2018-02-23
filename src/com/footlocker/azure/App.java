package com.footlocker.azure;


import com.microsoft.azure.documentdb.ConnectionPolicy;
import com.microsoft.azure.documentdb.ConsistencyLevel;
import com.microsoft.azure.documentdb.DocumentClient;
import com.microsoft.azure.documentdb.DocumentClientException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {

    public static void main(String[] args) throws IOException, DocumentClientException {
        // Get the location of the property file as an argument otherwise default to what we have
        String propertyFileLocation;
        if (args != null && args.length > 0) {
            propertyFileLocation = args[0];
        } else {
            propertyFileLocation = "runtime.properties";
        }

        //Fetch the properties
        Properties properties = new Properties();
        properties.load(new FileInputStream(propertyFileLocation));
        int noOfThreads = Integer.parseInt(properties.getProperty("threads"));
        String serviceEndpoint = properties.getProperty("serviceEndpoint");
        String masterKey = properties.getProperty("masterKey");
        String databaseName = properties.getProperty("databaseName");
        String collectionName = properties.getProperty("collectionName");
        int totalLineCount = Math.toIntExact(Files.lines(Paths.get(properties.getProperty("data"))).count());

        //Initialize the DocumentDB
        final DocumentClient client = new DocumentClient(serviceEndpoint, masterKey, new ConnectionPolicy(), ConsistencyLevel.Session);
        GeneralUtils.createDatabaseIfNotExists(client, databaseName);
        GeneralUtils.createDocumentCollectionIfNotExists(client, databaseName, collectionName);

        //Initialize other variables
        ConnectToCouch couch = new ConnectToCouch();
        int fullRange = totalLineCount / noOfThreads;

        //Bring in the Executor Service!
        ExecutorService executor = Executors.newFixedThreadPool(noOfThreads);
        Date startDate = new Date();
        for (int i=0;i<noOfThreads;i++){
            if (i != (noOfThreads -1)){
                List<String> fileList = GeneralUtils.getLinesFromFile(new File(properties.getProperty("data")),fullRange*i,fullRange*(i+1)-1);
                Runnable worker = new InsertJson(client,databaseName,collectionName,fileList,couch);
                executor.execute(worker);
            }else {
                List<String> fileList = GeneralUtils.getLinesFromFile(new File(properties.getProperty("data")),fullRange*i,totalLineCount);
                Runnable worker = new InsertJson(client,databaseName,collectionName,fileList,couch);
                executor.execute(worker);
            }
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }

        System.out.println("Finished all threads in "+((new Date()).getTime() - startDate.getTime())/1000 +" second");

        client.close();

    }
}
