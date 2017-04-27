package com.dstaley.ReactNativeChromeCustomTabs;

import android.content.Context;
import android.support.customtabs.CustomTabsIntent;

/**
 * Callback for customizing the creation of a CustomTabsIntent.
 * This is called just before a custom tab is opened.
 */
public interface CustomTabsIntentEditor {

  void customize(Context context, CustomTabsIntent.Builder builder);
}
