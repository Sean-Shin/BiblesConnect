package sean.to.readbiblesmart.data;

public class BibleData {
    public static String[] nameArray = {"Cupcake", "Donut", "Eclair", "Froyo", "Gingerbread", "Honeycomb", "Ice Cream Sandwich","JellyBean", "Kitkat", "Lollipop", "Marshmallow"};
    public static String[] versionArray = {"1.5", "1.6", "2.0-2.1", "2.2-2.2.3", "2.3-2.3.7", "3.0-3.2.6", "4.0-4.0.4", "4.1-4.3.1", "4.4-4.4.4", "5.0-5.1.1","6.0-6.0.1"};

//    static Integer[] drawableArray = {R.drawable.cupcake, R.drawable.donut, R.drawable.eclair,
//            R.drawable.froyo, R.drawable.gingerbread, R.drawable.honeycomb, R.drawable.ics,
//            R.drawable.jellybean, R.drawable.kitkat, R.drawable.lollipop,R.drawable.marsh};

    public static Integer[] id_ = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    public static String[] bibleNames = {"Genesis","Exodus","Leviticus","Numbers","Deuteronomy",
    "Joshua","Judges", "Ruth","1 Samuel", "2 Samuel", "1 Kings", "2 Kings",
            "1 Chronicles", "2 Chronicles", "Ezra", "Nehemiah", "Esther"
            , "Job", "Psalms", "Proverbs", "Ecclesiastes", "Songs"
            , "Isaiah", "Jeremiah", "Lamentations", "Ezekiel", "Daniel"
            , "Hosea", "Joel", "Amos", "Obadiah", "Jonah", "Micah", "Nahum", "Habakkuk", "Zephaniah", "Haggai", "Zechariah","Malachi"
            , "Matthew", "Mark", "Luke", "John", "Acts", "Romans", "1 Corinthians", "2 Corinthians"
            , "Galatians", "Ephesians", "Philippians", "Colossians", "1 Thessalonians", "2 Thessalonians"
            , "1 Timothy", "2 Timothy", "Titus", "Philemon", "Hebrews", "James"
            , "1 Peter", "2 Peter", "1 John", "2 John", "3 John", "Jude", "Revelation"};

//    public static int verses[66][];

