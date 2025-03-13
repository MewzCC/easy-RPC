package com.cc.protocol;

import com.cc.common.Invocation;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {

    public String send(String hostname, Integer port, Invocation invocation) {
        // 发送请求 (读取用户配置)
        try {
            URL url = new URL("http", hostname, port, "/");

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            // 发送请求类型
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            // 配置
            OutputStream outputStream = httpURLConnection.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);

            oos.writeObject(invocation);
            oos.flush();
            oos.close();

            // 读取响应
            InputStream inputStream = httpURLConnection.getInputStream();
            String result = IOUtils.toString(inputStream);
            return result;




          /*  // 读取响应
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return response.toString();
            } else {
                return "POST request failed: " + responseCode;
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
