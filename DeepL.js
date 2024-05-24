
const deepl = require('deepl-node');
const express=require('express');

const { Server } = require('http');
const sql = require('mssql/msnodesqlv8');

const authKey = "301c299d-0371-4fd7-9250-d62a32cb56a9:fx"; 
const translator = new deepl.Translator(authKey);

const basicvWord = [
    "apple", 
    "book", 
    "cat", 
    "dog", 
    "elephant", 
    "flower", 
    "guitar", 
    "hat", 
    "ice cream", 
    "juice", 
    "kite", 
    "lamp", 
    "monkey", 
    "notebook", 
    "orange", 
    "pencil", 
    "question", 
    "rabbit", 
    "sun", 
    "table", 
    "umbrella", 
    "violin", 
    "watch", 
    "xylophone", 
    "yacht", 
    "zebra", 
    "airplane", 
    "ball", 
    "car", 
    "door", 
    "egg", 
    "frog", 
    "grape", 
    "horse", 
    "island", 
    "jacket", 
    "key", 
    "lion", 
    "milk", 
    "nest", 
    "pizza", 
    "queen", 
    "rose", 
    "star", 
    "tree", 
    "van", 
    "watermelon", 
    "yogurt", 
    "zeppelin", 
    "ant", 
    "bear", 
    "cake", 
    "duck", 
    "fish", 
    "goat", 
    "hamburger", 
    "ink", 
    "jellyfish", 
    "kangaroo", 
    "lemon", 
    "mango", 
    "nose", 
    "octopus", 
    "peach", 
    "quilt", 
    "rain", 
    "socks", 
    "tiger", 
    "vase", 
    "whale", 
    "yak", 
    "balloon", 
    "giraffe", 
    "moon", 
    "penguin", 
    "train", 
    "bird", 
    "candle", 
    "banana", 
    "grapefruit", 
    "lime", 
    "peanut", 
    "melon", 
    "kiwi", 
    "pineapple", 
    "cherry", 
    "blueberry", 
    "strawberry", 
    "raspberry", 
    "blackberry", 
    "plum", 
    "apricot", 
    "pear", 
    "fig", 
    "date", 
    "olive", 
    "nectarine", 
    "pomegranate", 
    "tangerine"
  ]
  

Tr=[]
const config = {
    server: "LAPTOP-6GU3D2FV\\SQLEXPRESS",
    database: "words",
    
    options: {
        trustedConnection: true
    }
};
async function translateWords() {
    try {
        // Çeviri işlemlerini gerçekleştir
        for (let i = 0; i < basicvWord.length; i++) {
            const result = await translator.translateText(basicvWord[i], null, 'tr');
            Tr.push(result.text);
        }
        console.log("Tüm çeviriler tamamlandı:", Tr);
    } catch (err) {
        throw err;
    }
}

async function translateAndInsertWords() {
    try {
        // Çeviri işlemlerini gerçekleştir
        await translateWords();

        // Veritabanına bağlan
        const pool = await sql.connect(config);

        // basicvWord dizisindeki her kelimeyi veritabanına ekleyin
        for (let i = 0; i < basicvWord.length; i++) {
            await pool.request()
                .input('en', sql.VarChar, basicvWord[i])
                .input('tr',sql.VarChar,Tr[i])
                .query("INSERT INTO dbo.basic_words (en,tr) VALUES (@en,@tr)");
            console.log('Data inserted successfully.');
        }

        // Tr dizisindeki her çeviriyi veritabanına ekleyin
      

        console.log("Tüm çeviriler ve veriler veritabanına başarıyla eklendi.");
    } catch (err) {
        console.error("Bir hata oluştu:", err);
    }
}

// translateAndInsertWords fonksiyonunu çağırarak işlemi başlat
translateAndInsertWords();

    
   







