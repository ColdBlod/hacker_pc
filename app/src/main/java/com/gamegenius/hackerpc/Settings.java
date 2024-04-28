package com.gamegenius.hackerpc;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.HashMap;

public class Settings {
    public HashMap<Integer, String[]> quests = new HashMap<>();
    boolean is_it_all = false, is_showed_answer_relationship = false, love_walk = false, magazine_is_back_btn_showed=false;
    public int how_many = 1, realutionship_age = 0, showed_learn = 0, all_pc_complect=0, showed_elements_magazine=0, showed_elements=0;
    public String[] names = new String[10];
    public String[] jobs = new String[10];
    public int current_showcase=-1;
    public int[] showed_magazine_ls = new int[26];
    public HashMap<String, String[]> pets = new HashMap();
    public String[] list_of_pets = new String[]{"cat", "dog", "gui na pig", "marmot", "weasel", "mouses", "bird", "fishes"}, housing_house=new String[]{"dormitory", "studio", "one-room apartment", "kopeck piece", "three bedroom apartment", "villa", "personal island"};
    public ArrayAdapter jobs_pr;
    public String[] processors_keys=new String[]{"intel", "amd"}, motherboards_keys=new String[]{"lga1151", "lga1151-v2", "lga1200", "lga1700"}, disks_keys=new String[]{"ssd", "hdd"};
    public HashMap<String, String[][]> disks = new HashMap<>();
    public HashMap<String, String[]> psus = new HashMap<>();
    public HashMap<String, String[]> cases = new HashMap<>();
    public HashMap<String, String[][]> processors = new HashMap<>();
    public HashMap<String, String[][]> motherboards = new HashMap<>();
    public HashMap<String, String[]> culers = new HashMap<>();
    public HashMap<String, String[]> videocards = new HashMap<>();
    public HashMap<String, String[]> rams = new HashMap<>();

    protected void main() {
        for (int i=0;i<26;i++) showed_magazine_ls[i] = 0;
        pets.put("Cat", new String[]{"43.74", "2115", "24.09"});
        pets.put("Dog", new String[]{"219.21", "9450", "82.80"});
        pets.put("Guina pig", new String[]{"47.34", "9000", "36.30"});
        pets.put("marmot", new String[]{"27.34", "10000", "76.5"});
        pets.put("weasel", new String[]{"27.34", "2400", "300"});
        pets.put("mouses", new String[]{"32.80", "1890", "13,92"});
        pets.put("bird", new String[]{"54.67", "900", "6.31"});
        pets.put("fishes", new String[]{"546.75", "900", "21.87"});

        for (int i = 0; i < 75; i++) {
            String[] a = new String[5]; // от сюда
            a[0] = "Сделай приложение " + i; // text описание задания
            a[1] = "1500"; // money
            a[2] = "100"; // xp
            a[3] = "1"; // Clicks
            a[4] = Integer.toString(i); // id
            quests.put(i, a);

            a = new String[5]; // от сюда
            a[0] = "Сделай приложение " + (i + 1); // text описание задания
            a[1] = "14837"; // money
            a[2] = "150"; // xp
            a[3] = "2000"; // clicks
            a[4] = Integer.toString(i + 1); // id

            quests.put(i + 75, a); // до сюда надо копировать
        }


        names[0] = "Julet";
        names[1] = "Maria";
        names[2] = "Agata";
        names[3] = "Amanda";
        names[4] = "Alice";
        names[5] = "Victoria";
        names[6] = "Wendy";
        names[7] = "Gloria";
        names[8] = "Laura";
        names[9] = "Polly";


        jobs[0] = "Manager";
        jobs[1] = "Actor";
        jobs[2] = "Cook";
        jobs[3] = "Builder";
        jobs[4] = "Pilot";
        jobs[5] = "Programist";
        jobs[6] = "Teacher";
        jobs[7] = "Engineer";
        jobs[8] = "Policeman";
        jobs[9] = "Journalist";



        motherboards_make();

        proccessors_make();

        cases_make();

        psu_make();

        disks_make();

        culers_make();

        videovards_make();

        ram_make();


        all_pc_complect = rams.size() + videocards.size() + culers.size() + disks.size() + psus.size() + cases.size() + processors.size() + motherboards.size();

    }
        private void ram_make(){
            // оперативка

            String[] ram = new String[6];

            // копировать от сюда
            ram = new String[]{"price", "year", "memory_type", "max_memory", "memory_timing", "memory_frequency"};
            rams.put("name", ram); // замени name на название оперативки
            // до сюда
        }