    public static int verses[][] = {
           /*  0 to 4*/
            //0 Genesis
            {31,25,24,26,32,22,24,22,29,32,32,20,18,24,21,16,27,33,38,18,34,24,20,67,34,35,46,22,35,43,54,33,20,31,29,43,36,30,23,23,57,38,34,34,28,34,31,22,33,26},
            //1 Exodus
            {22,25,22,31,23,30,29,28,35,29,10,51,22,31,27,36,16,27,25,26,37,30,33,18,40,37,21,43,46,38,18,35,23,35,35,38,29,31,43,38},
//            2 Leviticus
            {17,16,17,35,26,23,38,36,24,20,47,8,59,57,33,34,16,30,37,27,24,33,44,23,55,46,34},
//            3 Numbers
            {54,34,51,49,31,27,89,26,23,36,35,16,33,45,41,35,28,32,22,29,35,41,30,25,19,65,23,31,39,17,54,42,56,29,34,13},
//            4 Deuteronomy
            {46,37,29,49,33,25,26,20,29,22,32,31,19,29,23,22,20,22,21,20,23,29,26,22,19,19,26,69,28,20,30,52,29,12},
        /* 5  to 16 */
//            5 Joshua
            {18,24,17,24,15,27,26,35,27,43,23,24,33,15,63,10,18,28,51,9,45,34,16,33},
//            6 Judges
            {36,23,31,24,31,40,25,35,57,18,40,15,25,20,20,31,13,31,30,48,25},
//            7 Ruth
            {22,23,18,22},
//            8 1 Samuel
            {28,36,21,22,12,21,17,22,27,27,15,25,23,52,35,23,58,30,24,42,16,23,28,23,43,25,12,25,11,31,13},
//            9 2 Samuel
            {27,32,39,12,25,23,29,18,13,19,27,31,39,33,37,23,29,32,44,26,22,51,39,25},
//            10 1 Kings
            {53,46,28,20,32,38,51,66,28,29,43,33,34,31,34,34,24,46,21,43,29,54},
//            11 2 Kings
            {18,25,27,44,27,33,20,29,37,36,20,22,25,29,38,20,41,37,37,21,26,20,37,20,30},
//            12 1 Chronicles
            {54,55,24,43,41,66,40,40,44,14,47,41,14,17,29,43,27,17,19,8,30,19,32,31,31,32,34,21,30},
//            13 2 Chronicles
            {18,17,17,22,14,42,22,18,31,19,23,16,23,14,19,14,19,34,11,37,20,12,21,27,28,23,9,27,36,27,21,33,25,33,26,23},
//            14 Ezra
            {11,70,13,24,17,22,28,36,15,44},
//            15 Nehemiah
            {11,20,38,17,19,19,72,18,37,40,36,47,31},
//            16 Esther
            {22,23,15,17,14,14,10,17,32,3,17,8,30,16,24,10},
    /* 17  to 21 */

//            17 Job
            {22,13,26,21,27,30,21,22,35,22,20,25,28,22,35,22,16,21,29,29,34,30,17,25,6,14,21,28,25,31,40,22,33,37,16,33,24,41,30,32,26,17},
//            18 Psalms
            {6,11,9,9,13,11,18,10,21,18,7,9,6,7,5,11,15,51,15,10,14,32,6,10,22,11,14,9,11,13,25,11,22,23,28,13,40,23,14,18,14,12,5,27,18,12,
                    10,15,21,23, 6,11,9,9,13,11,18,10,21,18,7,9,6,7,5,11,15,51,15,10,14,32,6,10,22,
                    11,14,9,11,13,25,11,22,23,28,13,40,23,14,18,14,12,5,27,18,12,10,15,21,23, 8,29,
                    22,35,45,48,43,14,31,7,10,10,9,8,18,19,2,29,176,7,8,9,4,8,5,6,5,6,8,8,3,18,3,3,21,
                    26,9,8,24,14,10,8,12,15,21,10,20,14,9,6},
//            19 Proverbs
            {33,22,35,27,23,35,27,36,18,32,31,28,25,35,33,33,28,24,29,30,31,29,35,34,28,28,27,28,27,33,31},

            {18,26,22,17,19,12,29,17,18,20,10,14},

            {17,17,11,16,16,12,14,14},
        /* 22  to 26 */
    {31,22,26,6,30,13,25,23,20,34,16,6,22,32,9,14,14,7,25,6,17,25,18,23,12,21,13,29,24,33,9,20,24,17,10,22,38,22,8,31,29,25,28,28,25,13,15,22,26,11,23,15,12,17,13,12,21,14,21,22,11,12,19,11,25,24},
    {19,37,25,31,31,30,34,23,25,25,23,17,27,22,21,21,27,23,15,18,14,30,40,10,38,24,22,17,32,24,40,44,26,22,19,32,21,28,18,16,18,22,13,30,5,28,7,47,39,46,64,34},
    {22,22,66,22,22},
    {28,10,27,17,17,14,27,18,11,22,25,28,23,23,8,63,24,32,14,44,37,31,49,27,17,21,36,26,21,26,18,32,33,31,15,38,28,23,29,49,26,20,27,31,25,24,23,35},
    {21,49,100,34,30,29,28,27,27,21,45,13,64,42},
        /* 27  to 38 */
    {9,25,5,19,15,11,16,14,17,15,11,15,15,10},
    {20,27,5,21},
    {15,16,15,13,27,14,17,14,15},
    {21},
    {16,11,10,11},
    {16,13,12,14,14,16,20},
    {14,14,19},
    {17,20,19},
    {18,15,20},
    {15,23},
    {17,17,10,14,11,15,14,23,17,12,17,14,9,21},
    {14,17,24},
        /* 39  to 65 */
    {25,23,17,25,48,34,29,34,38,42,30,50,58,36,39,28,27,35,30,34,46,46,39,51,46,75,66,20},
    {45,28,35,41,43,56,37,38,50,52,33,44,37,72,47,20},
    {80,52,38,44,39,49,50,56,62,42,54,59,35,35,32,31,37,43,48,47,38,71,56,53},
    {51,25,36,54,47,71,53,59,41,42,57,50,38,31,27,33,26,40,42,31,25},
    {26,47,26,37,42,15,60,40,43,48,30,25,52,28,41,40,34,28,40,38,40,30,35,27,27,32,44,31},
    {32,29,31,25,21,23,25,39,33,21,36,21,14,23,33,27},
    {31,16,23,21,13,20,40,13,27,33,34,31,13,40,58,24},
    {24,17,18,18,21,18,16,24,15,18,33,21,13},
    {24,21,29,31,26,18},
    {23,22,21,32,33,24},
    {30,30,21,23},
    {29,23,25,18},
    {10,20,13,18,28},
    {12,17,18},
    {20,15,16,16,25,21},
    {18,26,17,22},
    {16,15,15},
    {25},
    {14,18,19,16,14,20,28,13,28,39,40,29,25},
    {27,26,18,17,20},
    {25,25,22,19,14},
    {21,22,18},
    {10,29,24,21,21},
    {13},
    {15},
    {25},
    {20,29,22,11,14,17,17,13,21,11,19,17,18,20,8,21,18,24,21,15,27,21}};

