package com.footlocker.azure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.LineProcessor;
import com.microsoft.azure.documentdb.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeneralUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static Object parseJson(byte[] jsonData,Class objectClass) throws IOException {
        return objectMapper.readValue(jsonData,objectClass);
    }

    static List<String> getLinesFromFile(File file, int startLine, int endLine) {
        try {
            return com.google.common.io.Files.readLines(file, Charsets.UTF_8,
                    new LineProcessor<List<String>>() {

                        List<String> processedLines = new ArrayList<>();
                        int counter = 0;

                        @Override
                        public boolean processLine(String s) throws IOException {
                            if (counter >= startLine && counter <= endLine) {
                                processedLines.add(s.replaceAll("\"","%22"));
                                counter++;
                                return true;
                            } else if (counter < startLine){
                                counter++;
                                return true;
                            } else {
                                return false;
                            }
                        }

                        @Override
                        public List<String> getResult() {
                            return processedLines;
                        }
                    });

        } catch (IOException e) {

        } catch (Exception e) {

        }

        return Collections.emptyList();
    }

    static void createDatabaseIfNotExists(DocumentClient client,String databaseName) throws DocumentClientException {
        String databaseLink = String.format("/dbs/%s", databaseName);

        // Check to verify the database exist
        try {
            client.readDatabase(databaseLink, null);
        } catch (DocumentClientException de) {
            // If the database does not exist, create a new database
            if (de.getStatusCode() == 404) {
                Database database = new Database();
                database.setId(databaseName);
                client.createDatabase(database, null);
            } else {
                throw de;
            }
        }
    }

    static void createDocumentCollectionIfNotExists(DocumentClient client,String databaseName, String collectionName) throws IOException,
            DocumentClientException {
        String databaseLink = String.format("/dbs/%s", databaseName);
        String collectionLink = String.format("/dbs/%s/colls/%s", databaseName, collectionName);

        try {
            client.readCollection(collectionLink, null);
        } catch (DocumentClientException de) {
            // If the document collection does not exist, create a new
            // collection
            if (de.getStatusCode() == 404) {
                DocumentCollection collectionInfo = new DocumentCollection();
                collectionInfo.setId(collectionName);

                // Optionally, you can configure the indexing policy of a
                // collection. Here we configure collections for maximum query
                // flexibility including string range queries.
                RangeIndex index = new RangeIndex(DataType.String);
                index.setPrecision(-1);

                collectionInfo.setIndexingPolicy(new IndexingPolicy(new Index[] { index }));

                // DocumentDB collections can be reserved with throughput
                // specified in request units/second. 1 RU is a normalized
                // request equivalent to the read of a 1KB document. Here we
                // create a collection with 400 RU/s.
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.setOfferThroughput(400);

                client.createCollection(databaseLink, collectionInfo, requestOptions);
            } else {
                throw de;
            }
        }
    }

    static void createRowDocumentIfNotExists(DocumentClient client,String databaseName, String collectionName, Row row)
            throws DocumentClientException, IOException {
        try {
            String documentLink = String.format("/dbs/%s/colls/%s/docs/%s", databaseName, collectionName, row.getId());
            client.readDocument(documentLink, new RequestOptions());
        } catch (DocumentClientException de) {
            if (de.getStatusCode() == 404) {
                String collectionLink = String.format("/dbs/%s/colls/%s", databaseName, collectionName);
                client.createDocument(collectionLink, row, new RequestOptions(), true);
            } else {
                throw de;
            }
        }
    }


}
