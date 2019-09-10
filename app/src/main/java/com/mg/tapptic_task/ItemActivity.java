package com.mg.tapptic_task;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ItemActivity extends Activity implements RemoteCallListener{
    private TextView mItemTextView;
    private ImageView mItemImageView;
    public ArrayList<Item> items;
    NetworkInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detail_activity);
        mItemTextView = (TextView) findViewById(R.id.textView);
        mItemImageView = (ImageView) findViewById(R.id.imageView);

        items=new ArrayList<Item>();


        Bundle bundle = getIntent().getExtras();
        Log.e("DetailActivity",bundle.toString());

        if (bundle!=null) {
            String item_id=(String)bundle.get("Item_id");
            DownloadData_from2URL mAsync = new DownloadData_from2URL(this);
            mAsync.execute(item_id);

        }
        else
        {
            mItemTextView.setText("No text aviable");
        }



    }

    @Override
    public void onRemoteCallComplete(ArrayList<Item> arrayList) {
    if (arrayList!=null) {
    items.addAll(arrayList);

    mItemTextView.setText(items.get(0).getmText());
    new DownloadImageTask(mItemImageView).execute(items.get(0).getmImage());
      }
      else
    {
        showErrorScreen();
    }
    }

    private void showErrorScreen(){
        Toast.makeText(this,"error loading data-trying to reconnect",Toast.LENGTH_LONG).show();

        if (isConnected())
        {
            Bundle bundle = getIntent().getExtras();
            Log.e("DetailActivity",bundle.toString());

            if (bundle!=null) {
                String item_id=(String)bundle.get("Item_id");
                DownloadData_from2URL mAsync = new DownloadData_from2URL(this);
                mAsync.execute(item_id);

            }

        }

    }

    public boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        try {
            info = cm.getActiveNetworkInfo();
        }catch(Exception e)
        {
            Log.e("ItemActivity","Cannot retrieve ActiveNetworkInfo"+e.getMessage());
        }

        return info != null && info.isConnectedOrConnecting();
    }


    class DownloadData_from2URL extends AsyncTask<String, Void, ArrayList<Item>> {
        RemoteCallListener listener=null;

        public DownloadData_from2URL(RemoteCallListener listener) {
            this.listener=listener;
        }


        @Override
        protected ArrayList<Item> doInBackground(String... strings) {
try {
    return NetworkUtils.parseJSON_secondUrl(strings[0]);
}catch(Exception e)
            {
                Log.e("ItemActivity","doInbackground:"+e.getMessage());
            }
            return null;
        }

        protected void onPostExecute(ArrayList<Item> result)
        {

            listener.onRemoteCallComplete(result);
        }
    }
}
