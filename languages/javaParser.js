/**
 * Created by daniel on 4/18/16.
 */
var xmlRpcClient = require("./xml-rpc.js");


module.exports = {
    init: function (codeFile) {
        console.log(codeFile);
        this.codeFile = codeFile;
        this.ast = null;
        this.rpcClient = new xmlRpcClient.init(codeFile);
        this.getAst = function (nieco) {
            this.rpcClient.callMethod("getAst");
        }

        this.moveMethod = function (nieco) {


        }
    }

}