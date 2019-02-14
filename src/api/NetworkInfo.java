/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import com.oracle.json.Json;
import com.oracle.json.JsonObject;
import com.oracle.json.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import app.AppMain;
import javax.microedition.io.AccessPoint;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpsConnection;
import javax.microedition.io.NetworkInterface;

/**
 *
 * @author HARUN
 */
public class NetworkInfo {

    private static final String NETWORKINFO_API = "https://ipleak.net?mode=json";
    
    public String ExternalIP() {
        try {
            HttpsConnection conn = (HttpsConnection) Connector.open(NETWORKINFO_API);
            conn.setRequestMethod(HttpsConnection.GET);
            conn.setRequestProperty("Content-Type", "//text plain");
            conn.setRequestProperty("Connection", "close");
            if (conn.getResponseCode() == HttpsConnection.HTTP_OK) {
                InputStream inputstream = conn.openInputStream();
                JsonReader reader = Json.createReader(inputstream);
                JsonObject json = reader.readObject();
                String ip = json.getJsonString("ip").getString();
                String ulke = json.getJsonString("country_name").getString();
                String ip_ulke = String.format("%s (%s)", ip, ulke);
                return ip_ulke;
            }
        } catch (IOException ex) {
            Logger.getLogger(AppMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public String LocalIP(){
        StringBuilder sb = new StringBuilder();
        String[] nettypes = NetworkInterface.getNetworkTypes();
        for (String nettype : nettypes) {
            NetworkInterface[] nic = NetworkInterface.getNetworkInterfaces(nettype);
            for (NetworkInterface networkInterface : nic) {
                AccessPoint[] aps = networkInterface.getConnectedAccessPoints();
                for (AccessPoint ap : aps) {
                    sb.append(nettype).append(":").append(ap.getProperty("ipaddr")).append("\n");
                }
            }
        }
        return sb.toString();
    }
}
