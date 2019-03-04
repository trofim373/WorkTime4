package etsoftapp.worktime4;


import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private AdView mAdView;

    Boolean l_hide=false;
    SharedPreferences sPref;
    int savegr;

    Boolean open_menu=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-1716349820677911~4538137009");

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sPref = getPreferences(MODE_PRIVATE);

        String savegr_s=sPref.getString("savegr", ""); //сохранение настроек
        String sav_priv=sPref.getString("sav_priv", "");
        if(!sav_priv.equals("et")){
            privpol(null);

            SharedPreferences.Editor ed = sPref.edit();
            ed.putString("sav_priv", "et");
            ed.commit();

        }

        if(savegr_s.matches("[-+]?\\d+")){
            savegr=Integer.parseInt(savegr_s);
            Toast.makeText(this, "Выбран график: " + savegr_s, Toast.LENGTH_SHORT).show();
        }else{
            savegr=1;

        }

        select_gr(savegr);

 /*       WebView webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.loadUrl("file:///android_asset/1.html");
        */

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }




    public void bt_hide(View view) {

        RelativeLayout set1234 =(RelativeLayout) findViewById(R.id.set1234);
        //TextView setgr =(TextView) findViewById(R.id.setgr);//кнопка выбора графика при нажатии появляется окно

        if(l_hide){
            set1234.setVisibility(View.INVISIBLE);
            set1234.startAnimation(AnimationUtils.loadAnimation(this, R.anim.myscale_close));

            //setgr.setVisibility(View.INVISIBLE);
            //setgr.startAnimation(AnimationUtils.loadAnimation(this, R.anim.close_back));
        }else{
            set1234.setVisibility(View.VISIBLE);
            set1234.startAnimation(AnimationUtils.loadAnimation(this, R.anim.myscale_open));

            //setgr.setVisibility(View.VISIBLE);
            //setgr.startAnimation(AnimationUtils.loadAnimation(this, R.anim.open_back));
        }
        l_hide=!l_hide;
//*/
    }



    /*Слежка назад*/
    public void onBackPressed() {
        if(open_menu){
            bt_menu(null);
        }else if(l_hide){
            bt_hide(null);
        }else{
            moveTaskToBack(true);
            super.onBackPressed();
        }
    }


    public void g1(View view) { select_gr(1); } //выбор графика
    public void g2(View view) { select_gr(2); }
    public void g3(View view) { select_gr(3); }
    public void g4(View view) { select_gr(4); }


    public void select_gr(int n) {

        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("savegr", Integer.toString(n));
        ed.commit();

        WebView webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.loadUrl("file:///android_asset/" + Integer.toString(n) + ".html");

        Button bt_main = (Button)findViewById(R.id.button);
        bt_main.setText(Integer.toString(n));
    }





    public void bt_menu(View view) { //анимация открытия - закрытия меню
        RelativeLayout maction = (RelativeLayout)findViewById(R.id.maction);
        RelativeLayout backback = (RelativeLayout)findViewById(R.id.backback);

        if(open_menu){
            maction.startAnimation(AnimationUtils.loadAnimation(this, R.anim.close_menu));
            backback.startAnimation(AnimationUtils.loadAnimation(this, R.anim.close_back));
            maction.setVisibility(View.INVISIBLE);
            backback.setVisibility(View.INVISIBLE);
        }else{
            maction.setVisibility(View.VISIBLE);
            backback.setVisibility(View.VISIBLE);

            maction.startAnimation(AnimationUtils.loadAnimation(this, R.anim.open_menu));
            backback.startAnimation(AnimationUtils.loadAnimation(this, R.anim.open_back));
        }
        open_menu=!open_menu;
    }


    public void bt_exit(View view){  //КНОПКА ВЫХОДА
        System.runFinalizersOnExit(true);
        System.exit(0);
    }






    public void privpol(View v) {  //Кнопка политики конфиденциальности
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getString(R.string.privacy))
                .setMessage(getString(R.string.privacy1)) //
                //   .setContentView(R.layout.windowpriv)
                //  .setIcon(R.drawable.ic_android_cat)
                .setCancelable(false)

                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }



    public void alert_about(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getString(R.string.about))
                .setMessage(getString(R.string.about_txt)) //
                //   .setContentView(R.layout.windowpriv)
                //  .setIcon(R.drawable.ic_android_cat)
                .setCancelable(false)
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }





}