    public static BibleNames bibleKeys[] = {
            new BibleNames(0, "Genesis", "ge" , "창세기", 50),
            new BibleNames(1, "Exodus","exo","출애굽기",40),
            new BibleNames(2, "Leviticus","lev","레위기",27),
            new BibleNames(3, "Numbers","num","민수기",36),
            new BibleNames(4, "Deuteronomy","deu","신명기",34),
            new BibleNames(5, "Joshua","josh","여호수아",24),

            new BibleNames(6, "Judges","jdgs","사사기",21),
            new BibleNames(7, "Ruth","ruth","룻기",4),
            new BibleNames(8, "1 Samuel","1sm","사무엘상",31),
            new BibleNames(9, "2 Samuel","2sm","사무엘하",24),
            new BibleNames(10, "1 Kings","1ki","열왕기상",22),

            new BibleNames(11, "2 Kings","2ki","열왕기하",25),
            new BibleNames(12, "1 Chronicles","1chr","역대상",29),
            new BibleNames(13, "2 Chronicles","2chr","역대하",36),
            new BibleNames(14, "Ezra","ezra","에스라",10),
            new BibleNames(15, "Nehemiah","neh","느헤미야",13),

            new BibleNames(16, "Esther","est","에스더",10),
            new BibleNames(17, "Job","job","욥기",42),
            new BibleNames(18, "Psalms","psa","시편",150),
            new BibleNames(19, "Proverbs","prv","잠언",31),
            new BibleNames(20, "Ecclesiastes","eccl","전도서",12),

            new BibleNames(21, "Songs","ssol","아가",8),
            new BibleNames(22, "Isaiah","isa","이사야",66),
            new BibleNames(23, "Jeremiah","jer","예레미야",52),
            new BibleNames(24, "Lamentations","lam","예레미야애가",5),
            new BibleNames(25, "Ezekiel","eze","에스겔",48),

            new BibleNames(26, "Daniel","dan","다니엘",12),
            new BibleNames(27, "Hosea","hos","호세아",14),
            new BibleNames(28, "Joel","joel","요엘",3),
            new BibleNames(29, "Amos","amos","아모스",9),
            new BibleNames(30, "Obadiah","obad","오바댜",1),

            new BibleNames(31, "Jonah","jonah","요나",4),
            new BibleNames(32, "Micah","mic","미가",7),
            new BibleNames(33, "Nahum","nahum","나훔",3),
            new BibleNames(34, "Habakkuk","hab","하박국",3),
            new BibleNames(35, "Zephaniah","zep","스바냐",3),

            new BibleNames(36, "Haggai","hag","학개",2),
            new BibleNames(37, "Zechariah","zep","스가랴",14),
            new BibleNames(38, "Malachi","mal","말라기",4),
            new BibleNames(39, "Matthew","mat","마태복음",28),
            new BibleNames(40, "Mark","mark","마가복음",16),

            new BibleNames(41, "Luke","luke","누가복음",24),
            new BibleNames(42, "John","john","요한복음",21),
            new BibleNames(43, "Acts","acts","사도행전",28),
            new BibleNames(44, "Romans","rom","로마서",16),
            new BibleNames(45, "1 Corinthians","1cor","고린도전서",16),

            new BibleNames(46, "2 Corinthians","2cor","고린도후서",13),
            new BibleNames(47, "Galatians","gal","갈라디아서",6),
            new BibleNames(48, "Ephesians","eph","에베소서",6),
            new BibleNames(49, "Philippians","phi","빌립보서",4),
            new BibleNames(50, "Colossians","col","골로새서",4),

            new BibleNames(51, "1 Thessalonians","1th","데살로니가전서",5),
            new BibleNames(52, "2 Thessalonians","2th","데살로니가후서",3),
            new BibleNames(53, "1 Timothy","1tim","디모데전서",6),
            new BibleNames(54, "2 Timothy","2tim","디모데후서",4),
            new BibleNames(55, "Titus","titus","디도서",3),

            new BibleNames(56, "Philemon","phmn","빌레몬서",1),
            new BibleNames(57, "Hebrews","heb","히브리서",13),
            new BibleNames(58, "James","jas","야고보서",5),
            new BibleNames(59, "1 Peter","1pet","베드로전서",5),
            new BibleNames(60, "2 Peter","2pet","베드로후서",3),

            new BibleNames(61, "1 John","1jn","요한1서",5),
            new BibleNames(62, "2 John","2jn","요한2서",1),
            new BibleNames(63, "3 John","3jn","요한3서",1),
            new BibleNames(64, "Jude","jude","유다서",1),
            new BibleNames(65, "Revelation","rev","요한계시록",22)
    };

}

