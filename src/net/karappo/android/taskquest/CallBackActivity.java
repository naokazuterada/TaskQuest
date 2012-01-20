package net.karappo.android.taskquest;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

// Twitter認証からのコールバック画面
public class CallBackActivity extends Activity {

	// Debug
    private static final String TAG = "CallBackActivity";
    
    private Twitter _twitter;
    private String _accessToken, _accessTokenSecret;
    private Configuration _conf;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.callback);
 
        AccessToken token = null;
 
        //Twitterの認証画面から発行されるIntentからUriを取得
        Uri uri = getIntent().getData();
 
        if(uri != null && uri.toString().startsWith(StartUpActivity.CALLBACK_URL)){
            //oauth_verifierを取得する
            String verifier = uri.getQueryParameter("oauth_verifier");
            try {
                //AccessTokenオブジェクトを取得
            	//AccessToken at = mOauth.getOAuthAccessToken(verifier);
    			//_accessToken = at.getToken();
    			//_accessTokenSecret = at.getTokenSecret();
                token = StartUpActivity._oauth.getOAuthAccessToken(StartUpActivity._req, verifier);
                createTwitterInstance();
                
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }
        
        
        
        TextView tv = (TextView)findViewById(R.id.textView1);
        CharSequence cs = "token：" + token.getToken() + "\r\n" + "token secret：" + token.getTokenSecret();
        tv.setText(cs);
    }
    
    private void createTwitterInstance(){
    	ConfigurationBuilder cbuilder = new ConfigurationBuilder();
    	cbuilder.setOAuthConsumerKey(StartUpActivity.CONSUMER_KEY);
    	cbuilder.setOAuthConsumerSecret(StartUpActivity.CONSUMER_SECRET);
    	cbuilder.setOAuthAccessToken(_accessToken);
    	cbuilder.setOAuthAccessTokenSecret(_accessTokenSecret);
    	_conf = cbuilder.build();
    	TwitterFactory twitterFactory = new TwitterFactory(_conf);
    	_twitter = twitterFactory.getInstance();
    }
}
