package com.footlocker.azure;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class ConnectToCouch {

    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

    public String getHttpResponse(String uri) throws IOException {
        HttpGet httpget = new HttpGet(uri.replaceAll("\"","%22"));
        HttpResponse response  = httpClient.execute(httpget);
        JSONObject jsonObject = new JSONObject(EntityUtils.toString(response.getEntity()));
        JSONArray jsonArray = (JSONArray) jsonObject.get("rows");
        return jsonArray.get(0).toString();
    }
}
