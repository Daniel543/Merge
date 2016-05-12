/**
 * Created by daniel on 4/18/16.
 */
var xmlRpcClient = require("./xml-rpc.js");


module.exports = {
    init: function () {
        this.codeFiles = null;
        //this.ast = null;
        this.rpcClient = new xmlRpcClient.init();
        /*  this.getAst = function (nieco) {
            this.rpcClient.callMethod("getAst");
        }
         */
        this.parseFiles = function (codeFiles, callback) {
            this.rpcClient.callMethod("parseFiles", codeFiles, callback);
        }
        this.moveMethod = function (targetClass, methodName) {
            var args = [];
            args.push(targetClass, methodName);
            this.rpcClient.callMethod("moveMethod", args);
            
        }
        this.exitServer = function () {
            this.rpcClient.callMethod("exit");
        }
        this.writeFiles = function () {
            this.rpcClient.callMethod("writeFiles");
        }
    }

}