        private void videovards_make(){
            // видеокарты

            String[] videocard = new String[7];

            // копировать от сюда
            videocard = new String[]{"price", "year", "memory_type", "max_memory", "memoryt_bus", "memory_frequency", "TDP"};
            videocards.put("name", videocard); // замени name на название видюхи
            // до сюда
        }

        private void culers_make(){
            // кулеры

            String[] culer = new String[4];

            // копировать от сюда
            culer = new String[]{"price", "sockets", "speed", "TDP"};
            culers.put("name", culer); // замени name на название кулера
            // до сюда не включительно
        }

        private void disks_make(){
            // диски

            String[][] ssd = new String[2][5];

            // копировать от сюда
            String[] disk = new String[]{"name", "price", "disk_type", "max_memory", "speed"};
            ssd[0] = disk;
            // до сюда

            String[][] hdd = new String[2][5];

            // копировать от сюда
            disk = new String[]{"name", "price", "disk_type", "max_memory", "speed"};
            ssd[0] = disk;
            // до сюда

            disks.put("ssd", ssd);
            disks.put("hdd", hdd);
        }

        private void psu_make(){
            // блоки питания

            String[] psu = new String[5];

            // копировать от сюда
            psu = new String[]{"price", "cpu_connector", "PCIE_connector", "power"};
            psus.put("name", psu); // замени name на название блока
            // до сюда не включимтельно

        }

        private void cases_make(){
            // корпуса

            String[] casee = new String[4];

            // копировать от сюда
            casee = new String[]{"price", "year", "size"};
            cases.put("name", casee);
            // до сюда не включительно
        }

        private void motherboards_make(){
            // материнки
            String[] motherb = new String[8];

            String[][] lga1151 = new String[1][8];

            // от сюда можно копировать
            motherb = new String[]{"name", "price", "factor", "operative_memory", "operative_slots", "max_operative", "operative_quality", "m.2", "usb2", "usb3", "usb-c", "nutrition", "lights"};
            lga1151[0] = motherb; // замени i на индекс материнки они начинаются с 0
            // до сюда не включая

            String[][] lga1151v2 = new String[2][8];

            // от сюда можно копировать
            motherb = new String[]{"name", "price", "factor", "operative_memory", "operative_slots", "max_operative", "operative_quality", "m.2", "usb2", "usb3", "usb-c", "nutrition", "lights"};
            lga1151v2[0] = motherb; // замени i на индекс материнки они начинаются с 0
            // до сюда не включая

            String[][] lga1200 = new String[9][8];

            // от сюда можно копировать
            motherb = new String[]{"name", "price", "factor", "operative_memory", "operative_slots", "max_operative", "operative_quality", "m.2", "usb2", "usb3", "usb-c", "nutrition", "lights"};
            lga1200[0] = motherb; // замени i на индекс материнки они начинаются с 0
            // до сюда не включая

            String[][] lga1700 = new String[4][8];

            // от сюда можно копировать
            motherb = new String[]{"name", "price", "factor", "operative_memory", "operative_slots", "max_operative", "operative_quality", "m.2", "usb2", "usb3", "usb-c", "nutrition", "lights"};
            lga1700[0] = motherb; // замени i на индекс материнки они начинаются с 0
            // до сюда не включая

            motherboards.put("lga1151", lga1151);
            motherboards.put("lga1151-v2", lga1151v2);
            motherboards.put("lga1200", lga1200);
            motherboards.put("lga1700", lga1700);


        }

        private void proccessors_make(){
            // процессоры

            String[] proc = new String[20];

            String[][] intel = new String[1][20]; // количество процессоров замени x

            // копировать от сюда
            proc = new String[]{"Intel core i3-1000G1", "price", "cores", "potocs", "ecocores", "L2", "L3", "Z", "core", "quality", "operative_memory", "heighest_operative_quality", "maxmemory", "heat_generation", "maxtemperature", "graphic_model", "quality_graphic", "virtualization", "year"};
            intel[0] = proc; // i замени на индекс процессора индексация идёт от 0
            // до сюда не включительно

            String[][] amd = new String[1][20]; // тоже замени

            // копировать от сюда
            proc = new String[]{"name", "price", "cores", "potocs", "ecocores", "L2", "L3", "Z", "core", "quality", "operative_memory", "heighest_operative_quality", "maxmemory", "heat_generation", "maxtemperature", "graphic_model", "quality_graphic", "virtualization", "year"};
            amd[0] = proc; // i замени на индекс процессора индексация идёт от 0
            // до сюда не включительно

            processors.put("amd", amd);
            processors.put("intel", intel);
        }


}
