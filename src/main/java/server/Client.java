package server;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Client {
    public String path = "http://localhost:3399/hello";

    /**
     * sends json to the server.
     *
     * @param json to send
     * @param conn connection with the server
     * @return returns servers answer
     * @throws UnsupportedEncodingException encoding error
     * @throws IOException                  server communication error
     */
    public String sendJson(String json, HttpURLConnection conn)
        throws UnsupportedEncodingException, IOException {
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");


        conn.setRequestMethod("POST");
        conn.setConnectTimeout(500);
        conn.setReadTimeout(500);
        conn.setUseCaches(false);
        conn.setDoOutput(true);
        conn.setDoInput(true);

        OutputStream os = conn.getOutputStream();
        os.write(json.getBytes(StandardCharsets.UTF_8));

        // read the response
        InputStream in = new BufferedInputStream(conn.getInputStream());
        String result = IOUtils.toString(in, "UTF-8");

        conn.disconnect();
        return result;
    }

    /**
     * creates connection with the server.
     *
     * @param go link to go
     * @return returns connection
     * @throws IOException connection error
     */
    public HttpURLConnection connectServer(String go) throws IOException {
        URL url = new URL(go);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        System.setProperty("http.keepAlive", "false");
        return conn;
    }


}