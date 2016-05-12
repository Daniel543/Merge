var commandLineArgs = require('command-line-args');
var sys = require('sys');
var exec = require('child_process').exec;
var languageParser = require("./languageParser.js");
var files;
var rules;
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
if (options.rules === undefined) {
    console.log("Missing arguments, use -h for help");
    process.exit(1);
}

var lastChar;
lastChar = options.input.toString().charAt(options.input.toString().length - 1);
var filesCommand;
if (options.input[0][0] === '/') {       //checks for absolute path
    if (lastChar === '/') {
        filesCommand = options.input + '*';
    }
    else {
        filesCommand = options.input + '/*';
    }

}
else {
    if (lastChar === '/') {
        filesCommand = "$PWD/" + options.input + '*';

    }
    else {
        filesCommand = "$PWD/" + options.input + "/*";
    }
}

lastChar = options.rules.toString().charAt(options.rules.toString().length - 1);
var rulesCommand;
if (options.rules[0][0] === '/') {
    if (lastChar === '/') {
        rulesCommand = options.rules + '*';
    }
    else {
        rulesCommand = options.rules + '/*';
    }
}
else {
    if (lastChar === '/') {
        rulesCommand = "$PWD/" + options.rules + '*';

    }
    else {
        rulesCommand = "$PWD/" + options.rules + "/*";
    }
}
/*else {
 filesCommand = filesCommand + "/";
}
 */
exec("ls -d -1 " + filesCommand, function (error, stdout, stderr) {
    //sys.print('stdout: ' + stdout);
    files = stdout;
    var res = files.split('\n');
    if (error !== null) {
        console.log('exec error: ' + error);
    }
    //RULES
    exec("ls -d -1 " + rulesCommand, function (error, stdout, stderr) { //todo add java server start
        rules = stdout.split('\n');

        if (error !== null) {
            console.log('exec error: ' + error);
        }
        rules.length = rules.length - 1;
        res.length = res.length - 1;
        langParser = new languageParser.parse(res, rules, options.language[0]);
    });







  

});

