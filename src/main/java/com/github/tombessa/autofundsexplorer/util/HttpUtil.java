package com.github.tombessa.autofundsexplorer.util;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class HttpUtil {
    public static String get(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.setReadTimeout(5000);
        conn.setRequestProperty("Accept-Language", "pt-BR,pt;q=0.8,en-US;q=0.5,en;q=0.3");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:124.0) Gecko/20100101 Firefox/124.0");
        conn.setRequestProperty("Accept", "application/json, text/plain, */*");
        conn.setRequestProperty("x-funds-nonce", "61495f60b533cc40ad822e054998a3190ea9bca0d94791a1da");

        System.out.println("Request URL ... " + url);


        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));

        StringBuilder json = new StringBuilder();
        int c;
        while ((c = in.read()) != -1) {
            json.append((char) c);
        }

        in.close();
        return json.toString();
    }

}
