package com.springapp.mvc.dhis;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: kip
 * Date: 7/25/13
 * Time: 11:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class dhis
{

    public static final String REPORTS_METADATA_PATH = "/api/forms.xml";

    //  private String url="http://apps.dhis2.org/demo/api/indicators";
    private String url = " http://localhost:8090/dhis/api/indicators.xml";

    private String username = "admin";

    private String password = "district";

    /* String host = url.getHost();
     int port = url.getPort();
     HttpHost targetHost = new HttpHost( host, port, url.getProtocol() );   */
    DefaultHttpClient httpclient = new DefaultHttpClient();

    BasicHttpContext localcontext = new BasicHttpContext();

    public void post()
        throws Exception
    {
        try
        {

            HttpGet httpPost = new HttpGet( url );
            Credentials creds = new UsernamePasswordCredentials( username, password );
            Header bs = new BasicScheme().authenticate( creds, httpPost, localcontext );
            // httpPost.addHeader( "Authorization", bs.getValue() );
            httpPost.addHeader( "Content-Type", "application/xml" );
            httpPost.addHeader( "Accept", "application/xml" );

            HttpResponse response = httpclient.execute( httpPost );
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
        catch ( Exception ex )
        {
            ex.printStackTrace();
        }
    }

    public static void main( String[] args )
        throws Exception
    {

        dhis kip = new dhis();

        kip.post();

    }
}
