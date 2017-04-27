package com.dstaley.ReactNativeChromeCustomTabs;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class ChromeCustomTabsModule extends ReactContextBaseJavaModule implements CustomTabActivityHelper.ConnectionCallback {

  private CustomTabActivityHelper mCustomTabActivityHelper;
  private ReactApplicationContext mContext;
  private CustomTabsIntentEditor mIntentEditor;

  ChromeCustomTabsModule(
      ReactApplicationContext reactContext,
      @Nullable CustomTabsIntentEditor intentEditor) {
    super(reactContext);
    mContext = reactContext;
    mIntentEditor = intentEditor;
    mCustomTabActivityHelper = new CustomTabActivityHelper();
    mCustomTabActivityHelper.setConnectionCallback(this);
    mCustomTabActivityHelper.bindCustomTabsService(reactContext.getApplicationContext());
  }

  private void sendEvent(String eventName) {
    try {
      if (mContext.hasActiveCatalystInstance()) {
        mContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit(eventName, null);
      }
    } catch (RuntimeException e) {
      // Work around a race condition in RN < 0.38.0, resulting in
      // a RuntimeException of "Attempt to call JS function before JS bundle is loaded."
      // Fixed in react-native#6a45f05.
    }
  }

  @Override
  public String getName() {
    return "ChromeCustomTabsClient";
  }

  @Override
  public void onCustomTabsConnected() {
    this.sendEvent("customTabsConnected");
  }

  @Override
  public void onCustomTabsDisconnected() {
    this.sendEvent("customTabsDisconnected");
  }

  @ReactMethod
  public void mayLaunchUrl(String url) {
    Boolean didWarm = mCustomTabActivityHelper.mayLaunchUrl(Uri.parse(url), null, null);
    if (didWarm) {
      this.sendEvent("didWarmUrl");
    } else {
      this.sendEvent("didNotWarmUrl");
    }
  }

  @ReactMethod
  public void launchCustomTab(String url) {
    Activity activity = getCurrentActivity();
    if (activity == null) {
      return;
    }

    CustomTabsIntent.Builder builder =
        new CustomTabsIntent.Builder(mCustomTabActivityHelper.getSession());
    if (mIntentEditor != null) {
      mIntentEditor.customize(activity, builder);
    }
    CustomTabActivityHelper.openCustomTab(activity, builder.build(), Uri.parse(url),
        new CustomTabActivityHelper.CustomTabFallback() {
          @Override
          public void openUri(Activity activity, Uri uri) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
          }
        });
  }
}
