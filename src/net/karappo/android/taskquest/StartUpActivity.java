package net.karappo.android.taskquest;

import twitter4j.TwitterException;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationContext;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

// Twitterへの接続画面
public class StartUpActivity extends Activity {
	
	// Debug
    private static final String TAG = "StartUp";
    
    private static String CONSUMER_KEY = "pYOke1sv1o7FoHB1qrvg";
	private static String CONSUMER_SECRET = "nePzPbjhXPIKQNQU8iZP65Z1Hzbux1c6znAfR3M2zc";
	
    public static RequestToken _req = null;
    public static OAuthAuthorization _oauth = null;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup);
 
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new OnClickListener() {
 
            @Override
            public void onClick(View v) {
                executeOauth();
            }
        });
    }
 
    private void executeOauth(){
        //Twitetr4jの設定を読み込む
        Configuration conf = ConfigurationContext.getInstance();
 
        //Oauth認証オブジェクト作成
        _oauth = new OAuthAuthorization(conf);
        //Oauth認証オブジェクトにconsumerKeyとconsumerSecretを設定
        _oauth.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        //_oauth.setOAuthConsumer("iy2FEHXmSXNReJ6nYQ8FRg", "KYro4jM8BHlLSMsSdTylnTcm3pYaTCiG2UZrYK1yI4");
        _oauth.setOAuthAccessToken(null);
        //アプリの認証オブジェクト作成
        try {
        	//_req = _oauth.getOAuthRequestToken("Callback://CallBackActivity");
            _req = _oauth.getOAuthRequestToken("TaskQuest://CallBackActivity");
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"-----");
        Log.d(TAG,_req+"");
        String _uri;
        _uri = _req.getAuthorizationURL();
        startActivityForResult(new Intent(Intent.ACTION_VIEW , Uri.parse(_uri)), 0);
    }
}
