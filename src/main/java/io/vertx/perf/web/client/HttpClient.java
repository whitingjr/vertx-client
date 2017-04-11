package io.vertx.perf.web.client;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.buffer.impl.BufferFactoryImpl;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientRequest;

public class HttpClient extends AbstractVerticle {

   Buffer buf = null;
      // Convenience method so you can run it in your IDE
   public static void main(String[] args) {
      Runner.runExample(HttpClient.class);
   }

   @Override
     public void start() throws Exception {
       try {
          HttpClientRequest req = vertx.createHttpClient(new HttpClientOptions().setDefaultHost(System.getProperty("vertx.host", "localhost")))
                .post(8080, System.getProperty("vertx.host", "localhost"), System.getProperty("vertx.form"), resp -> {
             System.out.println("Response " + resp.statusCode()+ " message "+ resp.statusMessage() );
             vertx.close();
          });
   
          req.headers().set("content-length", "1024");
          req.end(buf);
       } catch (Exception e) {
          vertx.close();
       }
   }

   public HttpClient() {
      byte[] b = new byte[8];
      buf = new BufferFactoryImpl().buffer(1024);
      for (int i = 0; i < 1024; i += 8 ){
         buf.appendBytes(b);
      }
   }
}
