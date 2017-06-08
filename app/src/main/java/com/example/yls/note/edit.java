package com.example.yls.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by yls on 2017/6/8.
 */

public class edit extends Activity{
    public static final int CHECK_STATE = 0;
    public static final int EDIT_STATE = 1;
    public static final int ALERT_STATE = 2;

    private int state = -1;

    private Button addRecord;
    private Button complete;
    private EditText title;
    private EditText content;
    private DataManager dm = null;

    private String id = "";
    private String titleText = "";
    private String contentText = "";
    private String timeText = "";
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);

        Intent intent = getIntent();
        state = intent.getIntExtra("state", EDIT_STATE);

        addRecord = (Button) findViewById(R.id.addRecordButton);
        complete = (Button) findViewById(R.id.editComplete);
        title = (EditText) findViewById(R.id.editTitle);
        content = (EditText) findViewById(R.id.editContent);
        addRecord.setOnClickListener(new AddRecordListener());
        complete.setOnClickListener(new EditCompleteListener());
        content.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                content.setSelection(content.getText().toString().length());
                Editable ea=content.getText();
                Selection.setSelection(ea,ea.length());
                return false;
            }
        });
        if(state==ALERT_STATE){
            id=intent.getStringExtra("_id");
            titleText=intent.getStringExtra("title");
            timeText=intent.getStringExtra("time");
            title.setText(titleText);
            content.setText(contentText);
        }
        dm=new DataManager(this);

    }
    public class EditCompleteListener implements View.OnClickListener {

        public void onClick(View v) {

            titleText = title.getText().toString();
            contentText = content.getText().toString();

            try{
                dm.open();

                if(state == EDIT_STATE)
                    dm.insert(titleText, contentText);
                if(state == ALERT_STATE)
                    dm.update(Integer.parseInt(id), titleText, contentText);

                dm.close();

            }catch(Exception ex){
                ex.printStackTrace();
            }

            Intent intent = new Intent();
            intent.setClass(edit.this, MainActivity.class);
           edit.this.startActivity(intent);

        }

    }

    private class AddRecordListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }
}

