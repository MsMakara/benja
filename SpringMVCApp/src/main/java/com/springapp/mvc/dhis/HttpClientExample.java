package com.springapp.mvc.dhis;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import com.springapp.mvc.*;
import java.io.*;
import java.net.URL;

public class HttpClientExample
{
    User creds =new User();
    private final String USER_AGENT = "Mozilla/5.0";
    private String username=creds.getUsername();
    private String password=creds.getPassword();
  //  private URL url.=creds.getUrl();

    public static void main( String[] args )
        throws Exception
    {

     HttpClientExample http = new HttpClientExample();
          URL url=new URL("http://localhost:9090/dhis/api/dataSets.xml");
       System.out.println( "Testing 1 - Send Http GET request" );
        http.sendGet("admin","district","http://localhost:9090/dhis/api/dataSets.xml");
      /*
       System.out.println("\nTesting 2 - Send Http POST request");
          http.sendPost();   */

    }
    private static StringWriter readF() {
        String filename;
        StringWriter xmlReport = new StringWriter();
        filename= "/home/dubdabasoduba/git/SpringMVCApp/xml/benja.xml";
        File file = new File(filename);
        System.out.println(filename);
        try
        {
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;

            while ((strLine = br.readLine()) != null)   {
                xmlReport.write(strLine);
            }
            System.out.println(xmlReport);
        }
        catch (Exception e){}
        return xmlReport;
    }
    // HTTP GET request
    public void sendGet(String username,String password,String url)
        throws Exception
    {

     //  String url = "http://localhost:8090/dhis/api/dataSets.xml";

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet( url);
        DefaultHttpClient httpclient = new DefaultHttpClient();
        BasicHttpContext localcontext= new BasicHttpContext();
        // add request header
        Credentials creds = new UsernamePasswordCredentials( username, password );
        Header bs = new BasicScheme().authenticate(creds, request,localcontext);
        request.addHeader( "Authorization", bs.getValue() );
        request.addHeader( "Content-Type", "application/xml" );
        request.addHeader( "Accept", "application/xml" );
        //  request.addHeader("User-Agent", USER_AGENT);

        HttpResponse response = client.execute( request );

        System.out.println( "\nSending 'GET' request to URL : " + url );
        System.out.println( "Response Code : " + response.getStatusLine().getStatusCode() );

        BufferedReader rd = new BufferedReader( new InputStreamReader( response.getEntity().getContent() ) );

        StringBuffer result = new StringBuffer();

        String line = "";
        while ( (line = rd.readLine()) != null )
        {
            result.append( line );
        }

        System.out.println( result.toString() );

    }

    // HTTP POST request
    public void sendPost()
        throws Exception
    {


       URL url =new URL( "http://localhost:9090/dhis/api/dataValueSets");
        StringWriter poster=readF();

        HttpPost post = new HttpPost(url.getPath());

        // add header
        String host = url.getHost();
        int port = url.getPort();

        HttpHost targetHost = new HttpHost( host, port, url.getProtocol() );
        // add header
        DefaultHttpClient httpclient = new DefaultHttpClient();
        BasicHttpContext localcontext = new BasicHttpContext();
        // add request header
        Credentials creds = new UsernamePasswordCredentials( "admin", "district" );
        Header bs = new BasicScheme().authenticate( creds, post, localcontext );
        post.addHeader( "Authorization", bs.getValue() );
        post.addHeader( "Content-Type", "application/xml" );
        post.addHeader( "Accept", "application/xml" );

        post.setEntity( new StringEntity(poster.toString() ) );
        HttpResponse response = httpclient.execute(targetHost, post, localcontext );
        HttpEntity entity = response.getEntity();

        System.out.println( "\nSending 'POST' request to URL : " + url );
        System.out.println( "Post parameters : " + post.getEntity() );
        System.out.println( "Response Code : " + response.getStatusLine().getStatusCode() );

        BufferedReader rd = new BufferedReader( new InputStreamReader( response.getEntity().getContent() ) );

        StringBuffer result = new StringBuffer();

        String line = "";
        while ( (line = rd.readLine()) != null )
        {
            result.append( line );
        }

        System.out.println( result.toString() );

    }

}