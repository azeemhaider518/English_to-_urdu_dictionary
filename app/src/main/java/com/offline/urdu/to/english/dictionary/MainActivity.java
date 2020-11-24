package com.offline.urdu.to.english.dictionary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.offline.urdu.to.english.dictionary.adapter.AutocompleteCustomArrayAdapter;
import com.offline.urdu.to.english.dictionary.model.WordInfo;
import com.offline.urdu.to.english.dictionary.utils.DataBaseHelper;
import com.offline.urdu.to.english.dictionary.utils.EnglishDBHelper;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements
        View.OnFocusChangeListener, View.OnClickListener, View.OnLongClickListener,
        CompoundButton.OnCheckedChangeListener,AdapterView.OnItemSelectedListener {
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<WordInfo> mAdapter;
    List<WordInfo> list;
    DataBaseHelper dataBaseHelper;
    EnglishDBHelper englishDBHelper;
    WordInfo info;
    public static TextView txt_word;
    Button btn_clear;
    InterstitialAd mInterstitialAd;
RelativeLayout    ly_keyboard;

    private String[] category = null;
    Toolbar toolbar;
    Spinner navigationSpinner;
    String item;
///
private final static int URDU_PH_KEYBOARD = 0;
    private final static int PASHTO_KEYBOARD = 1;
    private final static int SINDHI_KEYBOARD = 2;
    private final static int URDU_AL_KEYBOARD = 3;
    private final static int ENGLISH_KEYBOARD = 4;

    private SoundPool soundPool;
    private int soundID;
    boolean loaded = false;

    private PopupWindow popupWindow;
    View keyboardPopup;
    Rect location;
    int[] keyLocation;

    Spinner spinner;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Dialog dialog;
    AudioManager am;
    Vibrator vibe;
    CheckBox _sound, _vib;
    public static boolean check = true;

    int cursorPosition;
    int textLength;
    int kbLanguage,kbSound;
    float volume;
    TextView keyBoardPhUr, keyBoardPs, keyBoardSndh, keyBoardAlpUr;
    ToggleButton sound, vibration;
    ImageView setting;
    RadioButton urduPhKeyboard, pashtoKeyboard, sindhKeyboard, urduAlpKeyboard,
            englishKeyboard;
    private Button mBSpace, mBdone, mBack, mBChange, mNum, mBSpace2, mBdone2,
            mBack2, mBChange2, done, cancel, mBackUrSC, xChangurSP, engcTOengl,
            spctourdchng, urduSpch1, urduSpch2, urduSpch3, urTOeng, urTOeng2,
            urTOeng3, engTour1, engToenSpChar, engSPCHARtoEng, engCAPTOsp,
            spCharEng, mSpaceEng, changeEngCTOL, enTour2, engTour3, EspCTOeng,
            mBSpace3, xDoneursp, xDoneEng, xDoneEngc, xDoneEsp, xbackengc,
            xChangeEngtoCap, xBackEng, xBackEngSc, xSpaceEngC, xSpaceEngsp,
            psSpace, psSpace2, psSpaceSc, psDone, psDone2, psDoneSc, psBack,
            psBack2, psBackSc, psChange, psChange2, psChangeSc, psToEng,
            psToEng2, psToEngSc, psToPsSc, psPsToSc, psPs2ToSc,

    sndhSpace, sndhSpace2, sndhSpaceSc, sndhDone, sndhDone2,
            sndhDoneSc, sndhBack, sndhBack2, sndhBackSc, sndhChange,
            sndhChange2, sndhChangeSc, sndhToEng, sndhToEng2, sndhToEngSc,
            sndhToSndhSc, sndhPsToSc, sndhPs2ToSc,

    urAlpSpace, urAlpSpace2, urAlpSpaceSc, urAlpDone, urAlpDone2,
            urAlpDoneSc, urAlpBack, urAlpBack2, urAlpBackSc, urAlpChange,
            urAlpChange2, urAlpChangeSc, urAlpToEng, urAlpToEng2, urAlpToEngSc,
            urAlpScToUrAlp, urAlpToUrAlpSc, urAlp2ToUrAlpSc;

    static MainActivity keyboardactObject;
    private LinearLayout mKLayout, mKLayout2, mKLayoutSch, mELayout, mELayoutC,
            mPsLayout, mPsLayout2, mPsLayoutSc, mSndhLayout, mSndhLayout2,
            mSndhLayoutSc, mUrAlpLayout, mUrAlpLayout2, mUrAlpLayoutSc;
    LinearLayout mELayoutSC;
    private LinearLayout mLayout;
    @SuppressWarnings("unused")
    private boolean isEdit = false;
    boolean soundState, vibState;
    Typeface tf2;
    boolean keybordflag = false;

    FileOutputStream fOut;
    OutputStreamWriter myOutWriter;
    Bitmap processedBitmap = null;
    StringBuffer output;
    String iconsStoragePath;
    Uri source1 = null;
    @SuppressWarnings("unused")
    private int w, mWindowWidth;
    private Button mB[] = new Button[405];

    ProgressDialog progressDialog;
    private EditText edText;
    public MainActivity() {
        this.textLength = 0;
    }
    ///
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        category = getResources().getStringArray(R.array.category);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        ly_keyboard= (RelativeLayout) findViewById(R.id.ly_keyboard);

        SpinnerAdapter spinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.category, R.layout.spinner_dropdown_item);
        navigationSpinner = new Spinner(getSupportActionBar().getThemedContext());
        navigationSpinner.setAdapter(spinnerAdapter);
        toolbar.addView(navigationSpinner, 0);
        navigationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item = category[position];
                if(position==0){
                    ly_keyboard.setVisibility(View.GONE);
                }else if(position==1){
                    ly_keyboard.setVisibility(View.GONE);
                }else  if(position==2){
                    ly_keyboard.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        AdView adView = (AdView) this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


       //loadAd();
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.inter_ad_unit_id));
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest1);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();

                mInterstitialAd.show();
                loadAd();
            }
        });

        try {
            dataBaseHelper = new DataBaseHelper(this);
            englishDBHelper = new EnglishDBHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }


        txt_word = (TextView) findViewById(R.id.txt_word);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);

        autoCompleteTextView.setHorizontallyScrolling(false);

        list = new ArrayList<>();

        mAdapter = new AutocompleteCustomArrayAdapter(this, R.layout.list_item, list);
        autoCompleteTextView.setAdapter(mAdapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {

                LinearLayout rl = (LinearLayout) arg1;
                TextView tv = (TextView) rl.getChildAt(0);
                autoCompleteTextView.setText("");

                txt_word.setText(tv.getText().toString());


            }

        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // txt_meaning.setText("");
                txt_word.setText("");
                autoCompleteTextView.setText("");
                if(mInterstitialAd.isLoaded()){
                    mInterstitialAd.show();
                    loadAd();
                }else {
                    loadAd();
                }
            }
        });

        autoCompleteTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    hidekeybord();
                }
                return false;
            }
        }
        );

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (item.matches("English to English")) {
                    Log.i("MainActivity", "afterTextChanged: EE ");

                    String word = "";
                    String sb = "";
                    word = s.toString();
                    if (!word.equalsIgnoreCase("")) {
                        sb = word.substring(0, 1).toUpperCase();

                        Log.i("MainActivity", "afterTextChanged:" + sb);
                        list.clear();
                        list = englishDBHelper.getSearchFeedFromEnglish(s.toString(), sb);
                        mAdapter = new AutocompleteCustomArrayAdapter(MainActivity.this, R.layout.list_item, list);

                        autoCompleteTextView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }

                    ;

                } else if(item.matches("English to Urdu")){

                    Log.i("MainActivity", "afterTextChanged: EU ");


                    String word = "";
                    String sb = "";
                    word = s.toString();
                    if (!word.equalsIgnoreCase("")) {
                        list = dataBaseHelper.getSearchFeed(s.toString());
                        mAdapter = new AutocompleteCustomArrayAdapter(MainActivity.this, R.layout.list_item, list);

                        autoCompleteTextView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }

                } else if(item.matches("Urdu to English")){

                    String word = "";
                    String sb = "";
                    word = s.toString();
                    if (!word.equalsIgnoreCase("")) {
                        list = dataBaseHelper.getUrduToEnglish(s.toString());
                        mAdapter = new AutocompleteCustomArrayAdapter(MainActivity.this, R.layout.list_item, list);

                        autoCompleteTextView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }
                }


                //  hidekeybord();


            }
        });

      /*  autoCompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hidekeybord();
                }
            }
        });*/

