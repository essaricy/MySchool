package com.myschool.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.FieldPosition;
import java.text.MessageFormat;

import javax.net.ssl.HttpsURLConnection;

public class GoogleCaptchaResponseReader {

    private final String USER_AGENT = "Mozilla/5.0";

    // HTTP GET request
    private void sendGet() throws Exception {

        String url = "http://www.google.com/search?q=mkyong";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }

    // HTTP POST request
    private void sendPost(String secret, String captchaResponse) throws Exception {

        String url = "https://www.google.com/recaptcha/api/siteverify";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "secret={0}&response={1}";
        String data = MessageFormat.format(urlParameters, secret, captchaResponse);
        System.out.println("data=" + data);

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(data);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + data);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }

    public static void main(String[] args) throws Exception {
        GoogleCaptchaResponseReader reader = new GoogleCaptchaResponseReader();
        reader.sendPost("6LeZRQcUAAAAAKEaTxRq15lE0MY1jshHcySsvbn3", "03AHJ_VuvhpTyG0sWSOHI5BRM7_3ULlTdltF9ssPP5BskeiZzE0-asVztg_Pj-BwZ3AwpnZylTcaD6jqmhhT-qNb8NM021iwGJot74mRgW4cuwnMtL6qekmUNcUD-zXXBIs2I1YB9u54ua3jCzhUNs7tJC0pemL3v43w9kf_uqvTQqoVEyQGOswE6FdNBTwV6kot0CBSAj2xWr-nd77EOp5fV_eZOIACSu89JjXJO1mrbNoTYUAsBURevkG9HcbcbvZvLO2MU6cRF17XORu7jrQzWaCPs92yHR8UL2dcPGFehOoGiqobO8dXrzVrm_L1HvXjbPQKf1hl3cE9vKDz47iGLbcYg0U_Z0jamZ6xlXc-ATCfvJb95K7VxEyKQysoUH7Ec_cru3LiFSYG-e6L0MfJFi5yYZxG5U7mSWB6F5Gi_vQAFnqiPeA8D9vSMzxCx6jJEId9g-bTx-ov6SOnfL2UP7oqJDmRgdsN3LD-8c1dmVq8BQJfNb_0s0p9gT8Bg0sK5jRFHrWh4e1dtsn4ZhzxIdZk5xARLjGitng5Vld9-e-l9tSq54Pcd9r5CXXIn3pn_WmLBicMN2sGRh5dBYaS2ed4s6myLuMjltwXxwblKf_HrXDyqTjPYPYSUn_iSflPAwLpH28oFQj2Vbx4KMqyMlD3Cs_R52sKoKIgAyG_SnuZkk3063_hNLzT_Ul9K0jMUHx36IrKEVoR0XKsd--QkiQ1whrQRegPhNdTOZeBgZQL1dKZn08v9sKiKq-QMep9qn6eLBhMkoVBNSw32iu1TQ0hm3GDDjDBQbr66H0XS3jPPR88oiIgQQF0t1oLe349jzmpOE9iBQ3ZyQ9XOs7u3T8rUir4qlhLP7e-j_FsHs2ioKlBX7OwGUNwCa1JDUbATqv3hKw7mvDDVyGCsLpGn6z_jdNl12iKwAdH8xwBRraBMT2XjdRLWdLniTPR-7HfDVfGPzLi0Fxuiv5sQnU0euyy9wo47u5lnZ9PXp-fLlY_lTVfTwElc");
    }

}
