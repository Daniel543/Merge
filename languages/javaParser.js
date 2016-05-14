/**
 * Created by daniel on 4/18/16.
 */
var xmlRpcClient = require("./xml-rpc.js");
var xmlCoder = require("./xmlCoder")


module.exports = {
    init: function () {
        this.rpcClient = new xmlRpcClient.init();

        this.moveMethod = function (srcAst, dstAst, targetClass, methodName, callback) {
            var args = [];
            var encodedSrcAst = xmlCoder.encode(srcAst)
            var encodedDstAst = xmlCoder.encode(dstAst)

            args.push(encodedSrcAst, encodedDstAst, targetClass, methodName);
            this.rpcClient.callMethod("mergeHandler.moveMethod", args, function (astHashmap) {
                var astArray = [];
                new xmlCoder.decode(astHashmap[0], function (decodedChangedSourceAst) {
                    new xmlCoder.decode(astHashmap[1], function (decodedChangedDestAst) {
                        astArray[0] = decodedChangedSourceAst
                        astArray[1] = decodedChangedDestAst
                        callback(astArray)
                    })

                })
            });


        }
        this.exitServer = function () {
            this.rpcClient.callMethod("parseHandler.exit", 0);
        }

        this.getAst = function (codefile, callback) {
            this.rpcClient.callMethod("parseHandler.getAst", codefile, function (ast) {
                new xmlCoder.decode(ast, function (decodedAst) {
                    callback(decodedAst)
                })
            });
        }
        this.getCode = function (ast, callback) {
            var encodedAst = xmlCoder.encode(ast);
            this.rpcClient.callMethod("parseHandler.getCode", encodedAst, callback);
        }

    }

}