/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import com.oracle.json.Json;
import com.oracle.json.JsonArray;
import com.oracle.json.JsonObject;
import com.oracle.json.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import app.AppMain;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpsConnection;

/**
 *
 * @author HARUN
 */
public class HavaDurumu {

    private static final String API_ROOT            = "https://servis.mgm.gov.tr";
    private static final String MERKEZLERILLER_API  = API_ROOT + "/api/merkezler/iller";
    private static final String MERKEZBILGI_API     = API_ROOT + "/api/merkezler?il=";
    private static final String ILILCELER_API       = API_ROOT + "/api/merkezler/ililcesi?il=";
    private static final String MERKEZSONDURUM_API  = API_ROOT + "/api/sondurumlar?il=";
    private static final String UCDEGERLER_API      = API_ROOT + "/api/ucdegerler?merkezid=%d&ay=%d&gun=%d";

    public List<Map<String, String>> Merkezler() {
        List<Map<String, String>> maplist = new ArrayList<>();
        try {
            HttpsConnection conn = (HttpsConnection) Connector.open(MERKEZLERILLER_API);
            conn.setRequestMethod(HttpsConnection.GET);
            conn.setRequestProperty("Content-Type", "//text plain");
            conn.setRequestProperty("Connection", "close");
            if (conn.getResponseCode() == HttpsConnection.HTTP_OK) {
                InputStream inputstream = conn.openInputStream();
                JsonReader reader = Json.createReader(inputstream);
                JsonArray jsonArray = reader.readArray();
                for (JsonObject json : jsonArray.getValuesAs(JsonObject.class)) {
                    Map<String, String> map = new HashMap<>();
                    for (String key : json.keySet()) {
                        map.put(key, json.get(key).toString());
                    }
                    maplist.add(map);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(AppMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maplist;
    }
    public List<Map<String, String>> MerkezIlceleri(String il) {
        List<Map<String, String>> maplist = new ArrayList<>();
        try {
            HttpsConnection conn = (HttpsConnection) Connector.open(ILILCELER_API + il);
            conn.setRequestMethod(HttpsConnection.GET);
            conn.setRequestProperty("Content-Type", "//text plain");
            conn.setRequestProperty("Connection", "close");
            if (conn.getResponseCode() == HttpsConnection.HTTP_OK) {
                InputStream inputstream = conn.openInputStream();
                JsonReader reader = Json.createReader(inputstream);
                JsonArray jsonArray = reader.readArray();
                for (JsonObject json : jsonArray.getValuesAs(JsonObject.class)) {
                    Map<String, String> map = new HashMap<>();
                    for (String key : json.keySet()) {
                        map.put(key, json.get(key).toString());
                    }
                    maplist.add(map);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(AppMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maplist;
    }
    public Map<String, String> MerkezBilgi(String il) {
        Map<String, String> map = new HashMap<>();
        try {
            HttpsConnection conn = (HttpsConnection) Connector.open(MERKEZBILGI_API + il);
            conn.setRequestMethod(HttpsConnection.GET);
            conn.setRequestProperty("Content-Type", "//text plain");
            conn.setRequestProperty("Connection", "close");
            if (conn.getResponseCode() == HttpsConnection.HTTP_OK) {
                InputStream inputstream = conn.openInputStream();
                JsonReader reader = Json.createReader(inputstream);
                JsonArray jsonArray = reader.readArray();
                JsonObject jsonObject = jsonArray.getValuesAs(JsonObject.class).get(0);
                for (String key : jsonObject.keySet()) {
                    map.put(key, jsonObject.get(key).toString());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(AppMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
    }

    public Map<String, String> SonDurum(String il) {
        Map<String, String> map = new HashMap<>();
        try {
            HttpsConnection conn = (HttpsConnection) Connector.open(MERKEZSONDURUM_API + il);
            conn.setRequestMethod(HttpsConnection.GET);
            conn.setRequestProperty("Content-Type", "//text plain");
            conn.setRequestProperty("Connection", "close");
            if (conn.getResponseCode() == HttpsConnection.HTTP_OK) {
                InputStream inputstream = conn.openInputStream();
                JsonReader reader = Json.createReader(inputstream);
                JsonArray jsonArray = reader.readArray();
                JsonObject jsonObject = jsonArray.getValuesAs(JsonObject.class).get(0);
                for (String key : jsonObject.keySet()) {
                    map.put(key, jsonObject.get(key).toString());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(AppMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
    }

    public Map<String, String> UcDegerler(int merkezId, int ay, int gun) {
        Map<String, String> map = new HashMap<>();
        try {
            HttpsConnection conn = (HttpsConnection) Connector.open(String.format(UCDEGERLER_API, merkezId, ay, gun));
            conn.setRequestMethod(HttpsConnection.GET);
            conn.setRequestProperty("Content-Type", "//text plain");
            conn.setRequestProperty("Connection", "close");
            if (conn.getResponseCode() == HttpsConnection.HTTP_OK) {
                InputStream inputstream = conn.openInputStream();
                JsonReader reader = Json.createReader(inputstream);
                JsonArray jsonArray = reader.readArray();
                JsonObject jsonObject = jsonArray.getValuesAs(JsonObject.class).get(0);
                for (String key : jsonObject.keySet()) {
                    map.put(key, jsonObject.get(key).toString());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(AppMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
    }
}
