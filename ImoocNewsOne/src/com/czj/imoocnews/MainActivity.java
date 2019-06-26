package com.czj.imoocnews;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class MainActivity extends Activity {
	private static String URL = "http://www.imooc.com/api/teacher?type=4&num=30";
	private ListView mlistview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new MyAsyncTask().execute(URL);
		mlistview = (ListView) findViewById(R.id.lv_view);
	}

	private List<NewsBean> getData(String url) {
		List<NewsBean> list = new ArrayList<NewsBean>();
		try {
			String jsonData = readstem(new URL(url).openStream());
			NewsBean Bean;
			JSONObject jsonObject = new JSONObject(jsonData);
			JSONArray array = jsonObject.getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				jsonObject = array.getJSONObject(i);
				Bean = new NewsBean();
				Bean.name = jsonObject.getString("name");
				Bean.picSmall = jsonObject.getString("picSmall");
				Bean.Title = jsonObject.getString("description");
				list.add(Bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	private String readstem(InputStream is) {
		InputStreamReader isr;
		String result = "";
		try {
			String line = "";
			isr = new InputStreamReader(is, "utf-8");
			BufferedReader buffe = new BufferedReader(isr);
			while ((line = buffe.readLine()) != null) {
				result += line;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	class MyAsyncTask extends AsyncTask<String, Void, List<NewsBean>> {

		@Override
		protected List<NewsBean> doInBackground(String... params) {

			return getData(params[0]);
		}

		@Override
		protected void onPostExecute(List<NewsBean> result) {
			super.onPostExecute(result);
			SimpleAdapte adapte = new SimpleAdapte(getApplicationContext(),
					result, mlistview);
			mlistview.setAdapter(adapte);
		}
	}
}
