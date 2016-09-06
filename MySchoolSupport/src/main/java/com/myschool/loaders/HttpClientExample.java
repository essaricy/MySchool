package com.myschool.loaders;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HttpClientExample {

    public static void main(String[] args) {
        //galleryNames();
        galleryItemNames();
        //getdesc();
    }

    private static void getdesc() {
        String response = httpGet("http://192.168.0.12:8080/demo/features/desc.xml");
        Document document = Jsoup.parse(response);
        Elements resources = document.select("resource");
        if (resources != null && !resources.isEmpty()) {
            for (Element resource : resources) {
                System.out.println("id = " + resource.attr("id"));
                System.out.println("short-desc = " + resource.attr("short-desc"));
                System.out.println("long-desc = " + resource.attr("long-desc"));
                /*String text = link.text();
                if (text != null && text.trim().length() != 0) {
                    text= text.trim().replaceAll(">", "").replaceAll("<", "");
                    if (!text.equalsIgnoreCase("Parent Directory") && !text.equalsIgnoreCase("PASSPORT") && !text.equalsIgnoreCase("THUMBNAIL")) {
                    }
                }*/
            }
        }
    }

    private static void galleryItemNames() {
        String response = httpGet("http://192.168.0.12:8080/demo/gallery/Brighty");
        Document document = Jsoup.parse(response);
        //System.out.println(document);
        Elements links = document.select("a");
        if (links != null && !links.isEmpty()) {
            for (Element link : links) {
                String text = link.text();
                if (text != null && text.trim().length() != 0) {
                    //text= text.trim().replaceAll(">", "").replaceAll("<", "");
                    if (!text.equalsIgnoreCase("<Parent Directory>") && !text.equalsIgnoreCase("<PASSPORT>") && !text.equalsIgnoreCase("<THUMBNAIL>")) {
                        System.out.println(text);
                    }
                }
            }
        }
    }

    private static void galleryNames() {
        String response = httpGet("http://192.168.0.12:8080/demo/gallery");
        Document document = Jsoup.parse(response);
        //System.out.println(document);
        Elements links = document.select("a");
        if (links != null && !links.isEmpty()) {
            for (Element link : links) {
                String text = link.text();
                if (text != null && text.trim().length() != 0) {
                    text= text.trim().replaceAll(">", "").replaceAll("<", "");
                    if (!text.equalsIgnoreCase("Parent Directory")) {
                        System.out.println(text);
                    }
                }
            }
        }
    }

    public static String httpGet(String url) {
        HttpURLConnection con = null;
        InputStream is = null;
        try {
            con = (HttpURLConnection) new URL(url).openConnection();
            con.connect();

            // 4xx: client error, 5xx: server error. See:
            // http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html.
            boolean isError = con.getResponseCode() >= 400;
            // The normal input stream doesn't work in error-cases.
            is = isError ? con.getErrorStream() : con.getInputStream();

            String contentEncoding = con.getContentEncoding() != null ? con
                    .getContentEncoding() : "UTF-8";
            return IOUtils.toString(is, contentEncoding); // Apache Commons Lang
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
            if (con != null) {
                con.disconnect();
            }
        }
    }

    public static String excutePost(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;
        try {
            // Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            if (urlParameters != null) {
                connection.setRequestProperty("Content-Length",
                        Integer.toString(urlParameters.getBytes().length));
                // Send request
                DataOutputStream wr = new DataOutputStream(
                        connection.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.close();
            }
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            // Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if
                                                          // not Java 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
