function fn() {

    var properties = karate.read('config.properties');
    if (!properties) {
        karate.log('Error: config.properties not found!');
    }

    var propertiesString = new java.lang.String(properties);

    var map = {};
    var lines = propertiesString.split('\n');

    lines.forEach(function(line) {
        if (line.trim() && line.indexOf('=') !== -1) {
            var parts = line.split('=');
            var key = parts[0].trim();
            var value = parts[1].trim();
            map[key] = value;
        }
    });

    karate.set('gatewayUrl', map.gatewayUrl);
    karate.set('camundaEngineUrl', map.camundaEngineUrl);
    karate.set('tenantID', map.tenantID);
    karate.set('sourceID', map.sourceID);
    karate.set('userID', map.userID);
    karate.set('userPWD', map.userPWD);
    karate.set('searchUserID', map.searchUserID);

    var config = {};

    karate: {
        log: 'INFO';
    }

    return config;
}