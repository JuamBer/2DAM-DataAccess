
// For all the movies titled Titanic, add a field called mustsee with true value and add Groenlandia to the countries array.
db.movies.updateMany({
    title: {
        $regex: /titanic/i
    }
}, {
    $set: {
        mustsee: true
    },
    $push: {
        countries: "Groenlandia"
    }
})



//For all the movies containing“ Rings” in the title and with the num_mflix_comments field, add 50 to num_mflix_comments.
db.movies.updateMany({
        title: {
            $regex: /rings/i
        },
        num_mflix_comments: {
            $exists: true
        }
    }, {
        $inc: {
            num_mflix_comments: 50
        }
})


//For the first movie containing“ Matrix” in the title and of the year 2003 remove awards object and rename the plot field to shortplot.
db.movies.updateOne({
        title: {
            $regex: /matrix/i
        },
        year: 2003
    }, {
        $unset: {
            awards: ""
        },
        $rename: {
            plot: "shortplot"
        }
})