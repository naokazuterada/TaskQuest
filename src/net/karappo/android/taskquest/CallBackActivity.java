package net.karappo.android.taskquest;

import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

// Twitter認証からのコールバック画面
public class CallBackActivity extends Activity {

	// Debug
    private static final String TAG = "StartUp";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.callback);
 
        AccessToken token = null;
 
        //Twitterの認証画面から発行されるIntentからUriを取得
        Uri uri = getIntent().getData();
 
        if(uri != null && uri.toString().startsWith("Callback://CallBackActivity")){
            //oauth_verifierを取得する
            String verifier = uri.getQueryParameter("oauth_verifier");
            try {
                //AccessTokenオブジェクトを取得
                token = StartUpActivity._oauth.getOAuthAccessToken(StartUpActivity._req, verifier);
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }
 
        TextView tv = (TextView)findViewById(R.id.textView1);
        CharSequence cs = "token：" + token.getToken() + "\r\n" + "token secret：" + token.getTokenSecret();
        tv.setText(cs);
    }
}
