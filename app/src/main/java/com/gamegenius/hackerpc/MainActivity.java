package com.gamegenius.hackerpc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Vibrator;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.start();
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
        LinearLayout row = new LinearLayout(getApplicationContext());

        for (int i=0;i<13;i++){
            LinearLayout linearLayout = new LinearLayout(getApplicationContext());
            for (int i2=0;i2<2;i2++){
                add_element_to_magazine(linearLayout, row);
            }
            row.addView(linearLayout);
        }

        showcase.addView(row);
    }
    protected void add_element_to_magazine(LinearLayout linearLayout, LinearLayout row){
        Random random = new Random();
        ImageButton btn = new ImageButton(getApplicationContext());
        TextView textView = new TextView(getApplicationContext());
        int tovar = random.nextInt(2);
        LinearLayout lt = new LinearLayout(getApplicationContext());

        lt.setOrientation(LinearLayout.VERTICAL);
        lt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));

        row.setOrientation(LinearLayout.VERTICAL);

        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));

        btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
        btn.setBackgroundColor(getColor(R.color.spirit));

        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getColor(R.color.black));
        textView.setBackgroundColor(getColor(R.color.spirit));
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 3.0f));

        if (tovar == 0) {
            if (random.nextInt(1) == 0) {
                String[][] lst = settings.processors.get("intel");
                int index = random.nextInt(20);
                String naming = lst[index][0];
                boolean is_found = false;
                for (int i3=0;i3<20;i3++) {naming = lst[random.nextInt(20)][0];System.out.println(naming);if (settings.was_tovar(naming) == false) {is_found=true;break;}}

                if (is_found == false){
                    add_element_to_magazine(linearLayout, row);
                }

                settings.was_len ++;
                settings.add_tovar_to_was(naming);

                try {
                    InputStream inputStream = getAssets().open("processors/" + naming + ".png");
                    System.out.println("processors/" + naming + ".png");
                    Drawable drawable = Drawable.createFromStream(inputStream, naming);
                    btn.setImageDrawable(drawable);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                lt.addView(textView);
                lt.addView(btn);
                textView.setText(naming);
                linearLayout.addView(lt);
            }
        } else if (tovar == 1){
            String[][] socket=new String[10][];
            String naming = "";
            while (true){
                int r = random.nextInt(4);
                int max = 0;
                if (r == 0) {
                    socket = settings.motherboards.get("lga1151");
                    max = 1;
                } else if (r == 1) {
                    socket = settings.motherboards.get("lga1151-v2");
                    max = 2;
                } else if (r == 2) {
                    socket = settings.motherboards.get("lga1200");
                    max = 8;
                } else {
                    socket = settings.motherboards.get("lga1700");
                    max = 4;
                }

                naming = socket[random.nextInt(max)][0];
                if (settings.was_tovar(naming) == false) break;
            }

            textView.setText(naming);



            try {
                InputStream inputStream = getAssets().open("motherboards/" + naming + ".png");
                Drawable drawable = Drawable.createFromStream(inputStream, naming);
                btn.setImageDrawable(drawable);
            } catch (Exception e){e.printStackTrace();}
            lt.addView(btn);
            lt.addView(textView);
            linearLayout.addView(lt);
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
        //Button realutionship = findViewById(R.id.life_relationship);
        //Button learn = findViewById(R.id.life_learn);
        //Button transport = findViewById(R.id.life_transport);
        //Button housing = findViewById(R.id.life_housing);
        Button computer = findViewById(R.id.life_computer);
        //end

        life_profile.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.main_activity);ini_profile();}});

        life_work.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.work_activity);ini_work();}});

        life_magazine.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.magazine_activity);ini_magazine();}});

        life_pet.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {if (db.life_zoo == null){setContentView(R.layout.choose_pet_activity);choose_pet();}else {setContentView(R.layout.pet_normal_activity);normal_pet();}}});

        //realutionship.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {if (db.partners_name == null){setContentView(R.layout.resolutionship_choose_activity);choose_realationship();}else {setContentView(R.layout.normal_relationship);normal_relationship();}}});

        //learn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.learn_activity);learn_ini();}});

        //housing.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.housing_activity);housing_ini();}});

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
            String name = "cases/" + db.inventory.get(db.inventory_names[i])[7] + ".png";

            // make layout
            LinearLayout layout = new LinearLayout(getApplicationContext());
            layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 750));
            layout.setOrientation(LinearLayout.HORIZONTAL);

            // make buttons
            // 1
            try {
                InputStream inputStream = getAssets().open(name);
                Drawable drawable = Drawable.createFromStream(inputStream, name);
                ImageButton btn = new ImageButton(getApplicationContext());
                btn.setScaleType(ImageView.ScaleType.FIT_XY);
                btn.setImageDrawable(drawable);
                btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                btn.setBackgroundColor(getColor(R.color.spirit));

                String[] finalName = db.inventory.get(db.inventory_names[i]);
                //btn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.show_pc);show_inventory_pc(finalName);}});

                layout.addView(btn);

            } catch (Exception e) {}


            // 2

            try {
                name = db.inventory.get(db.inventory_names[i + 1])[7] + ".png";
                InputStream inputStream = getAssets().open("cases/" + name);
                Drawable drawable = Drawable.createFromStream(inputStream, name);
                ImageButton btn = new ImageButton(getApplicationContext());
                btn.setScaleType(ImageView.ScaleType.FIT_XY);
                btn.setImageDrawable(drawable);
                btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                btn.setBackgroundColor(getColor(R.color.spirit));

                String[] finalName = db.inventory.get(db.inventory_names[i+1]);
                //btn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.show_pc);show_inventory_pc(finalName);}});

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
        Button sum_up = findViewById(R.id.make_pc_sum_up_btn);
        Button accept = findViewById(R.id.make_pc_accept_btn);
        LinearLayout showcase_layout = findViewById(R.id.showcase_layout);
        // end

        settings.current_showcase = 0;

        for (int i=0;i<8;i++){
            if (settings.new_pc[i] != null){
                if (i == 0) findViewById(R.id.make_pc_processor).setBackgroundColor(getColor(R.color.green));
                if (i == 1) findViewById(R.id.make_pc_motherboard).setBackgroundColor(getColor(R.color.green));
                if (i == 2) findViewById(R.id.make_pc_ram).setBackgroundColor(getColor(R.color.green));
                if (i == 3) findViewById(R.id.make_pc_disks).setBackgroundColor(getColor(R.color.green));
                if (i == 4) findViewById(R.id.make_pc_cooler).setBackgroundColor(getColor(R.color.green));
                if (i == 5) findViewById(R.id.make_pc_psu).setBackgroundColor(getColor(R.color.green));
                if (i == 6) findViewById(R.id.make_pc_videocard).setBackgroundColor(getColor(R.color.green));
                if (i == 7) findViewById(R.id.make_pc_case).setBackgroundColor(getColor(R.color.green));
            }
        }

        processor.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {settings.current_showcase = 0;show_category();}});
        motherboard.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {settings.current_showcase = 1;show_category();}});
        ram.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {settings.current_showcase = 2;show_category();}});
        cooler.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {settings.current_showcase = 3;show_category();}});
        psu.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {settings.current_showcase = 4;show_category();}});
        disks.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {settings.current_showcase = 5;show_category();}});
        videocard.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {settings.current_showcase = 6;show_category();}});
        make_pc_case.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {settings.current_showcase = 7;show_category();}});

        back.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.inventory_pc);inventory_pc();}});

        sum_up.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {sum_up_pc();}});
        accept.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {sum_up_pc();}});

        show_category();
    }
    public void sum_up_pc(){
        String[] processor=null, motherboard=null, casees, cooler, psu, videocard;
        HashMap<String, String[]> rams = new HashMap<>(), disks = new HashMap<>();
        LinearLayout main_lt = findViewById(R.id.showcase_layout);
        main_lt.removeAllViews();
        try {
            if (settings.string_checker(settings.new_pc[0].substring(0, 5), "Intel")) {
                for (int i = 0; i < settings.processors.get("intel").length; i++) {
                    if (settings.string_checker(settings.processors.get("intel")[i][0], settings.new_pc[0])) {
                        processor = settings.processors.get("intel")[i];
                    }
                }
            }
            String name_of_socket="";
            for (int sockets = 0; sockets < 4; sockets++) {
                String[][] socket = new String[0][0];
                if (sockets == 0) {
                    name_of_socket = "lga1151";
                } else if (sockets == 1) {
                    name_of_socket = "lga1151-v2";
                } else if (sockets == 2) {
                    name_of_socket = "lga1200";
                } else if (sockets == 3) {
                    name_of_socket = "lga1700";
                }
                socket = settings.motherboards.get(name_of_socket);
                for (int i = 0; i < socket.length; i++) {
                    if (settings.string_checker(socket[i][0], settings.new_pc[1])) {
                        motherboard = socket[i];
                        break;
                    }
                }
                if (motherboard != null) break;
            }
            for (int i = 0; i < settings.new_pc[2].split(";").length; i++) {
                String name = settings.new_pc[2].split(";")[i];
                rams.put(name, settings.rams.get(name));
            }
            casees = settings.cases.get(settings.new_pc[7]);
            cooler = settings.culers.get(settings.new_pc[3]);
            psu = settings.psus.get(settings.new_pc[4]);
            for (int i = 0; i < settings.new_pc[5].split(";").length; i++) {
                String name = settings.new_pc[5].split(";")[i];
                disks.put(name, settings.disks.get(name));
            }
            videocard = settings.videocards.get(settings.new_pc[6]);
            TextView tx = new TextView(getApplicationContext());
            tx.setGravity(Gravity.CENTER);
            tx.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            tx.setTextColor(getColor(R.color.black));
            if (settings.string_checker(processor[2], name_of_socket) == false){
                tx.setText("CPU and motherboard sockets do not match");
                main_lt.addView(tx);
            } else if (rams.size() > Integer.parseInt(motherboard[5])){
                tx.setText("too many RAM sticks");
                main_lt.addView(tx);
            } else if (Integer.parseInt(motherboard[9]) < disks.size()){
                tx.setText("There are not enough connectors for sata drives on the motherboard");
                main_lt.addView(tx);
            } else if (settings.string_checker(motherboard[3], casees[1])){
                tx.setText("Wrong size motherboard and case");
                main_lt.addView(tx);
            } else {
                creating_the_new_pc();
            }
        } catch (Exception e){main_lt.removeAllViews();TextView textView = new TextView(getApplicationContext());textView.setText("Not enough components");main_lt.addView(textView);}

    }
    protected void creating_the_new_pc(){
        LinearLayout main_lt = findViewById(R.id.showcase_layout);

        EditText editText = new EditText(getApplicationContext());
        editText.setText("Enter name of pc");
        editText.setBackgroundColor(getColor(R.color.polback));
        editText.setTextColor(getColor(R.color.black));
        editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        main_lt.addView(editText);

        Button accept = new Button(getApplicationContext());
        accept.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        accept.setText("accept");
        accept.setBackgroundColor(getColor(R.color.green));
        accept.setTextColor(getColor(R.color.black));
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().length() > 3 || editText.getText().length() < 20){
                    boolean is_added = false;
                    for (int i=0;i<10;i++){
                        if (db.inventory_names[i] == null){
                            is_added = true;
                            db.inventory_names[i] = editText.getText().toString();
                            break;
                        }
                    }
                    if (is_added) {
                        db.inventory.put(editText.getText().toString(), settings.new_pc);
                        setContentView(R.layout.inventory_pc);
                        inventory_pc();
                        int mod = 0;
                        for (int i=0;i<db.processors_inventory_len;i++){
                            if (mod == 1)db.processors[i] = db.processors[i+1];
                            else if (settings.string_checker(db.processors[i], settings.new_pc[0])) {db.processors[i] = db.processors[i+1];mod = 1;}
                        }
                        db.processors_inventory_len -= 1;
                        mod = 0;
                        for (int i=0;i<db.motherboard_inventory_len;i++){
                            if (mod == 1) db.motherboards[i] = db.motherboards[i+1];
                            else if (settings.string_checker(db.motherboards[i], settings.new_pc[1])) {db.motherboards[i] = db.motherboards[i+1];mod = 1;}
                        }
                        db.motherboard_inventory_len -= 1;
                        mod = 0;
                        for (int i=0;i<settings.new_pc[2].split(";").length;i++){
                            String ram = settings.new_pc[2].split(";")[i];
                            for (int i2=0;i2<db.rams.length;i2++){
                                if (db.rams[i2] != null) {if (settings.string_checker(ram, settings.find_the_name_of_ram(db.rams[i2])[0])) {
                                    db.remove_ram_element(i2);
                                }}
                            }
                            db.ram_inventory_len -= 1;
                        }
                        for (int i=0;i<db.coolers_inventory_len;i++){
                            if (mod == 1) db.coolers[i] = db.coolers[i+1];
                            else if (settings.string_checker(db.coolers[i], settings.new_pc[3])) {db.coolers[i] = db.coolers[i+1];mod = 1;}
                        }
                        db.cases_inventory_len -= 1;
                        mod = 0;
                        for (int i=0;i<db.psus_inventory_len;i++){
                            if (mod == 1) db.coolers[i] = db.coolers[i+1];
                            else if (settings.string_checker(db.psus[i], settings.new_pc[4])) {db.psus[i] = db.psus[i+1];mod = 1;}
                        }
                        db.psus_inventory_len -= 1;
                        mod = 0;
                        for (int i=0;i<settings.new_pc[5].split(";").length;i++){
                            String disk = settings.new_pc[5].split(";")[i];
                            for (int i2=0;i2<db.disks.length;i2++){
                                if (db.disks[i2] != null) {if (settings.string_checker(disk, settings.find_the_name_of_disk(db.disks[i2])[0])){
                                    db.remove_disk_element(i2);
                                }}
                            }
                            db.disks_inventory_len -= 1;
                        }
                        for (int i=0;i<db.videocards_inventory_len;i++){
                            if (mod == 1) db.videocards[i] = db.videocards[i+1];
                            else if (settings.string_checker(db.videocards[i], settings.new_pc[6])) {db.videocards[i] = db.videocards[i+1];mod = 1;}
                        }
                        mod = 0;
                        for (int i=0;i<db.cases_inventory_len;i++){
                            if (mod == 1) db.cases[i] = db.cases[i+1];
                            else if (settings.string_checker(db.cases[i], settings.new_pc[7])) {db.cases[i] = db.cases[i+1];mod = 1;}
                        }
                        try {
                            FileWriter fileWriter = new FileWriter(path + "/inventory.txt");
                            fileWriter.write(editText.getText().toString() + "\n");
                            fileWriter.write(settings.new_pc[0] + "," + settings.new_pc[1] + "," + settings.new_pc[2] + "," + settings.new_pc[3] + "," + settings.new_pc[4] + "," + settings.new_pc[5] + "," + settings.new_pc[6] + "," + settings.new_pc[7]);
                            fileWriter.close();

                            FileWriter fileWriter1 = new FileWriter(path + "/details.txt");
                            fileWriter1.write("0\n");
                            for (int i=0;i<db.processors_inventory_len;i++) fileWriter1.write(db.processors[i] + ",");
                            fileWriter1.write("\n1\n");
                            for (int i=0;i<db.motherboard_inventory_len;i++) fileWriter1.write(db.motherboards[i] + ",");
                            fileWriter1.write("\n2\n");
                            for (int i=0;i<db.ram_inventory_len;i++) fileWriter1.write(db.rams[i] + ",");
                            fileWriter1.write("\n3\n");
                            for (int i=0;i<db.cases_inventory_len;i++) fileWriter1.write(db.cases[i] + ",");
                            fileWriter1.write("\n4\n");
                            for (int i=0;i<db.psus_inventory_len;i++) fileWriter1.write(db.psus[i] + ",");
                            fileWriter1.write("\n5\n");
                            for (int i=0;i<db.disks_inventory_len;i++) fileWriter1.write(db.disks[i] + ",");
                            fileWriter1.write("\n6\n");
                            for (int i=0;i<db.coolers_inventory_len;i++) fileWriter1.write(db.coolers[i] + ",");
                            fileWriter1.write("\n7\n");
                            for (int i=0;i<db.videocards_inventory_len;i++) fileWriter1.write(db.videocards[i] + ",");
                            fileWriter1.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        settings.new_pc = new String[8];
                    }else {
                        main_lt.removeAllViews();
                        TextView tx = new TextView(getApplicationContext());
                        tx.setText("you have too many PCs in your inventory");
                        tx.setTextColor(getColor(R.color.red));
                        tx.setGravity(Gravity.CENTER);
                        main_lt.addView(tx);
                    }
                } else {
                    TextView tx = new TextView(getApplicationContext());
                    tx.setTextColor(getColor(R.color.red));
                    tx.setText("title size error");
                    main_lt.addView(tx);
                }
            }
        });
        main_lt.addView(accept);
    }
    public void show_category(){
        LinearLayout main_lt = findViewById(R.id.showcase_layout);
        main_lt.removeAllViews();
        if (settings.current_showcase == 0){
            if (settings.new_pc[0] == null){
                for (int i = 0; i < db.processors_inventory_len; i++) {
                    if (db.processors[i] != null) {
                        ImageButton btn = new ImageButton(getApplicationContext());
                        btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                        btn.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        btn.setBackgroundColor(getColor(R.color.spirit));

                        try {
                            InputStream inputStream = getAssets().open("processors/" + db.processors[i] + ".png");
                            Drawable drawable = Drawable.createFromStream(inputStream, db.processors[i]);
                            btn.setImageDrawable(drawable);

                            int finalI = i;
                            btn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {accept_selcted_element(db.processors[finalI]);}});

                            TextView tx = new TextView(getApplicationContext());
                            tx.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 2.0f));
                            tx.setText(db.processors[i]);
                            tx.setTextColor(getColor(R.color.black));
                            tx.setGravity(Gravity.CENTER);
                            LinearLayout home_ly = new LinearLayout(getApplicationContext());
                            home_ly.setOrientation(LinearLayout.VERTICAL);
                            home_ly.addView(tx);
                            home_ly.addView(btn);
                            main_lt.addView(home_ly);

                            TextView background = new TextView(getApplicationContext());
                            background.setBackgroundColor(getColor(R.color.black));
                            background.setText("");
                            background.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10));

                            main_lt.addView(background);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                Button buy = new Button(getApplicationContext());
                buy.setText("Buy a processor");
                buy.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.magazine_activity);ini_magazine();}});
                main_lt.addView(buy);
            }
            else {
                ImageView img = new ImageView(getApplicationContext());
                img.setScaleType(ImageView.ScaleType.FIT_CENTER);
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
                        textView.setTextColor(getColor(R.color.black));
                        scrollView.addView(textView);
                        textView.setGravity(Gravity.CENTER_HORIZONTAL);
                        String text="Name: " + settings.new_pc[0] + "\nSocket: ";
                        if (settings.string_checker(settings.new_pc[0].substring(0,5), "Intel")) {
                            for (int i = 0; i < settings.processors.get("intel").length; i++) {
                                if (settings.string_checker(settings.processors.get("intel")[i][0], settings.new_pc[0])) {
                                    text = text + settings.processors.get("intel")[i][2] + "\nCores: ";
                                    text = text + settings.processors.get("intel")[i][3] + "\nPotocs: ";
                                    text = text + settings.processors.get("intel")[i][4] + "\nEcocores: ";
                                    text = text + settings.processors.get("intel")[i][5] + "\nL2 cache: ";
                                    text = text + settings.processors.get("intel")[i][6] + "\nL3 cache: ";
                                    text = text + settings.processors.get("intel")[i][7] + "\nThickness: ";
                                    text = text + settings.processors.get("intel")[i][8] + "\nCore type: ";
                                    text = text + settings.processors.get("intel")[i][9] + "\nProcessor's frequency: ";
                                    text = text + settings.processors.get("intel")[i][10] + "Ggz\nProcessor's turbo frequency: ";
                                    text = text + settings.processors.get("intel")[i][11] + "Ggz\nMax operative frequency: ";
                                    text = text + settings.processors.get("intel")[i][13] + "\nMax memory: ";
                                    text = text + settings.processors.get("intel")[i][14] + "Gb\nHeat generation: ";
                                    text = text + settings.processors.get("intel")[i][15] + "W\nMax temperature: " + settings.processors.get("intel")[i][16];
                                    if (settings.string_checker(settings.processors.get("intel")[i][17], "") == false) {
                                        text = text + "\nGraphic model: " + settings.processors.get("intel")[i][17];
                                        text = text + "\nGraphic's frequency: " + settings.processors.get("intel")[i][18];
                                    }
                                    text = text + "\nVirtualization: " + settings.processors.get("intel")[i][19];
                                    text = text + "\nYear of issue: " + settings.processors.get("intel")[i][20];
                                }
                            }
                        }

                        textView.setText(text);
                        main_lt.addView(scrollView);
                        main_lt.addView(replacement_btn);
                    }
                });

                main_lt.addView(about);
                main_lt.addView(replacement_btn);

            }
        } else if (settings.current_showcase == 1){
            if (settings.new_pc[1] == null){
                for (int i=0;i<db.motherboard_inventory_len;i++){
                    ImageButton btn = new ImageButton(getApplicationContext());
                    btn.setBackgroundColor(getColor(R.color.spirit));
                    btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    btn.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    try {
                        InputStream inputStream = getAssets().open("motherboards/" + db.motherboards[i] + ".png");
                        Drawable drawable = Drawable.createFromStream(inputStream, db.motherboards[i]);
                        btn.setImageDrawable(drawable);

                        int finalI = i;
                        btn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {settings.new_pc[1] = db.motherboards[finalI];settings.current_showcase=2;show_category();Button btn = findViewById(R.id.make_pc_motherboard);btn.setBackgroundColor(getColor(R.color.green));}});

                        TextView tx = new TextView(getApplicationContext());
                        tx.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 2.0f));
                        tx.setText(db.motherboards[i]);
                        tx.setTextColor(getColor(R.color.black));
                        tx.setGravity(Gravity.CENTER);
                        LinearLayout home_ly = new LinearLayout(getApplicationContext());
                        home_ly.setOrientation(LinearLayout.VERTICAL);
                        home_ly.addView(tx);
                        home_ly.addView(btn);
                        main_lt.addView(home_ly);

                        TextView background = new TextView(getApplicationContext());
                        background.setBackgroundColor(getColor(R.color.black));
                        background.setText("");
                        background.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10));

                        main_lt.addView(background);
                    } catch (IOException e){e.printStackTrace();}
                }
                Button btn = new Button(getApplicationContext());
                btn.setText("Buy motherboard");
                btn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.magazine_activity);ini_magazine();}});
                main_lt.addView(btn);
            }
            else {
                ImageView img = new ImageView(getApplicationContext());
                img.setScaleType(ImageView.ScaleType.FIT_CENTER);
                try{
                    InputStream inputStream = getAssets().open("motherboards/" + settings.new_pc[1] + ".png");
                    Drawable drawable = Drawable.createFromStream(inputStream, settings.new_pc[1]);
                    img.setImageDrawable(drawable);

                    main_lt.addView(img);
                } catch (IOException e){e.printStackTrace();}

                Button replacement_btn = new Button(getApplicationContext());
                replacement_btn.setBackgroundColor(getColor(R.color.purple_200));
                replacement_btn.setText("Replace");
                replacement_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        settings.new_pc[1] = null;
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
                        textView.setTextColor(getColor(R.color.black));
                        scrollView.addView(textView);
                        textView.setGravity(Gravity.CENTER_HORIZONTAL);
                        String text="";
                        for (int sockets=0;sockets<4;sockets++) {
                            String name_of_socket="";
                            String[][] socket = new String[0][0];
                            if (sockets == 0) {
                                name_of_socket = "lga1151";
                            } else if (sockets == 1){
                                name_of_socket = "lga1151-v2";
                            } else if (sockets == 2){
                                name_of_socket = "lga1200";
                            } else if (sockets == 3){
                                name_of_socket = "lga1700";
                            }
                            socket = settings.motherboards.get(name_of_socket);
                            for (int i = 0; i < socket.length; i++) {
                                if (settings.string_checker(socket[i][0], settings.new_pc[1])) {
                                    text = "Name: " + settings.new_pc[1] + "\nSocket: ";
                                    text = text + name_of_socket + "\nPrice: ";
                                    text = text + settings.motherboards.get(name_of_socket)[i][1] + "$\nYear: ";
                                    text = text + settings.motherboards.get(name_of_socket)[i][2] + "\nForm-factor: ";
                                    text = text + settings.motherboards.get(name_of_socket)[i][3] + "\nTypes of RAM: ";
                                    text = text + settings.motherboards.get(name_of_socket)[i][4] + "\nRAM slots: ";
                                    text = text + settings.motherboards.get(name_of_socket)[i][5] + "\nMax RAM value: ";
                                    text = text + settings.motherboards.get(name_of_socket)[i][6] + "\nMax RAM frequency: ";
                                    text = text + settings.motherboards.get(name_of_socket)[i][7] + "\nM.2 slots: ";
                                    text = text + settings.motherboards.get(name_of_socket)[i][8] + "\nSata connectors: ";
                                    text = text + settings.motherboards.get(name_of_socket)[i][9] + "\nUsb2 connectors: ";
                                    text = text + settings.motherboards.get(name_of_socket)[i][10] + "\nUsb3 connectors: ";
                                    text = text + settings.motherboards.get(name_of_socket)[i][11] + "\nUsb-c connectors: ";
                                    text = text + settings.motherboards.get(name_of_socket)[i][12] + "\nNutrition: ";
                                    text = text + settings.motherboards.get(name_of_socket)[i][13] + "\nlights: " + settings.motherboards.get(name_of_socket)[i][14];
                                    break;
                                }
                            }
                            if (settings.string_checker(text, "") == false) break;
                        }

                        textView.setText(text);
                        main_lt.addView(scrollView);
                        main_lt.addView(replacement_btn);
                    }
                });

                main_lt.addView(about);
                main_lt.addView(replacement_btn);
            }
        } else if (settings.current_showcase == 2){
            if (settings.new_pc[2] == null){
                add_ram();
            }
            else {
                for (int index_of_ram=0;index_of_ram<settings.new_pc[2].split(";").length;index_of_ram++){
                    String name = settings.new_pc[2].split(";")[index_of_ram];
                    ImageView img = new ImageView(getApplicationContext());
                    img.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    try {
                        InputStream inputStream = getAssets().open("rams/" + settings.find_the_name_of_ram(name)[0] + ".png");
                        Drawable drawable = Drawable.createFromStream(inputStream, name);
                        img.setImageDrawable(drawable);

                        main_lt.addView(img);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Button replacement_btn = new Button(getApplicationContext());
                    replacement_btn.setBackgroundColor(getColor(R.color.purple_200));
                    replacement_btn.setText("Replace");
                    replacement_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (int i2=0;i2<settings.new_pc[2].split(";").length;i2++){
                                String naming = settings.new_pc[2].split(";")[i2];
                                boolean is_found = false;
                                for (int i = 0; i < db.ram_inventory_len; i++) {
                                    if (settings.string_checker(settings.find_the_name_of_ram(naming)[0], settings.find_the_name_of_ram(db.rams[i])[0])) {
                                        String ram = settings.find_the_name_of_ram(naming)[0];
                                        int kol = Integer.parseInt(settings.find_the_name_of_ram(naming)[1]), was = Integer.parseInt(settings.find_the_name_of_ram(db.rams[i])[1]);
                                        db.rams[i] = ram + " " + (kol + was);
                                        is_found = true;
                                        break;
                                    }
                                }
                                if (is_found == false) {
                                    String ram = settings.find_the_name_of_ram(naming)[0];
                                    int kol = Integer.parseInt(settings.find_the_name_of_ram(naming)[1]);
                                    db.rams[db.ram_inventory_len] = ram + " " + kol;
                                    db.ram_inventory_len++;
                                }
                            }
                            settings.new_pc[2] = null;
                            show_category();
                            Button btn = findViewById(R.id.make_pc_ram);
                            btn.setBackgroundColor(getColor(R.color.red));
                        }
                    });

                    Button add_btn = new Button(getApplicationContext());
                    add_btn.setText("add ram");
                    add_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            main_lt.removeAllViews();
                            add_ram();
                        }
                    });

                    Button about = new Button(getApplicationContext());
                    about.setText("About");
                    about.setBackgroundColor(getColor(R.color.light_blue));
                    about.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            main_lt.removeAllViews();
                            for (int img_of_ram=0;img_of_ram<settings.new_pc[2].split(";").length;img_of_ram++){
                                String path = settings.new_pc[2].split(";")[img_of_ram];
                                ImageView imageView = new ImageView(getApplicationContext());
                                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                try {
                                    InputStream inputStream = getAssets().open("rams/" + settings.find_the_name_of_ram(path)[0] + ".png");
                                    Drawable drawable = Drawable.createFromStream(inputStream, path);
                                    imageView.setImageDrawable(drawable);

                                    if (settings.string_checker(settings.find_the_name_of_ram(name)[0], settings.find_the_name_of_ram(path)[0]) == false) main_lt.addView(imageView);
                                } catch (IOException e) {e.printStackTrace();}
                            }
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
                            textView.setTextColor(getColor(R.color.black));
                            String ram = settings.find_the_name_of_ram(name)[0];
                            String kol = settings.find_the_name_of_ram(name)[1];
                            String text = "Name: " + ram + " x" + kol + "\nPrice: ";
                            text = text + settings.rams.get(ram)[0] + "$\nYear: ";
                            text = text + settings.rams.get(ram)[1] + "\nRAM type: ";
                            text = text + settings.rams.get(ram)[2] + "\nRAM value: ";
                            text = text + settings.rams.get(ram)[3] + "\nRAM timings: ";
                            text = text + settings.rams.get(ram)[4] + "\nRAM frequency: ";
                            text = text + settings.rams.get(ram)[5];

                            textView.setText(text);
                            main_lt.addView(scrollView);
                            main_lt.addView(replacement_btn);
                            main_lt.addView(add_btn);
                        }
                    });

                    TextView tx = new TextView(getApplicationContext());
                    tx.setBackgroundColor(getColor(R.color.black));
                    tx.setText("");
                    tx.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 15));

                    main_lt.addView(about);
                    main_lt.addView(replacement_btn);
                    main_lt.addView(add_btn);
                    main_lt.addView(tx);
                }

            }
        } else if (settings.current_showcase == 3){
            if (settings.new_pc[3] == null){
                for (int i=0;i<db.coolers_inventory_len;i++){
                    ImageButton btn = new ImageButton(getApplicationContext());
                    btn.setBackgroundColor(getColor(R.color.spirit));
                    btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    btn.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    try {
                        InputStream inputStream = getAssets().open("coolers/" + db.coolers[i] + ".png");
                        Drawable drawable = Drawable.createFromStream(inputStream, db.coolers[i]);
                        btn.setImageDrawable(drawable);

                        int finalI = i;
                        btn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {settings.new_pc[3] = db.coolers[finalI];settings.current_showcase = 4;show_category();Button btn = findViewById(R.id.make_pc_cooler);btn.setBackgroundColor(getColor(R.color.green));}});

                        TextView tx = new TextView(getApplicationContext());
                        tx.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 2.0f));
                        tx.setText(db.coolers[i]);
                        tx.setTextColor(getColor(R.color.black));
                        tx.setGravity(Gravity.CENTER);
                        LinearLayout home_ly = new LinearLayout(getApplicationContext());
                        home_ly.setOrientation(LinearLayout.VERTICAL);
                        home_ly.addView(tx);
                        home_ly.addView(btn);
                        main_lt.addView(home_ly);

                        TextView background = new TextView(getApplicationContext());
                        background.setBackgroundColor(getColor(R.color.black));
                        background.setText("");
                        background.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10));

                        main_lt.addView(background);
                    } catch (IOException e){e.printStackTrace();}
                }
                Button btn = new Button(getApplicationContext());
                btn.setText("Buy cooler");
                btn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.magazine_activity);ini_magazine();}});
                main_lt.addView(btn);
            }
            else {
                ImageView img = new ImageView(getApplicationContext());
                img.setScaleType(ImageView.ScaleType.FIT_CENTER);
                try{
                    InputStream inputStream = getAssets().open("coolers/" + settings.new_pc[3] + ".png");
                    Drawable drawable = Drawable.createFromStream(inputStream, settings.new_pc[3]);
                    img.setImageDrawable(drawable);

                    main_lt.addView(img);
                } catch (IOException e){e.printStackTrace();}

                Button replacement_btn = new Button(getApplicationContext());
                replacement_btn.setBackgroundColor(getColor(R.color.purple_200));
                replacement_btn.setText("Replace");
                replacement_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        settings.new_pc[3] = null;
                        show_category();
                        Button btn = findViewById(R.id.make_pc_cooler);
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
                        textView.setTextColor(getColor(R.color.black));

                        String text="Name: " + settings.new_pc[3] + "\nPrice: ";
                        text = text + settings.culers.get(settings.new_pc[3])[0] + "$\nSockets: ";
                        text = text + settings.culers.get(settings.new_pc[3])[1] + "\nSpeed: ";
                        text = text + settings.culers.get(settings.new_pc[3])[2] + "rpm\nHeat dissipation: ";
                        text = text + settings.culers.get(settings.new_pc[3])[3] + "W";

                        textView.setText(text);
                        main_lt.addView(scrollView);
                        main_lt.addView(replacement_btn);
                    }
                });

                main_lt.addView(about);
                main_lt.addView(replacement_btn);
            }
        } else if (settings.current_showcase == 4){
            if (settings.new_pc[4] == null){
                for (int i=0;i<db.psus_inventory_len;i++){
                    ImageButton btn = new ImageButton(getApplicationContext());
                    btn.setBackgroundColor(getColor(R.color.spirit));
                    btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    btn.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    try {
                        InputStream inputStream = getAssets().open("psus/" + db.psus[i] + ".png");
                        Drawable drawable = Drawable.createFromStream(inputStream, db.psus[i]);
                        btn.setImageDrawable(drawable);

                        int finalI = i;
                        btn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {settings.new_pc[4] = db.psus[finalI];settings.current_showcase = 6;show_category();Button btn = findViewById(R.id.make_pc_psu);btn.setBackgroundColor(getColor(R.color.green));}});

                        TextView tx = new TextView(getApplicationContext());
                        tx.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 2.0f));
                        tx.setText(db.psus[i]);
                        tx.setTextColor(getColor(R.color.black));
                        tx.setGravity(Gravity.CENTER);
                        LinearLayout home_ly = new LinearLayout(getApplicationContext());
                        home_ly.setOrientation(LinearLayout.VERTICAL);
                        home_ly.addView(tx);
                        home_ly.addView(btn);
                        main_lt.addView(home_ly);

                        TextView background = new TextView(getApplicationContext());
                        background.setBackgroundColor(getColor(R.color.black));
                        background.setText("");
                        background.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10));

                        main_lt.addView(background);
                    } catch (IOException e){e.printStackTrace();}
                }
                Button btn = new Button(getApplicationContext());
                btn.setText("Buy cooler");
                btn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.magazine_activity);ini_magazine();}});
                main_lt.addView(btn);
            }
            else {
                ImageView img = new ImageView(getApplicationContext());
                img.setScaleType(ImageView.ScaleType.FIT_CENTER);
                try{
                    InputStream inputStream = getAssets().open("psus/" + settings.new_pc[4] + ".png");
                    Drawable drawable = Drawable.createFromStream(inputStream, settings.new_pc[4]);
                    img.setImageDrawable(drawable);

                    main_lt.addView(img);
                } catch (IOException e){e.printStackTrace();}

                Button replacement_btn = new Button(getApplicationContext());
                replacement_btn.setBackgroundColor(getColor(R.color.purple_200));
                replacement_btn.setText("Replace");
                replacement_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        settings.new_pc[4] = null;
                        show_category();
                        Button btn = findViewById(R.id.make_pc_psu);
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
                        textView.setTextColor(getColor(R.color.black));

                        String text="Name: " + settings.new_pc[3] + "\nPrice: ";
                        text = text + settings.psus.get(settings.new_pc[4])[0] + "$\nMain power connector: ";
                        text = text + settings.psus.get(settings.new_pc[4])[1] + "\nCPU pins: ";
                        text = text + settings.psus.get(settings.new_pc[4])[2] + "\nPCI-E pins: ";
                        text = text + settings.psus.get(settings.new_pc[4])[3] + "\nTotal power: ";
                        text = text + settings.psus.get(settings.new_pc[4])[4] + "W";

                        textView.setText(text);
                        main_lt.addView(scrollView);
                        main_lt.addView(replacement_btn);
                    }
                });

                main_lt.addView(about);
                main_lt.addView(replacement_btn);
            }
        } else if (settings.current_showcase == 5){
            if (settings.new_pc[5] == null) {
                add_disk();
            }
            else {
                for (int index_of_ram=0;index_of_ram<settings.new_pc[5].split(";").length;index_of_ram++){
                    String name = settings.new_pc[5].split(";")[index_of_ram];
                    ImageView img = new ImageView(getApplicationContext());
                    img.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    try {
                        InputStream inputStream = getAssets().open("disks/" + settings.find_the_name_of_disk(name)[0] + ".png");
                        Drawable drawable = Drawable.createFromStream(inputStream, name);
                        img.setImageDrawable(drawable);

                        main_lt.addView(img);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Button replacement_btn = new Button(getApplicationContext());
                    replacement_btn.setBackgroundColor(getColor(R.color.purple_200));
                    replacement_btn.setText("Replace");
                    replacement_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (int i2=0;i2<settings.new_pc[5].split(";").length;i2++){
                                String naming = settings.new_pc[5].split(";")[i2];
                                boolean is_found = false;
                                for (int i = 0; i < db.disks_inventory_len; i++) {
                                    if (settings.string_checker(settings.find_the_name_of_ram(naming)[0], settings.find_the_name_of_ram(db.disks[i])[0])) {
                                        String ram = settings.find_the_name_of_ram(naming)[0];
                                        int kol = Integer.parseInt(settings.find_the_name_of_ram(naming)[1]), was = Integer.parseInt(settings.find_the_name_of_ram(db.disks[i])[1]);
                                        db.disks[i] = ram + " " + (kol + was);
                                        is_found = true;
                                        break;
                                    }
                                }
                                if (is_found == false) {
                                    String ram = settings.find_the_name_of_ram(naming)[0];
                                    int kol = Integer.parseInt(settings.find_the_name_of_ram(naming)[1]);
                                    db.disks[db.disks_inventory_len] = ram + " " + kol;
                                    db.disks_inventory_len++;
                                }
                            }
                            settings.new_pc[5] = null;
                            show_category();
                            Button btn = findViewById(R.id.make_pc_disks);
                            btn.setBackgroundColor(getColor(R.color.red));
                        }
                    });

                    Button add_btn = new Button(getApplicationContext());
                    add_btn.setText("add disk");
                    add_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            main_lt.removeAllViews();
                            add_disk();
                        }
                    });

                    Button about = new Button(getApplicationContext());
                    about.setText("About");
                    about.setBackgroundColor(getColor(R.color.light_blue));
                    about.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            main_lt.removeAllViews();
                            for (int img_of_ram=0;img_of_ram<settings.new_pc[5].split(";").length;img_of_ram++){
                                String path = settings.new_pc[5].split(";")[img_of_ram];
                                ImageView imageView = new ImageView(getApplicationContext());
                                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                try {
                                    InputStream inputStream = getAssets().open("disks/" + settings.find_the_name_of_ram(path)[0] + ".png");
                                    Drawable drawable = Drawable.createFromStream(inputStream, path);
                                    imageView.setImageDrawable(drawable);

                                    if (settings.string_checker(settings.find_the_name_of_disk(name)[0], settings.find_the_name_of_disk(path)[0]) == false) main_lt.addView(imageView);
                                } catch (IOException e) {e.printStackTrace();}
                            }
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
                            textView.setTextColor(getColor(R.color.black));
                            String disk = settings.find_the_name_of_disk(name)[0];
                            String kol = settings.find_the_name_of_disk(name)[1];
                            String text = "Name: " + disk + " x" + kol + "\nPrice: ";
                            text = text + settings.disks.get(disk)[0] + "$\nConnection type: ";
                            text = text + settings.disks.get(disk)[1] + "\nMemory: ";
                            text = text + settings.disks.get(disk)[2] + "Gb";

                            textView.setText(text);
                            main_lt.addView(scrollView);
                            main_lt.addView(replacement_btn);
                            main_lt.addView(add_btn);
                        }
                    });

                    TextView tx = new TextView(getApplicationContext());
                    tx.setBackgroundColor(getColor(R.color.black));
                    tx.setText("");
                    tx.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 15));

                    main_lt.addView(about);
                    main_lt.addView(replacement_btn);
                    main_lt.addView(add_btn);
                    main_lt.addView(tx);
                }
            }
        } else if (settings.current_showcase == 6){
            if (settings.new_pc[6] == null){
                for (int i = 0; i < db.videocards_inventory_len; i++) {
                    ImageButton btn = new ImageButton(getApplicationContext());
                    btn.setBackgroundColor(getColor(R.color.spirit));
                    btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
                    btn.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    try {
                        InputStream inputStream = getAssets().open("videocards/" + db.videocards[i] + ".png");
                        Drawable drawable = Drawable.createFromStream(inputStream, db.videocards[i]);
                        btn.setImageDrawable(drawable);

                        int finalI = i;
                        btn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {settings.new_pc[6] = db.videocards[finalI];settings.current_showcase = 7;show_category();Button btn = findViewById(R.id.make_pc_videocard);btn.setBackgroundColor(getColor(R.color.green));}});

                        TextView tx = new TextView(getApplicationContext());
                        tx.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 2.0f));
                        tx.setText(db.videocards[i]);
                        tx.setTextColor(getColor(R.color.black));
                        tx.setGravity(Gravity.CENTER);
                        LinearLayout home_ly = new LinearLayout(getApplicationContext());
                        home_ly.setOrientation(LinearLayout.VERTICAL);
                        home_ly.addView(tx);
                        home_ly.addView(btn);
                        main_lt.addView(home_ly);

                        TextView background = new TextView(getApplicationContext());
                        background.setBackgroundColor(getColor(R.color.black));
                        background.setText("");
                        background.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10));

                        main_lt.addView(background);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Button btn = new Button(getApplicationContext());
                btn.setText("Buy videocard");
                btn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.magazine_activity);ini_magazine();}});
                main_lt.addView(btn);
            }
            else {
                ImageView img = new ImageView(getApplicationContext());
                img.setScaleType(ImageView.ScaleType.FIT_CENTER);
                try {
                    InputStream inputStream = getAssets().open("videocards/" + settings.new_pc[6] + ".png");
                    Drawable drawable = Drawable.createFromStream(inputStream, settings.new_pc[6]);
                    img.setImageDrawable(drawable);

                    main_lt.addView(img);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Button replacement_btn = new Button(getApplicationContext());
                replacement_btn.setBackgroundColor(getColor(R.color.purple_200));
                replacement_btn.setText("Replace");
                replacement_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        settings.new_pc[6] = null;
                        show_category();
                        Button btn = findViewById(R.id.make_pc_videocard);
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
                        textView.setTextColor(getColor(R.color.black));

                        String text = "Name: " + settings.new_pc[6] + "\nPrice: ";
                        text = text + settings.videocards.get(settings.new_pc[6])[0] + "$\nYear: ";
                        text = text + settings.videocards.get(settings.new_pc[6])[1] + "\nMemory type: ";
                        text = text + settings.videocards.get(settings.new_pc[6])[2] + "\nMemory: ";
                        text = text + settings.videocards.get(settings.new_pc[6])[3] + "\nMemory bus: ";
                        text = text + settings.videocards.get(settings.new_pc[6])[4] + "\nMemory frequency: ";
                        text = text + settings.videocards.get(settings.new_pc[6])[5] + "\nTDP: ";
                        text = text + settings.videocards.get(settings.new_pc[6])[6];

                        textView.setText(text);
                        main_lt.addView(scrollView);
                        main_lt.addView(replacement_btn);
                    }
                });

                main_lt.addView(about);
                main_lt.addView(replacement_btn);
            }
        } else if (settings.current_showcase == 7){
            if (settings.new_pc[7] == null){
                for (int i = 0; i < db.cases_inventory_len; i++) {
                    ImageButton btn = new ImageButton(getApplicationContext());
                    btn.setBackgroundColor(getColor(R.color.spirit));
                    btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
                    btn.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    try {
                        InputStream inputStream = getAssets().open("cases/" + db.cases[i] + ".png");
                        Drawable drawable = Drawable.createFromStream(inputStream, db.cases[i]);
                        btn.setImageDrawable(drawable);

                        int finalI = i;
                        btn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {settings.new_pc[7] = db.cases[finalI];settings.current_showcase = 7;show_category();Button btn = findViewById(R.id.make_pc_case);btn.setBackgroundColor(getColor(R.color.green));}});

                        TextView tx = new TextView(getApplicationContext());
                        tx.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 2.0f));
                        tx.setText(db.cases[i]);
                        tx.setTextColor(getColor(R.color.black));
                        tx.setGravity(Gravity.CENTER);
                        LinearLayout home_ly = new LinearLayout(getApplicationContext());
                        home_ly.setOrientation(LinearLayout.VERTICAL);
                        home_ly.addView(tx);
                        home_ly.addView(btn);
                        main_lt.addView(home_ly);

                        TextView background = new TextView(getApplicationContext());
                        background.setBackgroundColor(getColor(R.color.black));
                        background.setText("");
                        background.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10));

                        main_lt.addView(background);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Button btn = new Button(getApplicationContext());
                btn.setText("Buy case");
                btn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.magazine_activity);ini_magazine();}});
                main_lt.addView(btn);
            }
            else {
                ImageView img = new ImageView(getApplicationContext());
                img.setScaleType(ImageView.ScaleType.FIT_CENTER);
                try {
                    InputStream inputStream = getAssets().open("cases/" + settings.new_pc[7] + ".png");
                    Drawable drawable = Drawable.createFromStream(inputStream, settings.new_pc[7]);
                    img.setImageDrawable(drawable);

                    main_lt.addView(img);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Button replacement_btn = new Button(getApplicationContext());
                replacement_btn.setBackgroundColor(getColor(R.color.purple_200));
                replacement_btn.setText("Replace");
                replacement_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        settings.new_pc[7] = null;
                        show_category();
                        Button btn = findViewById(R.id.make_pc_case);
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
                        textView.setTextColor(getColor(R.color.black));

                        String text = "Name: " + settings.new_pc[7] + "\nPrice: ";
                        text = text + settings.cases.get(settings.new_pc[7])[0] + "$\nSize: ";
                        text = text + settings.cases.get(settings.new_pc[7])[1];

                        textView.setText(text);
                        main_lt.addView(scrollView);
                        main_lt.addView(replacement_btn);
                    }
                });

                main_lt.addView(about);
                main_lt.addView(replacement_btn);
            }
        }
    }
    public void add_disk(){
        LinearLayout main_lt = findViewById(R.id.showcase_layout);
        for (int i=0;i<db.disks_inventory_len;i++){
            ImageButton btn = new ImageButton(getApplicationContext());
            btn.setBackgroundColor(getColor(R.color.spirit));
            btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            btn.setScaleType(ImageView.ScaleType.FIT_CENTER);
            String[] a = settings.find_the_name_of_disk(db.disks[i]);
            String disk=a[0];
            int kol = Integer.parseInt(a[1]);

            try {
                InputStream inputStream = getAssets().open("disks/" + disk + ".png");
                Drawable drawable = Drawable.createFromStream(inputStream, disk);
                btn.setImageDrawable(drawable);

                int finalI = i;
                btn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
                    String disk = settings.find_the_name_of_disk(db.disks[finalI])[0];
                    int kol = Integer.parseInt(settings.find_the_name_of_disk(db.disks[finalI])[1]);
                    kol -= 1;
                    if (kol > 0) db.disks[finalI] = disk + " " + kol;
                    else {
                        for (int i = 0; i < db.disks_inventory_len; i++) {
                            if (settings.string_checker(disk, settings.find_the_name_of_disk(db.disks[i])[0])) {
                                db.remove_disk_element(i);
                                break;
                            }
                        }
                    }

                    if (settings.new_pc[5] == null) settings.new_pc[5] = disk + " 1";
                    else {
                        boolean is_found = false;
                        for (int i=0;i<settings.new_pc[5].split(";").length;i++) {
                            String[] a = settings.new_pc[5].split(";");
                            String name = settings.new_pc[5].split(";")[i];
                            if (settings.string_checker(settings.find_the_name_of_disk(name)[0], disk)) {
                                settings.new_pc[5] = "";
                                for (int i2=0;i2<a.length;i2++){
                                    if (settings.string_checker(a[i2], disk + " " + (kol + 1))){
                                        settings.new_pc[5] = settings.new_pc[5] + disk + " " + (kol+2) + ";";
                                    } else {
                                        settings.new_pc[5] = settings.new_pc[5] + a[i2] + ";";
                                    }
                                }
                                is_found = true;
                                break;
                            }
                        }
                        if (is_found == false) {
                            settings.new_pc[5] = settings.new_pc[5] + ";" + disk + " " + 1;
                        }
                    }
                    settings.current_showcase=5;show_category();Button btn = findViewById(R.id.make_pc_disks);btn.setBackgroundColor(getColor(R.color.green));}});

                TextView tx = new TextView(getApplicationContext());
                tx.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 2.0f));
                if (kol == 1) tx.setText(disk);
                else tx.setText(disk + " x" + kol);
                tx.setTextColor(getColor(R.color.black));
                tx.setGravity(Gravity.CENTER);
                LinearLayout home_ly = new LinearLayout(getApplicationContext());
                home_ly.setOrientation(LinearLayout.VERTICAL);
                home_ly.addView(tx);
                home_ly.addView(btn);
                main_lt.addView(home_ly);

                TextView background = new TextView(getApplicationContext());
                background.setBackgroundColor(getColor(R.color.black));
                background.setText("");
                background.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10));

                main_lt.addView(background);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Button btn = new Button(getApplicationContext());
        btn.setText("Buy disk");
        btn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.magazine_activity);ini_magazine();}});
        main_lt.addView(btn);
    }
    public void add_ram(){
        LinearLayout main_lt = findViewById(R.id.showcase_layout);
        for (int i=0;i<db.ram_inventory_len;i++){
            ImageButton btn = new ImageButton(getApplicationContext());
            btn.setBackgroundColor(getColor(R.color.spirit));
            btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            btn.setScaleType(ImageView.ScaleType.FIT_CENTER);
            String[] a = settings.find_the_name_of_ram(db.rams[i]);
            String ram=a[0];
            int kol = Integer.parseInt(a[1]);

            try {
                InputStream inputStream = getAssets().open("rams/" + ram + ".png");
                Drawable drawable = Drawable.createFromStream(inputStream, ram);
                btn.setImageDrawable(drawable);

                int finalI = i;
                btn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
                    String ram = settings.find_the_name_of_ram(db.rams[finalI])[0];
                    int kol = Integer.parseInt(settings.find_the_name_of_ram(db.rams[finalI])[1]);
                    kol -= 1;
                    if (kol > 0) db.rams[finalI] = ram + " " + kol;
                    else {
                        for (int i=0;i<db.ram_inventory_len;i++){
                            if (settings.string_checker(ram, settings.find_the_name_of_ram(db.rams[i])[0])){
                                db.remove_ram_element(i);
                                break;
                            }}}
                    if (settings.new_pc[2] == null) settings.new_pc[2] = ram + " 1";
                    else {
                        boolean is_found = false;
                        for (int i=0;i<settings.new_pc[2].split(";").length;i++) {
                            String[] a = settings.new_pc[2].split(";");
                            String name = settings.new_pc[2].split(";")[i];
                            if (settings.string_checker(settings.find_the_name_of_ram(name)[0], ram)) {
                                settings.new_pc[2] = "";
                                for (int i2=0;i2<a.length;i2++){
                                    if (settings.string_checker(a[i2], ram + " " + (kol + 1))){
                                        settings.new_pc[2] = settings.new_pc[2] + ram + " " + (kol+2) + ";";
                                    } else {
                                        settings.new_pc[2] = settings.new_pc[2] + a[i2] + ";";
                                    }
                                }
                                is_found = true;
                                break;
                            }
                        }
                        if (is_found == false) {
                            settings.new_pc[2] = settings.new_pc[2] + ";" + ram + " " + 1;
                        }
                    }
                    settings.current_showcase=5;show_category();Button btn = findViewById(R.id.make_pc_ram);btn.setBackgroundColor(getColor(R.color.green));}});

                TextView tx = new TextView(getApplicationContext());
                tx.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 2.0f));
                if (kol == 1) tx.setText(ram);
                else tx.setText(ram + " x" + kol);
                tx.setTextColor(getColor(R.color.black));
                tx.setGravity(Gravity.CENTER);
                LinearLayout home_ly = new LinearLayout(getApplicationContext());
                home_ly.setOrientation(LinearLayout.VERTICAL);
                home_ly.addView(tx);
                home_ly.addView(btn);
                main_lt.addView(home_ly);

                TextView background = new TextView(getApplicationContext());
                background.setBackgroundColor(getColor(R.color.black));
                background.setText("");
                background.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10));

                main_lt.addView(background);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Button btn = new Button(getApplicationContext());
        btn.setText("Buy RAM");
        btn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.magazine_activity);ini_magazine();}});
        main_lt.addView(btn);
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
    public void show_inventory_pc(String[] components){
        // inizialization
        Button back = findViewById(R.id.show_pc_back);
        ImageView img = findViewById(R.id.show_pc_img);
        TextView textView = findViewById(R.id.show_pc_text);
        // end


        back.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {setContentView(R.layout.inventory_pc);inventory_pc();}});
        try {
            InputStream inputStream = getAssets().open("cases/" + components[7] + ".png");
            Drawable drawable = Drawable.createFromStream(inputStream, components[7]);
            img.setImageDrawable(drawable);
        } catch (IOException e) {e.printStackTrace();}

        String processor = components[0], motherboard = components[1], ram = components[2], cooler = components[3], disks = components[4], videocard = components[5], psu = components[6], casee = components[7];

        String text = "processor: " + processor + "\n\n";
        text += "motherboard: " + motherboard + "\n\n";
        text += "ram: " + ram + "\n\n";
        text += "cooler: " + cooler + "\n\n";
        String[] disking = disks.split(";");
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
        textView.setTextColor(getColor(R.color.black));
    }
}