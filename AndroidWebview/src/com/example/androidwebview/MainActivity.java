package com.example.androidwebview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.androidwebview.dialog.WebViewDialog;
/**
 * webView的使用
 * 弹出层webview效果
 */
public class MainActivity extends Activity implements OnClickListener{

	private Button btn_web;
	private ViewStub viewStub;
	private WebView webView;
	private String errorHtml;
	
	private WebViewDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn_web = (Button)findViewById(R.id.btn_web);
		viewStub = (ViewStub)findViewById(R.id.viewStub);
		btn_web.setVisibility(View.GONE);
		btn_web.setOnClickListener(this);
		
		dialog = new WebViewDialog(this);
		dialog.show();
		
		/*View view = viewStub.inflate();
		webView = (WebView)view.findViewById(R.id.wbeview);
		webView.getSettings().setJavaScriptEnabled(true); 
		webView.loadUrl("http://player.youku.com/embed/XNTM5MTUwNDA0");
		webView.requestFocus();
		webView.setWebViewClient(new MyWebViewClient());*/
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
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
		  }
		  //加载页面结束
		  @Override
		  public void onPageFinished(WebView view, String url) {
		   // TODO Auto-generated method stub
		   super.onPageFinished(view, url);
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
		  }
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_web:
			//startActivity(new Intent(this, WebViewActivity.class));
			break;
		default:
			break;
		}
		
	}
}
