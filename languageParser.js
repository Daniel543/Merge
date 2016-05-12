var fs = require('fs')

module.exports = {

    parse: function (codeFiles, rules, language) {
        if (language === "java") {
            var parser = require("./languages/javaParser");
        }
        var methods;
        var targetClass;
        var adapter = new parser.init();
        var are_parsed;
        adapter.parseFiles(codeFiles, function (value) {
            if (value !== true) {
                console.error("There was problem parsing data");
                process.exit(1);
            }

            for (var i in rules) {
                var contents = fs.readFileSync(rules[i], 'utf8')
                var contents_array = contents.split('\n');
                contents_array.length = contents_array.length - 1;
                targetClass = findTargetClass(contents_array);
                methods = getAllMethods(contents_array);
                for (var j in methods) {
                    adapter.moveMethod(targetClass, methods[j]);
                }
            
            
        }


            function findTargetClass(contents_array) {
                for (var j in contents_array) {
                    if (contents_array[j] === "[TargetClass]") {
                        return contents_array[++j];
                    }
                }
            }

            function getAllMethods(contents_array) {
                var methods = [];
                for (var j in contents_array) {
                    if (contents_array[j] === "[Methods]") {
                        j++;
                        while (contents_array[j] !== '[/Methods]') {
                            methods.push(contents_array[j]);
                            j++

                        }
                        return methods;
                    }
                }
            }
        });
    }
}



