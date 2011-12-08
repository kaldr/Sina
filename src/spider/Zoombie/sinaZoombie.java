/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spider.Zoombie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weibo4j.Timeline;
import weibo4j.Weibo;
import weibo4j.model.Paging;
import weibo4j.model.Status;
import weibo4j.model.WeiboException;

/**
 *
 * @author Administrator
 */
public class sinaZoombie {

    public static Map<String, String> idName = new HashMap<String, String>();
    public static Map<String, String> zoombie = new HashMap<String, String>();
    public static Map<String, String> zoombieNew = new HashMap<String, String>();
    public static Map<String, String> zoombieNew2 = new HashMap<String, String>();

    public static void main(String[] args) throws FileNotFoundException, IOException, WeiboException {
        prepareWeibo();
        readFile("E:\\Sina\\spider\\src\\PiPiTimer404.txt");
        readZoombie("E:\\Sina\\spider\\src\\Zoombie_From_PiPiTimer404.txt");
        readZoombieNew("E:\\Sina\\spider\\src\\ZoombieNew.txt");
        rebuildZoombie2();
        writeFile("E:\\Sina\\spider\\src\\ZoombieNew2.txt");
    }

    public static void prepareWeibo() {
        String access = spider.Auth4Code.getCode();
        Weibo weibo = new Weibo();
        weibo.setToken(access);
    }

    public static void readFile(String filename) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        String line = reader.readLine();
        while (line != null) {
            String s[] = line.split(" ");
            idName.put(s[0], s[1]);
            line = reader.readLine();
        }
        reader.close();
    }

    public static void readZoombie(String filename) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        String line = reader.readLine();
        while (line != null) {
            if (line.isEmpty()) {
                line = reader.readLine();
                continue;
            }
            String s[] = line.split(" ");
            if (s.length > 1) {
                zoombie.put(s[0], s[1]);
            }
            line = reader.readLine();
        }
        reader.close();
    }

    public static void readZoombieNew(String filename) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        String line = reader.readLine();
        while (line != null) {
            if (line.isEmpty()) {
                line = reader.readLine();
                continue;
            }
            String s[] = line.split(" ");
            if (s.length > 1) {
                zoombieNew.put(s[0], s[1]);
            }
            line = reader.readLine();
        }
        reader.close();
    }

    public static void rebuildZoombie2() throws WeiboException {
        Timeline tm = new Timeline();
        Set keys = zoombieNew.keySet();
        int count = keys.size();
        for (int i = 0; i < count; i++) {
            System.out.println("==============" + i + "===============");
            String id = (String) keys.toArray()[i];
            String name = zoombieNew.get(id);
            try {
                List<Status> status = tm.getUserTimeline(id, name, 200, new Paging(1, 200), 0, 0);
                status.addAll(tm.getUserTimeline(id, name, 200, new Paging(2, 200), 0, 0));
                status.addAll(tm.getUserTimeline(id, name, 200, new Paging(3, 200), 0, 0));
                String lastsource = "";
                int scount = status.size();
                int rcount = 0;
                for (Status s : status) {
                    String source = s.getSource().getName();
                    if (source != null && (source.equals("皮皮时光机") || source.equals("定时V") || source.equals("时光机") || source.equals("周博通微博管家") || source.equals("定时达人"))) {
                        rcount += 1;
                    }
                }
                if ((double) rcount / (double) scount > 0.05) {
                    zoombieNew2.put(id, name);
                } else {
                    continue;
                }
                for (Status s : status) {
                    Status r = s.getRetweetedStatus();
                    if (r != null) {
                        String source = r.getSource().getName();
                        if (source != null) {
                            if (source.equals(lastsource)) {
                                continue;
                            }
                        }
                        if (source != null && (source.equals("皮皮时光机") || source.equals("定时V") || source.equals("时光机") || source.equals("周博通微博管家") || source.equals("定时达人"))) {
                            String rusername = r.getUser().getName();
                            String ruserid = r.getUser().getId();
                            String verify = "";
                            if (r.getUser().isVerified()) {
                                verify = "verified";
                            }
                            if (idName.containsKey(ruserid) || zoombie.containsKey(ruserid) || zoombieNew.containsKey(ruserid) || zoombieNew2.containsKey(ruserid)) {
                                continue;
                            }
                            zoombieNew2.put(ruserid, rusername);
                            System.out.println(ruserid + " " + rusername + " " + verify);
                        }
                        lastsource = source;
                    }
                }
            } catch (Exception e) {
                System.out.println(id + " " + name + " is not available.");
            }
        }


    }

    public static void rebuildZoombie() throws WeiboException {

        Timeline tm = new Timeline();
        Set keys = idName.keySet();
        int count = keys.size();
        for (int i = 0; i < count; i++) {
            System.out.println("==============" + i + "===============");
            String id = (String) keys.toArray()[i];
            String name = idName.get(id);
            try {
                List<Status> status = tm.getUserTimeline(id, name, 200, new Paging(1, 200), 0, 0);
                status.addAll(tm.getUserTimeline(id, name, 200, new Paging(2, 200), 0, 0));
                status.addAll(tm.getUserTimeline(id, name, 200, new Paging(3, 200), 0, 0));
                String lastsource = "";
                for (Status s : status) {
                    Status r = s.getRetweetedStatus();
                    if (r != null) {
                        String source = r.getSource().getName();
                        if (source != null) {
                            if (source.equals(lastsource)) {
                                continue;
                            }
                        }
                        if (source != null && (source.equals("皮皮时光机") || source.equals("定时V") || source.equals("时光机") || source.equals("周博通微博管家") || source.equals("定时达人"))) {
                            String rusername = r.getUser().getName();
                            String ruserid = r.getUser().getId();
                            if (zoombie.containsKey(ruserid)) {
                                continue;
                            }
                            zoombie.put(ruserid, rusername);
                            System.out.println(ruserid + " " + rusername);
                        }
                        lastsource = source;

                    }
                }
            } catch (Exception e) {
                System.out.println(id + " " + name + " is not available.");
            }
        }
    }

    public static void writeFile(String filename) throws FileNotFoundException, IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));
        Set keys = zoombieNew2.keySet();
        int count = keys.size();
        for (int i = 0; i < count; i++) {
            String id = (String) keys.toArray()[i];
            String name = zoombieNew2.get(id);
            writer.write(id + " " + name + "\r\n");
        }
        writer.close();
    }
}
