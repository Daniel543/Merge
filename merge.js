var commandLineArgs = require('command-line-args');
var sys = require('sys');
var exec = require('child_process').exec;
var childProcess = require('child_process');
var languageParser = require("./languageParser.js");
var rules;
var langParser;
var cli = commandLineArgs([
    {name: 'verbose', alias: 'v', type: Boolean},
    {name: 'output', type: String, multiple: true},
    {name: 'rules', type: String, multiple: true},
    {name: 'language', type: String, multiple: true}
])
var options = cli.parse();
var isOutputDefined = true;

if (options.language === undefined) {
    console.log("Missing arguments, use -h for help");
    process.exit(1);

}
if (options.rules === undefined) {
    console.log("Missing arguments, use -h for help");
    process.exit(1);
}
if (options.output === undefined) {
    isOutputDefined = false;
    console.log("Output not defined, using standard output")
}
var lastChar;

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
exec("java -jar JavaParser.jar", function (error, stdout, stderr) {
    if (error !== null) {
        console.log('exec error: ' + error);
    }

})

exec("ls -d -1 " + rulesCommand, function (error, stdout, stderr) {
    rules = stdout.split('\n');

    if (error !== null) {
        console.log('exec error: ' + error);
    }
    rules.length = rules.length - 1;


    langParser = new languageParser.parse(rules, options.language[0], options.output);

});