const mid_Level = [
    "Beautiful",
    "Delicious",
    "Understand",
    "Problem",
    "Important",
    "Travel",
    "Education",
    "Conversation",
    "Decision",
    "Friendship",
    "Together",
    "Difference",
    "Opportunity",
    "Relationship",
    "Experience",
    "Challenge",
    "Adventure",
    "Solution",
    "Success",
    "Failure",
    "Comfortable",
    "Convenient",
    "Necessary",
    "Possible",
    "Difficult",
    "Unique",
    "Ambitious",
    "Independent",
    "Creative",
    "Patient",
    "Curious",
    "Confident",
    "Responsible",
    "Reliable",
    "Generous",
    "Honest",
    "Sincere",
    "Polite",
    "Courageous",
    "Motivated",
    "Enthusiastic",
    "Optimistic",
    "Pessimistic",
    "Sociable",
    "Introverted",
    "Extroverted",
    "Flexible",
    "Persistent",
    "Resourceful",
    "Versatile"
];

const hard_Level = [
    "Accommodate",
    "Consequence",
    "Ecstasy",
    "Fascinate",
    "Inevitable",
    "Nourishment",
    "Resilient",
    "Ubiquitous",
    "Volatile",
    "Zealous",
    "Aberration",
    "Ambiguous",
    "Discrepancy",
    "Ephemeral",
    "Magnanimous",
    "Penultimate",
    "Reticent",
    "Ubiquity",
    "Veracity",
    "Antediluvian",
    "Cacophony",
    "Discombobulate",
    "Epiphany",
    "Mellifluous",
    "Non sequitur",
    "Procrastinate",
    "Serendipity",
    "Superfluous",
    "Vicarious",
    "Zenith",
    "Ameliorate",
    "Capricious",
    "Abominable",
    "Defenestration",
    "Mellifluous",
    "Obfuscate",
    "Pulchritudinous",
    "Querulous",
    "Rambunctious",
    "Sesquipedalian",
    "Ultracrepidarian",
    "Xenophobie",
    "Abnegation",
    "Enervate",
    "Nefarious",
    "Ostentatious",
    "Paradigm",
    "Quixotic",
    "Salient",
    "Truculent",
    "Visceral"
];

const hard_core = [
    "Abominable",
    "Defenestration",
    "Mellifluous",
    "Obfuscate",
    "Pulchritudinous",
    "Querulous",
    "Rambunctious",
    "Sesquipedalian",
    "Ultracrepidarian",
    "Xenophobie",
    "Abnegation",
    "Enervate",
    "Nefarious",
    "Ostentatious",
    "Paradigm",
    "Quixotic",
    "Salient",
    "Truculent",
    "Visceral",
    "Cacophony",
    "Discombobulate",
    "Epiphany",
    "Superfluous",
    "Vicarious",
    "Zenith",
    "Ambiguous",
    "Magnanimous",
    "Penultimate",
    "Ubiquity",
    "Ameliorate",
    "Capricious",
    "Discrepancy",
    "Ephemeral",
    "Non sequitur",
    "Procrastinate",
    "Serendipity",
    "Ubiquitous",
    "Veracity",
    "Antediluvian",
    "Mellifluous",
    "Fascinate",
    "Inevitable",
    "Zealous",
    "Accommodate",
    "Ecstasy",
    "Resilient",
    "Volatile",
    "Zealous"
];




            

            

//   (async () => {
//     for(var i=0;i<50;i++)
//     {
//         var result = await translator.translateText(mid_Level[i], null, 'tr');
//         //console.log(result.text)

        
//     }
//    })();



// //  (async () => {
    
// //     for(var i=0;i<50;i++)
// //     {
// //         var result = await translator.translateText(hard_Level[i], null, 'tr');
// //         //console.log(result.text)

// //     }
// //  })();



// //  (async () => {
    
// //   for(var i=0;i<50;i++)
// //     {
// //         var result = await translator.translateText(hard_core[i], null, 'tr');
// //         //console.log(result.text)
// //     }
// //  })();