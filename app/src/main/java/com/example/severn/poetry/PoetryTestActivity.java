package com.example.severn.poetry;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.severn.util.MyOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PoetryTestActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "PoetryTest";
    private TextView text_title;
    private Button button_backward;
    private RelativeLayout biaoti;
    private TextView tv_question;
    private RadioButton radio1;
    private RadioButton radio2;
    private RadioButton radio3;
    private RadioButton radio4;
    private RadioButton[] radioButtons;
    private RadioGroup radioGroup;
    private TextView tv_last;
    private TextView tv_next;
    private List<String> list_questions=new ArrayList<>();
    private List<String[]> list_answers=new ArrayList<>();
    private List<String> list_corrects=new ArrayList<>();
    private List<String> list_userAnswers=new ArrayList<>();
    private int index=0;
    private int grade=0;
    private boolean isTest=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poetry_test);
        initView();
        initTitle();
        initQuestions();
        showQuestions(index);
    }

    @Override
    public void onClick(View v) {
        if (v == tv_next) {
            if (tv_next.getText().toString().equalsIgnoreCase("下一题")){
                //下一题
                saveAnswer();
                Log.d(TAG, "onClick: "+ ++index);
                if (index<list_questions.size()){
                    radioGroup.clearCheck();
                    showQuestions(index);
                }else {
                    index--;
                }
                if (index == list_questions.size() - 1) {
                    if (isTest){
                        tv_next.setText("提交");
                    }else {
                        tv_next.setText("关闭");
                    }
                }else {
                    tv_next.setText("下一题");
                }
            }else if (tv_next.getText().toString().equalsIgnoreCase("提交")){
                //提交
                saveAnswer();
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("提示");
                View view= LayoutInflater.from(this).inflate(R.layout.poetry_test_commit,null,false);
                TextView tv_noQuestion=view.findViewById(R.id.tv_noQuestion);
                TextView tv_allQuestion=view.findViewById(R.id.tv_allQuestion);
                tv_allQuestion.setText(list_questions.size()+"");
                int i=0;
                for (String s:list_userAnswers){
                    if (s.equalsIgnoreCase("-1")){
                        i++;
                    }
                }
                tv_noQuestion.setText(i+"");
                builder.setView(view);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showResult();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }else {
                finish();
            }
        } else if (v == tv_last) {
            //上一题
            saveAnswer();
            Log.d(TAG, "onClick: "+ --index);
            if (index>=0){
                radioGroup.clearCheck();
                showQuestions(index);
            }else {
                Toast.makeText(this,"当前位第一题",Toast.LENGTH_LONG).show();
                index++;
            }
            if (index == list_questions.size() - 1) {
                if (isTest){
                    tv_next.setText("提交");
                }else {
                    tv_next.setText("关闭");
                }
            }else {
                tv_next.setText("下一题");
            }
        }
    }

    /**
     * 显示得分结果
     */
    private void showResult(){
        List<String> list_result=checkAnswer();
        AlertDialog.Builder builder=new AlertDialog.Builder(PoetryTestActivity.this);
        builder.setTitle("提示");
        View view= LayoutInflater.from(PoetryTestActivity.this).inflate(R.layout.poetry_test_result,null,false);
        TextView tv_grade=view.findViewById(R.id.tv_grade);
        TextView tv_errorQuestion=view.findViewById(R.id.tv_errorQuestion);
        String result="";
        for (String s:list_result){
            result+="  "+s;
        }
        tv_errorQuestion.setText(result);
        tv_grade.setText((float)(int) (((float)(list_questions.size()-list_result.size())/list_questions.size())*100*10)/10+"");
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isTest=false;
                tv_next.setText("关闭");
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    /**
     * 批改试卷
     * @return 返回对的题的集合
     */
    private List<String> checkAnswer(){
        grade=0;
        List<String> list=new ArrayList<>();
        for (int i=0;i<list_questions.size();i++){
            String correctAnswer=list_corrects.get(i);
            String userAnswer=list_userAnswers.get(i);
            if (correctAnswer.equalsIgnoreCase(userAnswer)){
                grade++;
            }else {
                list.add(i+1+"");
            }
        }
        MyOpenHelper myOpenHelper=new MyOpenHelper(this,null,null,0);
        SQLiteDatabase sqLiteDatabase=myOpenHelper.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("grade",grade);
        contentValues.put("time",new Date().getTime()+"");
        sqLiteDatabase.insert("Grade",null,contentValues);
        return list;
    }

    /**
     * 保存用户选择的答案
     */
    private void saveAnswer(){
        RadioButton radioButton=radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        String answer=radioButton!=null?radioButton.getText().toString():"-1";
        try{
            list_userAnswers.set(index,answer);
        }catch (Exception e){
            list_userAnswers.add(index,answer);
        }
    }

    /**
     * 显示一道题目
     * @param index 题目在数组中的位置
     */
    private void showQuestions(int index){
        tv_question.setText(index+1+"、"+list_questions.get(index));
        String[] strings=list_answers.get(index);
        for (int i=0;i<radioButtons.length;i++){
            radioButtons[i].setText(strings[i]);
        }
        try {
            String answer=list_userAnswers.get(index);
            Log.d(TAG, "showQuestions: "+answer+"----"+index);
            if (!answer.equalsIgnoreCase("-1")){
                for (RadioButton radioButton:radioButtons){
                    if (radioButton.getText().toString().equalsIgnoreCase(answer)){
                        radioButton.setChecked(true);
                    }
                }
            }
        }catch (Exception e){
            Log.d(TAG, "showQuestions: "+e.getMessage());
        }

    }

    /**
     * 初始化题目，选项和正确答案
     */
    private void initQuestions(){
        saveQuestions("1+1=?",new String[]{"2","3","4","5"},2);
        saveQuestions("1+2=?",new String[]{"2","3","4","5"},3);
        saveQuestions("1+3=?",new String[]{"2","3","4","5"},4);
        saveQuestions("1+4=?",new String[]{"2","3","4","5"},5);
        saveQuestions("1+5=?",new String[]{"3","4","5","6"},6);
    }

    /**
     * 封装单个题目，答案选项和正确答案
     */
    private void saveQuestions(String question,String[] answers,int corrects){
        list_questions.add(question);
        list_answers.add(answers);
        list_corrects.add(corrects+"");
    }

    private void initTitle(){
        button_backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        text_title = (TextView) findViewById(R.id.text_title);
        button_backward = (Button) findViewById(R.id.button_backward);
        biaoti = (RelativeLayout) findViewById(R.id.biaoti);
        tv_question = (TextView) findViewById(R.id.tv_question);
        radio1 = (RadioButton) findViewById(R.id.radio1);
        radio2 = (RadioButton) findViewById(R.id.radio2);
        radio3 = (RadioButton) findViewById(R.id.radio3);
        radio4 = (RadioButton) findViewById(R.id.radio4);
        radioButtons=new RadioButton[]{radio1,radio2,radio3,radio4};
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        tv_last = (TextView) findViewById(R.id.tv_last);
        tv_next = (TextView) findViewById(R.id.tv_next);

        tv_next.setOnClickListener(this);
        tv_last.setOnClickListener(this);
    }
}
