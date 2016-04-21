module.exports = {

    parse: function (codeFiles, language) {
        if (language === "java") {
            var parser = require("./languages/javaParser");
        }
        for (var i in codeFiles) {
            val = codeFiles[i];
            var adapter = new parser.init(val);
            adapter.getAst();

        }


    }

}