///
        //keyborde
        preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        soundState=getFromSP("key");
        vibState=getFromSP1("key1");
        kbLanguage=preferences.getInt("kb", 0);
        kbSound=preferences.getInt("spinnerSelection", 0);
        hideDefaultKeyboard();
        keyboardactObject = this;
        new LongOperation().execute("Loading Content");

        mLayout = (LinearLayout) findViewById(R.id.xK1);
        mKLayout = (LinearLayout) findViewById(R.id.xKeyBoard);
        mKLayout2 = (LinearLayout) findViewById(R.id.xKeyBoard2);
        mKLayoutSch = (LinearLayout) findViewById(R.id.xKeyBoardSC);
        mELayout = (LinearLayout) findViewById(R.id.xKeyBoardEn);
        mELayoutC = (LinearLayout) findViewById(R.id.xKeyBoardEnCAP);
        mELayoutSC = (LinearLayout) findViewById(R.id.xKeyBoardEnSch);
        mPsLayout = (LinearLayout) findViewById(R.id.xKeyBoardPs);
        mPsLayout2 = (LinearLayout) findViewById(R.id.xKeyBoardPs2);
        mPsLayoutSc = (LinearLayout) findViewById(R.id.xKeyBoardPsSc);
        mSndhLayout = (LinearLayout) findViewById(R.id.xKeyBoardSndh);
        mSndhLayout2 = (LinearLayout) findViewById(R.id.xKeyBoardSndh2);
        mSndhLayoutSc = (LinearLayout) findViewById(R.id.xKeyBoardSndhSc);
        mUrAlpLayout = (LinearLayout) findViewById(R.id.xKeyBoardUrAlp);
        mUrAlpLayout2 = (LinearLayout) findViewById(R.id.xKeyBoardUrAlp2);
        mUrAlpLayoutSc = (LinearLayout) findViewById(R.id.xKeyBoardUrAlpSc);

        if(kbLanguage==0){
            urduPhoneticKeyboard();
        }else if(kbLanguage==1){
            pashtoKeyboard();
        }else if(kbLanguage==2){
            sindhiKeyboard();
        }else if(kbLanguage==3){
            urduAlphabeticKeyboard();
        }else if(kbLanguage==4){
            englishKeyboard();
        }
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,
                                       int status) {
                loaded = true;
            }
        });
        loadSound();

    }

    private void loadAd() {

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.inter_ad_unit_id));
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest1);
    }

    private void hidekeybord() {
        InputMethodManager inputManager =
                (InputMethodManager) MainActivity.this.
                        getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }


    @Override
    public void onBackPressed() {

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            super.onBackPressed();
        }

    }

    ///keyborde
    private void loadSound() {
        if(kbSound==0){
            soundID = soundPool.load(this, R.raw.btnclicksnd1, 1);
            Toast.makeText(getApplication(), "sound 1", 0).show();
        }else if(kbSound==1){
            soundID = soundPool.load(this, R.raw.btnclicksnd2, 1);
            Toast.makeText(getApplication(), "sound 2", 0).show();
        }else if(kbSound==2){
            soundID = soundPool.load(this, R.raw.btnclicksnd3, 1);
            Toast.makeText(getApplication(), "sound 3", 0).show();
        }else if(kbSound==3){
            soundID = soundPool.load(this, R.raw.btnclicksnd4, 1);
            Toast.makeText(getApplication(), "sound 4", 0).show();
        }else if(kbSound==4){
            soundID = soundPool.load(this, R.raw.btnclicksnd8, 1);
            Toast.makeText(getApplication(), "sound 5", 0).show();
        }else if(kbSound==5){
            soundID = soundPool.load(this, R.raw.btnclicksnd6, 1);
            Toast.makeText(getApplication(), "sound 6", 0).show();
        }

    }


    public class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(MainActivity.this,
                    "please Wait", "Loading Contents...");
            MainActivity.this.init();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    @SuppressLint("CutPasteId")
    private void init() {
        tf2= Typeface.createFromAsset(getAssets(),
                "jameel.ttf");
        this.edText = (EditText) findViewById(R.id.xEt);
        done = (Button) findViewById(R.id.done);
        cancel = (Button) findViewById(R.id.cancel);
        setting = (ImageView) findViewById(R.id.setting);






        hideDefaultKeyboard();

        setKeys();







    }

    public static MainActivity getInstance() {
        return keyboardactObject;
    }

    private void isBack(View v) {
        if ((autoCompleteTextView.getText().toString().length() > 0)) {
            int temp = autoCompleteTextView.getSelectionEnd() - 1;
            if (temp >= 0) {
                autoCompleteTextView.setText((autoCompleteTextView.getText().toString()
                        .substring(0, autoCompleteTextView.getSelectionEnd() - 1).concat(autoCompleteTextView
                                .getText()
                                .toString()
                                .substring(autoCompleteTextView.getSelectionEnd(),
                                        autoCompleteTextView.getText().length()))));
                autoCompleteTextView.setSelection(temp);
            }
        }
    }

    public static void setTextInTextView(TextView tv, Context context,
                                         String text) {
        tv.setGravity(80);
        tv.setText(text);
    }

    /*enabling customized keyboard*/
    private void enableKeyboard() {
        keybordflag = true;
        mLayout.setVisibility(LinearLayout.VISIBLE);
        mKLayout.setVisibility(RelativeLayout.VISIBLE);

    }

    private void hideDefaultKeyboard() {
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void myFancyMethod(View v){
        //addText(v);
        final View pressbutton = (View) v;
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        keyboardPopup = inflater.inflate(R.layout.keyboardpop, null);
        popupWindow = new PopupWindow(keyboardPopup, v.getWidth() + 20,
                v.getHeight() + 20);
        TextView keyboardKey = (TextView) keyboardPopup
                .findViewById(R.id.popuptv);
//			keyboardKey.setText(pressbutton.getText().toString());

        keyLocation = new int[2];
//			pressbutton.getLocationOnScreen(keyLocation);
        location = new Rect();
        location.left = keyLocation[0];
        location.top = keyLocation[1];
//			location.right = location.left + pressbutton.getWidth();
//			location.bottom = location.top + pressbutton.getHeight();
    }
    //Toast.makeText(this, "click", Toast.LENGTH_SHORT).show()

    @SuppressLint("CutPasteId")
    private void setKeys() {
        mWindowWidth = getWindowManager().getDefaultDisplay().getWidth();
        //urdu first keyboard keys
        mB[0] = (Button) findViewById(R.id.xP);
        mB[1] = (Button) findViewById(R.id.xH);
        mB[2] = (Button) findViewById(R.id.xY);
        mB[3] = (Button) findViewById(R.id.Xha);
        mB[4] = (Button) findViewById(R.id.xYY);
        mB[5] = (Button) findViewById(R.id.xT);
        mB[6] = (Button) findViewById(R.id.xR);
        mB[7] = (Button) findViewById(R.id.xE);
        mB[8] = (Button) findViewById(R.id.xW);
        mB[9] = (Button) findViewById(R.id.xQ);
        mB[10] = (Button) findViewById(R.id.x1L);
        mB[11] = (Button) findViewById(R.id.x1K);
        mB[12] = (Button) findViewById(R.id.x1J);
        mB[13] = (Button) findViewById(R.id.x1H);
        mB[14] = (Button) findViewById(R.id.x1G);
        mB[15] = (Button) findViewById(R.id.x1F);
        mB[16] = (Button) findViewById(R.id.x1D);
        mB[17] = (Button) findViewById(R.id.x1S);
        mB[18] = (Button) findViewById(R.id.x1A);
        mB[19] = (Button) findViewById(R.id.X2lm);
        mB[20] = (Button) findViewById(R.id.x2n);
        mB[21] = (Button) findViewById(R.id.x2B);
        mB[22] = (Button) findViewById(R.id.x2T);
        mB[23] = (Button) findViewById(R.id.x2CH);
        mB[24] = (Button) findViewById(R.id.x2SH);
        mB[25] = (Button) findViewById(R.id.x2Z);
        //urdu second keyboard keys
        mB[26] = (Button) findViewById(R.id.cLB);
        mB[27] = (Button) findViewById(R.id.cRB);
        mB[28] = (Button) findViewById(R.id.cP);
        mB[29] = (Button) findViewById(R.id.cUP);
        mB[30] = (Button) findViewById(R.id.cSN);
        mB[31] = (Button) findViewById(R.id.cTE);
        mB[32] = (Button) findViewById(R.id.cRE);
        mB[33] = (Button) findViewById(R.id.cAS);
        mB[34] = (Button) findViewById(R.id.cDD);
        mB[35] = (Button) findViewById(R.id.cATT);
        mB[36] = (Button) findViewById(R.id.cSD);
        mB[37] = (Button) findViewById(R.id.cDDT);
        mB[38] = (Button) findViewById(R.id.cRH);
        mB[39] = (Button) findViewById(R.id.cKH);
        mB[40] = (Button) findViewById(R.id.cZD);
        mB[41] = (Button) findViewById(R.id.cHH);
        mB[42] = (Button) findViewById(R.id.cGH);
        mB[43] = (Button) findViewById(R.id.cCM);
        mB[44] = (Button) findViewById(R.id.cDL);
        mB[45] = (Button) findViewById(R.id.cSNS);
        mB[46] = (Button) findViewById(R.id.cNGN);
        mB[47] = (Button) findViewById(R.id.cZZ);
        mB[48] = (Button) findViewById(R.id.cSA);
        mB[49] = (Button) findViewById(R.id.cXAH);
        mB[50] = (Button) findViewById(R.id.cZAH);
        mB[51] = (Button) findViewById(R.id.cST);
        mB[52] = (Button) findViewById(R.id.cRZ);
        mB[53] = (Button) findViewById(R.id.cST2);
        // urdu digits
        mB[54] = (Button) findViewById(R.id.ur1);
        mB[55] = (Button) findViewById(R.id.ur2);
        mB[56] = (Button) findViewById(R.id.ur3);
        mB[57] = (Button) findViewById(R.id.ur4);
        mB[58] = (Button) findViewById(R.id.ur5);
        mB[59] = (Button) findViewById(R.id.ur6);
        mB[60] = (Button) findViewById(R.id.ur7);
        mB[61] = (Button) findViewById(R.id.ur8);
        mB[62] = (Button) findViewById(R.id.ur9);
        mB[63] = (Button) findViewById(R.id.urzr);
        // urdu special characters
        mB[64] = (Button) findViewById(R.id.urdusp1);
        mB[65] = (Button) findViewById(R.id.urdusp2);
        mB[66] = (Button) findViewById(R.id.urdusp3);
        mB[67] = (Button) findViewById(R.id.urdusp4);
        mB[68] = (Button) findViewById(R.id.urdusp5);
        mB[69] = (Button) findViewById(R.id.urdusp6);
        mB[70] = (Button) findViewById(R.id.urdusp7);
        mB[71] = (Button) findViewById(R.id.urdusp8);
        mB[72] = (Button) findViewById(R.id.urdusp9);
        mB[73] = (Button) findViewById(R.id.ursp12);
        mB[74] = (Button) findViewById(R.id.ursp13);
        mB[75] = (Button) findViewById(R.id.ursp15);
        mB[76] = (Button) findViewById(R.id.ursp16);
        mB[77] = (Button) findViewById(R.id.ursp17);
        mB[78] = (Button) findViewById(R.id.ursp18);
        mB[79] = (Button) findViewById(R.id.ursp19);
        mB[80] = (Button) findViewById(R.id.cSTur);
        //english keybord
        mB[81] = (Button) findViewById(R.id.engA1);
        mB[82] = (Button) findViewById(R.id.engA2);
        mB[83] = (Button) findViewById(R.id.engA3);
        mB[84] = (Button) findViewById(R.id.engA4);
        mB[85] = (Button) findViewById(R.id.engA5);
        mB[86] = (Button) findViewById(R.id.engA6);
        mB[87] = (Button) findViewById(R.id.engA7);
        mB[88] = (Button) findViewById(R.id.engA8);
        mB[89] = (Button) findViewById(R.id.engA9);
        mB[90] = (Button) findViewById(R.id.engA10);
        mB[91] = (Button) findViewById(R.id.engA11);
        mB[92] = (Button) findViewById(R.id.engA12);
        mB[93] = (Button) findViewById(R.id.engA13);
        mB[94] = (Button) findViewById(R.id.engA14);
        mB[95] = (Button) findViewById(R.id.engA15);
        mB[96] = (Button) findViewById(R.id.engA16);
        mB[97] = (Button) findViewById(R.id.engA17);
        mB[98] = (Button) findViewById(R.id.engA18);
        mB[99] = (Button) findViewById(R.id.eng19);
        mB[100] = (Button) findViewById(R.id.eng20);
        mB[101] = (Button) findViewById(R.id.eng21);
        mB[102] = (Button) findViewById(R.id.eng22);
        mB[103] = (Button) findViewById(R.id.eng23);
        mB[104] = (Button) findViewById(R.id.eng24);
        mB[105] = (Button) findViewById(R.id.eng25);
        mB[106] = (Button) findViewById(R.id.eng26);
        mB[107] = (Button) findViewById(R.id.engStop);
        //english keybord capital
        mB[108] = (Button) findViewById(R.id.engAC1);
        mB[109] = (Button) findViewById(R.id.engAC2);
        mB[110] = (Button) findViewById(R.id.engAC3);
        mB[111] = (Button) findViewById(R.id.engAC4);
        mB[112] = (Button) findViewById(R.id.engAC5);
        mB[113] = (Button) findViewById(R.id.engAC6);
        mB[114] = (Button) findViewById(R.id.engAC7);
        mB[115] = (Button) findViewById(R.id.engAC8);
        mB[116] = (Button) findViewById(R.id.engAC9);
        mB[117] = (Button) findViewById(R.id.engAC10);
        mB[118] = (Button) findViewById(R.id.engAC11);
        mB[119] = (Button) findViewById(R.id.engAC12);
        mB[120] = (Button) findViewById(R.id.engAC13);
        mB[121] = (Button) findViewById(R.id.engAC14);
        mB[122] = (Button) findViewById(R.id.engAC15);
        mB[123] = (Button) findViewById(R.id.engAC16);
        mB[124] = (Button) findViewById(R.id.engAC17);
        mB[125] = (Button) findViewById(R.id.engAC18);
        mB[126] = (Button) findViewById(R.id.engAC19);
        mB[127] = (Button) findViewById(R.id.engAC20);
        mB[128] = (Button) findViewById(R.id.engAC21);
        mB[129] = (Button) findViewById(R.id.engAC22);
        mB[130] = (Button) findViewById(R.id.engAC23);
        mB[131] = (Button) findViewById(R.id.engAC24);
        mB[132] = (Button) findViewById(R.id.engAC25);
        mB[133] = (Button) findViewById(R.id.engAC26);
        mB[134] = (Button) findViewById(R.id.engStopcS);
        //english keybord digits
        mB[135] = (Button) findViewById(R.id.eng1);
        mB[136] = (Button) findViewById(R.id.eng2);
        mB[137] = (Button) findViewById(R.id.eng3);
        mB[138] = (Button) findViewById(R.id.eng4);
        mB[139] = (Button) findViewById(R.id.eng5);
        mB[140] = (Button) findViewById(R.id.eng6);
        mB[141] = (Button) findViewById(R.id.eng7);
        mB[142] = (Button) findViewById(R.id.eng8);
        mB[143] = (Button) findViewById(R.id.eng9);
        mB[144] = (Button) findViewById(R.id.engzr);
        //english keybord Special Character
        mB[145] = (Button) findViewById(R.id.engsp1);
        mB[146] = (Button) findViewById(R.id.engsp2);
        mB[147] = (Button) findViewById(R.id.engsp3);
        mB[148] = (Button) findViewById(R.id.engsp4);
        mB[149] = (Button) findViewById(R.id.engsp5);
        mB[150] = (Button) findViewById(R.id.engsp6);
        mB[151] = (Button) findViewById(R.id.engsp7);
        mB[152] = (Button) findViewById(R.id.engsp8);
        mB[153] = (Button) findViewById(R.id.engsp9);
        mB[154] = (Button) findViewById(R.id.engsp12);
        mB[155] = (Button) findViewById(R.id.engsp13);
        mB[156] = (Button) findViewById(R.id.engsp14);
        mB[157] = (Button) findViewById(R.id.engsp15);
        mB[158] = (Button) findViewById(R.id.engsp16);
        mB[159] = (Button) findViewById(R.id.engsp17);
        mB[160] = (Button) findViewById(R.id.engsp18);
        mB[161] = (Button) findViewById(R.id.cSTENG);
        //pashto first keyboard
        mB[162] = (Button) findViewById(R.id.psP);
        mB[163] = (Button) findViewById(R.id.psH);
        mB[164] = (Button) findViewById(R.id.psY);
        mB[165] = (Button) findViewById(R.id.psXha);
        mB[166] = (Button) findViewById(R.id.psYY);
        mB[167] = (Button) findViewById(R.id.psT);
        mB[168] = (Button) findViewById(R.id.psR);
        mB[169] = (Button) findViewById(R.id.psE);
        mB[170] = (Button) findViewById(R.id.psW);
        mB[171] = (Button) findViewById(R.id.psQ);
        mB[172] = (Button) findViewById(R.id.ps1L);
        mB[173] = (Button) findViewById(R.id.ps1K);
        mB[174] = (Button) findViewById(R.id.ps1J);
        mB[175] = (Button) findViewById(R.id.ps1H);
        mB[176] = (Button) findViewById(R.id.ps1G);
        mB[177] = (Button) findViewById(R.id.ps1F);
        mB[178] = (Button) findViewById(R.id.ps1D);
        mB[179] = (Button) findViewById(R.id.ps1S);
        mB[180] = (Button) findViewById(R.id.ps1A);
        mB[181] = (Button) findViewById(R.id.ps2lm);
        mB[182] = (Button) findViewById(R.id.ps2n);
        mB[183] = (Button) findViewById(R.id.ps2B);
        mB[184] = (Button) findViewById(R.id.ps2T);
        mB[185] = (Button) findViewById(R.id.ps2CH);
        mB[186] = (Button) findViewById(R.id.ps2SH);
        mB[187] = (Button) findViewById(R.id.ps2Z);
        // pashto second keyboard keys
        mB[188] = (Button) findViewById(R.id.psLB);
        mB[189] = (Button) findViewById(R.id.psRB);
        mB[190] = (Button) findViewById(R.id.psSp);
        mB[191] = (Button) findViewById(R.id.psUP);
        mB[192] = (Button) findViewById(R.id.psSN);
        mB[193] = (Button) findViewById(R.id.psTE);
        mB[194] = (Button) findViewById(R.id.psRE);
        mB[195] = (Button) findViewById(R.id.psAS);
        mB[196] = (Button) findViewById(R.id.psDD);
        mB[197] = (Button) findViewById(R.id.psATT);
        mB[198] = (Button) findViewById(R.id.psSD);
        mB[199] = (Button) findViewById(R.id.psDDT);
        mB[200] = (Button) findViewById(R.id.psRH);
        mB[201] = (Button) findViewById(R.id.psKH);
        mB[202] = (Button) findViewById(R.id.psZD);
        mB[203] = (Button) findViewById(R.id.psHH);
        mB[204] = (Button) findViewById(R.id.psGH);
        mB[205] = (Button) findViewById(R.id.psCM);
        mB[206] = (Button) findViewById(R.id.psDL);
        mB[207] = (Button) findViewById(R.id.psSNS);
        mB[208] = (Button) findViewById(R.id.psNGN);
        mB[209] = (Button) findViewById(R.id.psZZ);
        mB[210] = (Button) findViewById(R.id.psSA);
        mB[211] = (Button) findViewById(R.id.psXAH);
        mB[212] = (Button) findViewById(R.id.psZAH);
        mB[213] = (Button) findViewById(R.id.psST);
        mB[214] = (Button) findViewById(R.id.psRZ);
        mB[215] = (Button) findViewById(R.id.psST2);
        //pashto digits
        mB[216] = (Button) findViewById(R.id.ps1);
        mB[217] = (Button) findViewById(R.id.ps2);
        mB[218] = (Button) findViewById(R.id.ps3);
        mB[219] = (Button) findViewById(R.id.ps4);
        mB[220] = (Button) findViewById(R.id.ps5);
        mB[221] = (Button) findViewById(R.id.ps6);
        mB[222] = (Button) findViewById(R.id.ps7);
        mB[223] = (Button) findViewById(R.id.ps8);
        mB[224] = (Button) findViewById(R.id.ps9);
        mB[225] = (Button) findViewById(R.id.pszr);
        //urdu special characters
        mB[226] = (Button) findViewById(R.id.psSpc1);
        mB[227] = (Button) findViewById(R.id.psSpc2);
        mB[228] = (Button) findViewById(R.id.psSp3);
        mB[229] = (Button) findViewById(R.id.psSp4);
        mB[230] = (Button) findViewById(R.id.psSp5);
        mB[231] = (Button) findViewById(R.id.psSp6);
        mB[232] = (Button) findViewById(R.id.psSp7);
        mB[233] = (Button) findViewById(R.id.psSp8);
        mB[234] = (Button) findViewById(R.id.psSp9);
        mB[235] = (Button) findViewById(R.id.psSp12);
        mB[236] = (Button) findViewById(R.id.psSp13);
        mB[237] = (Button) findViewById(R.id.psSp15);
        mB[238] = (Button) findViewById(R.id.psSp16);
        mB[239] = (Button) findViewById(R.id.psSp17);
        mB[240] = (Button) findViewById(R.id.psSp18);
        mB[241] = (Button) findViewById(R.id.psSp19);
        mB[242] = (Button) findViewById(R.id.psSTur);
        //sindhi first keyboard
        mB[243] = (Button) findViewById(R.id.sndhP);
        mB[244] = (Button) findViewById(R.id.sndhH);
        mB[245] = (Button) findViewById(R.id.sndhY);
        mB[246] = (Button) findViewById(R.id.sndhXha);
        mB[247] = (Button) findViewById(R.id.sndhYY);
        mB[248] = (Button) findViewById(R.id.sndhT);
        mB[249] = (Button) findViewById(R.id.sndhR);
        mB[250] = (Button) findViewById(R.id.sndhE);
        mB[251] = (Button) findViewById(R.id.sndhW);
        mB[252] = (Button) findViewById(R.id.sndhQ);
        mB[253] = (Button) findViewById(R.id.sndh1L);
        mB[254] = (Button) findViewById(R.id.sndh1K);
        mB[255] = (Button) findViewById(R.id.sndh1J);
        mB[256] = (Button) findViewById(R.id.sndh1H);
        mB[257] = (Button) findViewById(R.id.sndh1G);
        mB[258] = (Button) findViewById(R.id.sndh1F);
        mB[259] = (Button) findViewById(R.id.sndh1D);
        mB[260] = (Button) findViewById(R.id.sndh1S);
        mB[261] = (Button) findViewById(R.id.sndh1A);
        mB[262] = (Button) findViewById(R.id.sndh2lm);
        mB[263] = (Button) findViewById(R.id.sndh2n);
        mB[264] = (Button) findViewById(R.id.sndh2B);
        mB[265] = (Button) findViewById(R.id.sndh2T);
        mB[266] = (Button) findViewById(R.id.sndh2CH);
        mB[267] = (Button) findViewById(R.id.sndh2SH);
        mB[268] = (Button) findViewById(R.id.sndh2Z);
        //sindhi second keyboard keys
        mB[269] = (Button) findViewById(R.id.sndhLB);
        mB[270] = (Button) findViewById(R.id.sndhRB);
        mB[271] = (Button) findViewById(R.id.sndhSp);
        mB[272] = (Button) findViewById(R.id.sndhUP);
        mB[273] = (Button) findViewById(R.id.sndhSN);
        mB[274] = (Button) findViewById(R.id.sndhTE);
        mB[275] = (Button) findViewById(R.id.sndhRE);
        mB[276] = (Button) findViewById(R.id.sndhAS);
        mB[277] = (Button) findViewById(R.id.sndhDD);
        mB[278] = (Button) findViewById(R.id.sndhATT);
        mB[279] = (Button) findViewById(R.id.sndhSD);
        mB[280] = (Button) findViewById(R.id.sndhDDT);
        mB[281] = (Button) findViewById(R.id.sndhRH);
        mB[282] = (Button) findViewById(R.id.sndhKH);
        mB[283] = (Button) findViewById(R.id.sndhZD);
        mB[284] = (Button) findViewById(R.id.sndhHH);
        mB[285] = (Button) findViewById(R.id.sndhGH);
        mB[286] = (Button) findViewById(R.id.sndhCM);
        mB[287] = (Button) findViewById(R.id.sndhDL);
        mB[288] = (Button) findViewById(R.id.sndhSNS);
        mB[289] = (Button) findViewById(R.id.sndhNGN);
        mB[290] = (Button) findViewById(R.id.sndhZZ);
        mB[291] = (Button) findViewById(R.id.sndhSA);
        mB[292] = (Button) findViewById(R.id.sndhXAH);
        mB[293] = (Button) findViewById(R.id.sndhZAH);
        mB[294] = (Button) findViewById(R.id.sndhST);
        mB[295] = (Button) findViewById(R.id.sndhRZ);
        mB[296] = (Button) findViewById(R.id.sndhST2);
        //sindhi digits
        mB[297] = (Button) findViewById(R.id.sndh1);
        mB[298] = (Button) findViewById(R.id.sndh2);
        mB[299] = (Button) findViewById(R.id.sndh3);
        mB[300] = (Button) findViewById(R.id.sndh4);
        mB[301] = (Button) findViewById(R.id.sndh5);
        mB[302] = (Button) findViewById(R.id.sndh6);
        mB[303] = (Button) findViewById(R.id.sndh7);
        mB[304] = (Button) findViewById(R.id.sndh8);
        mB[305] = (Button) findViewById(R.id.sndh9);
        mB[306] = (Button) findViewById(R.id.sndhzr);
        //sindhi special characters
        mB[307] = (Button) findViewById(R.id.sndhSp1Sc);
        mB[308] = (Button) findViewById(R.id.sndhSp2Sc);
        mB[309] = (Button) findViewById(R.id.sndhSp3);
        mB[310] = (Button) findViewById(R.id.sndhSp4);
        mB[311] = (Button) findViewById(R.id.sndhSp5);
        mB[312] = (Button) findViewById(R.id.sndhSp6);
        mB[313] = (Button) findViewById(R.id.sndhSp7);
        mB[314] = (Button) findViewById(R.id.sndhSp8);
        mB[315] = (Button) findViewById(R.id.sndhSp9);
        mB[316] = (Button) findViewById(R.id.sndhSp12);
        mB[317] = (Button) findViewById(R.id.sndhSp13);
        mB[318] = (Button) findViewById(R.id.sndhSp15);
        mB[319] = (Button) findViewById(R.id.sndhSp16);
        mB[320] = (Button) findViewById(R.id.sndhSp17);
        mB[321] = (Button) findViewById(R.id.sndhSp18);
        mB[322] = (Button) findViewById(R.id.sndhSp19);
        mB[323] = (Button) findViewById(R.id.sndhSTur);
        //urdu Alphabetic first keyboard
        mB[324] = (Button) findViewById(R.id.alifmadd);
        mB[325] = (Button) findViewById(R.id.alif);
        mB[326] = (Button) findViewById(R.id.be);
        mB[327] = (Button) findViewById(R.id.pe);
        mB[328] = (Button) findViewById(R.id.te);
        mB[329] = (Button) findViewById(R.id.tte);
        mB[330] = (Button) findViewById(R.id.se);
        mB[331] = (Button) findViewById(R.id.jim);
        mB[332] = (Button) findViewById(R.id.che);
        mB[333] = (Button) findViewById(R.id.he);
        mB[334] = (Button) findViewById(R.id.khe);
        mB[335] = (Button) findViewById(R.id.dal);
        mB[336] = (Button) findViewById(R.id.ddal);
        mB[337] = (Button) findViewById(R.id.zal);
        mB[338] = (Button) findViewById(R.id.re);
        mB[339] = (Button) findViewById(R.id.rre);
        mB[340] = (Button) findViewById(R.id.ze);
        mB[341] = (Button) findViewById(R.id.zhe);
        mB[342] = (Button) findViewById(R.id.sin);
        mB[343] = (Button) findViewById(R.id.shin);
        mB[344] = (Button) findViewById(R.id.svad);
        mB[345] = (Button) findViewById(R.id.zvad);
        mB[346] = (Button) findViewById(R.id.toe);
        mB[347] = (Button) findViewById(R.id.zoe);
        mB[348] = (Button) findViewById(R.id.ain);
        mB[349] = (Button) findViewById(R.id.ghin);
        //urdu Alphabetic second keyboard
        mB[350] = (Button) findViewById(R.id.fe);
        mB[351] = (Button) findViewById(R.id.qaf);
        mB[352] = (Button) findViewById(R.id.kaf);
        mB[353] = (Button) findViewById(R.id.ghaf);
        mB[354] = (Button) findViewById(R.id.lam);
        mB[355] = (Button) findViewById(R.id.mim);
        mB[356] = (Button) findViewById(R.id.non);
        mB[357] = (Button) findViewById(R.id.nunghunna);
        mB[358] = (Button) findViewById(R.id.wawo);
        mB[359] = (Button) findViewById(R.id.hmzawawo);
        mB[360] = (Button) findViewById(R.id.he1);
        mB[361] = (Button) findViewById(R.id.he2);
        mB[362] = (Button) findViewById(R.id.teh);
        mB[363] = (Button) findViewById(R.id.hamza);
        mB[364] = (Button) findViewById(R.id.ye);
        mB[365] = (Button) findViewById(R.id.ye1);
        mB[366] = (Button) findViewById(R.id.yi);
        mB[367] = (Button) findViewById(R.id.yi1);
        mB[368] = (Button) findViewById(R.id.zabar);
        mB[369] = (Button) findViewById(R.id.zir);
        mB[370] = (Button) findViewById(R.id.pish);
        mB[371] = (Button) findViewById(R.id.shad);
        mB[372] = (Button) findViewById(R.id.saw);
        mB[373] = (Button) findViewById(R.id.rza);
        mB[374] = (Button) findViewById(R.id.rh);
        mB[375] = (Button) findViewById(R.id.as);
        //urdu alphabetic digits
        mB[376] = (Button) findViewById(R.id.ek);
        mB[377] = (Button) findViewById(R.id.du);
        mB[378] = (Button) findViewById(R.id.teen);
        mB[379] = (Button) findViewById(R.id.chaar);
        mB[380] = (Button) findViewById(R.id.panch);
        mB[381] = (Button) findViewById(R.id.chi);
        mB[382] = (Button) findViewById(R.id.sath);
        mB[383] = (Button) findViewById(R.id.ath);
        mB[384] = (Button) findViewById(R.id.nov);
        mB[385] = (Button) findViewById(R.id.das);
        //urdu alphabatic special characters
        mB[386] = (Button) findViewById(R.id.urAlpSp1);
        mB[387] = (Button) findViewById(R.id.urAlpSp2);
        mB[388] = (Button) findViewById(R.id.alpSp3);
        mB[389] = (Button) findViewById(R.id.alpSp4);
        mB[390] = (Button) findViewById(R.id.alpSp5);
        mB[391] = (Button) findViewById(R.id.alpSp6);
        mB[392] = (Button) findViewById(R.id.alpSp7);
        mB[393] = (Button) findViewById(R.id.alpSp8);
        mB[394] = (Button) findViewById(R.id.alpSp9);
        mB[395] = (Button) findViewById(R.id.alpSp10);
        mB[396] = (Button) findViewById(R.id.alpSp11);
        mB[397] = (Button) findViewById(R.id.alpSp12);
        mB[398] = (Button) findViewById(R.id.alpSp13);
        mB[399] = (Button) findViewById(R.id.alpSp14);
        mB[400] = (Button) findViewById(R.id.alpSp15);
        mB[401] = (Button) findViewById(R.id.alpSp16);
        mB[402] = (Button) findViewById(R.id.alpTur);
        mB[403] = (Button) findViewById(R.id.alpTur2);
        mB[404] = (Button) findViewById(R.id.alpTurSc);
		/* Second key bord controls */
        mBSpace = (Button) findViewById(R.id.xSpace);
        mBSpace.setTypeface(tf2);
        mBSpace2 = (Button) findViewById(R.id.xSpace2);
        mBSpace2.setTypeface(tf2);

        mBdone = (Button) findViewById(R.id.xDone);
        mBChange = (Button) findViewById(R.id.xChange);
        mBack = (Button) findViewById(R.id.xBack);

        mBdone2 = (Button) findViewById(R.id.xDone2);
        mBChange2 = (Button) findViewById(R.id.xChange2);
        mBack2 = (Button) findViewById(R.id.xBack2);
        mBSpace3 = (Button) findViewById(R.id.xSpaceUrSp);
        mBackUrSC = (Button) findViewById(R.id.xBackursp);
        xChangurSP = (Button) findViewById(R.id.xChangeursp);
        spctourdchng = (Button) findViewById(R.id.alpSpcUr);
        //
        urduSpch1 = (Button) findViewById(R.id.alpSp1);
        urduSpch1.setTypeface(tf2, Typeface.BOLD);
        urduSpch1.setTextSize(20);
        //
        urduSpch2 = (Button) findViewById(R.id.alpSp2);
        urduSpch2.setTypeface(tf2, Typeface.BOLD);
        urduSpch2.setTextSize(20);
        //
        urduSpch3 = (Button) findViewById(R.id.alpSpcUr);
        urduSpch3.setTypeface(tf2, Typeface.BOLD);
        urduSpch3.setTextSize(20);
        // xBackursp = (Button) findViewById(R.id.xBackursp);
        xDoneursp = (Button) findViewById(R.id.xDoneursp);
        urTOeng = (Button) findViewById(R.id.eng);
        urTOeng2 = (Button) findViewById(R.id.uToeng2);
        urTOeng3 = (Button) findViewById(R.id.engSP3);
        engTour1 = (Button) findViewById(R.id.engtour1);
        enTour2 = (Button) findViewById(R.id.engtour2);
        engTour3 = (Button) findViewById(R.id.enToUr3);
        spCharEng = (Button) findViewById(R.id.xChangeEngtoCap);
        engToenSpChar = (Button) findViewById(R.id.engToEnSp);
        changeEngCTOL = (Button) findViewById(R.id.xChangeEngtoEngl);
        EspCTOeng = (Button) findViewById(R.id.xChangeengsptoEng);
        engSPCHARtoEng = (Button) findViewById(R.id.enSCtoENG);
        xDoneEng = (Button) findViewById(R.id.xDoneEng);
        xBackEng = (Button) findViewById(R.id.xBackEng);
        xChangeEngtoCap = (Button) findViewById(R.id.xChangeEngtoCap);
        mSpaceEng = (Button) findViewById(R.id.xSpaceEng);
        // mNum = (Button) findViewById(R.id.xNum);
        engCAPTOsp = (Button) findViewById(R.id.EnCAPtoSP);
        engcTOengl = (Button) findViewById(R.id.xChangeEngtoEngl);
        xbackengc = (Button) findViewById(R.id.xBackEngC);
        xDoneEngc = (Button) findViewById(R.id.xDoneEngC);
        xSpaceEngC = (Button) findViewById(R.id.xSpaceEngC);
        xBackEngSc = (Button) findViewById(R.id.xBackengsp);
        xDoneEsp = (Button) findViewById(R.id.xDoneENGsp);
        xSpaceEngsp = (Button) findViewById(R.id.xSpaceENGsp);
		/* pashto keyboard buttons control */
        psToEng = (Button) findViewById(R.id.psToEng);
        psToEng2 = (Button) findViewById(R.id.psToEng2);
        psToEngSc = (Button) findViewById(R.id.psToEngSc);
        psToPsSc = (Button) findViewById(R.id.psSpcPs);

        psPsToSc = (Button) findViewById(R.id.psSp1);
        psPs2ToSc = (Button) findViewById(R.id.psSp2);

        psChange = (Button) findViewById(R.id.psChange);
        psChange2 = (Button) findViewById(R.id.psChange2);
        psChangeSc = (Button) findViewById(R.id.psChangeSc);

        psSpace = (Button) findViewById(R.id.psSpace);
        psSpace2 = (Button) findViewById(R.id.psSpace2);
        psSpaceSc = (Button) findViewById(R.id.psSpaceSc);

        psDone = (Button) findViewById(R.id.psDone);
        psDone2 = (Button) findViewById(R.id.psDone2);
        psDoneSc = (Button) findViewById(R.id.psDoneSc);

        psBack = (Button) findViewById(R.id.psBack);
        psBack2 = (Button) findViewById(R.id.psBack2);
        psBackSc = (Button) findViewById(R.id.psBackSc);
		/* sindhi keyboard control */
        sndhToEng = (Button) findViewById(R.id.sndhToEng);
        sndhToEng2 = (Button) findViewById(R.id.sndhToEng2);
        sndhToEngSc = (Button) findViewById(R.id.sndhToEngSc);
        sndhToSndhSc = (Button) findViewById(R.id.sndhTosndhSc);

        sndhChange = (Button) findViewById(R.id.sndhChange);
        sndhChange2 = (Button) findViewById(R.id.sndhChange2);
        sndhChangeSc = (Button) findViewById(R.id.sndhChangeSc);

        sndhPsToSc = (Button) findViewById(R.id.sndhSp1);
        sndhPs2ToSc = (Button) findViewById(R.id.sndhSp2);

        sndhSpace = (Button) findViewById(R.id.sndhSpace);
        sndhSpace2 = (Button) findViewById(R.id.sndhSpace2);
        sndhSpaceSc = (Button) findViewById(R.id.sndhSpaceSc);

        sndhDone = (Button) findViewById(R.id.sndhDone);
        sndhDone2 = (Button) findViewById(R.id.sndhDone2);
        sndhDoneSc = (Button) findViewById(R.id.sndhDoneSc);

        sndhBack = (Button) findViewById(R.id.sndhBack);
        sndhBack2 = (Button) findViewById(R.id.sndhBack2);
        sndhBackSc = (Button) findViewById(R.id.sndhBackSc);
		/* Urdu Alphabetic keyboard control */
        urAlpToEng = (Button) findViewById(R.id.alpToEng);
        urAlpToEng2 = (Button) findViewById(R.id.alpToEng2);
        urAlpToEngSc = (Button) findViewById(R.id.alpToEngSc);
        urAlpScToUrAlp = (Button) findViewById(R.id.alpScToAlp);

        urAlpChange = (Button) findViewById(R.id.alpChange);
        urAlpChange2 = (Button) findViewById(R.id.alpChange2);
        urAlpChangeSc = (Button) findViewById(R.id.alpChangeSc);

        urAlpToUrAlpSc = (Button) findViewById(R.id.alpToAlpSc);
        urAlp2ToUrAlpSc = (Button) findViewById(R.id.alp2ToAlpSc);

        urAlpSpace = (Button) findViewById(R.id.alpSpace);
        urAlpSpace2 = (Button) findViewById(R.id.alpSpace2);
        urAlpSpaceSc = (Button) findViewById(R.id.alpSpaceSc);

        urAlpDone = (Button) findViewById(R.id.alpDone);
        urAlpDone2 = (Button) findViewById(R.id.alpDone2);
        urAlpDoneSc = (Button) findViewById(R.id.alpDoneSc);

        urAlpBack = (Button) findViewById(R.id.alpBack);
        urAlpBack2 = (Button) findViewById(R.id.alpBack2);
        urAlpBackSc = (Button) findViewById(R.id.alpBackSc);

        for (int i = 0; i < mB.length; i++) {
            mB[i].setTypeface(tf2, Typeface.BOLD);
            mB[i].setTextSize(20);
            mB[i].setOnClickListener(this);
        }

        mBSpace.setOnClickListener(this);
        mBdone.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mBChange.setOnClickListener(this);
        mBSpace2.setOnClickListener(this);
        mBdone2.setOnClickListener(this);
        mBack2.setOnClickListener(this);
        mBChange2.setOnClickListener(this);
        mBSpace3.setOnClickListener(this);
        mBackUrSC.setOnClickListener(this);
        xChangurSP.setOnClickListener(this);
        spctourdchng.setOnClickListener(this);
        urduSpch1.setOnClickListener(this);
        urduSpch2.setOnClickListener(this);
        urduSpch3.setOnClickListener(this);

        urTOeng.setOnClickListener(this);
        urTOeng.setTypeface(tf2, Typeface.BOLD);
        urTOeng.setTextSize(20);
        //
        engTour1.setOnClickListener(this);
        engTour1.setTypeface(tf2, Typeface.BOLD);
        engTour1.setTextSize(20);
        //
        urTOeng3.setOnClickListener(this);
        urTOeng3.setTypeface(tf2, Typeface.BOLD);
        urTOeng3.setTextSize(20);
        //
        urTOeng2.setOnClickListener(this);
        urTOeng2.setTypeface(tf2, Typeface.BOLD);
        urTOeng2.setTextSize(20);

        enTour2.setOnClickListener(this);
        enTour2.setTypeface(tf2, Typeface.BOLD);
        enTour2.setTextSize(20);
        //
        engTour3.setOnClickListener(this);
        engTour3.setTypeface(tf2, Typeface.BOLD);
        engTour3.setTextSize(20);
        //
        spCharEng.setOnClickListener(this);
        engToenSpChar.setOnClickListener(this);
        changeEngCTOL.setOnClickListener(this);
        EspCTOeng.setOnClickListener(this);
        engSPCHARtoEng.setOnClickListener(this);
        engSPCHARtoEng.setTypeface(tf2, Typeface.BOLD);
        engSPCHARtoEng.setTextSize(20);
        engCAPTOsp.setOnClickListener(this);
        xDoneEng.setOnClickListener(this);
        xBackEng.setOnClickListener(this);
        xChangeEngtoCap.setOnClickListener(this);
        mSpaceEng.setOnClickListener(this);
        engcTOengl.setOnClickListener(this);
        xbackengc.setOnClickListener(this);
        xDoneEngc.setOnClickListener(this);
        xSpaceEngC.setOnClickListener(this);
        xDoneursp.setOnClickListener(this);
        xBackEngSc.setOnClickListener(this);
        xDoneEsp.setOnClickListener(this);
        xSpaceEngsp.setOnClickListener(this);
		/* pashto keyboard buttons onclick */
        psChange.setOnClickListener(this);
        psChange2.setOnClickListener(this);
        psChangeSc.setOnClickListener(this);

        psSpace.setOnClickListener(this);
        psSpace2.setOnClickListener(this);
        psSpaceSc.setOnClickListener(this);

        psDone.setOnClickListener(this);
        psDone2.setOnClickListener(this);
        psDoneSc.setOnClickListener(this);

        psBack.setOnClickListener(this);
        psBack2.setOnClickListener(this);
        psBackSc.setOnClickListener(this);
        // psBack.setOnLongClickListener(this);

        psToEng.setOnClickListener(this);
        psToEng.setTypeface(tf2, Typeface.BOLD);
        psToEng.setTextSize(20);

        psToEng2.setOnClickListener(this);
        psToEng2.setTypeface(tf2, Typeface.BOLD);
        psToEng2.setTextSize(20);

        psToEngSc.setOnClickListener(this);
        psToEngSc.setTypeface(tf2, Typeface.BOLD);
        psToEngSc.setTextSize(20);

        psToPsSc.setOnClickListener(this);
        psToPsSc.setTypeface(tf2, Typeface.BOLD);
        psToPsSc.setTextSize(20);

        psPsToSc.setOnClickListener(this);
        psPsToSc.setTypeface(tf2, Typeface.BOLD);
        psPsToSc.setTextSize(20);

        psPs2ToSc.setOnClickListener(this);
        psPs2ToSc.setTypeface(tf2, Typeface.BOLD);
        psPs2ToSc.setTextSize(20);

		/* sindhi keyboard buttons onclick */
        sndhChange.setOnClickListener(this);
        sndhChange2.setOnClickListener(this);
        sndhChangeSc.setOnClickListener(this);

        sndhPsToSc.setOnClickListener(this);
        sndhPsToSc.setTypeface(tf2, Typeface.BOLD);
        sndhPsToSc.setTextSize(20);

        sndhPs2ToSc.setOnClickListener(this);
        sndhPs2ToSc.setTypeface(tf2, Typeface.BOLD);
        sndhPs2ToSc.setTextSize(20);

        sndhSpace.setOnClickListener(this);
        sndhSpace2.setOnClickListener(this);
        sndhSpaceSc.setOnClickListener(this);

        sndhDone.setOnClickListener(this);
        sndhDone2.setOnClickListener(this);
        sndhDoneSc.setOnClickListener(this);

        sndhBack.setOnClickListener(this);
        sndhBack2.setOnClickListener(this);
        sndhBackSc.setOnClickListener(this);

        sndhToEng.setOnClickListener(this);
        sndhToEng.setTypeface(tf2, Typeface.BOLD);
        sndhToEng.setTextSize(20);

        sndhToEng2.setOnClickListener(this);
        sndhToEng2.setTypeface(tf2, Typeface.BOLD);
        sndhToEng2.setTextSize(20);

        sndhToEngSc.setOnClickListener(this);
        sndhToEngSc.setTypeface(tf2, Typeface.BOLD);
        sndhToEngSc.setTextSize(20);

        sndhToSndhSc.setOnClickListener(this);
        sndhToSndhSc.setTypeface(tf2, Typeface.BOLD);
        sndhToSndhSc.setTextSize(20);
		/* Urdu Alphabetic keyboard buttons onclick */
        urAlpChange.setOnClickListener(this);
        urAlpChange2.setOnClickListener(this);
        urAlpChangeSc.setOnClickListener(this);

        urAlpToUrAlpSc.setOnClickListener(this);
        urAlpToUrAlpSc.setTypeface(tf2, Typeface.BOLD);
        urAlpToUrAlpSc.setTextSize(20);

        urAlp2ToUrAlpSc.setOnClickListener(this);
        urAlp2ToUrAlpSc.setTypeface(tf2, Typeface.BOLD);
        urAlp2ToUrAlpSc.setTextSize(20);

        urAlpSpace.setOnClickListener(this);
        urAlpSpace2.setOnClickListener(this);
        urAlpSpaceSc.setOnClickListener(this);

        urAlpDone.setOnClickListener(this);
        urAlpDone2.setOnClickListener(this);
        urAlpDoneSc.setOnClickListener(this);

        urAlpBack.setOnClickListener(this);
        urAlpBack2.setOnClickListener(this);
        urAlpBackSc.setOnClickListener(this);

        urAlpToEng.setOnClickListener(this);
        urAlpToEng.setTypeface(tf2, Typeface.BOLD);
        urAlpToEng.setTextSize(20);

        urAlpToEng2.setOnClickListener(this);
        urAlpToEng2.setTypeface(tf2, Typeface.BOLD);
        urAlpToEng2.setTextSize(20);

        urAlpToEngSc.setOnClickListener(this);
        urAlpToEngSc.setTypeface(tf2, Typeface.BOLD);
        urAlpToEngSc.setTextSize(20);

        urAlpScToUrAlp.setOnClickListener(this);
        urAlpScToUrAlp.setTypeface(tf2, Typeface.BOLD);
        urAlpScToUrAlp.setTextSize(20);

        xChangeEngtoCap.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                check = true;

                mELayout.setVisibility(RelativeLayout.GONE);
                mELayoutC.setVisibility(RelativeLayout.VISIBLE);
                return true;
            }
        });

    }

    private void addText(View v) {
        if(soundState){
            sound();
        }
        vibration();

        int temp = autoCompleteTextView.getSelectionEnd();
        if (temp >= 0) {
            String b = "";
            b = (String) v.getTag();

            autoCompleteTextView.setText((autoCompleteTextView.getText().toString()
                    .substring(0, autoCompleteTextView.getSelectionEnd()) + b.concat(autoCompleteTextView
                    .getText().toString()
                    .substring(autoCompleteTextView.getSelectionEnd(), autoCompleteTextView.getText().length()))));

            autoCompleteTextView.setSelection(temp + 1);
        }
    }

    private void vibration() {
        if(vibState){
            vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibe.vibrate(50);
        }
    }
    private void sound() {
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        float actualVolume = (float) audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume = actualVolume / maxVolume;
        // Is the sound loaded already?
        if (loaded) {
            soundPool.play(soundID, volume, volume, 1, 0, 1f);
            Log.e("Test", "Played sound");
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.xEt:
                hideDefaultKeyboard();
                enableKeyboard();
                cursorPosition = autoCompleteTextView.getSelectionStart();
                break;

            case R.id.xChangeursp:
                mKLayout.setVisibility(RelativeLayout.VISIBLE);
                mKLayoutSch.setVisibility(RelativeLayout.GONE);
                break;
            case R.id.alpSp1:
                mKLayout.setVisibility(RelativeLayout.GONE);
                mKLayoutSch.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.alpSp2:
                mKLayout2.setVisibility(RelativeLayout.GONE);
                mKLayoutSch.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.uToeng2:
                selectLanguage(v);
                break;
            case R.id.alpSpcUr:
                mKLayout.setVisibility(RelativeLayout.VISIBLE);
                mKLayoutSch.setVisibility(RelativeLayout.GONE);
                break;
            case R.id.eng:
                //sLanguage(v);
                selectLanguage(v);
                break;
            case R.id.engSP3:
                selectLanguage(v);
                break;
            case R.id.engtour1:
                selectLanguage(v);
                check = true;
                break;
            case R.id.engtour2:
                selectLanguage(v);
                check = true;
                break;
            case R.id.enToUr3:
                selectLanguage(v);
                check = true;
                break;
            case R.id.xChangeEngtoCap:
                mELayout.setVisibility(RelativeLayout.GONE);
                mELayoutC.setVisibility(RelativeLayout.VISIBLE);
                check = false;
                break;

            case R.id.engToEnSp:
                mELayout.setVisibility(RelativeLayout.GONE);
                mELayoutSC.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.xChangeEngtoEngl:
                mELayout.setVisibility(RelativeLayout.VISIBLE);
                mELayoutC.setVisibility(RelativeLayout.GONE);
                break;
            case R.id.xChangeengsptoEng:
                mELayout.setVisibility(RelativeLayout.VISIBLE);
                mELayoutSC.setVisibility(RelativeLayout.GONE);
                break;

            case R.id.enSCtoENG:
                mELayout.setVisibility(RelativeLayout.VISIBLE);
                mELayoutSC.setVisibility(RelativeLayout.GONE);
                break;
            case R.id.EnCAPtoSP:
                mELayoutC.setVisibility(RelativeLayout.GONE);
                mELayoutSC.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.xChange:
                mKLayout.setVisibility(RelativeLayout.GONE);
                mKLayout2.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.xChange2:
                mKLayout.setVisibility(RelativeLayout.VISIBLE);
                mKLayout2.setVisibility(RelativeLayout.GONE);
                break;
            case R.id.psChange:
                mPsLayout.setVisibility(RelativeLayout.GONE);
                mPsLayout2.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.psChange2:
                mPsLayout2.setVisibility(RelativeLayout.GONE);
                mPsLayout.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.psChangeSc:
                mPsLayoutSc.setVisibility(RelativeLayout.GONE);
                mPsLayout.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.psToEng:
                selectLanguage(v);
                break;
            case R.id.psToEng2:
                selectLanguage(v);
                break;
            case R.id.psToEngSc:
                selectLanguage(v);
                break;
            case R.id.psSpcPs:
                mPsLayoutSc.setVisibility(RelativeLayout.GONE);
                mPsLayout.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.sndhToEng:
                selectLanguage(v);
                break;
            case R.id.sndhToEng2:
                selectLanguage(v);
                break;
            case R.id.sndhToEngSc:
                selectLanguage(v);
                break;
            case R.id.sndhTosndhSc:
                mSndhLayoutSc.setVisibility(RelativeLayout.GONE);
                mSndhLayout.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.psSp1:
                mPsLayout.setVisibility(RelativeLayout.GONE);
                mPsLayoutSc.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.psSp2:
                mPsLayout2.setVisibility(RelativeLayout.GONE);
                mPsLayoutSc.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.sndhChange:
                mSndhLayout.setVisibility(RelativeLayout.GONE);
                mSndhLayout2.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.sndhChange2:
                mSndhLayout2.setVisibility(RelativeLayout.GONE);
                mSndhLayout.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.sndhChangeSc:
                mSndhLayoutSc.setVisibility(RelativeLayout.GONE);
                mSndhLayout.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.sndhSp1:
                mSndhLayout.setVisibility(RelativeLayout.GONE);
                mSndhLayoutSc.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.sndhSp2:
                mSndhLayout2.setVisibility(RelativeLayout.GONE);
                mSndhLayoutSc.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.alpChange:
                mUrAlpLayout.setVisibility(RelativeLayout.GONE);
                mUrAlpLayout2.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.alpChange2:
                mUrAlpLayout2.setVisibility(RelativeLayout.GONE);
                mUrAlpLayout.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.alpChangeSc:
                mUrAlpLayoutSc.setVisibility(RelativeLayout.GONE);
                mUrAlpLayout.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.alpToEng:
                selectLanguage(v);
                //sLanguage(v);
                break;
            case R.id.alpToEng2:
                selectLanguage(v);
                break;
            case R.id.alpToEngSc:
                selectLanguage(v);
                break;
            case R.id.alpScToAlp:
                mUrAlpLayoutSc.setVisibility(RelativeLayout.GONE);
                mUrAlpLayout.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.alpToAlpSc:
                mUrAlpLayout.setVisibility(RelativeLayout.GONE);
                mUrAlpLayoutSc.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.alp2ToAlpSc:
                mUrAlpLayout2.setVisibility(RelativeLayout.GONE);
                mUrAlpLayoutSc.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.xDone:
                addText(v);
                break;
            case R.id.xDone2:
                addText(v);
                break;
            case R.id.xDoneursp:
                addText(v);
                break;
            case R.id.xDoneEng:
                addText(v);
                break;
            case R.id.xDoneEngC:
                addText(v);
                break;
            case R.id.xBack:
                isBack(v);
                break;
            case R.id.xBack2:
                isBack(v);
                break;
            case R.id.xBackursp:
                isBack(v);
                break;
            case R.id.xBackEng:
                isBack(v);
                break;
            case R.id.xBackEngC:
                isBack(v);
                break;
            case R.id.xBackengsp:
                isBack(v);
                break;
            case R.id.psBack:
                isBack(v);
                break;
            case R.id.psBack2:
                isBack(v);
                break;
            case R.id.psBackSc:
                isBack(v);
                break;
            case R.id.sndhBack:
                isBack(v);
                break;
            case R.id.sndhBack2:
                isBack(v);
                break;
            case R.id.sndhBackSc:
                isBack(v);
                break;
            case R.id.alpBack:
                isBack(v);
                break;
            case R.id.alpBack2:
                isBack(v);
                break;
            case R.id.alpBackSc:
                isBack(v);
                break;

            default:
                if (v != mBdone && v != mBack && v != psBack && v != sndhBack
                        && v != xbackengc && v != mBChange && v != mNum
                        && v != mBdone2 && v != urAlpBack && v != xDoneursp
                        && v != mBack2 && v != mBChange2 && v != xChangurSP
                        && v != xChangeEngtoCap && v != xBackEng && v != xDoneEng
                        && v != engcTOengl && v != mBackUrSC && v != EspCTOeng
                        && v != xBackEngSc && v != xDoneEngc) {

                    if (check == false) {
                        mELayoutC.setVisibility(RelativeLayout.GONE);
                        mELayout.setVisibility(RelativeLayout.VISIBLE);
                        addText(v);
                    } else {
                        addText(v);
                    }
                }
                break;
        }

    }

    //	private void sLanguage(View v) {
