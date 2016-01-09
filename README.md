# react-native-chrome-custom-tabs

![Demo of Chrome Custom Tabs in React Native](http://i.imgur.com/ZxO4Tut.gif)

## Installation

1. `npm install --save react-native-chrome-custom-tabs`
2. Add the following to `android/settings.gradle`:
```
include ':ReactNativeChromeCustomTabs', ':app'
project(':ReactNativeChromeCustomTabs').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-chrome-custom-tabs/android')
```
3. Add `compile project(':ReactNativeChromeCustomTabs')` to  `dependencies` in `android/app/build.gradle`
4. Import and register the module in your `MainActivity.java` file:
```java
import com.dstaley.ReactNativeChromeCustomTabs.ChromeCustomTabsPackage; // <-- Import

public class MainActivity extends Activity implements DefaultHardwareBackBtnHandler {

    private ReactInstanceManager mReactInstanceManager;
    private ReactRootView mReactRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReactRootView = new ReactRootView(this);

        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setBundleAssetName("index.android.bundle")
                .setJSMainModuleName("index.android")
                .addPackage(new MainReactPackage())
                .addPackage(new ChromeCustomTabsPackage(this))          // <-- Register
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();

        mReactRootView.startReactApplication(mReactInstanceManager, "DemoReactNativeCustomTabs", null);

        setContentView(mReactRootView);
    }
```

## Usage

```js
var ChromeCustomTabsClient = require('react-native-chrome-custom-tabs');

// Tell Chrome to preload a URL
ChromeCustomTabsClient.mayLaunchUrl('http://i.imgur.com/6ogeF96.gif');

// Launch a Custom Tab
ChromeCustomTabsClient.launchCustomTab('http://i.imgur.com/xjdem.gif');
```

## License

MIT