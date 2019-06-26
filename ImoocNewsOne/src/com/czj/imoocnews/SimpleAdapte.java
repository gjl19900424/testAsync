package com.czj.imoocnews;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SimpleAdapte extends BaseAdapter implements OnScrollListener {
	private List<NewsBean> list;
	private LayoutInflater minflater;
	private ImageLoader mImageLoader;

	private int mStart, mEnd;
	public static String[] URLs;
	private boolean mFirstIn;

	public SimpleAdapte(Context context, List<NewsBean> list, ListView listView) {
		this.list = list;
		minflater = LayoutInflater.from(context);
		mImageLoader = new ImageLoader(listView);
		URLs = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			URLs[i] = list.get(i).picSmall;
		}
		mFirstIn = true;
		// 注册监听事件
		listView.setOnScrollListener(this);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = minflater.inflate(R.layout.item, null);
			viewHolder.title = (TextView) convertView
					.findViewById(R.id.lv_title);
			viewHolder.content = (TextView) convertView
					.findViewById(R.id.lv_content);
			viewHolder.iamge = (ImageView) convertView
					.findViewById(R.id.lv_image);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.iamge.setImageResource(R.drawable.ic_launcher);
		String url = list.get(position).picSmall;
		viewHolder.iamge.setTag(url);
		// 传递url和imageview
		/*
		 * new ImageLoader().ShowImageByThread(viewHolder.iamge,
		 * list.get(position).picSmall);
		 */
		mImageLoader.ShowImageByAsyncTask(viewHolder.iamge, url);
		viewHolder.title.setText(list.get(position).name);
		viewHolder.content.setText(list.get(position).Title);
		return convertView;
	}

	class ViewHolder {
		private TextView title;
		private TextView content;
		private ImageView iamge;

	}

	// 滑动状态切换时候调用
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == SCROLL_STATE_IDLE) {
			// 闲置状态时
			mImageLoader.loadImages(mStart, mEnd);
		} else {
			// 滑动时
			mImageLoader.cancelAllTasks();
		}
	}

	// 在滑动时调用
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// 第一个的可以见元素
		mStart = firstVisibleItem;
		// 第一个可见的元素+可见元素的数量=可见的起始项；
		mEnd = visibleItemCount + firstVisibleItem;
		// 第一次显示的时候预加载
		if (mFirstIn == true && visibleItemCount > 0) {
			mImageLoader.loadImages(mStart, mEnd);
			mFirstIn = false;
		}

	}
}
