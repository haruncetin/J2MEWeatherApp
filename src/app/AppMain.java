/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.microedition.midlet.MIDlet;
import jdk.dio.DeviceManager;
import jdk.dio.gpio.GPIOPin;
import jdk.dio.gpio.GPIOPinConfig;
import jdk.dio.gpio.PinEvent;
import jdk.dio.gpio.PinListener;

/**
 *
 * @author HARUN
 */
public class AppMain extends MIDlet {

    public static final String PROJECTNAME = "JAVAME TEST APP";
    public static final String VERSION = "1.0";

    @Override
    public void startApp() {
        try {
            start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void destroyApp(boolean unconditional) {
    }

    private void start() throws IOException {
        String ver = String.format("%s (%s)", PROJECTNAME, VERSION);
        System.out.println(ver);
        String ext_ip = new api.NetworkInfo().ExternalIP();
        String loc_ip = new api.NetworkInfo().LocalIP();
        System.out.println("External IP: " + ext_ip);
        System.out.println("Local IP:");
        System.out.println(loc_ip);

        List<Map<String, String>> maplist = new api.HavaDurumu().MerkezIlceleri("Yozgat");

        final List<GPIOPin> pins = new ArrayList();
        pins.add((GPIOPin) DeviceManager.open(
                new GPIOPinConfig.Builder()
                        .setPinNumber(0)
                        .setDirection(GPIOPinConfig.DIR_OUTPUT_ONLY)
                        .setInitValue(false)
                        .build()));
        pins.add((GPIOPin) DeviceManager.open(
                new GPIOPinConfig.Builder()
                        .setPinNumber(1)
                        .setDirection(GPIOPinConfig.DIR_OUTPUT_ONLY)
                        .setInitValue(false)
                        .build()));
        pins.add((GPIOPin) DeviceManager.open(
                new GPIOPinConfig.Builder()
                        .setPinNumber(7)
                        .setDirection(GPIOPinConfig.DIR_OUTPUT_ONLY)
                        .setInitValue(false)
                        .build()));

        final GPIOPin btn1 = (GPIOPin) DeviceManager.open(
                new GPIOPinConfig.Builder()
                        .setPinNumber(12)
                        .setDirection(GPIOPinConfig.DIR_INPUT_ONLY)
                        .setDriveMode(GPIOPinConfig.MODE_INPUT_PULL_DOWN)
                        .setTrigger(GPIOPinConfig.TRIGGER_FALLING_EDGE)
                        .setInitValue(false)
                        .build());
        btn1.setInputListener(new PinListener() {
            @Override
            public void valueChanged(PinEvent event) {
                if (!event.getValue()) {
                    try {
                        pins.get(1).setValue(true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Bekleyin... Sorgulanıyor...");
                    api.HavaDurumu hdapi = new api.HavaDurumu();
                    List<String> iller = new ArrayList();
                    iller.add("ankara");
                    iller.add("izmir");
                    iller.add("yozgat");
                    for (String il : iller) {
                        Map<String, String> mb = hdapi.MerkezBilgi(il);
                        Map<String, String> hd = hdapi.SonDurum(il);
                        System.out.println("--------------------------------------------------");
                        System.out.println("İL\t: " + mb.get("il"));
                        System.out.println("ENLEM\t: " + mb.get("enlem"));
                        System.out.println("BOYLAM\t: " + mb.get("boylam"));
                        System.out.println("SICAKLIK: " + hd.get("sicaklik"));
                        System.out.println("BASINÇ\t: " + hd.get("aktuelBasinc"));
                        System.out.println("NEM\t: " + hd.get("nem"));
                        System.out.println("GÖRÜŞ MESAFESİ\t: " + hd.get("gorus") + "m");
                        System.out.println("--------------------------------------------------");
                    }
                    try {
                        Thread.sleep(1000);
                        pins.get(1).setValue(false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(AppMain.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    private void wait_ms(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
