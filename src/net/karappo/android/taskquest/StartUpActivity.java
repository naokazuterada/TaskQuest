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
    
    public static String CONSUMER_KEY = "7uTPcYftfVfN2i7kgCOQ";
    public static String CONSUMER_SECRET = "fnCdZV8rWTWOt5x7LQxxJmq76Q9wGnYE30QRo9ZT0U";
	public static final String CALLBACK_URL = "taskquest://oauth"; // TwitterサイトからコールバックされるURL
	
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
        _oauth.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        _oauth.setOAuthAccessToken(null);
        //アプリの認証オブジェクト作成
        try {
            _req = _oauth.getOAuthRequestToken(CALLBACK_URL);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        
        String _uri;
        _uri = _req.getAuthorizationURL();
        startActivityForResult(new Intent(Intent.ACTION_VIEW , Uri.parse(_uri)), 0);
    }
}
