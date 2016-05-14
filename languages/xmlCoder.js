var xml2js = require('xml2js')
var xml2jsParser = new xml2js.Parser();
var xml2jsBuilder = new xml2js.Builder();

module.exports = {
    decode: function (input, callback) {
        xml2jsParser.parseString(input, function (err, result) {
            callback(result);
        })

    },
    encode: function (input) {

        return xml2jsBuilder.buildObject(input)
    }
        

}