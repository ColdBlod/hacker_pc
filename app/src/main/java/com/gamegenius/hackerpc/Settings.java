package com.gamegenius.hackerpc;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.HashMap;

public class Settings {
    public HashMap<Integer, String[]> quests = new HashMap<>();
    boolean is_it_all = false, is_showed_answer_relationship = false, love_walk = false, magazine_is_back_btn_showed=false;
    public int how_many = 1, realutionship_age = 0, showed_learn = 0, all_pc_complect=0, showed_elements_magazine=0, showed_elements=0;
    public String[] names = new String[10], new_pc = new String[8];
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
        // {price, year, memory_type, max_memory, memory_timings, memory_frequency, id}
        // копировать от сюда
        ram = new String[]{"3490", "2016", "DDR4", "4", "22-22-22-32", "3200", "0"};
        rams.put("Kingston ValueRAM", ram); // замени name на название оперативки
        ram = new String[]{"3090", "2016", "DDR4", "8", "19-19-19-19", "2666", "1"};
        rams.put("Silicon Power", ram); // замени name на название оперативки
        ram = new String[]{"3390", "2016", "DDR4", "16", "22-22-22-22", "3200", "2"};
        rams.put("Foxline", ram); // замени name на название оперативки
        ram = new String[]{"3790", "2017", "DDR4", "32", "20-26-26-46", "3600", "3"};
        rams.put("Patriot Viper Elite II", ram); // замени name на название оперативки
        ram = new String[]{"6990", "2023", "DDR5", "8", "40-40-40", "4800", "4"};
        rams.put("Samsung", ram); // замени name на название оперативки
        ram = new String[]{"6990", "2023", "DDR5", "16", "46-45-45", "5600", "5"};
        rams.put("ADATA XPG Lancer Blade", ram); // замени name на название оперативки
        ram = new String[]{"8990", "2024", "DDR5", "32", "30-40-40", "6000", "6"};
        rams.put("ADATA XPG Lancer", ram); // замени name на название оперативки
        // до сюда
    }

    private void videovards_make(){
        // видеокарты

        String[] videocard = new String[7];
        // {price, year, memory_type, max_memory, memory_bus, memory_frequency, TDP}
        // копировать от сюда
        videocard = new String[]{"210990", "2022", "GDDR6X", "24", "384", "21000","450"};
        videocards.put("RTX 4090 VENTUS 3X", videocard); // замени name на название видюхи
        videocard = new String[]{"189990", "2022", "GDDR6X", "24", "384", "20000","355"};
        videocards.put("Sapphire AMD Radeon RX 7900 XTX NITRO+", videocard); // замени name на название видюхи
        videocard = new String[]{"129990", "2022", "GDDR6X", "12", "384", "21000","200"};
        videocards.put("GIGABYTE GeForce RTX 4070 AORUS MASTER 12G", videocard); // замени name на название видюхи
        videocard = new String[]{"69990", "2023", "GDDR6X", "16", "256", "19500","300"};
        videocards.put("Sapphire AMD Radeon RX 7800 XT PURE", videocard); // замени name на название видюхи
        videocard = new String[]{"109990", "2023", "GDDR6X", "12", "192", "18000","245"};
        videocards.put("ASUS AMD Radeon RX 7700 XT TUF Gaming OC Edition", videocard); // замени name на название видюхи
        videocard = new String[]{"67990", "2023", "GDDR6", "8", "128", "17000","115"};
        videocards.put("GIGABYTE GeForce RTX 4060 AERO OC", videocard); // замени name на название видюхи
        videocard = new String[]{"49990", "2024", "GDDR6", "16", "128", "17000","190"};
        videocards.put("ASRock AMD Radeon RX 7600 XT Challenger OC", videocard); // замени name на название видюхи
        videocard = new String[]{"54990", "2020", "GDDR6X", "24", "384", "19500","350"};
        videocards.put("Palit GeForce RTX 3090 GamingPro", videocard); // замени name на название видюхи
        videocard = new String[]{"79990", "2020", "GDDR6X", "10", "320", "19000","320"};
        videocards.put("Palit GeForce RTX 3080 GamingPro OC LHR", videocard); // замени name на название видюхи
        videocard = new String[]{"69990", "2020", "GDDR6", "8", "256", "14000","220"};
        videocards.put("GeForce RTX 3070 GameRock V1", videocard); // замени name на название видюхи
        videocard = new String[]{"59900", "2020", "GDDR6", "8", "192", "15000","170"};
        videocards.put("KFA2 GeForce RTX 3060 CORE", videocard); // замени name на название видюхи
        videocard = new String[]{"29900", "2020", "GDDR6", "8", "128", "14000","130"};
        videocards.put("ASUS GeForce RTX 3050 Dual V2 OC Edition", videocard); // замени name на название видюхи
        videocard = new String[]{"24990", "2019", "GDDR6", "8", "256", "14000","175"};
        videocards.put("GIGABYTE GeForce RTX 2060 SUPER WINDFORCE OC", videocard); // замени name на название видюхи
        videocard = new String[]{"20990", "2019", "GDDR6", "6", "192", "14000","175"};
        videocards.put("GIGABYTE GeForce RTX 2060 D6 6G", videocard); // замени name на название видюхи
        videocard = new String[]{"19990", "2019", "GDDR6", "6", "192", "14000","125"};
        videocards.put("GIGABYTE GeForce GTX 1660 SUPER D6", videocard); // замени name на название видюхи
        videocard = new String[]{"13990", "2019", "GDDR5", "4", "192", "14000","75"};
        videocards.put("ASUS GeForce GTX 1650 Dual EVO OC Edition", videocard); // замени name на название видюхи
        videocard = new String[]{"14990", "2019", "GDDR6", "8", "192", "14000","130"};
        videocards.put("ASRock AMD Radeon RX 5500 XT", videocard); // замени name на название видюхи
        videocard = new String[]{"4990", "2016", "GDDR5", "4", "192", "7008","75"};
        videocards.put("ASUS GeForce GTX 1050 Ti Cerberus OC Edition", videocard); // замени name на название видюхи
        videocard = new String[]{"5990", "2016", "GDDR5", "8", "256", "7008","185"};
        videocards.put("PowerColor AMD Radeon RX 580 Red Dragon", videocard); // замени name на название видюхи
        videocard = new String[]{"6799", "2016", "GDDR5", "4", "64", "6000","30"};
        videocards.put("GIGABYTE GeForce GT 1030 Low Profile D4 2G", videocard); // замени name на название видюхи

        // до сюда
    }

    private void culers_make(){
        // кулеры

        String[] culer = new String[4];
        // {price, sockets, speed, heat_dissipation}
        // копировать от сюда
        culer = new String[]{"890", "LGA 1150, LGA 1151, LGA 1151-v2, LGA 1155, LGA 1156, LGA 1200", "2000", "82"};
        culers.put("DEEPCOOL Theta 9", culer); // замени name на название кулера
        culer = new String[]{"1790", "AM4, LGA 1150, LGA 1151, LGA 1151-v2, LGA 1155, LGA 1156, LGA 1200, LGA 1700", "1500", "180"};
        culers.put("ID-COOLING SE-214-XT", culer); // замени name на название кулера
        culer = new String[]{"1190", "AM2, AM2+, AM3, AM3+, AM4, FM1, FM2, LGA 775, LGA 1150, LGA 1151, LGA 1151-v2, LGA 1155, LGA 1156, LGA 1200", "1600", "95"};
        culers.put("DEEPCOOL Gamma Archer", culer); // замени name на название кулера
        culer = new String[]{"3190", "AM4, AM5, LGA 1150, LGA 1151, LGA 1151-v2, LGA 1155, LGA 1156, LGA 1200, LGA 1700", "1600", "230"};
        culers.put("PCCooler K4 WH", culer); // замени name на название кулера
        // до сюда не включительно
    }

    private void disks_make(){
        // диски

        String[] disk = new String[5];

        String[][] m2 = new String[2][5];

        String[][] ssd = new String[2][5];

        String[][] hdd = new String[6][5];
        // {name, price, port, memory}
        // копировать от сюда
        disk = new String[]{"WD Blue 512gb", "500", "sata3", "512", "7200"};
        hdd[0] = disk;
        disk = new String[]{"WD Blue 1tb", "1000", "sata3", "1024", "7200"};
        hdd[1] = disk;
        disk = new String[]{"WD Blue 2tb", "2000", "sata3", "2048", "7200"};
        hdd[2] = disk;
        disk = new String[]{"WD Ultrastar DC HA210 512gb", "400", "sata3", "512", "5200"};
        hdd[3] = disk;
        disk = new String[]{"WD Ultrastar DC HA210 1tb", "890", "sata3", "1024", "5200"};
        hdd[4] = disk;
        disk = new String[]{"WD Ultrastar DC HA210 2tb", "1890", "sata3", "2048", "5200"};
        hdd[5] = disk;
        disk = new String[]{"Kingston A400", "1280", "sata3", "1024", "320"};
        ssd[0] = disk;
        disk = new String[]{"HP S750", "1990", "sata3", "256", "560"};
        ssd[1] = disk;
        disk = new String[]{"HP EX900", "3990", "m2", "512", "650"};
        m2[0] = disk;
        disk = new String[]{"ARDOR GAMING Ally AL1282", "5900", "m2", "1024", "1300"};
        m2[1] = disk;
        // до сюда

        disks.put("ssd", ssd);
        disks.put("hdd", hdd);
        disks.put("m.2", m2);
    }

    private void psu_make(){
        // блоки питания

        String[] psu = new String[5];
        // {price, main_power_connector, cpu_pins, PCI-E, power}
        // PCI-E = "connector1+connector2+..+connector_n quality_of_pins"
        // копировать от сюда
        psu = new String[]{"1999", "20+4", "4+4", "-", "350"};
        psus.put("AeroCool VX PLUS 350W", psu); // замени name на название блока
        psu = new String[]{"2899", "20+4", "4+4", "6+2 x2", "350"};
        psus.put("DEEPCOOL PF350", psu); // замени name на название блока
        psu = new String[]{"4999", "24", "4+4", "6+2 x2", "700"};
        psus.put("FSP PNR PRO 700W", psu); // замени name на название блока
        psu = new String[]{"5499", "20+4", "4+4", "6+2 x2", "700"};
        psus.put("AeroCool KCAS PLUS 700W", psu); // замени name на название блока
        psu = new String[]{"7699", "20+4", "4+4", "6+2 x2", "550"};
        psus.put("SYSTEM POWER 10 550W", psu); // замени name на название блокa
        psu = new String[]{"12399", "20+4", "4+4", "6+2 x2", "650"};
        psus.put("Pure Power 12 M 650W", psu); // замени name на название блока
        psu = new String[]{"34799", "20+4", "4+4", "6+2 x2", "1000"};
        psus.put("DARK POWER 13 1000W", psu); // замени name на название блока
        // до сюда не включительно

    }

        private void cases_make(){
            // корпуса

            String[] casee = new String[4];
            // {price, size}
            // копировать от сюда
            casee = new String[]{"1499", "Micro-ATX"};
            cases.put("DEXP DC-302B", casee);
            casee = new String[]{"1999", "Standart-ATX"};
            cases.put("DEXP AWS-DE7", casee);
            casee = new String[]{"2590", "E-ATX"};
            cases.put("AeroCool Bolt", casee);
            casee = new String[]{"3099", "not-standart"};
            cases.put("AeroCool Aero One", casee);
            casee = new String[]{"5890", "micro-ATX"};
            cases.put("ZALMAN S4 PLUS", casee);
                // до сюда не включительно
        }

        private void motherboards_make(){
        // материнки
        String[] motherb = new String[8];

        String[][] lga1151 = new String[1][8];

        // от сюда можно копировать
        motherb = new String[]{"ASRock H110 Pro BTC+", "15399", "2017", "Standart-ATX", "DDR4", "2", "32", "DDR4:2400", "1", "4", "4", "2", "0", "24", "true"};

        lga1151[0] = motherb; // замени i на индекс материнки они начинаются с 0
        // до сюда не включая

        String[][] lga1151v2 = new String[2][8];

        // от сюда можно копировать
        motherb = new String[]{"ASRock H310CM-DVS", "4499", "2018", "micro-ATX", "DDR4", "2", "32", "DDR4:2666", "0", "4", "1", "1", "0", "24", "false"};
        lga1151v2[0] = motherb; // замени i на индекс материнки они начинаются с 0
        motherb = new String[]{"ASRock H370M-HDV", "5599", "2020", "micro-ATX", "DDR4", "2", "64", "DDR4:2666", "0", "4", "1", "1", "0", "24", "false"};
        lga1151v2[1] = motherb; // замени i на индекс материнки они начинаются с 0
        // до сюда не включая

        String[][] lga1200 = new String[9][8];
        // {name, price, year, factor, operative_memory_types, operative_slots, max_operative, operative_quality, m.2, sata, usb2, usb3, usb-c, nutrition, lights}
        // от сюда можно копировать
        motherb = new String[]{"ASRock H470M-HDV", "6999", "2021", "micro-ATX", "DDR4", "2", "64", "DDR4:2933", "0", "4", "4", "2", "0", "24", "false"};
        lga1200[0] = motherb; // замени i на индекс материнки они начинаются с 0
        motherb = new String[]{"ASRock B560M-HDV", "6999", "2023", "micro-ATX", "DDR4", "2", "64", "DDR4:3200", "1", "4", "4", "2", "0", "24", "false"};
        lga1200[1] = motherb;
        motherb = new String[]{"ASRock B560 Steel Legend", "8299", "2021", "Standart-ATX", "DDR4", "4", "128", "DDR4:3200", "3", "6", "2", "4", "1", "24", "true"};
        lga1200[2] = motherb;
        motherb = new String[]{"ASRock Z590 Pro 4", "11599", "2021", "Standart-ATX", "DDR4", "4", "128", "DDR4:3200", "3", "6", "2", "2", "1", "24", "true"};
        lga1200[3] = motherb;
        motherb = new String[]{"ASRock H510 Pro BTC+", "12499", "2021", "not-standart", "DDR4", "1", "32", "DDR4:3200", "1", "1", "2", "2", "0", "24", "false"};
        lga1200[4] = motherb;
        motherb = new String[]{"ASRock Z590 OX Formula", "22999", "2021", "E-ATX", "DDR4", "2", "64", "DDR4:3200", "3", "8", "4", "2", "1", "24", "true"};
        lga1200[5] = motherb;
        motherb = new String[]{"ASRock Z490 AQUA", "50499", "2020", "E-ATX", "DDR4", "4", "128", "DDR4:2933", "4", "8", "1", "2", "1", "24", "true"};
        lga1200[6] = motherb;
        motherb = new String[]{"ASRock H510-HDV/M.2 SE", "6799", "2020", "micro-ATX", "DDR4", "2", "64", "DDR4:3200", "1", "4", "2", "4", "0", "24", "false"};
        lga1200[7] = motherb;
        String[][] lga1700 = new String[4][8];

        // от сюда можно копировать
        motherb = new String[]{"ASRock H610-HVS", "7099", "2022", "micro-ATX", "DDR4", "2", "64", "DDR4:3200", "0", "4", "1", "1", "0", "24", "false"};
        lga1700[0] = motherb; // замени i на индекс материнки они начинаются с 0
        motherb = new String[]{"ASRock B760M PG Lightning", "13999", "2023", "micro-ATX", "DDDR4", "4", "192", "DDR4:4800", "1", "2", "2", "3", "1", "24", "false"};
        lga1700[1] = motherb; // замени i на индекс материнки они начинаются с 0
        motherb = new String[]{"MSI PRO Z790-P WIFI", "25990", "2023", "micro-ATX", "DDR5", "4", "192", "DDR5:5600", "2", "sata", "4", "3", "1", "24", "false"};
        lga1700[2] = motherb; // замени i на индекс материнки они начинаются с 0
        motherb = new String[]{"ASUS TUF GAMING B660M-PLUS WIFI D4", "10990", "2023", "micro-ATX", "DDR4", "4", "192", "DDR4:3200", "2", "4", "2", "5", "1", "24", "false"};
        lga1700[3] = motherb; // замени i на индекс материнки они начинаются с 0
        // до сюда не включая

        motherboards.put("lga1151", lga1151);
        motherboards.put("lga1151-v2", lga1151v2);
        motherboards.put("lga1200", lga1200);
        motherboards.put("lga1700", lga1700);


    }

        private void proccessors_make(){
            // процессоры

            String[] proc = new String[20];

            String[][] intel = new String[23][20]; // количество процессоров замени x
            // {name, price, cores, potoc, ecocores, L2, L3, Z, core, quality, turbo, operative_memory_type, highest_operative_quality, maxmemory, heat_generation, max_temperaturem graphic_model, quality_graphic, virtualization, year}
            // копировать от сюда
            proc = new String[]{"Intel Pentium G4400", "2999", "2", "2", "0", "0.5", "3", "14", "Intel Skylake-S", "3.3", "3.3", "DDR3L,DDR4", "DDR3L:2133,DDR4:2133", "64", "54", "72", "Intel HD Graphics 510", "1000", "true", "2015"};
            intel[0] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"Intel Core i3-9100F", "9499", "4", "4", "0", "1", "6", "14", "Intel Coffee Lake R", "3.6", "4.2", "DDR4", "DDR4:2400", "64", "65", "100", "", "0", "true", "2019"};
            intel[1] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"Intel Core i3-9100","13199", "6", "6", "0", "1.5", "9", "14", "Intel Coffee Lake R", "2.9", "4.1", "DDR4", "DDR4:2666", "128", "65", "100", "Intel UHD Graphics 630", "1050", "true", "2019"};
            intel[2] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"Intel Core i5-9400F", "9799", "6", "6", "0", "1.5", "9", "14", "Intel Coffee Lake R", "2.9", "4.1", "DDR4", "DDR4:2666", "128", "65", "100", "", "0", "true", "2019"};
            intel[3] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"Intel Core i5-9400", "9199", "6", "6", "0", "1.5", "9", "14", "Intel Coffee Lake R", "2.9", "4.1", "DDR4", "DDR4:2666", "128", "65", "100", "Intel UHD Graphics 630", "1050", "true", "2019"};
            intel[4] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"Intel Core i7-9700KF","17699", "8", "8", "0", "2", "12", "14", "Intel Coffee Lake R", "3.6", "4.9", "DDR4", "DDR4:2666", "128", "95", "100", "", "0", "true", "2019"};
            intel[5] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"Intel Celeron G6900", "5499", "2", "2", "0", "2.5", "4", "10", "Intel Alder Lake-S", "3.4", "3.4", "DDR4,DDR5", "DDR4:3200,DDR5:4800", "128", "46", "100", "Intel UHD Graphics 710", "1.3", "true", "2022"};
            intel[6] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"Intel Core i3-12100F","8499", "4", "8", "0", "5", "12", "10", "Intel Alder Lake-S", "3.3", "4.3", "DDR4,DDR5", "DDR4:3200, DDR5:4800", "128", "89", "100", "", "0", "true", "2022"};
            intel[7] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"Intel Pentium Gold G740", "9299", "2", "4", "0", "2.5", "6", "10", "Intel Alder Lake-S", "3.7", "3.7", "3200", "4800", "128", "46", "100", "Intel UHD Graphics", "710", "1.35", "true", "2022"};
            intel[8] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"Intel Core i3-12100","11299", "4", "8", "0", "5", "12", "10", "Intel Alder Lake-S", "3.3", "4.3", "DDR4,DDR5", "DDR4:3200,DDR5:4800", "128", "89", "100", "Intel UHD Graphics 730", "1.4", "true", "2022"};
            intel[9] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"Intel Core i3-13100F", "12499", "4", "8", "0", "5", "12", "10", "Intel Raptor Lake-S", "3.4", "4.5", "DDR4,DDR5", "DDR4:3200,DDR5:4800", "192", "89", "100", "", "0", "true", "2023"};
            intel[10] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"Intel Core i3-13100", "13999", "4", "8", "0", "5", "12", "10", "Intel Raptor Lake-S", "3.4", "4.5", "DDR4,DDR5", "DDR4:3200,DDR5:4800", "192", "110", "100", "Intel UHD Graphics 730", "1.5", "true", "2023"};
            intel[11] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"Intel Core i5-12400F", "14799", "6", "12", "0", "7.5", "18", "10", "Intel Alder Lake-S", "2.5", "4.4", "DDR4,DDR5", "DDR4:3200,DDR5:4800", "128", "117", "100", "", "0", "true", "2022"};
            intel[12] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"Intel Core i5-12400", "16499", "6", "12", "0", "7.5", "18", "10", "Intel Alder Lake-S", "2.5", "4.4", "DDR4,DDR5", "DDR4:3200,DDR5:4800", "128", "117", "100", "Intel UHD Graphics 730", "1.45", "true", "2022"};
            intel[13] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"Intel Core i5-12600K","25499", "10", "16", "4", "9.5", "20", "10", "Intle Alder Lake-S", "3.7", "4.9", "DDR4,DDR5", "DDR4:3200,DDR5:4800", "128", "150", "100", "Intel UHD Graphics 770", "1.45", "true", "2022"};
            intel[14] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"Intel Core i5-13500", "27999", "14", "20", "8", "11.5", "24", "10", "Intel Raptor Lake-S", "2.5", "4.8", "DDR4,DDR5", "DDR4:3200,DDR5:4800", "192", "154", "100", "Intel UHD Graphics 770", "1.55", "true", "2023"};
            intel[15] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"Intel Core i7-12700F", "31499", "12", "20", "4", "12", "25", "10", "Intel Alder Lake-S", "2.1", "4.9", "DDR4,DDR5", "DDR4:3200,DDR5:4800", "128", "180", "100", "", "0", "true", "2021"};
            intel[16] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"Intel Core i7-12700","31999", "12", "20", "4", "12", "25", "10", "Intel Alder Lake-S", "2.1", "4.9", "DDR4,DDR5", "DDR4:3200,DDR5:4800", "128", "180", "100", "Intel UHD Graphics 770", "1.5", "true", "2021"};
            intel[17] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"Intel Core i7-12700K","35999", "12", "20", "4", "12", "25", "10", "Intel Alder Lake-S", "3.6", "5", "DDR4,DDR5", "DDR4:3200,DDR5:4800", "128", "190", "100", "Intel UHD Graphics 770", "1.5", "true", "2021"};
            intel[18] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"Intel Core i9-11900", "24999", "10", "20", "4", "0.25", "20", "10", "Rocket Lake", "2.8", "5.1", "DDR4", "DDR4:3200,DDR5:4800", "128", "65", "100", "Intel UHD Graphics 630", "1.3", "true", "2021"};
            intel[19] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"Intel Core i7-10700","20999", "6", "12", "4", "0.25", "12", "14", "Rocket Lake", "3.1", "4.5", "DDR4", "DDR4:3200", "64", "65", "100", "Intel UHD Graphics 630", "1.3", "true", "2021"};
            intel[20] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"Intel Core i5-11500","15499", "6", "12", "4", "0.25", "12", "14", "Rocket Lake", "2.7", "4.6", "DDR4", "DDR4:3200", "64", "65", "100", "Intel UHD Graphics 630", "1.3", "true", "2021"};
            intel[21] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"Intel Core i3-10100","12199", "4", "8", "4", "0.25", "12", "14", "Rocket Lake", "3.6", "4.3", "DDR4", "DDR4:3200", "64", "65", "100", "Intel UHD Graphics 630", "1.3", "true", "2021"};
            intel[22] = proc; // i замени на индекс процессора индексация идёт от 0
            // до сюда не включительно

            String[][] amd = new String[20][20]; // тоже замени

            // копировать от сюда
            proc = new String[]{"AMD Athlon 200GE", "price", "cores", "potocs", "ecocores", "L2", "L3", "Z", "core", "quality", "operative_memory", "heighest_operative_quality", "maxmemory", "heat_generation", "maxtemperature", "graphic_model", "quality_graphic", "virtualization", "year"};
            amd[0] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"7899", "6", "12", "0", "3", "8", "12", "AMD Renoir", "3.6", "4.1", "DDR4", "3200", "128", "65", "95", "", "0", "true", "2019"};
            amd[1] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"AMD Ryzen 5 5600","11799", "6", "12", "0", "3", "32", "12", "AMD Vermeer", "3.5", "4.4", "DDR4", "3200", "128", "65", "90", "", "0", "true", "2022"};
            amd[2] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"AMD Ryzen 3 3200G","8799", "4", "4", "0", "2", "4", "12", "AMD Picasso", "3.6", "4", "DDR4", "2933", "64", "65", "95", "AMD Radeon Vega 8", "1.25", "true", "2019"};
            amd[3] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"AMD Ryzen 5 4600G","11999", "6", "12", "0", "3", "8", "12", "AMD Renoir", "3.7", "4.2", "DDR4", "3200", "128", "65", "95", "AMD Radeon Vega 7", "1.9", "true", "2020"};
            amd[4] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"AMD Ryzen 5 5600G", "12499", "6", "12", "0", "3", "16", "12", "AMD Cezanne", "3.9", "4.4", "DDR4", "3200", "128", "65", "95", "AMD Radeon Vega 7", "1.9", "true", "2021"};
            amd[5] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"AMD Ryzen 7 3700X","15999", "8", "16", "0", "4", "32", "12", "AMD Matisse", "3.6", "4.4", "DDR4", "3200", "128", "65", "95", "", "0", "true", "2019"};
            amd[6] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"AMD Ryzen 5 3600X","16499", "6", "12", "0", "3", "32", "12", "AMD Matisse", "3.8", "4.4", "DDR4", "3200", "128", "95", "95", "", "0", "true", "2019"};
            amd[7] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"AMD Ryzen 7 5700X", "18799", "8", "16", "0", "4", "32", "12", "AMD Vermeer", "3.4", "4.6", "DDR4", "3200", "128", "65", "90", "", "0", "true", "2022"};
            amd[8] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"AMD Ryzen 7 5800X", "22999", "8", "16", "0", "4", "32", "12", "AMD Vermeer", "3.8", "4.7", "DDR4", "3200", "128", "105", "90", "", "0", "true", "2020"};
            amd[10] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"AMD Ryzen 9 5950X", "48999", "16", "32", "0", "8", "64", "12", "AMD Vermeer", "3.4", "4.9", "DDR4", "3200", "128", "105", "90", "", "0", "true", "2020"};
            amd[11] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"AMD Ryzen 5 7500F", "17999", "6", "12", "0", "6", "32", "5", "AMD Raphael", "3.7", "5", "DDR5", "5200", "128", "65", "95", "", "0", "true", "2023"};
            amd[12] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"AMD Ryzen 5 7600", "20499", "6", "12", "0", "6", "32", "5", "AMD Raphael", "3.8", "5.1", "DDR5", "5200", "128", "65", "95", "AMD Radeon Graphics", "2.2", "true", "2023"};
            amd[13] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"AMD Ryzen 5 7600X", "25999", "6", "12", "0", "6", "32", "5", "AMD Raphael", "4.7", "5.3", "DDR5", "5200", "128", "105", "95", "AMD Radeon Graphics", "2.2", "true", "2022"};
            amd[14] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"AMD Ryzen 7 7700","28499", "8", "16", "0", "8", "32", "5", "AMD Raphael", "3.8", "5.3", "DDR5", "5200", "128", "65", "95", "AMD Radeon Graphics", "2.2", "true", "2023"};
            amd[15] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"AMD Ryzen 7 7700X","33299", "8", "16", "0", "8", "32", "5", "AMD Raphael", "4.5", "5.4", "DDR5", "5200", "128", "105", "95", "AMD Radeon Graphics", "2.2", "true", "2022"};
            amd[16] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"AMD Ryzen 9 7900X","46399", "12", "24", "0", "12", "64", "5", "AMD Raphael", "4.7", "5.6", "DDR5", "5200", "128", "170", "95", "AMD Radeon Graphics", "2.2", "true", "2022"};
            amd[17] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"AMD Ryzen 9 7950X", "62499", "16", "32", "0", "16", "64", "5", "AMD Raphael", "4.5", "5.7", "DDR5", "5200", "128", "170", "95", "AMD Radeon Graphics", "2.2", "true", "2022"};
            amd[18] = proc; // i замени на индекс процессора индексация идёт от 0
            proc = new String[]{"AMD Ryzen 9 7900", "45999", "12", "24", "0", "12", "64", "5", "AMD Raphael", "3.7", "5.4", "DDR5", "5200", "128", "65", "95", "AMD Radeon Graphics", "2.2", "true", "2023"};
            amd[19] = proc; // i замени на индекс процессора индексация идёт от 0

            // до сюда не включительно

            processors.put("amd", amd);
            processors.put("intel", intel);
    }

        protected boolean string_checker(String string1, String string2){
            boolean answer;
            answer = true;
            if (string1.length() == string2.length()){
                for (int i = 0; i < string1.length(); i++) {
                    if (string1.charAt(i) != string2.charAt(i)) answer = false;
                }
            } else {
                answer = false;
            }
            return answer;
        }

        protected String[] find_the_name_of_ram(String ram){
            String name="", number="";
            for (int i=0;i<ram.length();i++){
                if (ram.charAt(ram.length()-1-i) == ' '){
                    name = ram.substring(0, ram.length()-1-i);
                    number = ram.substring(ram.length()-i, ram.length());
                    break;
                }
            }
            return new String[]{name, number};
        }
}
