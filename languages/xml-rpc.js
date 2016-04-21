var xmlrpc = require('xmlrpc')
var xml2js = require('xml2js')
var traverse = require('traverse');
var xml2jsparser = new xml2js.Parser();

module.exports = {
    init: function (codeFile) {
        this.client = xmlrpc.createClient({host: 'localhost', port: 80});
        this.codeFile = codeFile;
        this.callMethod = function (nameOfMethod) {
            this.client.methodCall('parser.' + nameOfMethod, [this.codeFile], function (error, value) {
                if (error) {
                    console.log('error:', error);
                    console.log('req headers:', error.req && error.req._header);
                    console.log('res code:', error.res && error.res.statusCode);
                    console.log('res body:', error.body);
                } else {
                    xml2jsparser.parseString(value, function (err, result) {
                        xml2jsparser.var
                        daco = xmlrpc.createClient({host: 'localhost', port: 80});
                        daco.methodCall('parser.' + "setAst", [result], function (error, value) {
                            if (error) {
                                console.log('error:', error);
                                console.log('req headers:', error.req && error.req._header);
                                console.log('res code:', error.res && error.res.statusCode);
                                console.log('res body:', error.body);
                            } else {
                                console.log(value);
                            }
                        });
                    });
                }
            });
        }

    }


}