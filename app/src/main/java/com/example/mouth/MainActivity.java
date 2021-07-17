//package com.example.mouth;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//
//import android.Manifest;
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.speech.RecognitionListener;
//import android.speech.RecognizerIntent;
//import android.speech.SpeechRecognizer;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//
//
//public class MainActivity extends AppCompatActivity {
//
//    TextView textView;
//    Button button;
//    Intent intent;
//    SpeechRecognizer mRecognizer;
//    final int PERMISSION = 1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // 안드로이드 6.0버전 이상인지 체크해서 퍼미션 체크
//        if(Build.VERSION.SDK_INT >= 23){
//            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET,
//                    Manifest.permission.RECORD_AUDIO},PERMISSION);
//        }
//
//        textView = findViewById(R.id.sttResult);
//        button = findViewById(R.id.sttStart);
//
//        // RecognizerIntent 생성
//        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName()); // 여분의 키
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR"); // 언어 설정
//
//        // 버튼 클릭 시 객체에 Context와 listener를 할당
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mRecognizer = SpeechRecognizer.createSpeechRecognizer(MainActivity.this); // 새 SpeechRecognizer 를 만드는 팩토리 메서드
//                mRecognizer.setRecognitionListener(listener); // 리스너 설정
//                mRecognizer.startListening(intent); // 듣기 시작
//            }
//        });
//    }
//
//    private RecognitionListener listener = new RecognitionListener() {
//        @Override
//        public void onReadyForSpeech(Bundle params) {
//            // 말하기 시작할 준비가되면 호출
//            Toast.makeText(getApplicationContext(),"음성인식 시작",Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onBeginningOfSpeech() {
//            // 말하기 시작했을 때 호출
//        }
//
//        @Override
//        public void onRmsChanged(float rmsdB) {
//            // 입력받는 소리의 크기를 알려줌
//        }
//
//        @Override
//        public void onBufferReceived(byte[] buffer) {
//            // 말을 시작하고 인식이 된 단어를 buffer에 담음
//        }
//
//        @Override
//        public void onEndOfSpeech() {
//            // 말하기를 중지하면 호출
//        }
//
//        @Override
//        public void onError(int error) {
//            // 네트워크 또는 인식 오류가 발생했을 때 호출
//            String message;
//
//            switch (error) {
//                case SpeechRecognizer.ERROR_AUDIO:
//                    message = "오디오 에러";
//                    break;
//                case SpeechRecognizer.ERROR_CLIENT:
//                    message = "클라이언트 에러";
//                    break;
//                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
//                    message = "퍼미션 없음";
//                    break;
//                case SpeechRecognizer.ERROR_NETWORK:
//                    message = "네트워크 에러";
//                    break;
//                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
//                    message = "네트웍 타임아웃";
//                    break;
//                case SpeechRecognizer.ERROR_NO_MATCH:
//                    message = "찾을 수 없음";
//                    break;
//                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
//                    message = "RECOGNIZER 가 바쁨";
//                    break;
//                case SpeechRecognizer.ERROR_SERVER:
//                    message = "서버가 이상함";
//                    break;
//                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
//                    message = "말하는 시간초과";
//                    break;
//                default:
//                    message = "알 수 없는 오류임";
//                    break;
//            }
//
//            Toast.makeText(getApplicationContext(), "에러 발생 : " + message,Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onResults(Bundle results) {
//            // 인식 결과가 준비되면 호출
//            // 말을 하면 ArrayList에 단어를 넣고 textView에 단어를 이어줌
//            ArrayList<String> matches =
//                    results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
//
//            for(int i = 0; i < matches.size() ; i++){
//                textView.setText(matches.get(i));
//            }
//        }
//
//        @Override
//        public void onPartialResults(Bundle partialResults) {
//            // 부분 인식 결과를 사용할 수 있을 때 호출
//        }
//
//        @Override
//        public void onEvent(int eventType, Bundle params) {
//            // 향후 이벤트를 추가하기 위해 예약
//        }
//    };
//}

