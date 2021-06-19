package com.jaktech.eduscribe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaktech.eduscribe.Double.DoubleClick;
import com.jaktech.eduscribe.Double.DoubleClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private SpeechRecognizer speechRecognizer;
    private TextToSpeech textToSpeech;
    private TextView firstname,lastname,userid,password1,phoneno,emailid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        speechRecognizer=SpeechRecognizer.createSpeechRecognizer(this);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},200);
        textToSpeech=new TextToSpeech(this,this);
        firstname=findViewById(R.id.firstname);
        lastname=findViewById(R.id.lastname);
        userid=findViewById(R.id.userid);
        password1=findViewById(R.id.password1);
        phoneno=findViewById(R.id.phoneno);
        emailid=findViewById(R.id.emailid);
        callServer();
        //  login=findViewById(R.id.login);

        firstname.setOnClickListener(new DoubleClick(new DoubleClickListener() {
            @Override
            public void onSingleClick(View view) {
                //Single Click Ask user to tell name
                String text=firstname.getText().toString();
                if(text.equals("Name"))
                {
                    textToSpeech.speak("Double Tab to Tell Name",TextToSpeech.QUEUE_FLUSH,null);
                }
                else
                {
                    textToSpeech.speak(firstname.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
                }

            }

            @Override
            public void onDoubleClick(View view) {
                //Double Click listen user for username
                listenforfirstname();

            }
        },2000));
        lastname.setOnClickListener(new DoubleClick(new DoubleClickListener() {
            @Override
            public void onSingleClick(View view) {
                //Single Click Ask user to tell Password
                String text=lastname.getText().toString();
                if(text.equals("Password"))
                {
                    textToSpeech.speak("Double Tab to Tell Password",TextToSpeech.QUEUE_FLUSH,null);
                }
                else
                {
                    textToSpeech.speak(lastname.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
                }

            }

            @Override
            public void onDoubleClick(View view) {
                //Double Click listen user for password
                listenForlastname();

            }
        },2000));
    /*    login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getText().toString().equals("Name")) {
                    listenforUsername();
                } else if (user.getText().toString().equals("Password")) {
                    listenForPassword();
                } else {
                    // TODO change url for server
                    String url = "http://192.168.0.242:9090/login";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray array = new JSONArray(response);
                                if (array.length() == 1) {
                                    //Todo next activity
                                    Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                } else {
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
                    }) {
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
            }
        });*/

    }

    /*private void intitalPlaylist() {

        int orientation = getResources().getConfiguration().orientation;
        if (Configuration.ORIENTATION_LANDSCAPE == orientation) {
            textToSpeech.speak("Welcome to Eduscribe.. Please Change phone from portrait mode to Landscape mode for better experience",TextToSpeech.QUEUE_FLUSH,null);

        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int orientation=newConfig.orientation;
        if(orientation==Configuration.ORIENTATION_LANDSCAPE)
        {
            textToSpeech.speak("Good",TextToSpeech.QUEUE_FLUSH,null);
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            listenforUsername();

        }
    }*/
    public void callServer()
    {
        String url = "http://192.168.0.242:5487/student/login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject array = new JSONObject(response);
                    if(array!=null)
                    {
                        Log.i("tag",array.toString());
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("name", "sankar");
                map.put("pass", "sankar");
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }

    public void listenforfirstname()
    {
        textToSpeech.speak("Tell your first Name",TextToSpeech.QUEUE_FLUSH,null);
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
    public void listenForlastname()
    {
        textToSpeech.speak("Tell your lastname",TextToSpeech.QUEUE_FLUSH,null);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivityForResult(intent,102);
            }
        },3000);

    }


    public void listenForuserid() {
        textToSpeech.speak("Tell your userid", TextToSpeech.QUEUE_FLUSH, null);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivityForResult(intent, 103);
            }
        }, 3000);
    }


    public void listenForpassword() {
        textToSpeech.speak("Tell your password", TextToSpeech.QUEUE_FLUSH, null);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivityForResult(intent, 104);
            }
        }, 3000);
    }

    public void listenForemailid() {
        textToSpeech.speak("Tell your emailid", TextToSpeech.QUEUE_FLUSH, null);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivityForResult(intent, 106);
            }
        }, 3000);

    }

    public void listenForphoneno() {
        textToSpeech.speak("Tell your phone number", TextToSpeech.QUEUE_FLUSH, null);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivityForResult(intent, 105);
            }
        }, 3000);
    }

    private void listenforLogin() {
        textToSpeech.speak("do you want login say login", TextToSpeech.QUEUE_FLUSH, null);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivityForResult(intent, 108);
            }
        }, 3000);
    }
        public void listenForregister()
    {
        textToSpeech.speak("Do you want to register",TextToSpeech.QUEUE_FLUSH,null);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        // intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivityForResult(intent,107);
            }
        },3000);
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
                    firstname.setText(a);
                    listenForlastname();
                }
            }break;
            //Password Result
            case 102:if(resultCode==RESULT_OK)
            {
                ArrayList dat=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String a=dat.get(0).toString();
                if(!a.equals(""))
                {
                    lastname.setText(a);
                    listenForuserid();
                }
            }break;
            case 103:if(resultCode==RESULT_OK)
            {
                ArrayList dat=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String a=dat.get(0).toString();
                if(!a.equals(""))
                {
                    userid.setText(a);
                    listenForpassword();
                }
            }break;
            case 104:if(resultCode==RESULT_OK)
            {
                ArrayList dat=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String a=dat.get(0).toString();
                if(!a.equals(""))
                {
                    password1.setText(a);
                    listenForphoneno();
                }
            }break;
            case 105:if(resultCode==RESULT_OK)
            {
                ArrayList dat=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String a=dat.get(0).toString();
                if(!a.equals(""))
                {
                    phoneno.setText(a);
                    listenForemailid();
                }
            }break;
            case 106:if(resultCode==RESULT_OK)
            {
                ArrayList dat=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String a=dat.get(0).toString();
                if(!a.equals(""))
                {
                    emailid.setText(a);
                    listenForregister();
                }
            }break;

            case 107:if(resultCode==RESULT_OK)
            {
                ArrayList dat=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String a=dat.get(0).toString();
                if(!a.equals(""))
                {
                    if(a.equals("login")) {
                        //Todo call api for login
                        textToSpeech.speak("Login Started", TextToSpeech.QUEUE_FLUSH, null);
                    }
                    else {
                        textToSpeech.speak("register Successfully", TextToSpeech.QUEUE_FLUSH, null);
                        listenforLogin();
                    }
                }
            }break;

            case 108:if(resultCode==RESULT_OK)
            {
                ArrayList dat=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String a=dat.get(0).toString();
                if(!a.equals(""))
                {
                    if(a.toLowerCase().contains("login"))
                    {
                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                    }
                    else
                    {
                        startActivity(new Intent(RegisterActivity.this,SplashActivity.class));
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
                    listenforfirstname();
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