package com.gamegenius.hackerpc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Vibrator;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.VideoView;

import java.util.HashMap;
import java.util.zip.ZipInputStream;


public class MainActivity extends Activity {
    public Settings settings = new Settings();
    public Database db = new Database();
    public Quests_actions quest_actions = new Quests_actions();
    HashMap<String, String[]>inventory = new HashMap<>();
    public String name = "Kostya";
    public String path;
    int present = 0;
    Vibrator vibrator;
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("-------------------------------------------------------- game started ------------------------------------------------------------------");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);

        String[] a = new String[3];
        a[0] = "base";
        a[1] = "normal";
        a[2] = "hard";
        inventory.put("case", a);
        inventory.put("motherboard", a);
        inventory.put("processor", a);
        inventory.put("culer", a);
        inventory.put("disks", a);
        inventory.put("videocard", a);

        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        this.path = getApplicationContext().getFilesDir().getAbsolutePath();

        // inizializating varibales

        blocking b = new blocking();

        b.main();

        db.main(path);

        settings.main();



        settings.jobs_pr = new ArrayAdapter(this, R.layout.filter_activity, settings.jobs);

        // inizializating elements
        setContentView(R.layout.main_activity);
        ini_profile();
    }

    //work                                                                 ___      | /
    //                                            \    /\    /   ------   |___|     |/
    //                                             \  /  \  /   |      |  |\        |\
    //                                              \/    \/     ------   | \       | \
    protected void ini_work(){
        // inizializate elements
        Button work_profile = findViewById(R.id.work_profile);
        Button work_magazine = findViewById(R.id.work_magazine);
        Button work_life = findViewById(R.id.work_life);
        Button make_app_btn = findViewById(R.id.work_make_app);
        // end

        work_life.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.life_activity);ini_life();}});

        work_profile.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.main_activity);ini_profile();}});

        work_magazine.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.magazine_activity);ini_magazine();}});

        make_app_btn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {if (!quest_actions.is_working){setContentView(R.layout.choose_quest_activity);choose_quest();}else{setContentView(R.layout.clicking_layout);begin_clicking();}}});
    }
    protected void choose_quest(){
        // inizializate elements
        Button back = findViewById(R.id.choose_quest_back);
        LinearLayout quest_list_layt = findViewById(R.id.choose_quest_quests_list_widget);
        ScrollView scrollview = findViewById(R.id.choose_quest_scrollview);
        // end

        Button more_btn = new Button(getApplicationContext());
        more_btn.setText("More quests");
        more_btn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {add_quests_to_scrolview(quest_list_layt, more_btn);}});


        add_quests_to_scrolview(quest_list_layt, more_btn);


        back.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {settings.how_many=1;settings.is_it_all = false;setContentView(R.layout.work_activity);ini_work();}});


    }

    protected void add_quests_to_scrolview(LinearLayout quest_list_layt, Button more_btn){
        if (quest_list_layt.getChildCount() != 0){
            quest_list_layt.removeView(more_btn);
        }
        if (settings.quests.size() > 25*settings.how_many-1){
            for (int i=0;i<26;i++) {
                int c = i;
                i = i+25*(settings.how_many-1);
                if (settings.how_many > 1){
                    i = i + 1;
                }
                Button a = new Button(getApplicationContext());
                String text = settings.quests.get(i)[0] + "\nMoneys: " + settings.quests.get(i)[1] + "$\nxps: " + settings.quests.get(i)[2] + "\nneed clicks: " + settings.quests.get(i)[3];
                a.setText(text);
                int number = i;
                a.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {settings.how_many=1;settings.is_it_all = false;action_quest(number);}});
                quest_list_layt.addView(a);
                i = c;
            }
            settings.how_many += 1;
        }
        else if (!settings.is_it_all) {
            int c = 0;
            if (settings.how_many > 1){
                c = c +1;
            }
            if (c + 25 * settings.how_many - 1 == settings.quests.size()) {
                for (int i = 0; i < settings.quests.size() - 1; i++) {
                    i = i + 25 * (settings.how_many - 1);
                    if (settings.how_many > 1) {
                        i = i + 1;
                    }
                    Button a = new Button(getApplicationContext());
                    String text = settings.quests.get(i)[0] + "\nMoneys: " + settings.quests.get(i)[1] + "$\nxps: " + settings.quests.get(i)[2] + "\nneed clicks: " + settings.quests.get(i)[3];
                    a.setText(text);
                    int number = i;
                    a.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {settings.how_many = 1;settings.is_it_all = false;action_quest(number);}});
                    quest_list_layt.addView(a);
                }
            }
            settings.is_it_all = true;
        }
        quest_list_layt.addView(more_btn);
    }

    protected void action_quest(int number){
        quest_actions.quest_number = number;
        setContentView(R.layout.choose_languege_make_app);
        choose_languege();
    }

    protected void choose_languege(){
        // inizializate elements
        Button back = findViewById(R.id.chooese_langue_make_app_back);
        Button clicks_btn = findViewById(R.id.chooese_langue_make_app_clicks_btn);
        Button blocking_btn = findViewById(R.id.chooese_langue_make_app_blocking_btn);
        // end

        blocking_btn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.blocking_program);make_blocking();}});

        back.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.choose_quest_activity);choose_quest();}});

        clicks_btn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.clicking_layout);begin_clicking();}});
    }

    protected void make_blocking(){
        // inizializate elements
        ProgressBar progbar = findViewById(R.id.blocking_loading_progress_bar);
        TextView lb = findViewById(R.id.blocking_loading_text);
        // end

        // extracting the archive
        InputStream inputStream = getResources().openRawResource(R.raw.bin);
        db.save(path, new ZipInputStream(inputStream));
        // end
        try {
            Runtime.getRuntime().exec("chmod -R 777 " + path);
            System.out.println("get 777 permission for files");
        } catch (Exception e) {e.printStackTrace();}
        try {
            Runtime.getRuntime().exec("chmod -R 755 -x " + path + "/bin");
            System.out.println("get 777 permission for bin");
        } catch (Exception e) {e.printStackTrace();}
        try {
            Runtime.getRuntime().exec("chmod - R 777 -x " + path + "/bin/javac");
            System.out.println("get 777 permission for javac");
        } catch (Exception e) {e.printStackTrace();}


    }

    protected void begin_clicking(){
        // clicking

        quest_actions.is_working = true;

        // inizializate elements
        VideoView videoView = findViewById(R.id.clicking_background_video);
        Button back = findViewById(R.id.clicking_back_btn);
        ImageButton keyboard = findViewById(R.id.clicking_keyboard_btn);
        ImageView monitor = findViewById(R.id.clicking_monitor);
        TextView monitor_lb = findViewById(R.id.clicking_monitortext_lb);
        ImageButton drink_btn = findViewById(R.id.clicking_drink_btn);
        ImageButton eat_btn = findViewById(R.id.clicking_eat_btn);
        // end

        keyboard.setScaleType(ImageView.ScaleType.FIT_END);
        Display windowManager = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        windowManager.getSize(point);
        float width = point.x,height=point.y;

        float res = width/842 * 1.38f;
        videoView.setScaleX(res);
        res = height/1280 * 1.55f;
        videoView.setScaleY(res);

        videoView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return false;
            }
        });

        monitor.setImageResource(R.drawable.monitor_lvl1);
        monitor.setScaleType(ImageView.ScaleType.FIT_END);

        Uri resource = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.app_background);

        videoView.setVideoURI(resource);
        videoView.start();

        quest_actions.money = Integer.parseInt(settings.quests.get(quest_actions.quest_number)[1]);
        quest_actions.need_clicks = Integer.parseInt(settings.quests.get(quest_actions.quest_number)[3]);
        quest_actions.xp = Integer.parseInt(settings.quests.get(quest_actions.quest_number)[2]);
        quest_actions.keyboard_mod = 0;

        back.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.work_activity);ini_work();}});
        String text = "Clicks/need clicks: " + quest_actions.clicks + "/" + quest_actions.need_clicks + "\nxp: " + quest_actions.xp + "\nmoney: " + quest_actions.money;
        monitor_lb.setText(text);
        keyboard.setCropToPadding(true);
        keyboard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int action = event.getActionMasked();
                if (action==1 | action == 6) {ad_click(monitor_lb, keyboard);}
                return false;
            }
        });
    }

    protected void ad_click(TextView monitor_lb, ImageButton keyboard){
        vibrator.vibrate(10);
        db.total_clicks += 1;
        quest_actions.clicks += 1;
        monitor_lb.setText("Clicks/need clicks: " + quest_actions.clicks + "/" + quest_actions.need_clicks +"\nxp: " + quest_actions.xp + "\nmoney: " + quest_actions.money);
        quest_actions.keyboard_mod = quest_actions.keyboard_mod + 1;
        if (quest_actions.keyboard_mod == 1)
            keyboard.setImageResource(R.drawable.keyboard1);
        else if (quest_actions.keyboard_mod == 2)
            keyboard.setImageResource(R.drawable.keyboard2);
        else if (quest_actions.keyboard_mod == 3)
            keyboard.setImageResource(R.drawable.keyboard3);
        else{
            keyboard.setImageResource(R.drawable.keyboard1);
            quest_actions.keyboard_mod = 1;
        }
        if (quest_actions.clicks == quest_actions.need_clicks){
            quest_actions.is_working = false;
            setContentView(R.layout.horrey_activity);

            // inizializate elements
            TextView lb = findViewById(R.id.horrey_text_lb);
            Button claim = findViewById(R.id.horrey_get_btn);
            // end

            lb.setText("Money: " + quest_actions.money + "\nxp: " + quest_actions.xp);

            claim.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {db.money += quest_actions.money;db.xp += quest_actions.xp;db.total_clicks += quest_actions.need_clicks;quest_actions.money = 0;quest_actions.xp = 0;quest_actions.need_clicks = 0;quest_actions.clicks = 0;
                if (db.xp >= db.need_xp){setContentView(R.layout.level_up_activity);level_up();}
                else{setContentView(R.layout.work_activity);ini_work();}}});
        }
    }

    protected void level_up(){
        // inizialization
        Button claim = findViewById(R.id.level_up_claim_btn);
        TextView tx = findViewById(R.id.level_up_text);
        // end

        Animation animation_tx = AnimationUtils.loadAnimation(this, R.anim.level_up_anim);
        tx.setText("I congratulate you, you have become older and smarter, here is my gift for you on the day you grow up, don’t confuse it with your birthday\n\n+" + 500*db.level + "$");


        tx.startAnimation(animation_tx);
        claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.level += 1;
                db.money += 500*db.level;
                db.xp -= db.need_xp;
                db.need_xp *= 2;
                setContentView(R.layout.work_activity);
                ini_work();
            }
        });

    }


    //profile                      ____     ____     _______    _______    ____ _____  |           ______
    //                            |____|   |____|   |       |   |______        |       |           |
    //                            |        |\       |       |   |              |       |           |----
    //                            |        | \      |_______|   |          ____|____   |_______    |_____
    protected void ini_profile(){
        //profile
        TextView profile_text = findViewById(R.id.profile_text);
        Button profile_life = findViewById(R.id.profile_life);
        Button profile_magazine = findViewById(R.id.profile_magazine);
        Button profile_work = findViewById(R.id.profile_work);
        Button profile_apps_arch = findViewById(R.id.profile_apps_arch);
        Button profile_pcs_arch = findViewById(R.id.profile_pcs_archive);
        ImageView profile_icon = findViewById(R.id.profile_icon);
        TextView money = findViewById(R.id.profile_money_lb);
        TextView time_lb = findViewById(R.id.profile_time);
        Button end_of_day = findViewById(R.id.profile_end);
        // end

        if (db.money < 1000) money.setText("money: " + db.money + "$");
        else if (db.money > 999 & db.money < 1000000) money.setText("money: " + db.money/1000 + "k$");

        if (db.day < 10) {String day = "0" + db.day;time_lb.setText(day);}
        else {String day = Integer.toString(db.day);time_lb.setText(day);}
        if (db.month < 10) {String month = "0" + db.month;time_lb.setText(time_lb.getText()+"."+month+"."+db.year);}
        else {String month = Integer.toString(db.month);time_lb.setText(time_lb.getText()+"."+month+"."+db.year);}

        profile_text.setText("name: " + db.name + "\nlevel: " + db.level + "\nxp: " + db.xp + "/" + db.need_xp + "\napps: " + db.total_apps + "\npcs: " + db.total_pcs + "\ntotal clicks: " + db.total_clicks);

        end_of_day.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {end_of_day();ini_profile();}});

        profile_icon.setImageResource(R.drawable.black);

        profile_magazine.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.magazine_activity);ini_magazine();}});

        profile_life.setOnClickListener(new View.OnClickListener(){@Override public void onClick(View v) {setContentView(R.layout.life_activity);ini_life();}});

        profile_work.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.work_activity);ini_work();}});
    }

    protected void end_of_day(){

        db.money -= db.spending;

        Random r = new Random();
        if (db.life_zoo != null){
            db.pet_food -= r.nextInt(30) + 10;
            db.pet_love -= r.nextInt(30) + 10;
            if (db.pet_food < 0) {
                db.life_zoo = "escaped";
            }
        }
        if (db.university_time == 1){db.users_universities += 1;}
        if (db.university_time > 0 ) db.university_time -= 1;

        db.day += 1;
        if (db.month % 2 == 1 & db.day == 31) {db.month += 1;db.day = 1;}
        else if (db.day == 30 & db.month % 2 == 0 & db.month != 2){db.month += 1;db.day = 1;}
        else if (db.month == 2 & db.day == 29 & db.year % 4 == 0){db.month += 1;db.day = 1;}
        else if (db.month == 2 & db.day == 28 & db.year % 4 != 0){db.month += 1;db.day = 1;}

        if (db.month == 12){db.year += 1;db.month = 1;}

        if (db.partners_name != "escaped" & db.partners_name != null) db.relationship_love -= r.nextInt(10)+5;
        if (db.relationship_love < 0) db.partners_name = "escaped";

        db.save(path);
    }

    //magazine                                          _____    ________    ____ ____             ______
    //                            |\    /|      /\     |           /   /         |       |\  |    |
    //                            | \  / |     /__\    |   __     /   /          |       | \ |    |----
    //                            |  \/  |    /    \   |____|    /___/____   ____|____   |  \|    |_____
    protected void ini_magazine(){
        // magazine
        Button magazine_profile = findViewById(R.id.magazine_profile);
        Button magazine_work = findViewById(R.id.magazine_work);
        Button magazine_life = findViewById(R.id.magazine_life);
        LinearLayout showcase = findViewById(R.id.magazine_showcase);
        // end
        add_componets_to_showcase(showcase);

        magazine_profile.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {end_of_magazine_showcase();setContentView(R.layout.main_activity);ini_profile();}});

        magazine_life.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {end_of_magazine_showcase();setContentView(R.layout.life_activity);ini_life();}});

        magazine_work.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {end_of_magazine_showcase();setContentView(R.layout.work_activity);ini_work();}});
    }

    protected void add_componets_to_showcase(LinearLayout showcase){
        int index_tovar, tovar;

        Random random = new Random();
        for (int i=0;i<25;i++){
            LinearLayout linearLayout = new LinearLayout(getApplicationContext());
            LinearLayout btn_lb = new LinearLayout(getApplicationContext());
            ImageButton button = new ImageButton(getApplicationContext());
            TextView label = new TextView(getApplicationContext());
            tovar = random.nextInt(1);
            if (tovar < 3) {
                HashMap<String, String[][]> current_tovar;
                String[][] tovars;
                String name_of_tovar;
                if (tovar == 0) {
                    current_tovar = settings.processors;
                    tovars = current_tovar.get(settings.processors_keys[random.nextInt(settings.processors_keys.length)]);
                    name_of_tovar = "processors/" + tovars[random.nextInt(1)];
                } else if (tovar == 1) {
                    current_tovar = settings.disks;
                    tovars = current_tovar.get(settings.disks_keys[random.nextInt(settings.disks_keys.length)]);
                    name_of_tovar = "disks/" + tovars[random.nextInt(1)];
                } else {
                    current_tovar = settings.motherboards;
                    tovars = current_tovar.get(settings.motherboards_keys[random.nextInt(settings.motherboards_keys.length)]);
                }


            }
            else {
                HashMap<String, String[]> current_tovar;
                if (tovar == 3){
                    current_tovar = settings.psus;
                } else if (tovar == 4) {
                    current_tovar = settings.cases;
                } else if (tovar == 5) {
                    current_tovar = settings.culers;
                } else if (tovar == 7) {
                    current_tovar = settings.videocards;
                } else {
                    current_tovar = settings.rams;
                }
            }

        }
    }

    protected void end_of_magazine_showcase(){
        settings.magazine_is_back_btn_showed=false;
        settings.showed_elements_magazine=0;
        settings.showed_elements=0;
    }

    protected void magazine_show(String name_element){
        // inizializate
        ImageView img = findViewById(R.id.magazine_show_img);
        Button buy_btn = findViewById(R.id.magazine_show_buy_btn);
        Button back = findViewById(R.id.magazine_show_back);
        TextView text = findViewById(R.id.magazine_show_text);
        // end
        String[] characteristics = new String[19];
        if (name_element.indexOf("Intel") != -1) {
            for (int i=0;i<settings.processors.get("intel").length;i++){
                if (settings.processors.get("intel")[i][0].indexOf(name_element) != -1) {characteristics = settings.processors.get("intel")[i];break;}
            }
        }
        else {
            for (int i=0;i<settings.processors.get("amd").length;i++){
                if (settings.processors.get("amd")[i][0].indexOf(name_element) != -1) {characteristics = settings.processors.get("amd")[i];break;}
            }
        }
        Drawable drawable;
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        try {
            InputStream inputStream = getAssets().open("processors/" + name_element + ".png");
            drawable = Drawable.createFromStream(inputStream, "magazine_show");
            img.setImageDrawable(drawable);
        } catch (IOException e) {e.printStackTrace();}


        text.setText("name: " + name_element + "\nprice " + characteristics[1] + "\ncores: " + characteristics[2] + "\npotocs: " + characteristics[3] + "\necocores: " + characteristics[4] + "\nL2: " + characteristics[5] + "\nL3: " + characteristics[6] + "\nthickness: " + characteristics[7] + "\ntype of core: " + characteristics[8] + "core frequency: " + characteristics[9] + "\nmax operative memory: " + characteristics[10] + "\nhighest operative frequency: " + characteristics[11] + "\nmax operative memory: " + characteristics[12] + "\nheat generation: " + characteristics[13] + "\nmax work temperature: " + characteristics[14] + "\ngraphic type of core: " + characteristics[15] + "\ngraphic core frequency: " + characteristics[16] + "\nvirtualization: " + characteristics[17] + "\nyear: " + characteristics[18]);

        back.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.magazine_activity);ini_magazine();}});
    }

    //life                                    |           ____ ____     _____    _____
    //                                        |               |        |_____    |
    //                                        |               |        |         |----
    //                                        |_______    ____|____    |         |____
    protected void ini_life(){
        //life
        Button life_profile = findViewById(R.id.life_profile);
        Button life_magazine = findViewById(R.id.life_magazine);
        Button life_work = findViewById(R.id.life_work);
        Button life_pet = findViewById(R.id.life_pet);
        Button realutionship = findViewById(R.id.life_relationship);
        Button learn = findViewById(R.id.life_learn);
        Button transport = findViewById(R.id.life_transport);
        Button housing = findViewById(R.id.life_housing);
        Button computer = findViewById(R.id.life_computer);
        //end

        life_profile.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.main_activity);ini_profile();}});

        life_work.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.work_activity);ini_work();}});

        life_magazine.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.magazine_activity);ini_magazine();}});

        life_pet.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {if (db.life_zoo == null){setContentView(R.layout.choose_pet_activity);choose_pet();}else {setContentView(R.layout.pet_normal_activity);normal_pet();}}});

        realutionship.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {if (db.partners_name == null){setContentView(R.layout.resolutionship_choose_activity);choose_realationship();}else {setContentView(R.layout.normal_relationship);normal_relationship();}}});

        learn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.learn_activity);learn_ini();}});

        housing.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.housing_activity);housing_ini();}});

        computer.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.inventory_pc);inventory_pc();}});
    }

    // house
    protected void housing_ini(){
        // inizializate
        Button back = findViewById(R.id.housing_back);
        ImageButton left = findViewById(R.id.housing_left);
        ImageButton right = findViewById(R.id.housing_right);
        ImageView img = findViewById(R.id.housing_img);
        Button buy_btn = findViewById(R.id.housing_buy);
        Button arend_btn = findViewById(R.id.housing_arend_btn);
        // end

        left.setScaleType(ImageView.ScaleType.FIT_XY);
        right.setScaleType(ImageView.ScaleType.FIT_XY);

        left.setImageResource(R.drawable.left);
        right.setImageResource(R.drawable.right);

        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 2 || event.getAction() == 0) right.setBackgroundColor(getColor(R.color.rightleft_btn_pressed));
                if (event.getAction() == 1 || event.getAction() == 6) right.setBackgroundColor(getColor(R.color.spirit));
                return false;
            }
        });
        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 0 || event.getAction() == 2) left.setBackgroundColor(getColor(R.color.rightleft_btn_pressed));
                if (event.getAction() == 1 || event.getAction() == 6) left.setBackgroundColor(getColor(R.color.spirit));
                return false;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.life_activity);ini_life();}});

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    // education
    protected void learn_ini(){
        // inizializate elements
        Button back = findViewById(R.id.learn_back);
        Button learn_btn = findViewById(R.id.learn_buy);
        TextView graduated_tx = findViewById(R.id.learn_lb);
        ImageButton right = findViewById(R.id.learn_right);
        ImageButton left = findViewById(R.id.learn_left);
        TextView time_lb = findViewById(R.id.learn_time_lb);
        // end

        settings.showed_learn = 0;

        if (db.university_time > 0) time_lb.setText("Still studying at " + db.universities[db.users_universities+1] + " for " + db.university_time + "days");
        if (db.university_time == 1) time_lb.setText("Still studying at university for one day");

        left.setImageResource(R.drawable.left);
        right.setImageResource(R.drawable.right);

        if (db.users_universities > settings.showed_learn){learn_btn.setVisibility(View.INVISIBLE);graduated_tx.setVisibility(View.VISIBLE);graduated_tx.setText("You've already graduated in " + db.universities[settings.showed_learn]);}
        else {learn_btn.setVisibility(View.VISIBLE);graduated_tx.setVisibility(View.INVISIBLE);}
        learn_btn.setText(500*(settings.showed_learn+1)+"$\n"+db.universities[settings.showed_learn]);

        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==0 | event.getAction()==2) left.setBackgroundColor(getColor(R.color.rightleft_btn_pressed));
                if (event.getAction()==1 | event.getAction()==6) left.setBackgroundColor(getColor(R.color.spirit));
                return false;
            }
        });

        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==0 | event.getAction()==2) right.setBackgroundColor(getColor(R.color.rightleft_btn_pressed));
                if (event.getAction()==1 | event.getAction()==6) right.setBackgroundColor(getColor(R.color.spirit));
                return false;
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.showed_learn += 1;
                if (settings.showed_learn > 19) settings.showed_learn = 0;
                if (db.users_universities > settings.showed_learn){learn_btn.setVisibility(View.INVISIBLE);graduated_tx.setVisibility(View.VISIBLE);graduated_tx.setText("You've already graduated in " + db.universities[settings.showed_learn]);}
                else {learn_btn.setVisibility(View.VISIBLE);graduated_tx.setVisibility(View.INVISIBLE);}
                learn_img_make();
                learn_btn.setText(500*(settings.showed_learn+1)+"$\n"+db.universities[settings.showed_learn]);
            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.showed_learn -= 1;
                if (settings.showed_learn < 0)settings.showed_learn=19;
                if (db.users_universities > settings.showed_learn){learn_btn.setVisibility(View.INVISIBLE);graduated_tx.setVisibility(View.VISIBLE);graduated_tx.setText("You've already graduated in " + db.universities[settings.showed_learn]);}
                else {learn_btn.setVisibility(View.VISIBLE);graduated_tx.setVisibility(View.INVISIBLE);}
                learn_img_make();
                learn_btn.setText(500*(settings.showed_learn+1)+"$\n"+db.universities[settings.showed_learn]);
            }
        });

        learn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.university_time > 0) {
                    Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.error_mes1);
                    Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.error_mes2);

                    animation1.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {}
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            time_lb.startAnimation(animation2);
                        }@Override public void onAnimationRepeat(Animation animation) {}});

                    time_lb.startAnimation(animation1);
                }
                else {
                    db.university_time = 5*(settings.showed_learn+1);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.life_activity);ini_life();}});

        learn_img_make();
    }
    protected void learn_img_make(){
        // inizializate
        ImageView img = findViewById(R.id.learn_img);
        // end

        img.setScaleType(ImageView.ScaleType.FIT_CENTER);

        if (settings.showed_learn == 0) img.setImageResource(R.drawable.learn_cources1);
        else if (settings.showed_learn == 1) img.setImageResource(R.drawable.learn_cources2);
        else if (settings.showed_learn == 2) img.setImageResource(R.drawable.learn_cources3);
        else if (settings.showed_learn == 3) img.setImageResource(R.drawable.learn_cources4);
        else if (settings.showed_learn == 4) img.setImageResource(R.drawable.learn_cources5);
    }

    // pet
    protected void normal_pet(){
        // inizializate elements
        ImageView img = findViewById(R.id.pet_normal_pet_img);
        Button back = findViewById(R.id.pet_normal_back);
        ImageButton feed = findViewById(R.id.pet_normal_food_btn);
        ImageButton love = findViewById(R.id.pet_normal_love_btn);
        ProgressBar food_br = findViewById(R.id.pet_normal_progress_food);
        ProgressBar love_br = findViewById(R.id.pet_normal_progress_love);
        TextView text = findViewById(R.id.pet_normal_escape_text);
        LinearLayout second = findViewById(R.id.pet_normal_second_layout);
        LinearLayout foodlove = findViewById(R.id.pet_normal_foodlove_layout);
        // end

        food_br.setProgress(db.pet_food);
        love_br.setProgress(db.pet_love);

        feed.setImageResource(R.drawable.food);
        feed.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {feed_pet(food_br);}});

        love.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {love_pet(love_br);}});

        love.setImageResource(R.drawable.heart);

        if (db.life_zoo == "escaped") {
            text.setText("Your pet ran away from you\n\n\nBecause you didn't feed and didn't play with him");
            text.setBackgroundColor(getColor(R.color.polback));
            second.removeView(foodlove);
            Button a = new Button(getApplicationContext());
            a.setText("OK");
            a.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {kick_pet();setContentView(R.layout.life_activity);ini_life();}});
            second.addView(a);
        }
        System.out.println(db.life_zoo);
        if (db.life_zoo.indexOf("cat") != -1) img.setImageResource(R.drawable.cat);

        back.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.life_activity);ini_life();}});
    }
    protected void kick_pet(){
        db.life_zoo = null;
        TextView text = findViewById(R.id.pet_normal_escape_text);
        text.setBackgroundColor(getColor(R.color.spirit));
        text.setText("");
    }
    protected void choose_pet(){
        // inizializate elements
        Button back = findViewById(R.id.choose_pet_back);
        ImageButton left = findViewById(R.id.choose_pet_left_btn);
        ImageButton right = findViewById(R.id.choose_pet_right_btn);
        ImageView img = findViewById(R.id.choose_pet_image);
        TextView text = findViewById(R.id.choose_pet_text);
        Button accept = findViewById(R.id.choose_pet_take);
        // end

        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==0 | event.getAction()==2) left.setBackgroundColor(getColor(R.color.rightleft_btn_pressed));
                if (event.getAction()==1 | event.getAction()==6) left.setBackgroundColor(getColor(R.color.spirit));
                return false;
            }
        });

        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==0 | event.getAction()==2) right.setBackgroundColor(getColor(R.color.rightleft_btn_pressed));
                if (event.getAction()==1 | event.getAction()==6) right.setBackgroundColor(getColor(R.color.spirit));
                return false;
            }
        });

        img.setImageResource(R.drawable.cat);

        back.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.life_activity);ini_life();}});

        left.setImageResource(R.drawable.left);
        right.setImageResource(R.drawable.right);

        text.setText(settings.list_of_pets[present]);

        left.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {if (present > 0){present -= 1;}else{present = settings.list_of_pets.length-1;};text.setText(settings.list_of_pets[present]);if (present == 0) img.setImageResource(R.drawable.cat);}});

        right.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {if (present < settings.list_of_pets.length-1){present += 1;}else{present = 0;};text.setText(settings.list_of_pets[present]);if (present == 0) img.setImageResource(R.drawable.cat);}});

        accept.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {db.life_zoo = settings.list_of_pets[present];db.pet_love=100;db.pet_food=100;setContentView(R.layout.life_activity);ini_life();}});
    }
    protected void feed_pet(ProgressBar foodbar){
        if (db.pet_food > 99){
            TextView text = findViewById(R.id.pet_normal_escape_text);
            text.setText("Your pet is full");
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.pet_normal_full);
            text.startAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {@Override public void onAnimationStart(Animation animation) {text.setTextColor(getColor(R.color.red));}@Override public void onAnimationEnd(Animation animation) {text.setText("");
            }@Override public void onAnimationRepeat(Animation animation) {}});
        }
        else{
            Random r = new Random();
            db.pet_food += r.nextInt(10) + 10;
            foodbar.setProgress(db.pet_food);
        }
    }
    protected void love_pet(ProgressBar lovebar){
        if (db.pet_love > 99){
            TextView text = findViewById(R.id.pet_normal_escape_text);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.pet_normal_full);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    text.setText("Your pet is tired");
                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    text.setText("");
                }@Override public void onAnimationRepeat(Animation animation) {}
            });
            text.startAnimation(animation);
        }
        else {
            Random r = new Random();
            db.pet_love += r.nextInt(10)+10;
            lovebar.setProgress(db.pet_love);
        }
    }


    // relationship
    protected void choose_realationship(){
        // inizialization
        Button back = findViewById(R.id.resolutionship_choose_back);
        ImageButton right = findViewById(R.id.resolutionship_choose_right_btn);
        ImageView img = findViewById(R.id.resolutionship_choose_img);
        TextView text = findViewById(R.id.resolutionship_choose_text);
        Button filter = findViewById(R.id.resolutionship_choose_filter_btn);
        Button take = findViewById(R.id.resolutionship_choose_take_btn);
        // end

        settext(text);

        right.setImageResource(R.drawable.right);

        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==0 | event.getAction()==2) right.setBackgroundColor(getColor(R.color.rightleft_btn_pressed));
                if (event.getAction()==1 | event.getAction()==6) right.setBackgroundColor(getColor(R.color.spirit));
                return false;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.life_activity);ini_life();}});

        right.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {settext(text);}});

        filter.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.filter_activity);filter_init();}});

        take.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {if (!settings.is_showed_answer_relationship){accept_relationship(text);settings.is_showed_answer_relationship=true;}else {
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.error_mes1);
            TextView t = findViewById(R.id.resolutionship_choose_text_anim);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    t.setTextColor(getColor(R.color.red));
                    t.setText("Don't click!");
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    t.setTextColor(getColor(R.color.spirit));
                    t.setText("");
                    settings.is_showed_answer_relationship = false;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            t.startAnimation(animation);
        }}});
    }
    protected void accept_relationship(TextView text){
        Random r = new Random();
        TextView t = findViewById(R.id.resolutionship_choose_text_anim);
        if (r.nextInt(100) > 0) {
            String[] res = text.getText().toString().split("\n");
            db.partners_name = res[0].toString().split(" ")[1];
            db.partners_age = Integer.parseInt(res[1].toString().split(" ")[1]);
            db.partners_job = res[2].toString().split(" ")[1];
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.pet_normal_full);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    t.setTextColor(getColor(R.color.red));
                    t.setText("Success");
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    t.setText("");
                    t.setTextColor(getColor(R.color.spirit));
                    setContentView(R.layout.life_activity);ini_life();
                    settings.is_showed_answer_relationship = false;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            t.startAnimation(animation);
        }
        else {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.pet_normal_full);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    t.setTextColor(getColor(R.color.red));
                    t.setText("She doesn't like you");
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    t.setText("");
                    t.setTextColor(getColor(R.color.spirit));
                    settings.is_showed_answer_relationship = false;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            t.startAnimation(animation);
            settext(findViewById(R.id.resolutionship_choose_text));
        }
    }
    private void settext(TextView text){
        Random r = new Random();
        if (settings.realutionship_age == 0)
            text.setText("name: " + settings.names[r.nextInt(10)] + "\nage: " + (r.nextInt(40) + 16) + "\njob: " + settings.jobs[r.nextInt(10)]);
        else
            text.setText("name: " + settings.names[r.nextInt(10)] + "\nage: " + settings.realutionship_age + "\njob: " + settings.jobs[r.nextInt(10)]);
    };
    protected void filter_init(){
        // inizializate
        SeekBar sb = findViewById(R.id.filter_age_bar);
        TextView text = findViewById(R.id.filter_text);
        Button take = findViewById(R.id.filter_take_btn);
        // end

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                text.setText("Age\n\n\n" + (progress + 16));
            }@Override public void onStartTrackingTouch(SeekBar seekBar) {}@Override public void onStopTrackingTouch(SeekBar seekBar) {}});

        take.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {settings.realutionship_age = Integer.parseInt(text.getText().toString().substring(text.getText().length()-2));setContentView(R.layout.resolutionship_choose_activity);choose_realationship();}});
    }
    protected void normal_relationship(){
        // inizialization
        Button back = findViewById(R.id.normal_relationship_back);
        ImageButton love_btn = findViewById(R.id.normal_relationship_love_btn);
        ProgressBar love_prg = findViewById(R.id.normal_relationship_prgbr_love);
        TextView text_err = findViewById(R.id.normal_relationship_text_err);
        // end

        love_btn.setImageResource(R.drawable.heart);

        love_btn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
            if (!settings.love_walk){
                settings.love_walk = true;
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pet_normal_full);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        if (db.relationship_love < 100) {
                            text_err.setText("-10$");
                            db.money -= 10;
                        } else {
                            text_err.setText("-1$");
                            db.money -= 1;
                        }
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        text_err.setText("");
                        settings.love_walk = false;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                text_err.startAnimation(animation);
            }
            else {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.error_mes1);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        text_err.setText("Don't click!");
                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        text_err.setText("");
                        settings.love_walk = false;
                    }@Override public void onAnimationRepeat(Animation animation) {}
                });
                text_err.startAnimation(animation);
            }
            Random r = new Random();
            db.relationship_love += r.nextInt(20) + 5;
            love_prg.setProgress(db.relationship_love);
        }});

        love_prg.setProgress(db.relationship_love);

        back.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.life_activity);ini_life();}});

        if (db.partners_name == "escaped") {
            love_btn.setImageResource(R.drawable.broken_heart);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.pet_normal_full);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    text_err.setText("Your girlfriend left you\nbecause you didn't pay enough attention to your relationship.");
                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    text_err.setText("");
                    db.partners_name = null;
                    db.partners_job = null;
                    db.partners_age = 0;
                    LinearLayout main_layout = findViewById(R.id.normal_relation_main_layout);
                    Button a = new Button(getApplicationContext());
                    a.setText("ok");
                    a.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.life_activity);ini_life();}});
                    main_layout.addView(a);
                }
                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            text_err.startAnimation(animation);

        }
        else {

        }
    }

    protected void inventory_pc(){
        // inizialization
        LinearLayout main_layout = findViewById(R.id.main_lt);
        Button back = findViewById(R.id.inventory_back);
        // end

        back.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.life_activity);ini_life();}});

        for (int i=0;i<db.inventory.size();i += 2){
            String name = db.inventory_names[i] + ".jpg";

            // make layout
            LinearLayout layout = new LinearLayout(getApplicationContext());
            layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 750));
            layout.setOrientation(LinearLayout.HORIZONTAL);

            // make buttons
            // 1
            try {
                InputStream inputStream = getAssets().open("my pcs/" + name);
                Drawable drawable = Drawable.createFromStream(inputStream, name);
                ImageButton btn = new ImageButton(getApplicationContext());
                btn.setScaleType(ImageView.ScaleType.FIT_XY);
                btn.setImageDrawable(drawable);
                btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                btn.setBackgroundColor(getColor(R.color.spirit));

                String finalName = name;
                btn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.show_pc);show_inventory_pc(finalName);}});

                layout.addView(btn);

            } catch (Exception e) {}



            // 2

            try {
                name = db.inventory_names[i + 1] + ".jpg";
                InputStream inputStream = getAssets().open("my pcs/" + name);
                Drawable drawable = Drawable.createFromStream(inputStream, name);
                ImageButton btn = new ImageButton(getApplicationContext());
                btn.setScaleType(ImageView.ScaleType.FIT_XY);
                btn.setImageDrawable(drawable);
                btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                btn.setBackgroundColor(getColor(R.color.spirit));

                String finalName = name;
                btn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.show_pc);show_inventory_pc(finalName);}});

                layout.addView(btn);

            } catch (Exception e) {}

            main_layout.addView(layout);
        }
        Button btn = new Button(getApplicationContext());
        btn.setText("➕");
        btn.setTextSize(40f);
        btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f));
        btn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.make_pc);create_pc();}});
        main_layout.addView(btn);
    }

    public void create_pc() {
        // inizialate elements
        Button back = findViewById(R.id.make_pc_back);
        Button make_pc_case = findViewById(R.id.make_pc_case);
        Button motherboard = findViewById(R.id.make_pc_motherboard);
        Button processor = findViewById(R.id.make_pc_processor);
        Button disks = findViewById(R.id.make_pc_disks);
        Button videocard = findViewById(R.id.make_pc_videocard);
        Button cooler = findViewById(R.id.make_pc_cooler);
        Button psu = findViewById(R.id.make_pc_psu);
        Button ram = findViewById(R.id.make_pc_ram);
        LinearLayout showcase_layout = findViewById(R.id.showcase_layout);
        // end

        settings.current_showcase = 0;

        processor.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {settings.current_showcase = 0;show_category();}});

        back.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.inventory_pc);inventory_pc();}});

        show_category();
    }

    public void show_category(){
        LinearLayout main_lt = findViewById(R.id.showcase_layout);
        main_lt.removeAllViews();
        if (settings.current_showcase == 0){
            if (settings.new_pc[0] == null){
                for (int i = 0; i < db.processors_inventory_len; i++) {
                    if (db.processors[i][0] != null) {
                        ImageButton btn = new ImageButton(getApplicationContext());
                        btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                        btn.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        btn.setBackgroundColor(getColor(R.color.spirit));

                        try {
                            InputStream inputStream = getAssets().open("processors/" + db.processors[i][0] + ".png");
                            Drawable drawable = Drawable.createFromStream(inputStream, db.processors[i][0]);
                            btn.setImageDrawable(drawable);

                            int finalI = i;
                            btn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {accept_selcted_element(db.processors[finalI][0]);}});

                            main_lt.addView(btn);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                Button buy = new Button(getApplicationContext());
                buy.setText("Buy a processor");
                buy.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.magazine_activity);ini_magazine();}});
                main_lt.addView(buy);
            } else {
                ImageView img = new ImageView(getApplicationContext());
                img.setScaleType(ImageView.ScaleType.FIT_CENTER);
                System.out.println("processors/" + settings.new_pc[0] + ".png");
                try{
                    InputStream inputStream = getAssets().open("processors/" + settings.new_pc[0] + ".png");
                    Drawable drawable = Drawable.createFromStream(inputStream, settings.new_pc[0]);
                    img.setImageDrawable(drawable);

                    main_lt.addView(img);
                } catch (IOException e){e.printStackTrace();}

                Button replacement_btn = new Button(getApplicationContext());
                replacement_btn.setBackgroundColor(getColor(R.color.purple_200));
                replacement_btn.setText("Replace");
                replacement_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        settings.new_pc[0] = null;
                        show_category();
                        Button btn = findViewById(R.id.make_pc_processor);
                        btn.setBackgroundColor(getColor(R.color.red));
                    }
                });

                Button about = new Button(getApplicationContext());
                about.setText("About");
                about.setBackgroundColor(getColor(R.color.light_blue));
                about.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        main_lt.removeAllViews();
                        main_lt.addView(img);
                        Button back_btn = new Button(getApplicationContext());
                        back_btn.setText("Back");
                        back_btn.setBackgroundColor(getColor(R.color.red));
                        back_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                main_lt.removeAllViews();
                                main_lt.addView(img);
                                main_lt.addView(about);
                                main_lt.addView(replacement_btn);
                            }
                        });

                        ScrollView scrollView = new ScrollView(getApplicationContext());
                        TextView textView = new TextView(getApplicationContext());
                        scrollView.addView(textView);
                        textView.setGravity(Gravity.CENTER_HORIZONTAL);
                        String text="Name: " + settings.new_pc[0];
                        if (settings.new_pc[0].substring(0) == "i"){
                            text = text + settings.processors.get("intel")[];
                        }
                    }
                });

                main_lt.addView(about);
                main_lt.addView(replacement_btn);

            }
        }
    }

    public void accept_selcted_element(String name){
        settings.new_pc[settings.current_showcase] = name;
        if (settings.current_showcase == 0){
            Button processors_btn = findViewById(R.id.make_pc_processor);
            processors_btn.setBackgroundColor(getColor(R.color.green));
            settings.current_showcase = 1;
            show_category();
        }
    }

    public void show_inventory_pc(String pc_name){
        pc_name = pc_name.substring(0, pc_name.length()-4);
        // inizialization
        Button back = findViewById(R.id.show_pc_back);
        ImageView img = findViewById(R.id.show_pc_img);
        TextView textView = findViewById(R.id.show_pc_text);
        // end

        back.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.inventory_pc);inventory_pc();}});

        try {
            InputStream inputStream = getAssets().open("my pcs" + pc_name + ".jpg");
            Drawable drawable = Drawable.createFromStream(inputStream, pc_name);
            img.setImageDrawable(drawable);
        } catch (Exception e) {e.printStackTrace();}

        String processor = db.inventory.get(pc_name)[0], motherboard = db.inventory.get(pc_name)[1], ram = db.inventory.get(pc_name)[2], cooler = db.inventory.get(pc_name)[3], disks = db.inventory.get(pc_name)[4], videocard = db.inventory.get(pc_name)[5], psu = db.inventory.get(pc_name)[6], casee = db.inventory.get(pc_name)[7];

        String text = "processor: " + processor + "\n\n";
        text += "motherboard: " + motherboard + "\n\n";
        text += "ram: " + ram + "\n\n";
        text += "cooler: " + cooler + "\n\n";
        String[] disking = disks.split(",");
        for (int i=0;i<disking.length;i++){
            String disk = disking[i];
            if (i == 0){
                disk = disk.substring(1, disk.length());
            } else if (i == disking.length - 1 & i != 0) {
                disk = disk.substring(0, disk.length() - 1);
            }
            text += "disk " + (i + 1) + ": " + disk + "\n\n";
        }
        text += "videocard: " + videocard + "\n\n";
        text += "psu: " + psu + "\n\n";
        text += "casee: " + casee;

        textView.setText(text);
    }
}