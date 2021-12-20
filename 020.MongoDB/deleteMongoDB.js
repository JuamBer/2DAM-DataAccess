//Delete all movies titled Casablanca

db.movies.deleteMany({
    title: "Casablanca"
})

//Delete one of all the movies directed by“ William K.L.Dickson”

db.movies.deleteOne({
    directors: {
        $elemMatch: { $eq: "William K.L.Dickson" }
    }
})

//Delete all the movies directed by“ William K.L.Dickson”

db.movies.deleteMany({
    directors: {
        $elemMatch: {
            $eq: "William K.L.Dickson"
        }
    }
})

//Delete one of all movies that “Humphrey Bogart” is in the cast and have won at least 5 awards

db.movies.deleteOne({
    cast: {
        $elemMatch: {
            $eq: "Humphrey Bogart"
        }
    },
    "awards.wins": {
        $gte: 5
    }
})