//		// TODO Auto-generated method stub
//		PopupMenu menu = new PopupMenu(this);
//		menu.setHeaderTitle("Select Language");
//		// Set Listener
//		menu.setOnItemSelectedListener(this);
//		// Add Menu (Android menu like style)
//		menu.add(URDU_PH_KEYBOARD, R.string.ur_ph_kb).setIcon(
//				getResources().getDrawable(R.drawable.kb_icon));
//		menu.add(PASHTO_KEYBOARD, R.string.ps_kb).setIcon(
//				getResources().getDrawable(R.drawable.kb_icon));
//		menu.add(SINDHI_KEYBOARD, R.string.sndh_kb).setIcon(
//				getResources().getDrawable(R.drawable.kb_icon));
//		menu.add(URDU_AL_KEYBOARD, R.string.ur_al_kb).setIcon(
//				getResources().getDrawable(R.drawable.kb_icon));
//		menu.add(ENGLISH_KEYBOARD, R.string.eng_kb).setIcon(
//				getResources().getDrawable(R.drawable.kb_icon));
//		menu.show(v);
//	}
    @SuppressWarnings("rawtypes")
    private void selectLanguage(View v) {
        PopupMenu popup = new PopupMenu(MainActivity.this, v);
        popup.getMenuInflater().inflate(R.menu.languages_menu, popup.getMenu());
        Object menuHelper;
        Class[] argTypes;
        try {
            Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
            fMenuHelper.setAccessible(true);
            menuHelper = fMenuHelper.get(popup);
            argTypes = new Class[] { boolean.class };
            menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
        } catch (Exception e) {
            popup.show();
            return;
        }

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.urPhKB:
                        editor = preferences.edit();
                        editor.putInt("kb", 0);
                        editor.commit();
                        urduPhoneticKeyboard();
                        Toast.makeText(MainActivity.this,
                                "You Clicked Urdu Keyboard : ",
                                Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.engKB:
                        editor = preferences.edit();
                        editor.putInt("kb", 4);
                        editor.commit();
                        englishKeyboard();
                        Toast.makeText(MainActivity.this,
                                "You Clicked English Keyboard : ",
                                Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        break;
                }
                return true;
            }
        });

        popup.show();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v == autoCompleteTextView && hasFocus == true) {
            isEdit = true;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.psBack:
                autoCompleteTextView.setText("");
                return true;

            default:
                break;
        }
        return true;
    }

    private void urduPhoneticKeyboard() {
        mELayout.setVisibility(RelativeLayout.GONE);
        mELayoutC.setVisibility(RelativeLayout.GONE);
        mPsLayout.setVisibility(RelativeLayout.GONE);
        mPsLayout2.setVisibility(RelativeLayout.GONE);
        mPsLayoutSc.setVisibility(RelativeLayout.GONE);
        mSndhLayout.setVisibility(RelativeLayout.GONE);
        mSndhLayout2.setVisibility(RelativeLayout.GONE);
        mSndhLayoutSc.setVisibility(RelativeLayout.GONE);
        mUrAlpLayout.setVisibility(RelativeLayout.GONE);
        mUrAlpLayout2.setVisibility(RelativeLayout.GONE);
        mUrAlpLayoutSc.setVisibility(RelativeLayout.GONE);
        mKLayout.setVisibility(RelativeLayout.VISIBLE);
    }

    private void pashtoKeyboard(){
        mKLayout.setVisibility(RelativeLayout.GONE);
        mKLayout2.setVisibility(RelativeLayout.GONE);
        mKLayoutSch.setVisibility(RelativeLayout.GONE);
        mELayout.setVisibility(RelativeLayout.GONE);
        mELayoutC.setVisibility(RelativeLayout.GONE);
        mPsLayout.setVisibility(RelativeLayout.VISIBLE);
        mSndhLayout.setVisibility(RelativeLayout.GONE);
        mSndhLayout2.setVisibility(RelativeLayout.GONE);
        mSndhLayoutSc.setVisibility(RelativeLayout.GONE);
        mUrAlpLayout.setVisibility(RelativeLayout.GONE);
        mUrAlpLayout2.setVisibility(RelativeLayout.GONE);
        mUrAlpLayoutSc.setVisibility(RelativeLayout.GONE);
    }

    private void sindhiKeyboard(){
        mKLayout.setVisibility(RelativeLayout.GONE);
        mKLayout2.setVisibility(RelativeLayout.GONE);
        mKLayoutSch.setVisibility(RelativeLayout.GONE);
        mELayout.setVisibility(RelativeLayout.GONE);
        mELayoutC.setVisibility(RelativeLayout.GONE);
        mPsLayout.setVisibility(RelativeLayout.GONE);
        mPsLayout2.setVisibility(RelativeLayout.GONE);
        mPsLayoutSc.setVisibility(RelativeLayout.GONE);
        mUrAlpLayout.setVisibility(RelativeLayout.GONE);
        mUrAlpLayout2.setVisibility(RelativeLayout.GONE);
        mUrAlpLayoutSc.setVisibility(RelativeLayout.GONE);
        mSndhLayout.setVisibility(RelativeLayout.VISIBLE);
    }

    private void urduAlphabeticKeyboard(){
        mKLayout.setVisibility(RelativeLayout.GONE);
        mKLayout2.setVisibility(RelativeLayout.GONE);
        mKLayoutSch.setVisibility(RelativeLayout.GONE);
        mELayout.setVisibility(RelativeLayout.GONE);
        mELayoutC.setVisibility(RelativeLayout.GONE);
        mPsLayout.setVisibility(RelativeLayout.GONE);
        mPsLayout2.setVisibility(RelativeLayout.GONE);
        mPsLayoutSc.setVisibility(RelativeLayout.GONE);
        mSndhLayout.setVisibility(RelativeLayout.GONE);
        mSndhLayout2.setVisibility(RelativeLayout.GONE);
        mSndhLayoutSc.setVisibility(RelativeLayout.GONE);
        mUrAlpLayout.setVisibility(RelativeLayout.VISIBLE);

    }

    private void englishKeyboard(){
        mKLayout.setVisibility(RelativeLayout.GONE);
        mKLayout2.setVisibility(RelativeLayout.GONE);
        mKLayoutSch.setVisibility(RelativeLayout.GONE);
        mPsLayout.setVisibility(RelativeLayout.GONE);
        mPsLayout2.setVisibility(RelativeLayout.GONE);
        mPsLayoutSc.setVisibility(RelativeLayout.GONE);
        mSndhLayout.setVisibility(RelativeLayout.GONE);
        mSndhLayout2.setVisibility(RelativeLayout.GONE);
        mSndhLayoutSc.setVisibility(RelativeLayout.GONE);
        mELayout.setVisibility(RelativeLayout.VISIBLE);
        mUrAlpLayout.setVisibility(RelativeLayout.GONE);
        mUrAlpLayout2.setVisibility(RelativeLayout.GONE);
        mUrAlpLayoutSc.setVisibility(RelativeLayout.GONE);
    }

    private void keyBoardSetting() {

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.keyboard_setting);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        spinner = (Spinner) dialog.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        List<String> soundList = new ArrayList<String>();
        soundList.add("Sound001");
        soundList.add("Sound002");
        soundList.add("Sound003");
        soundList.add("Sound004");
        soundList.add("Sound005");
        soundList.add("Sound006");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, soundList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(preferences.getInt("spinnerSelection",0));

        _sound=(CheckBox) dialog.findViewById(R.id.sound_);
        _sound.setChecked(getFromSP("sound"));
        _sound.setOnCheckedChangeListener(this);
        _vib=(CheckBox) dialog.findViewById(R.id.vib_);
        _vib.setChecked(getFromSP1("vibration"));
        _vib.setOnCheckedChangeListener(this);
        Button dialogButton = (Button) dialog.findViewById(R.id.ok_btn);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                soundState=getFromSP("key");
                if(soundState){
                    //Toast.makeText(getApplication(), "Sound Will Be ON", Toast.LENGTH_LONG).show();
                }
                vibState=getFromSP1("key");
                if(vibState){
                    //Toast.makeText(getApplication(), "Vibration Will Be ON", Toast.LENGTH_LONG).show();
                }
                kbSound=preferences.getInt("spinnerSelection", 0);
                loadSound();
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);


		/*LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View settingView = layoutInflater.inflate(R.layout.kb_setting, null);
		final PopupWindow popupWindow = new PopupWindow(settingView,
		LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		popupSpinner = (Spinner) settingView.findViewById(R.id.languages_spinner);
		Button btnOk = (Button) settingView.findViewById(R.id.ok_btn);

		_sound = (CheckBox)settingView.findViewById(R.id.sound_);
		_sound.setChecked(getFromSP("sound"));
		Toast.makeText(getApplication(), "sond checked!!!", 0).show();
		_sound.setOnCheckedChangeListener(this);

		_vib = (CheckBox)settingView.findViewById(R.id.vib_);
		_vib.setChecked(getFromSP("vibration"));
		Toast.makeText(getApplication(), "vib checked!!!", 0).show();
		_vib.setOnCheckedChangeListener(this);*/

		/*ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				KeyboardActivity.this, android.R.layout.simple_spinner_item,
				languages);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		popupSpinner.setAdapter(adapter);

		popupSpinner.setSelection(preferences.getInt("spinnerSelection", 0));
		popupSpinner.setOnItemSelectedListener(this);*/

		/*btnOk.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});*/
		 /*btnSave.setOnClickListener(new Button.OnClickListener(){

		 @Override
		 public void onClick(View v) {
		 popupWindow.dismiss();
		 }
		 });*/

        //popupWindow.showAsDropDown(setting, 50, -30);
		/*
		 * try { LayoutInflater inflater = (LayoutInflater)
		 * KeyboardActivity.this
		 * .getSystemService(Context.LAYOUT_INFLATER_SERVICE); View layout =
		 * inflater.inflate(R.layout.setting, (ViewGroup)
		 * findViewById(R.id.popup_element)); pwindo = new PopupWindow(layout,
		 * 300, 370, true); pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
		 * Spinner spinner = (Spinner) findViewById(R.id.languages_spinner);
		 * spinner.setOnItemSelectedListener(this);
		 *
		 * ArrayAdapter<String> aa = new
		 * ArrayAdapter<String>(KeyboardActivity.this
		 * ,android.R.layout.simple_spinner_item,languages);
		 * aa.setDropDownViewResource
		 * (android.R.layout.simple_spinner_dropdown_item);
		 * spinner.setAdapter(aa);
		 *
		 * Button btnClosePopup = (Button) layout.findViewById(R.id.cencel_btn);
		 * btnClosePopup.setOnClickListener(cancel_button_click_listener);
		 *
		 * } catch (Exception e) { e.printStackTrace(); } }
		 *
		 * private OnClickListener cancel_button_click_listener = new
		 * OnClickListener() { public void onClick(View v) { pwindo.dismiss();
		 *
		 * } };
		 *
		 * @Override public void onItemSelected(AdapterView<?> parent, View
		 * view, int position, long id) { // TODO Auto-generated method stub
		 *
		 * }
		 *
		 * @Override public void onNothingSelected(AdapterView<?> parent) { //
		 * TODO Auto-generated method stub
		 *
		 * }
		 */

    }

    private boolean getFromSP(String key) {
        return preferences.getBoolean("key", false);

    }

    private boolean getFromSP1(String key) {
        return preferences.getBoolean("key1", false);
    }

    private void saveInSp(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("key", value);
        editor.commit();
    }
    private void saveInSp1(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("key1", value);
        editor.commit();
    }

	/*@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		int item = popupSpinner.getSelectedItemPosition();
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt("spinnerSelection", item);
		editor.commit();
		if(item==0){
			urduPhoneticKeyboard();
		}if (item==1){
			pashtoKeyboard();
			}if (item==2){
				sindhiKeyboard();
				}if (item==3){
					urduAlphabeticKeyboard();
					}if (item==4){
						englishKeyboard();
						}
	}
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}*/

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.sound_:
                saveInSp("sound", isChecked);
                break;
            case R.id.vib_:
                saveInSp1("vibration", isChecked);
                break;
        }
    }
    /*@Override
    public void onItemSelected(hb.popupmenu.lib.MenuItem item) {
        switch (item.getItemId())
        {
        case URDU_PH_KEYBOARD:
            break;
        case PASHTO_KEYBOARD:
            break;
        case SINDHI_KEYBOARD:
            break;
        case URDU_AL_KEYBOARD:
            break;
        case ENGLISH_KEYBOARD:
            break;
        }
    }*/
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        int item = spinner.getSelectedItemPosition();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("spinnerSelection", item);
        editor.commit();

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub

    }

}
