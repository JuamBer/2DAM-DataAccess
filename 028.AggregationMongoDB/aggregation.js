//1. Count all movies filmed in year 2000
db.movies.aggregate([{
    $match: {
        year: 2000
    }
}, {
    $group: {
        _id: null,
        totalMovies: {
            $sum: 1
        }
    }
}]);

//2. Obtain all distinct genres
db.movies.distinct("genres");

//3. Count how many movies were filmed in each year
db.movies.aggregate([{
    $group: {
        _id: "$year",
        totalMovies: {
            $count: {}
        }
    }
}]);

//4. Count how many movies were filmed in each year, between 2000 and 2020
db.movies.aggregate([{
    $match: {
        $and: [{
            year: {
                $gte: 2000
            }
        }, {
            year: {
                $lte: 2020
            }
        }]
    }
}, {
    $group: {
        _id: "$year",
        totalMovies: {
            $count: {}
        }
    }
}]);

//5. Obtain the total amount of nominations and wins of awards of the films of the year 2000.
db.movies.aggregate([{
    $match: {
        year: 2000
    }
}, {
    $group: {
        _id: "$year",
        totalAmountNominations: {
            $sum: "$awards.nominations"
        },
        totalAmountWins: {
            $sum: "$awards.wins"
        }
    }
}]);

//6. Get the max awards won by a film.
db.movies.aggregate([{
    $group: {
        _id: null,
        awards: {
            $max: "$awards.wins"
        }
    }
}]);

//7. For “Steven Spielberg”, get the total won awards, the max awards of an individual film and the average awards per film.
db.movies.aggregate([{
    $match: {
        directors: "Steven Spielberg"
    }
}, {
    $group: {
        _id: null,
        totalAwards: {
            $sum: "$awards.wins"
        },
        maxAwards: {
            $max: "$awards.wins"
        },
        averageAwards: {
            $avg: "$awards.wins"
        }
    }
}]);


//1. Get the film which won the maximum number of awards and show only 
// the id, title, directors and number of awards(this named as win).
db.movies.aggregate([{
    $sort: {
        "awards.wins": 1
    }
}, {
    $limit: 1
}, {
    $set:{
        win: "$awards.wins"
    }
},
{
    $project: {
        title: 1,
        directors: 1,
        win:1
    }
}]);

//2. Para las películas cuyo año es un string. modifica el campo year 
//para que sea un numero formado pormlas cuadtro primeras cifras

db.movies.aggregate([{
        $match: {
            year: {
                $type: "string"
            }
        }
    }, {
        $set: {
            year: { $substrBytes: [ "$year", 0, 4 ]}
        }
    },
    {
        $out: {
            db:"mflix",
            coll: "data"
        }
    }
]);