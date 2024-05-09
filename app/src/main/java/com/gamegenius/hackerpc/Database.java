package com.gamegenius.hackerpc;

import android.content.res.AssetManager;

import java.lang.Exception;
import java.io.*;
import java.io.FileWriter;
import java.util.HashMap;
import java.time.Year;
import java.util.zip.*;

public class Database {
    public int money, xp, total_clicks, level, user_money, partners_age, need_xp, day, month, year, pet_food=100, pet_love=100, relationship_love, users_universities, university_time;
    public int processors_inventory_len=0, motherboard_inventory_len=0, ram_inventory_len=0, cases_inventory_len=0, psus_inventory_len=0, disks_inventory_len=0, coolers_inventory_len=0, videocards_inventory_len=0;
    public double spending = 0;
    public String life_zoo, name, total_apps, total_pcs, partners_name=null, partners_job;
    public String[] universities, inventory_names = new String[10];
    public String[][] processors = new String[11][20], motherboards = new String[11][8], rams = new String[11][6], cases = new String[11][4], psus = new String[11][5], disks = new String[11][5], coolers = new String[11][4],  videocards = new String[11][7];
    public HashMap<String, String[]>inventory = new HashMap<>();

    public void main(String path) {

        int a = 1;
        year = 2024;
        day = 28;
        month = 3;
        relationship_love = 100;
        try {
            FileReader fr = new FileReader("/data/data/com.gamegenius.hackerpc/files/user.txt");
            fr.close();
        } catch (Exception e) {
            a = 0;
        }
        if (a == 0) {
            try {
                FileWriter file = new FileWriter("/data/data/com.gamegenius.hackerpc/files/user.txt");
                file.write("noname\n28.3.2024\n0\n0\n1\n100\n0");
                file.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedReader file = new BufferedReader(new FileReader(path + "/user.txt"));
            name = file.readLine();
            String date = file.readLine();
            int first_t=date.indexOf("."), second_t=date.indexOf(".", date.indexOf(".")+1);
            day = Integer.parseInt(date.substring(0, first_t));
            month = Integer.parseInt(date.substring(first_t+1, second_t));
            year = Integer.parseInt(date.substring(second_t+1, date.length()));
            xp = Integer.parseInt(file.readLine());
            money = Integer.parseInt(file.readLine());
            level = Integer.parseInt(file.readLine());
            need_xp = Integer.parseInt(file.readLine());
            total_clicks = Integer.parseInt(file.readLine());
        } catch (Exception e){
            try{
                FileWriter file = new FileWriter("/data/data/com.gamegenius.hackerpc/files/user.txt");
                file.write("noname\n28.3.2024\n0\n0\n1\n100\n0");
                file.close();
                e.printStackTrace();
            }catch (Exception exception) {exception.printStackTrace();}
        }
        try {
            BufferedReader file = new BufferedReader(new FileReader(path+"/zoo.txt"));
            this.life_zoo = file.readLine();
        } catch (IOException e) {
            try {
                FileWriter file = new FileWriter(path + "/zoo.txt");
                file.write("cat");
                file.close();
                this.life_zoo = null;
            } catch (IOException exception) {exception.printStackTrace();}
        }
        try {
            BufferedReader file = new BufferedReader(new FileReader(path + "/inventory.txt"));
            String text, name = null;
            int i=0, i2=-1;
            while ((text = file.readLine()) != null) {
                if (i==0) {
                    name = text;
                    i2 ++;
                    inventory_names[i2] = name;
                    i = 1;
                } else {
                    String[] elements;
                    i = 0;
                    elements = text.split(";");
                    inventory.put(name, elements);
                }
            }
            file.close();
            processors[0] = new String[10];
            motherboards[0] = new String[10];
            rams[0] = new String[10];
            coolers[0] = new String[10];
            disks[0] = new String[10];
            videocards[0] = new String[10];
            psus[0] = new String[10];
            cases[0] = new String[10];
            try{
                file = new BufferedReader(new FileReader(path + "/details.txt"));
                int mod=0;
                while ((text = file.readLine()) != null) {
                    if (text.length() < 3){
                        mod = Integer.parseInt(text);
                    }
                    else if (mod == 0){
                        processors_inventory_len += 1;
                        processors[processors_inventory_len-1] = text.split(";");
                    } else if (mod == 1){
                        motherboard_inventory_len += 1;
                        motherboards[motherboard_inventory_len-1] = text.split(";");
                    } else if (mod == 2){
                        ram_inventory_len += 1;
                        rams[ram_inventory_len-1] = text.split(";");
                    } else if (mod == 3){
                        cases_inventory_len += 1;
                        cases[cases_inventory_len-1] = text.split(";");
                    } else if (mod == 4) {
                        psus_inventory_len += 1;
                        psus[psus_inventory_len-1] = text.split(";");
                    } else if (mod == 5) {
                        disks_inventory_len += 1;
                        disks[disks_inventory_len-1] = text.split(";");
                    } else if (mod == 6) {
                        coolers_inventory_len += 1;
                        coolers[coolers_inventory_len-1] = text.split(";");
                    } else if (mod == 7) {
                        videocards_inventory_len += 1;
                        videocards[videocards_inventory_len-1] = text.split(";");
                    }
                }
            } catch (IOException e){
                FileWriter filewriter = new FileWriter(path + "/details.txt");
                filewriter.write("0\nIntel Core i3-9100F\n1\nASRock Z590 Pro 4\n2\nKingston ValueRAM 2\n3\nDEXP DC-302B\n4\nAeroCool VX PLUS 350W\n5\nWD Blue 512gb\n6\nDEEPCOOL Theta 9\n7\nRTX 4090 VENTUS 3X\n");
                filewriter.close();
            }
        } catch (Exception e) {
            try {
                FileWriter fileWriter = new FileWriter(path + "/inventory.txt");
                fileWriter.close();
            } catch (Exception exception) {exception.printStackTrace();}
        }
        money = 1000000;

        universities = new String[20];
        universities[0] = "internet course step 1";
        universities[1] = "internet course step 2";
        universities[2] = "internet course step 3";
        universities[3] = "college step 1";
        universities[4] = "college step 2";
        universities[5] = "college step 3";
        universities[6] = "college step 4";
        universities[7] = "university step 1";
        universities[8] = "university step 2";
        universities[9] = "university step 3";
        universities[10] = "university step 4";
        universities[11] = "university step 5";
        universities[12] = "Academy step 1";
        universities[13] = "Academy step 2";
        universities[14] = "Academy step 3";
        universities[15] = "Academy step 4";
        universities[16] = "Academy step 5";
        universities[17] = "Academy step 6";
        universities[18] = "Academy step 7";
        universities[19] = "Academy step 8";

        users_universities = 0;

        university_time = 1;
    }

    protected void save(String path){
        try {
            FileWriter file = new FileWriter(path + "/user.txt");
            file.write(name + "\n" + day + "." + month + "." + year + "\n" + xp + "\n" + money + "\n" + level + "\n" + need_xp + "\n" + total_clicks);
            file.close();
        } catch (IOException e) {e.printStackTrace();}
    }

    protected void activate_acrch(String path, ZipInputStream zin){
        boolean is_made = false;
        try {
            File dir = new File(path+"/bin/javac");
            is_made = dir.exists();
            dir.mkdirs();
            if (dir.exists() == false)
                dir.delete();
        } catch (Exception e){ e.printStackTrace();}
        try {
            ZipEntry entry;
            String name;
            while((entry=zin.getNextEntry())!=null){

                name = entry.getName(); // получим название файла
                name.substring(2, name.length()-1);
                if (is_made == false){
                    System.out.printf("File name: %s \n", name);

                    // распаковка
                    FileOutputStream fout = new FileOutputStream(path + "/" + name);
                    for (int c = zin.read(); c != -1; c = zin.read()) {
                        fout.write(c);
                    }
                    fout.flush();
                    zin.closeEntry();
                    fout.close();
                }
            }
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public void save(String path, ZipInputStream zipInputStream){
        try {
            FileWriter file = new FileWriter(path + "user.txt");
            file.write(name + "\n" + level + "\n" + xp + "\n");
            file.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}