package com.example.androidwebview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.androidwebview.R;
/**
 * webview弹出层效果
 * 描述：Dialog方式
 */
public class WebViewDialog extends Dialog implements
		android.view.View.OnClickListener {

	private Context mContext;

	public WebViewDialog(Context context) {
		super(context, R.style.ShareDialog);
		Log.i("MV", "context");
		mContext = context;
		initView();

	}

	private WebView webView;
	private ProgressBar progress;
	private String errorHtml;
	private RelativeLayout relative;

	private void initView() {

		Log.i("MV", "initView");
		View convertView = getLayoutInflater().inflate(
				R.layout.activity_webview, null);
		progress = (ProgressBar) convertView.findViewById(R.id.loading);
		webView = (WebView) convertView.findViewById(R.id.wbeview);
		relative = (RelativeLayout) convertView.findViewById(R.id.relative);

		relative.setOnClickListener(this);
		errorHtml = "<html><body><h1>Page not find！</h1></body></html>";
		webView.getSettings().setJavaScriptEnabled(true);
		//可解决webview在xml设置background无效的情况
		webView.setBackgroundColor(0); // 设置背景色

		// webView.loadUrl("http://www.google.com");
		webView.loadUrl("http://player.youku.com/embed/XNTM5MTUwNDA0");
		webView.setWebViewClient(new MyWebViewClient());
		// setContentView(convertView);

		/*
		 * Window win = getWindow(); win.getDecorView().setPadding(0, 0, 0, 0);
		 * WindowManager.LayoutParams lp = win.getAttributes(); lp.width =
		 * WindowManager.LayoutParams.FILL_PARENT; lp.height =
		 * WindowManager.LayoutParams.WRAP_CONTENT; win.setAttributes(lp);
		 */

		setContentView(convertView, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		Window window = getWindow();
		window.setGravity(Gravity.TOP);
		// 设置显示动画
		// 设置dialog占满父容器
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = LayoutParams.MATCH_PARENT;
		window.setAttributes(lp);

		setCanceledOnTouchOutside(true);

	}

	@Override
	public void cancel() {
		super.cancel();
		try {
			webView.getClass().getMethod("destroy")
					.invoke(webView, (Object[]) null);
		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	private class MyWebViewClient extends WebViewClient {
		// 加载页面开始
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

		// 加载页面开始中
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			// mHandler.sendEmptyMessage(10);
			addProgressBar();
		}

		// 加载页面结束
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			// mHandler.sendEmptyMessage(20);
			closeProgressBar();
		}

		/**
		 * 无网络情况下简单处理/ 访问web页面都是在网络的情况下， 一旦没有网络就会显示"无法找到该网页"的信息，
		 * 这样会暴露我们的连接，所以我们需要一个有好的提示，
		 * 并且不会暴露链接的方法。这时候WebViewClient的onReceivedError方法就派上了用场
		 * 
		 */
		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			// 这里进行无网络或错误处理，具体可以根据errorCode的值进行判断，做跟详细的处理。
			view.loadData(errorHtml, "text/html", "UTF-8");
			// mHandler.sendEmptyMessage(20);
			closeProgressBar();
		}
	}

	public void addProgressBar() {

		if (progress != null) {

			progress.setVisibility(View.VISIBLE);
		}
	}

	public void closeProgressBar() {

		if (progress != null) {

			progress.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		Log.i("MV", "onClick");
		switch (v.getId()) {
		case R.id.relative:
			WebViewDialog.this.cancel();
			break;
		default:
			break;
		}

	}

}
