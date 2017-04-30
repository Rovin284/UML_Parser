/**
 * Created by rovinpatwal on 4/30/17.
 */


/*
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UmlGenerator {
    public static Boolean generatePNG(String grammar, String outPath) {
        try {
            String webLink = "http://yuml.me/diagram/plain/class/" + grammar
                    + ".png";
            URL url = new URL(webLink);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException(
                        "Failed : HTTP error code : " + conn.getResponseCode());
            }
            OutputStream outputStream = new FileOutputStream(new File(outPath));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = conn.getInputStream().read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.close();
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}*/


//package com.cakes;

        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.OutputStream;
        import java.net.URL;

public class UmlGenerator {
    
    public static void generatePNG(String grammar, String outPath) throws IOException {
        System.out.println("grammar "+grammar);
        String imageUrl = "https://yuml.me/diagram/plain/class/" + grammar + ".png";
        String destinationFile = "image.png";
        URL url = new URL(imageUrl);
        System.out.println("imageUrl --- "+imageUrl);
        InputStream is = url.openStream();
        System.out.println("imageUrl --- "+is);

        OutputStream os = new FileOutputStream(destinationFile);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }

}
