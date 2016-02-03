package com.example.androidwebview;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
/**
 * webView的使用Activity方式
 */
public class WebViewActivity extends Activity {

	private WebView webView;
	private ProgressBar progress;
	private String errorHtml;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		
		errorHtml = "<html><body><h1>Page not find！</h1></body></html>"; 
		webView = (WebView)findViewById(R.id.wbeview);
		progress = (ProgressBar)findViewById(R.id.loading);
		
		webView.getSettings().setJavaScriptEnabled(true); 
		//webView.loadUrl("http://www.baidu.com");
		webView.loadUrl("http://player.youku.com/embed/XNTM5MTUwNDA0");
		webView.requestFocus();
		webView.setWebViewClient(new MyWebViewClient());
		
		//无法根据浏览器居中显示内容这个问题
		//webView.getSettings().setLoadWithOverviewMode(true);
		//webView.getSettings().setUseWideViewPort(true);
		
	}
	
	//用于加载、取消loading
	 private Handler mHandler = new Handler(){ 
		 
		 public void handleMessage(android.os.Message msg) {
			   
			   switch (msg.what) {
			   case 10:
			    addProgressBar();
			    break;
			   case 20:
			    closeProgressBar();
			   default:
			    break;
			   }
			 };
		 
	 };
	  
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
	private class MyWebViewClient extends WebViewClient{
		  //加载页面开始
		  @Override
		  public boolean shouldOverrideUrlLoading(WebView view, String url) {
		   view.loadUrl(url); 
		   return true;
		  }
		  
		  //加载页面开始中
		  @Override
		  public void onPageStarted(WebView view, String url, Bitmap favicon) {
		   super.onPageStarted(view, url, favicon);
		   mHandler.sendEmptyMessage(10);
		  }
		  //加载页面结束
		  @Override
		  public void onPageFinished(WebView view, String url) {
		   // TODO Auto-generated method stub
		   super.onPageFinished(view, url);
		   mHandler.sendEmptyMessage(20);
		  }
		  
		  /**
		   * 无网络情况下简单处理/
		   * 访问web页面都是在网络的情况下，
		   * 一旦没有网络就会显示"无法找到该网页"的信息，
		   * 这样会暴露我们的连接，所以我们需要一个有好的提示，
		   * 并且不会暴露链接的方法。这时候WebViewClient的onReceivedError方法就派上了用场
		   *
		   */
		  @Override
		  public void onReceivedError(WebView view, int errorCode,
		    String description, String failingUrl) {
		   super.onReceivedError(view, errorCode, description, failingUrl);
		   //这里进行无网络或错误处理，具体可以根据errorCode的值进行判断，做跟详细的处理。 
		            view.loadData(errorHtml, "text/html", "UTF-8"); 
		            mHandler.sendEmptyMessage(20);
		  }
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		try {
			webView.getClass().getMethod("onResume").invoke(webView,(Object[])null);
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			webView.getClass().getMethod("onPause").invoke(webView,(Object[])null);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	

}
