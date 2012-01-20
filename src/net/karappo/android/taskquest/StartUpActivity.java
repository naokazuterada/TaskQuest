package net.karappo.android.taskquest;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.conf.ConfigurationContext;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

// Twitterへの接続画面
public class StartUpActivity extends Activity {
	
	// Debug
    private static final String TAG = "StartUp";
    
    private static String CONSUMER_KEY = "7uTPcYftfVfN2i7kgCOQ";
    private static String CONSUMER_SECRET = "fnCdZV8rWTWOt5x7LQxxJmq76Q9wGnYE30QRo9ZT0U";
    private static final String CALLBACK_URL = "taskquest://oauth"; // TwitterサイトからコールバックされるURL
	
	private static RequestToken _req = null;
    private static OAuthAuthorization _oauth = null;
    
    private Twitter _twitter;
    private String _accessToken, _accessTokenSecret;
    private Configuration _conf;
    
    // Request Code
    private static int OAUTH_REQUEST = 0;
    
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
        
        Uri uri = getIntent().getData();
        Log.d(TAG,"--------------onCreate"+uri);
        
        if(uri != null && uri.toString().startsWith(CALLBACK_URL)){
        	
        	// Aouth認証からのコールバックだった時
        	
            String verifier = uri.getQueryParameter("oauth_verifier");
            
            Log.d(TAG,"-------------");
            Log.d(TAG,_req.toString());
            
            try {
                AccessToken at = _oauth.getOAuthAccessToken(_req,verifier);
    			_accessToken = at.getToken();
    			_accessTokenSecret = at.getTokenSecret();
    			createTwitter();
                
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            
            Log.d(TAG,"_twitter="+_twitter);
            if(_twitter!=null)
            {
            	Log.d(TAG,"true");
            }else
            {
            	Log.d(TAG,"false");
            }
            
            
            /*
            String name = "未確認";
    		try {
    			
    			name = _twitter.getScreenName();
    			
    		} catch (IllegalStateException e) {
    			e.printStackTrace();
    		} catch (TwitterException e) {
    			e.printStackTrace();
    		}
            
            TextView tv = (TextView)findViewById(R.id.TV1);
            CharSequence cs = "ScreenName：" + name;
            tv.setText(cs);
            */
        }
    }
    
    private void executeOauth(){
    	
        startActivity(new Intent(Intent.ACTION_VIEW , Uri.parse(_req.getAuthorizationURL())));
        
        // MEMO: Callback時にonCreateが呼ばれるようにするために終了する。
        finish(); 
    }
    
    private void createTwitter(){
    	
    	Log.d(TAG,"createTwitter()");
    	ConfigurationBuilder cbuilder = new ConfigurationBuilder();
    	cbuilder.setOAuthConsumerKey(StartUpActivity.CONSUMER_KEY);
    	cbuilder.setOAuthConsumerSecret(StartUpActivity.CONSUMER_SECRET);
    	cbuilder.setOAuthAccessToken(_accessToken);
    	cbuilder.setOAuthAccessTokenSecret(_accessTokenSecret);
    	_conf = cbuilder.build();
    	TwitterFactory twitterFactory = new TwitterFactory(_conf);
    	_twitter = twitterFactory.getInstance();
    	Log.d(TAG,"_conf"+_conf);
    	Log.d(TAG,"_twitter"+_twitter);
    }
}
