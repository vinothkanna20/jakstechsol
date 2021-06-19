package com.jaktech.eduscribe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.SensorManager;
import android.nfc.cardemulation.OffHostApduService;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaktech.eduscribe.Double.DoubleClick;
import com.jaktech.eduscribe.Double.DoubleClickListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private SpeechRecognizer speechRecognizer;
    private TextToSpeech textToSpeech;
    private TextView user,pass;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speechRecognizer=SpeechRecognizer.createSpeechRecognizer(this);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},200);
        textToSpeech=new TextToSpeech(this,this);
        user=findViewById(R.id.user);
        pass=findViewById(R.id.pass);
        //login=findViewById(R.id.login);

    }

    private void intitalPlaylist() {

        int orientation = getResources().getConfiguration().orientation;
        if (Configuration.ORIENTATION_LANDSCAPE != orientation) {
            textToSpeech.speak("Welcome to Eduscribe.. Please Change phone from portrait mode to Landscape mode for better experience",TextToSpeech.QUEUE_FLUSH,null);

        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int orientation=newConfig.orientation;
        if(orientation==Configuration.ORIENTATION_LANDSCAPE)
        {
            textToSpeech.speak("Good",TextToSpeech.QUEUE_ADD,null);
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            listenforUsername();

        }
    }

    public void listenforUsername()
    {
        textToSpeech.speak("Tell your Name",TextToSpeech.QUEUE_FLUSH,null);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
       // intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivityForResult(intent,101);
            }
        },3000);
    }

    public void listenForPassword()
    {
        textToSpeech.speak("Tell your Password",TextToSpeech.QUEUE_ADD,null);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
      //  intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivityForResult(intent,102);
            }
        },2000);

    }
    public void listenForLogin()
    {
        textToSpeech.speak("Do you want to Login",TextToSpeech.QUEUE_ADD,null);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
       // intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivityForResult(intent,103);
            }
        },3000);
    }
    public void reentername()
    {
        textToSpeech.speak("Do you want to re enter your name",TextToSpeech.QUEUE_ADD,null);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        // intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivityForResult(intent,104);
            }
        },7000);
    }
    public void reenterpassword()
    {
        textToSpeech.speak("Do you want to re enter your password",TextToSpeech.QUEUE_ADD,null);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        // intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivityForResult(intent,105);
            }
        },7000);
    }
    public void login()
    {
        String url = "http://192.168.1.102:9090/login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    if (array.length() == 1) {
                        //Todo next activity
                        textToSpeech.speak("Login success",TextToSpeech.QUEUE_ADD,null);


                        Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    } else {
                        textToSpeech.speak("Login Failed",TextToSpeech.QUEUE_ADD,null);
                        Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("name", user.getText().toString());
                map.put("password", pass.getText().toString());
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }

    public void spell(String number){
        for(int i=0;i<number.length();i++){
            textToSpeech.speak(Character.toString(number.charAt(i)),TextToSpeech.QUEUE_ADD,null);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            //UserName result
            case 101:if(resultCode==RESULT_OK)
            {
                ArrayList dat=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String a=dat.get(0).toString();
                if(!a.equals(""))
                {
                    user.setText(a);

                    textToSpeech.speak("Welcome " + a,TextToSpeech.QUEUE_ADD,null);
                    spell(a);
                    //textToSpeech.speak("Do you want to re-enter your name",TextToSpeech.QUEUE_ADD,null);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            reentername();
                        }
                    },1000);

                }
            }break;
            //Password Result
            case 102:if(resultCode==RESULT_OK)
            {
                ArrayList dat=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String a=dat.get(0).toString();
                if(!a.equals(""))
                {
                    pass.setText(a);
                    textToSpeech.speak("your password is " + a,TextToSpeech.QUEUE_ADD,null);
                    spell(a);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            reenterpassword();
                        }
                    },1500);
                }
            }break;
            case 103:if(resultCode==RESULT_OK)
            {
                ArrayList dat=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String a=dat.get(0).toString();
                if(!a.equals(""))
                {
                    if(a.equals("ok")) {
                        //Todo call api for login
                        textToSpeech.speak("Login Started,Wait for a minute", TextToSpeech.QUEUE_ADD, null);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                               login();
                            }
                        },3000);

                    }
                    else {
                        textToSpeech.speak("Please try again", TextToSpeech.QUEUE_ADD, null);
                    }
                }
            }break;
            case 104:if(resultCode==RESULT_OK)
            {
                ArrayList dat=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String a=dat.get(0).toString();
                if(!a.equals(""))
                {
                    if(a.equals("ok")) {
                        //Todo call api for login
                        //textToSpeech.speak("Login Started,Wait for a minute", TextToSpeech.QUEUE_ADD, null);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                listenforUsername();
                            }
                        },1000);

                    }
                    else if(a.equals("no")){
                        //Todo call api for login
                        //textToSpeech.speak("Login Started,Wait for a minute", TextToSpeech.QUEUE_ADD, null);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                listenForPassword();
                            }
                        },1000);

                    }
                }
            }break;
            case 105:if(resultCode==RESULT_OK)
            {
                ArrayList dat=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String a=dat.get(0).toString();
                if(!a.equals(""))
                {
                    if(a.equals("ok")) {
                        //Todo call api for login
                        //textToSpeech.speak("Login Started,Wait for a minute", TextToSpeech.QUEUE_ADD, null);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                listenForPassword();
                            }
                        },1000);

                    }
                    else if(a.equals("no")){
                        //Todo call api for login
                        //textToSpeech.speak("Login Started,Wait for a minute", TextToSpeech.QUEUE_ADD, null);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                listenForLogin();
                            }
                        },1000);

                    }
                }
            }break;

        }
    }

    @Override
    public void onInit(int i) {
        if(i==TextToSpeech.SUCCESS)
        {
            textToSpeech.setLanguage(Locale.ENGLISH);
            textToSpeech.setPitch(1);
            textToSpeech.setSpeechRate(1);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(ContextCompat.checkSelfPermission(this,permissions[0])== PackageManager.PERMISSION_GRANTED)
        {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    intitalPlaylist();
                }
            },100);
        }
        else
        {
            ActivityCompat.requestPermissions(this,permissions,200);
        }
    }

    @Override
    protected void onDestroy() {
        if(textToSpeech!=null)
        {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}