const express = require("express");
const boduParser = require("body-parser");

const app = express();

app.get("/", function(req, res){

    var today = new Date();
    var currentDay = today.getDay();
    
    if (currentDay === 6 || currentDay === 0 ){
        res.write("<h1>Yay its the weekend</h1>");
    } else {
        res.write("<h1>Boo! I have to work</h1>");
    }
})

app.listen(3000, function(){
    console.log("server started")
})