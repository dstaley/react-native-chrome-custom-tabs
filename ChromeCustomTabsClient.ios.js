'use strict';

var { Linking } = require('react-native');

// Fall back to Linking.openURL on iOS.
module.exports.launchCustomTab = Linking.openURL;
module.exports.mayLaunchUrl = function(url) {};
