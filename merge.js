var commandLineArgs = require('command-line-args');
var sys = require('sys');
var exec = require('child_process').exec;
var languageParser = require("./languageParser.js");
var files;
var langParser;
var cli = commandLineArgs([
    {name: 'verbose', alias: 'v', type: Boolean},
    {name: 'input', type: String, multiple: true},
    {name: 'output', type: String, multiple: true},
    {name: 'rules', type: String, multiple: true},
    {name: 'language', type: String, multiple: true}
])
var options = cli.parse();
if (options.input === undefined) {
    console.log("Missing arguments, use -h for help");
    process.exit(1);
}
if (options.language === undefined) {
    console.log("Missing arguments, use -h for help");
    process.exit(1);

}
var lsCommand;
if (options.input[0][0] === '/') {       //checks for absolute path
    lsCommand = options.input;
}
else {
    lsCommand = "$PWD/" + options.input + "/*";
}
if (options.input[0][options.input[0].length - 1] === '/') {

}
else {
    lsCommand = lsCommand + "/";
}

exec("ls -d -1 " + lsCommand + "*", function (error, stdout, stderr) {
    //sys.print('stdout: ' + stdout);
    files = stdout;
    var res = files.split('\n');
    if (error !== null) {
        console.log('exec error: ' + error);
    }
    res.length = res.length - 1;
    langParser = new languageParser.parse(res, options.language[0]) //todo add rules

});

