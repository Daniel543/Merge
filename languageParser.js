var fs = require('fs')
module.exports = {

    parse: function (rules, language, outputDir) {
        if (language === "java") {
            var parser = require("./languages/javaParser");
        }
        var adapter = new parser.init();
        var rulesString = fs.readFileSync(rules[0], "utf8")
        var rulesJson = JSON.parse(rulesString);


        adapter.getAst(rulesJson.src, function (sourceAst) {
            adapter.getAst(rulesJson.dst, function (dstAst) {
                adapter.moveMethod(sourceAst, dstAst, rulesJson.targetClass, rulesJson.methods[0].methodName, function (astArrayList) {
                    var changedSourceAst = astArrayList[0];         //index 0 is source
                    var changedDestinationAst = astArrayList[1];    //index 1 is destination
                    var same = false;
                    if (rulesJson.src === rulesJson.dst) {
                        same = true;
                    }
                    if (!same) {
                        adapter.getCode(changedSourceAst, function (changedSourceCode) {
                            adapter.getCode(changedDestinationAst, function (changedDestCode) {
                                if (outputDir === undefined) {
                                    console.log("SOURCE FILE:\n" + changedSourceCode)
                                    console.log("DESTINATION FILE:\n" + changedDestCode)
                                }
                                else {
                                    fs.writeFileSync(outputDir + "changedCode_source.java", changedSourceCode, 'utf8');
                                    fs.writeFileSync(outputDir + "changedCode_dest.java", changedDestCode, 'utf8');
                                    adapter.exitServer();

                                }
                            })
                        })
                    }
                    else {
                        adapter.getCode(changedSourceAst, function (changedSourceCode) {
                            if (outputDir === undefined) {
                                console.log("CHANGED FILE: \n" + changedSourceCode)
                            }
                            else {
                                fs.writeFileSync(outputDir + "changedCode.java", changedSourceCode, 'utf8');
                                adapter.exitServer();
                            }
                        })
                    }

                })

            })
        })

    }
}



