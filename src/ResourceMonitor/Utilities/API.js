const http = require('http');

const obj = {
    "logdate" : "2020-10-20",
    "cpuusage" : 10,
    "ramusage" : 80,
    "hddusage" : 60             
}
const serverinstance = http.createServer((req, res) => {
    res.write(JSON.stringify(obj));
    res.end();
})

serverinstance.listen(8000, () => console.log("started"));