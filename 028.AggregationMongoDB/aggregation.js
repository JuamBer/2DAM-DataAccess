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