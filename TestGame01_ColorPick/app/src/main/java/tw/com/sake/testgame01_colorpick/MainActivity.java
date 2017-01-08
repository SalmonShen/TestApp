package tw.com.sake.testgame01_colorpick;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.start;
import static tw.com.sake.testgame01_colorpick.R.id.StartB;
import static tw.com.sake.testgame01_colorpick.R.id.imageV1;


public class MainActivity extends AppCompatActivity {

    Button startB, stopB, endB;
    TextView tv1;
    Button redB, greenB, blueB, yellowB;
    ImageView imageView1, imageView2;
    int Rungo = 0; //遊戲執行 0終止 1開始 2暫停
    int RanColor; //隨機顏色題目
    int ColorFront; //第一個顏色
    int ScoreG = 0; //分數
    int count = 60; //遊戲時間
    ProgressBar pbarTime;
    Thread gameThread; //執行緒
    Handler mHandler;
    ImageView[] imageViews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameview();
        mHandler = new Handler();
        ColorFront = (int) (Math.random() * 4 + 1);
//        imageViews = new ImageView[] = {iv01,iv02,iv03,iv04,iv05,iv06,iv07,iv08,iv09,iv10};
    }

    public void gameview() {
        startB = (Button) findViewById(StartB);
        stopB = (Button) findViewById(R.id.StopB);
        endB = (Button) findViewById(R.id.EndB);
        tv1 = (TextView) findViewById(R.id.tv01);
        redB = (Button) findViewById(R.id.RedB);
        greenB = (Button) findViewById(R.id.GreenB);
        blueB = (Button) findViewById(R.id.BlueB);
        yellowB = (Button) findViewById(R.id.YellowB);
        imageView1 = (ImageView) findViewById(R.id.imageV1);
        imageView2 = (ImageView) findViewById(R.id.imageV2);
        pbarTime = (ProgressBar)findViewById(R.id.progressBar);

        redB.setOnClickListener(redListener);
        greenB.setOnClickListener(greenListener);
        blueB.setOnClickListener(blueListener);
        yellowB.setOnClickListener(yellowListener);

        startB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 60;
                ScoreG = 0;
                Log.i("Rungo", Rungo + "");
                try {
                    if (Rungo == 1) {
                        Rungo = 0;
                        mHandler.removeCallbacks(gameRun);
                        Rungo = 1;
                        mHandler.post(gameRun);
                        FrontColorView();
                        StartGame();
                    } else {
                        Rungo = 1;
                        mHandler.post(gameRun);
                        FrontColorView();
                        StartGame();
                    }
                } catch (Exception ex) {
                    Log.e("JB_TAG", "gameStart Error:" + ex.getMessage());
                }
            }
        });
        stopB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (Rungo == 1) {
                        Rungo = 2;
                    } else if (Rungo == 2) {
                        Rungo = 1;
                        mHandler.removeCallbacks(gameRun);
                    } else {
                        mHandler.removeCallbacks(gameRun);
                    }
                } catch (Exception ex) {
                    Log.e("JB_TAG", "無法成功結束，錯誤原因:" + ex.getMessage());
                }


            }
        });
        endB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rungo = 0;
                count = 60;
            }
        });
    }

    public final Runnable gameRun = new Runnable() {
        public void run() {
            try {
                if (Rungo == 1 && count > 0) {
                    mHandler.postDelayed(this, 1000);
                    count--;
                    tv1.setText("時間倒數" + Integer.toString(count) + "\n得分：" + ScoreG);
                    pbarTime.incrementProgressBy(60);
                    pbarTime.setProgress(count);
                } else if (Rungo == 2 && count > 0) {

                } else if (count <= 0) {
                    Rungo = 0;
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); //建立對話窗
                    builder.setCancelable(false); //點擊空白處不會消失
                    builder.setTitle("Time Over"); //對話窗標題
                    builder.setMessage("Your time is over !\n" + "Your score is " + ScoreG + " !");//對話窗內容
                    builder.setNegativeButton("Click", new DialogInterface.OnClickListener() { //對話窗按鈕
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub

                        }
                    });
                    builder.show();
                }
            } catch (Exception ex) {
                Log.e("JB_TAG", "Runnable執行錯誤 :" + ex.getMessage());
            }
        }
    };

    public void StartGame() {
        switch (Rungo) {
            case 1: {
                RanColor = (int) (Math.random() * 4 + 1);
                switch (RanColor) {
                    case 1:
                        imageView1.setImageResource(R.drawable.red);
                        break;
                    case 2:
                        imageView1.setImageResource(R.drawable.blue);
                        break;
                    case 3:
                        imageView1.setImageResource(R.drawable.green);
                        break;
                    case 4:
                        imageView1.setImageResource(R.drawable.yellow);
                        break;
                }
                break;
            }
            case 2: {

            }
        }
    }

    public void FrontColorView() {
        switch (Rungo) {
            case 1: {
                switch (ColorFront) {
                    case 1:
                        imageView2.setImageResource(R.drawable.red);
                        break;
                    case 2:
                        imageView2.setImageResource(R.drawable.blue);
                        break;
                    case 3:
                        imageView2.setImageResource(R.drawable.green);
                        break;
                    case 4:
                        imageView2.setImageResource(R.drawable.yellow);
                        break;
                }
                break;
            }
        }
    }

        Button.OnClickListener redListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ColorFront == 1) {
                    ScoreG = ScoreG + 1;
                    ColorFront = RanColor;
                    FrontColorView();
                    StartGame();

                } else
                    ScoreG = ScoreG - 1;
            }
        };

    Button.OnClickListener blueListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ColorFront == 2) {
                ScoreG = ScoreG + 1;
                ColorFront = RanColor;
                FrontColorView();
                StartGame();
            } else
                ScoreG = ScoreG - 1;
        }
    };
    Button.OnClickListener greenListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ColorFront == 3) {
                ScoreG = ScoreG + 1;
                ColorFront = RanColor;
                FrontColorView();
                StartGame();
            } else
                ScoreG = ScoreG - 1;
        }
    };
    Button.OnClickListener yellowListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ColorFront == 4) {
                ScoreG = ScoreG + 1;
                ColorFront = RanColor;
                FrontColorView();
                StartGame();
            } else
                ScoreG = ScoreG - 1;
        }
    };

    public void aniMationView(){
        //動畫路徑設定(x1,x2,y1,y2)
        Animation am = new TranslateAnimation(30,0,-30,0);
        //動畫淡入設定
        Animation fadeIn = new AlphaAnimation(0, 1);
        //動畫開始到結束的時間，2秒
        am.setDuration( 800 );
        fadeIn.setDuration(800);
        // 動畫重覆次數 (-1表示一直重覆，0表示不重覆執行，所以只會執行一次)
        am.setRepeatCount( 0 );
        fadeIn.setRepeatCount( 0 );
        //將動畫寫入ImageView
        imageView2.setAnimation(am);
        imageView2.setAnimation(fadeIn);
        //開始動畫
        am.startNow();
        fadeIn.startNow();
    }


}

