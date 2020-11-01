const http = require('http');

// Randomly generates a number using Math.random between 0 and 75, math.floor to remove the decimal places
// Keeps the same log date to avoid flooding of random dates in the DB
const obj = {
    "logdate" : "2020-10-20",
    "cpuusage" : Math.floor(Math.random() * 75),
    "ramusage" : Math.floor(Math.random() * 75),
    "hddusage" : Math.floor(Math.random() * 75)             
}
console.log(obj)
const serverinstance = http.createServer((req, res) => {
    // Listens on my server port 8000 and sends all requests the same JSON body defined above 
    res.write(JSON.stringify(obj));
    res.end();
})

serverinstance.listen(8000, () => console.log("started"));