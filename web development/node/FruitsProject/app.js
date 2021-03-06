const mongoose = require('mongoose');

mongoose.connect("mongodb://localhost:27017/fruitsDB", {useNewUrlParser: true});

const fruitSchema = new mongoose.Schema({
    name: {
        name: String,
        required: [true, "Please check your data, no name specified"]
    },
    rating: {
        type: Number,
        min: 0,
        max: 10
    },
    review: String
});

const Fruit = mongoose.model("Fruit", fruitSchema);

const fruit = new Fruit({
        name: "Apple",
        score: 7,
        review: "Pretty solid as a fruit"
    });


// fruit.save();


const personSchema = new mongoose.Schema({
    name: String,
    age: Number
});

const Person = mongoose.model("Person", personSchema);

const person = new Person({
        name: "John",
        age: 37
    });

person.save();

const kiwi = new Fruit({
    name: "Kiwi",
    score: 10,
    review: "The best fruit"
});

const orange = new Fruit({
    name: "orange",
    score: 4,
    review: "sour fruit"
});

const banana = new Fruit({
    name: "banana",
    score: 7,
    review: "good fruit"
});

Fruit.insertMany([kiwi, orange, banana], function(err){
    if(err){
        console.log(err);
    } else{
        console.log("Successfully saved all the fruits to fruitsDB")
    }
});

Fruit.find(function(err, fruits){
    if(err){
        console.log(err);
    } else {
        console.log(fruits);

        fruits.forEach(function(fruit){
            console.log(fruit.name);
        });
    }
});

