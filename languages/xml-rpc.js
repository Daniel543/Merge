var xmlrpc = require('xmlrpc')
var xml2js = require('xml2js')
var xml2jsparser = new xml2js.Parser();

module.exports = {
    init: function () {
        this.client = xmlrpc.createClient({host: 'localhost', port: 80});
        this.codeFile = null;

        this.callMethod = function (nameOfMethod, args, callback) {
            this.client.methodCall('parser.' + nameOfMethod, [args], function (error, value) {
                if (error) {
                    console.log('error:', error);
                    console.log('req headers:', error.req && error.req._header);
                    console.log('res code:', error.res && error.res.statusCode);
                    console.log('res body:', error.body);
                } else {
                    if (callback !== undefined) {
                        callback(value);
                    }
                }
            });
        }

    }


}