package com.example.mouth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class MainActivity extends AppCompatActivity {

    private final  String TAG = getClass().getSimpleName();
    private MyAPI mMyAPI;

    TextView textView;
    Button button;
    Intent intent;
    SpeechRecognizer mRecognizer;
    final int PERMISSION = 1;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Log.d("ACTIVITY_LC", "onCreate 호출됨");
//        Toast.makeText(getApplicationContext(), "onCreate 호출됨", Toast.LENGTH_SHORT).show();
//
//        Button button = (Button) findViewById(R.id.sttStart);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), SubActivity.class);
//                startActivity(intent);
//
//            }
//        });
//
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // ImageView imgGIF = (ImageView)findViewById(R.id.gif_image);

        // 안드로이드 6.0버전 이상인지 체크해서 퍼미션 체크
        if(Build.VERSION.SDK_INT >= 23){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO},PERMISSION);
        }

        textView = findViewById(R.id.sttResult);
        button = findViewById(R.id.sttStart);

        // RecognizerIntent 생성
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName()); // 여분의 키
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR"); // 언어 설정

        // 버튼 클릭 시 객체에 Context와 listener를 할당
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 추가한 부분 SubActivity.class 실행

                Intent intent = new Intent(this,SubActivity.class);
                startActivity(intent);

                mRecognizer = SpeechRecognizer.createSpeechRecognizer(MainActivity.this); // 새 SpeechRecognizer 를 만드는 팩토리 메서드
                mRecognizer.setRecognitionListener(listener); // 리스너 설정
                //mRecognizer.startListening(intent); // 듣기 시작
            }
        });

        initMyAPI(mMyAPI.API_URL);

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://0116515cbeb3.ngrok.io/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        MyAPI MyAPI = retrofit.create(MyAPI.class);
//        MyAPI.getData(text:"가").enqueue(new Callback<List<ResponseItem>>(){
//            @Override
//            public void onResponse(@NonNull Call<List<ResponseItem>> call, @NonNull Response<List<ResponseItem>> response){
//                if(response.isSuccessful()){
//                    List<ResponseItem> data = response.body();
//                    Log.d(data.getData());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<ResponseItem>> call, Throwable t){
//                t.printStackTrace();
//            }
//        });

    }

    private void initMyAPI(String baseUrl){
        Log.d(TAG,"initMyAPI : " + baseUrl);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mMyAPI = retrofit.create(MyAPI.class);
    }


    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            // 말하기 시작할 준비가되면 호출
            Toast.makeText(getApplicationContext(),"음성인식 시작",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBeginningOfSpeech() {
            // 말하기 시작했을 때 호출
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            // 입력받는 소리의 크기를 알려줌
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            // 말을 시작하고 인식이 된 단어를 buffer에 담음
        }

        @Override
        public void onEndOfSpeech() {
            // 말하기를 중지하면 호출
        }

        @Override
        public void onError(int error) {
            // 네트워크 또는 인식 오류가 발생했을 때 호출
            String message;

            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "오디오 에러";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "클라이언트 에러";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "퍼미션 없음";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "네트워크 에러";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "네트웍 타임아웃";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "찾을 수 없음";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RECOGNIZER 가 바쁨";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "서버가 이상함";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "말하는 시간초과";
                    break;
                default:
                    message = "알 수 없는 오류임";
                    break;
            }

            Toast.makeText(getApplicationContext(), "에러 발생 : " + message,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResults(Bundle results) {
            // 인식 결과가 준비되면 호출
            // 말을 하면 ArrayList에 단어를 넣고 textView에 단어를 이어줌
            ArrayList<String> matches =
                    results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);


            for(int i = 0; i < matches.size() ; i++){
                textView.setText(matches.get(i));
                Log.d(TAG, "POST");
                Log.d(TAG, matches.get(i));

                PostItem item = new PostItem();
                item.setText(matches.get(i));
                Call<PostItem> postCall = mMyAPI.post_posts(item);
                postCall.enqueue(new Callback<PostItem>(){
                @Override
                public void onResponse(Call<PostItem> call, Response<PostItem> response){
                    if(response.isSuccessful()){
                        Log.d(TAG, "등록 완료");
                        Log.d(TAG, item.getText());

                    }else {
                        Log.d(TAG, "Status Code : " + response.code());
                        Log.d(TAG, response.errorBody().toString());
                        Log.d(TAG, call.request().body().toString());
                    }
                }
                @Override
                public void onFailure(Call<PostItem> call, Throwable t){
                    Log.d(TAG, "Fail msg : " + t.getMessage());
                }
            });

            }
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            // 부분 인식 결과를 사용할 수 있을 때 호출
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            // 향후 이벤트를 추가하기 위해 예약
        }
    };
}
