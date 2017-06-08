package com.example.yls.note;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends ListActivity implements AbsListView.OnScrollListener {
    public static final int CHECK_STATE = 0;
    public static final int EDIT_STATE = 1;
    public static final int ALERT_STATE = 2;
    private ListView listView;
    private ListViewAdapter adapter;// 数据源对象
    private View RecordView;///列表布局
    private View longClickView ;///长按弹出的布局
    private Button addRecordButton;//新增
    private Button deleteRecordButton;//删除
    private Button checkRecordButton;//查看
    private Button modifyRecordButton;//修改
    private DataManager dm = null;// 数据库管理对象
    private Cursor cursor = null;
    private int id = -1;//被点击的条目

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        longClickView=getLayoutInflater().inflate(R.layout.long_activity,null);
        RecordView = getLayoutInflater().inflate(R.layout.footer,null);
        addRecordButton = (Button) RecordView.findViewById(R.id.addRecordButton);
        deleteRecordButton = (Button)longClickView.findViewById(R.id.deleteRecordButton);
        checkRecordButton = (Button) longClickView.findViewById(R.id.checkRecordButton);
        modifyRecordButton = (Button) longClickView.findViewById(R.id.modifyRecordButton);
        dm=new DataManager(this);
        listView = getListView();
        listView.addFooterView(RecordView);
        intiAdpater();
        setListAdpater(adapter);

        listView.setOnScrollListener(this);
        listView.setOnCreateContextMenuListener(new myOnCreateContextMenuListener());
        addRecordButton.setOnClickListener(new AddRecordListener());//新增
        deleteRecordButton.setOnClickListener(new DeleteRecordListener());//删除
        checkRecordButton.setOnClickListener(new CheckRecordListener());//查看
        modifyRecordButton.setOnClickListener(new ModifyRecordListener());//修改

    }

    private void setListAdpater(ListViewAdapter adapter) {
    }

    private void intiAdpater() {
        dm.open();//打开数据库操作对象

        cursor = dm.selectAll();//获取所有数据

        cursor.moveToFirst();//将游标移动到第一条数据，使用前必须调用

        int count = cursor.getCount();//个数

        ArrayList<String> items = new ArrayList<String>();
        ArrayList<String> times = new ArrayList<String>();
        for(int i= 0; i < count; i++){
            items.add(cursor.getString(cursor.getColumnIndex("title")));
            times.add(cursor.getString(cursor.getColumnIndex("time")));
            cursor.moveToNext();
        }

        dm.close();//关闭数据操作对象
        adapter = new ListViewAdapter(this,items,times);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }




    private class myOnCreateContextMenuListener implements View.OnCreateContextMenuListener {
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            final AdapterView.AdapterContextMenuInfo info =
                    (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle("");
            //设置选项
            menu.add(0,0,0,"删除");
            menu.add(0,1,0,"修改");
            menu.add(0,2,0,"查看");
        }
    }
    public boolean onContextItemSelection(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo)item
                        .getMenuInfo();

        dm.open();

        switch(item.getItemId()){
            case 0:
                try{
                    cursor.moveToPosition(menuInfo.position);

                    int i = dm.delete(Long.parseLong(cursor.getString(cursor.getColumnIndex("_id"))));//删除数据

                    adapter.removeListItem(menuInfo.position);//删除数据
                    adapter.notifyDataSetChanged();//通知数据源，数据已经改变，刷新界面


                }catch(Exception ex){
                    ex.printStackTrace();
                }

                break;
            case 1:

                try{
                    cursor.moveToPosition(menuInfo.position);

                    //用于Activity之间的通讯
                    Intent intent = new Intent();
                    //通讯时的数据传送
                    intent.putExtra("id", cursor.getString(cursor.getColumnIndex("_id")));
                    intent.putExtra("state", ALERT_STATE);
                    intent.putExtra("title", cursor.getString(cursor.getColumnIndex("title")));
                    intent.putExtra("time", cursor.getString(cursor.getColumnIndex("time")));
                    intent.putExtra("content", cursor.getString(cursor.getColumnIndex("content")));
                    //设置并启动另一个指定的Activity
                    intent.setClass(MainActivity.this, edit.class);
                    MainActivity.this.startActivity(intent);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                break;
            case 2:
                try{
                    cursor.moveToPosition(menuInfo.position);

                    Intent intent = new Intent();

                    intent.putExtra("id", cursor.getString(cursor.getColumnIndex("_id")));
                    intent.putExtra("title", cursor.getString(cursor.getColumnIndex("title")));
                    intent.putExtra("time", cursor.getString(cursor.getColumnIndex("time")));
                    intent.putExtra("content", cursor.getString(cursor.getColumnIndex("content")));

                    intent.setClass(MainActivity.this, CheckActivity.class);
                   MainActivity.this.startActivity(intent);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                break;
            default:;
        }
        dm.close();
        return super.onContextItemSelected(item);

    }

    private class DeleteRecordListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }

    private class CheckRecordListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }

    private class ModifyRecordListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }

    private class AddRecordListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.putExtra("state", EDIT_STATE);
            intent.setClass(MainActivity.this,edit.class);
            MainActivity.this.startActivity(intent);
        }
    }
}