//        bible : item.bible,
//        chapter : item.chapter,
//        verse : item.verse,
//        body : item.body

//    var names = [];
//        names.push({no: 0, en: 'Genesis', key: 'ge' , kr: '창세기', ch: 50});
//        names.push({no: 1, en: 'Exodus', key: 'exo' , kr: '출애굽기', ch: 40});
//        names.push({no: 2, en: 'Leviticus', key: 'lev', kr: '레위기', ch: 27});
//        names.push({no: 3, en: 'Numbers', key: 'num', kr: '민수기', ch: 36});
//        names.push({no: 4, en: 'Deuteronomy', key: 'deu' , kr: '신명기', ch: 34});
//
//        names.push({no: 5, en: 'Joshua', key: 'josh', kr: '여호수아', ch: 24});
//        names.push({no: 6, en: 'Judges', key: 'jdgs', kr: '사사기', ch: 21});
//        names.push({no: 7, en: 'Ruth', key: 'ruth', kr: '룻기', ch: 4});
//        names.push({no: 8, en: '1 Samuel', key: '1sm', kr: '사무엘상', ch: 31});
//        names.push({no: 9, en: '2 Samuel', key: '2sm', kr: '사무엘하', ch: 24});
//        names.push({no: 10, en: '1 Kings', key: '1ki', kr: '열왕기상', ch: 22});
//        names.push({no: 11, en: '2 Kings', key: '2ki', kr: '열왕기하', ch: 25});
//        names.push({no: 12, en: '1 Chronicles', key: '1chr', kr: '역대상', ch: 29});
//        names.push({no: 13, en: '2 Chronicles', key: '2chr', kr: '역대하', ch: 36});
//        names.push({no: 14, en: 'Ezra', key: 'ezra', kr: '에스라', ch: 10});
//        names.push({no: 15, en: 'Nehemiah', key: 'neh', kr: '느헤미야', ch: 13});
//        names.push({no: 16, en: 'Esther', key: 'est', kr: '에스더', ch: 10});
//
//        names.push({no: 17, en: 'Job', key: 'job', kr: '욥기', ch: 42});
//        names.push({no: 18, en: 'Psalms', key: 'psa', kr: '시편', ch: 150});
//        names.push({no: 19, en: 'Proverbs', key: 'prv', kr: '잠언', ch: 31});
//        names.push({no: 20, en: 'Ecclesiastes', key: 'eccl', kr: '전도서', ch: 12});
//        names.push({no: 21, en: 'Songs', key: 'ssol', kr: '아가', ch: 8});
//
//        names.push({no: 22, en: 'Isaiah', key: 'isa', kr: '이사야', ch: 66});
//        names.push({no: 23, en: 'Jeremiah', key: 'jer', kr: '예레미야', ch: 52});
//        names.push({no: 24, en: 'Lamentations', key: 'lam', kr: '예레미야애가', ch: 5});
//        names.push({no: 25, en: 'Ezekiel', key: 'eze', kr: '에스겔', ch: 48});
//        names.push({no: 26, en: 'Daniel', key: 'dan', kr: '다니엘', ch: 12});
//
//        names.push({no: 27, en: 'Hosea', key: 'hos', kr: '호세아', ch: 14});
//        names.push({no: 28, en: 'Joel', key: 'joel', kr: '요엘', ch: 3});
//        names.push({no: 29, en: 'Amos', key: 'amos', kr: '아모스', ch: 9});
//        names.push({no: 30, en: 'Obadiah', key: 'obad', kr: '오바댜', ch: 1});
//        names.push({no: 31, en: 'Jonah', key: 'jonah', kr: '요나', ch: 4});
//        names.push({no: 32, en: 'Micah', key: 'mic', kr: '미가', ch: 7});
//        names.push({no: 33, en: 'Nahum', key: 'nahum', kr: '나훔', ch: 3});
//        names.push({no: 34, en: 'Habakkuk', key: 'hab', kr: '하박국', ch: 3});
//        names.push({no: 35, en: 'Zephaniah', key: 'zep', kr: '스바냐', ch: 3});
//        names.push({no: 36, en: 'Haggai', key: 'hag', kr: '학개', ch: 2});
//        names.push({no: 37, en: 'Zechariah', key: 'zep', kr: '스가랴', ch: 14});
//        names.push({no: 38, en: 'Malachi', key: 'mal', kr: '말라기', ch: 4});
//
//        names.push({no: 39, en: 'Matthew', key: 'mat', kr: '마태복음', ch: 28});
//        names.push({no: 40, en: 'Mark', key: 'mark', kr: '마가복음', ch: 16});
//        names.push({no: 41, en: 'Luke', key: 'luke', kr: '누가복음', ch: 24});
//        names.push({no: 42, en: 'John', key: 'john', kr: '요한복음', ch: 21});
//        names.push({no: 43, en: 'Acts', key: 'acts', kr: '사도행전', ch: 28});
//        names.push({no: 44, en: 'Romans', key: 'rom', kr: '로마서', ch: 16});
//        names.push({no: 45, en: '1 Corinthians', key: '1cor', kr: '고린도전서', ch: 16});
//        names.push({no: 46, en: '2 Corinthians', key: '2cor', kr: '고린도후서', ch: 13});
//        names.push({no: 47, en: 'Galatians', key: 'gal', kr: '갈라디아서', ch: 6});
//        names.push({no: 48, en: 'Ephesians', key: 'eph', kr: '에베소서', ch: 6});
//        names.push({no: 49, en: 'Philippians', key: 'phi', kr: '빌립보서', ch: 4});
//        names.push({no: 50, en: 'Colossians', key: 'col', kr: '골로새서', ch: 4});
//        names.push({no: 51, en: '1 Thessalonians', key: '1th', kr: '데살로니가전서', ch: 5});
//        names.push({no: 52, en: '2 Thessalonians', key: '2th', kr: '데살로니가후서', ch: 3});
//        names.push({no: 53, en: '1 Timothy', key: '1tim', kr: '디모데전서', ch: 6});
//        names.push({no: 54, en: '2 Timothy', key: '2tim', kr: '디모데후서', ch: 4});
//        names.push({no: 55, en: 'Titus', key: 'titus', kr: '디도서', ch: 3});
//        names.push({no: 56, en: 'Philemon', key: 'phmn', kr: '빌레몬서', ch: 1});
//        names.push({no: 57, en: 'Hebrews', key: 'heb', kr: '히브리서', ch: 13});
//        names.push({no: 58, en: 'James', key: 'jas', kr: '야고보서', ch: 5});
//        names.push({no: 59, en: '1 Peter', key: '1pet', kr: '베드로전서', ch: 5});
//        names.push({no: 60, en: '2 Peter', key: '2pet', kr: '베드로후서', ch: 3});
//        names.push({no: 61, en: '1 John', key: '1jn', kr: '요한1서', ch: 5});
//        names.push({no: 62, en: '2 John', key: '2jn', kr: '요한2서', ch: 1});
//        names.push({no: 63, en: '3 John', key: '3jn', kr: '요한3서', ch: 1});
//        names.push({no: 64, en: 'Jude', key: 'jude', kr: '유다서', ch: 1});
//        names.push({no: 65, en: 'Revelation', key: 'rev', kr: '요한계시록', ch: 22});
//        this.names = names;