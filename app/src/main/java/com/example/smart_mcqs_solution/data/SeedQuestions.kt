package com.example.smart_mcqs_solution.data

import com.example.smart_mcqs_solution.data.model.Question

// HARD-CODING THESE JUST FOR THE TASK -> CAN BE MADE DYNAMIC LATER
fun fetchQuestions(): List<Question> {
    return listOf(
        Question(
            questionText = "Which event sparked the official beginning of World War II in Europe?",
            options = listOf("The assassination of Archduke Franz Ferdinand", "The invasion of Poland", "The bombing of Pearl Harbor", "The signing of the Treaty of Versailles"),
            correctAnswer = "The invasion of Poland"
        ),
        Question(
            questionText = "In which year did the United States officially enter World War II?",
            options = listOf("1939", "1940", "1941", "1942"),
            correctAnswer = "1941"
        ),
        Question(
            questionText = "What was the code name for the Allied invasion of Normandy on June 6, 1944?",
            options = listOf("Operation Barbarossa", "Operation Overlord", "Operation Torch", "Operation Sea Lion"),
            correctAnswer = "Operation Overlord"
        ),
        Question(
            questionText = "Who was the Prime Minister of Great Britain during most of World War II?",
            options = listOf("Neville Chamberlain", "Clement Attlee", "Winston Churchill", "Anthony Eden"),
            correctAnswer = "Winston Churchill"
        ),
        Question(
            questionText = "Which battle is widely considered the major turning point on the Eastern Front, ending German advances into the Soviet Union?",
            options = listOf("The Battle of Stalingrad", "The Battle of the Bulge", "The Battle of Berlin", "The Battle of Kursk"),
            correctAnswer = "The Battle of Stalingrad"
        ),
        Question(
            questionText = "What military strategy did Germany use to quickly overwhelm Poland and France?",
            options = listOf("Trench Warfare", "Blitzkrieg", "Island Hopping", "Attrition Warfare"),
            correctAnswer = "Blitzkrieg"
        ),
        Question(
            questionText = "Which Japanese city was the first to be struck by an atomic bomb in August 1945?",
            options = listOf("Nagasaki", "Tokyo", "Hiroshima", "Kyoto"),
            correctAnswer = "Hiroshima"
        ),
        Question(
            questionText = "What was the name of the research project that developed the atomic bomb for the United States?",
            options = listOf("The Manhattan Project", "The Apollo Program", "The Manhattan Plan", "Project Trinity"),
            correctAnswer = "The Manhattan Project"
        ),
        Question(
            questionText = "Which conference in 1945 saw Churchill, Roosevelt, and Stalin discuss the post-war reorganization of Europe?",
            options = listOf("The Potsdam Conference", "The Yalta Conference", "The Tehran Conference", "The Casablanca Conference"),
            correctAnswer = "The Yalta Conference"
        ),
        Question(
            questionText = "The Battle of Midway was a decisive naval battle fought primarily between which two nations?",
            options = listOf("United States and Germany", "Great Britain and Japan", "United States and Japan", "Soviet Union and Japan"),
            correctAnswer = "United States and Japan"
        ),
        Question(
            questionText = "Who was the supreme commander of the Allied Expeditionary Forces in Europe?",
            options = listOf("Douglas MacArthur", "George S. Patton", "Bernard Montgomery", "Dwight D. Eisenhower"),
            correctAnswer = "Dwight D. Eisenhower"
        ),
        Question(
            questionText = "Which country signed a non-aggression pact with Germany in 1939, which was later broken in 1941?",
            options = listOf("The Soviet Union", "Italy", "Japan", "France"),
            correctAnswer = "The Soviet Union"
        ),
        Question(
            questionText = "What was the name of the German air force that engaged the British RAF during the Battle of Britain?",
            options = listOf("Wehrmacht", "Luftwaffe", "Kriegsmarine", "Bundeswehr"),
            correctAnswer = "Luftwaffe"
        ),
        Question(
            questionText = "Which Axis power was the first to surrender to the Allies?",
            options = listOf("Germany", "Japan", "Italy", "Hungary"),
            correctAnswer = "Italy"
        ),
        Question(
            questionText = "What international peacekeeping organization was created immediately following the end of World War II to replace the League of Nations?",
            options = listOf("NATO", "The Warsaw Pact", "The United Nations", "The European Union"),
            correctAnswer = "The United Nations"
        ),
        Question(
            questionText = "What was the name of the system of fortifications built by France along its border with Germany before WWII?",
            options = listOf("The Siegfried Line", "The Maginot Line", "The Hindenburg Line", "The Atlantic Wall"),
            correctAnswer = "The Maginot Line"
        ),
        Question(
            questionText = "Which battle fought in late 1944 was Germany's last major offensive campaign on the Western Front?",
            options = listOf("The Battle of the Bulge", "The Battle of Dunkirk", "The Battle of Somme", "The Battle of Verdun"),
            correctAnswer = "The Battle of the Bulge"
        ),
        Question(
            questionText = "Who was known as the 'Desert Fox' due to his tactical leadership of the German Afrika Korps?",
            options = listOf("Heinz Guderian", "Erwin Rommel", "Hermann Göring", "Karl Dönitz"),
            correctAnswer = "Erwin Rommel"
        ),
        Question(
            questionText = "What was the code name for the evacuation of over 330,000 Allied soldiers from the beaches of France in 1940?",
            options = listOf("Operation Dynamo", "Operation Market Garden", "Operation Sea Lion", "Operation Valkyrie"),
            correctAnswer = "Operation Dynamo"
        ),
        Question(
            questionText = "Which island was the site of a fierce 1945 Pacific battle where U.S. Marines famously raised the American flag on Mount Suribachi?",
            options = listOf("Okinawa", "Guadalcanal", "Iwo Jima", "Saipan"),
            correctAnswer = "Iwo Jima"
        ),
        Question(
            questionText = "What was the primary purpose of the Nuremberg Trials held after the war?",
            options = listOf("To redraw European borders", "To prosecute major Nazi war criminals", "To establish the United Nations", "To negotiate the surrender of Japan"),
            correctAnswer = "To prosecute major Nazi war criminals"
        ),
        Question(
            questionText = "Which treaty, signed at the end of WWI, created harsh economic conditions in Germany that contributed to the rise of WWII?",
            options = listOf("The Treaty of Versailles", "The Treaty of Paris", "The Treaty of Utrecht", "The Brest-Litovsk Treaty"),
            correctAnswer = "The Treaty of Versailles"
        ),
        Question(
            questionText = "What was the code name for Germany's massive, ill-fated invasion of the Soviet Union in June 1941?",
            options = listOf("Operation Barbarossa", "Operation Citadel", "Operation Reinhard", "Operation Winter Storm"),
            correctAnswer = "Operation Barbarossa"
        ),
        Question(
            questionText = "Who was the Emperor of Japan during World War II?",
            options = listOf("Hirohito", "Mutsuhito", "Akihito", "Hideki Tojo"),
            correctAnswer = "Hirohito"
        ),
        Question(
            questionText = "Which famous female pilot organization flew military aircraft within the United States to free up male pilots for combat?",
            options = listOf("The Night Witches", "The WAVES", "The WASPs", "The WACs"),
            correctAnswer = "The WASPs"
        ),
        Question(
            questionText = "What was the name of the German encryption machine cracked by Allied codebreakers at Bletchley Park?",
            options = listOf("The Lorenz Cipher", "The Enigma Machine", "The Purple Machine", "The Hebern Rotor"),
            correctAnswer = "The Enigma Machine"
        ),
        Question(
            questionText = "Which major Allied country fell to German forces in June 1940, leaving Britain to fight largely alone in Western Europe?",
            options = listOf("Belgium", "Poland", "France", "The Netherlands"),
            correctAnswer = "France"
        ),
        Question(
            questionText = "The Tripartite Pact of 1940 formally established the Alliance between which three nations?",
            options = listOf("Germany, Italy, and Japan", "Great Britain, USA, and USSR", "Germany, USSR, and Japan", "France, Great Britain, and USA"),
            correctAnswer = "Germany, Italy, and Japan"
        ),
        Question(
            questionText = "What strategy did Allied forces use in the Pacific theater to bypass heavily fortified Japanese islands and capture strategic ones closer to Japan?",
            options = listOf("Blitzkrieg", "Island Hopping", "Trench Warfare", "Total War"),
            correctAnswer = "Island Hopping"
        ),
        Question(
            questionText = "In which month and year did Germany officially surrender, marking the end of WWII in Europe (V-E Day)?",
            options = listOf("September 1945", "May 1945", "November 1944", "June 1944"),
            correctAnswer = "May 1945"
        ),
        Question(
            questionText = "What is the largest planet in our solar system?",
            options = listOf("Earth", "Mars", "Jupiter", "Saturn"),
            correctAnswer = "Jupiter"
        ),
        Question(
            questionText = "Which element has the chemical symbol 'O'?",
            options = listOf("Gold", "Oxygen", "Osmium", "Silver"),
            correctAnswer = "Oxygen"
        ),
        Question(
            questionText = "Which continent is known as the 'Dark Continent'?",
            options = listOf("Asia", "Africa", "South America", "Australia"),
            correctAnswer = "Africa"
        ),
        Question(
            questionText = "Who painted the Mona Lisa?",
            options = listOf("Vincent van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Claude Monet"),
            correctAnswer = "Leonardo da Vinci"
        ),
        Question(
            questionText = "What is the capital city of Japan?",
            options = listOf("Seoul", "Beijing", "Tokyo", "Bangkok"),
            correctAnswer = "Tokyo"
        ),
        Question(
            questionText = "Which gas do plants absorb from the atmosphere for photosynthesis?",
            options = listOf("Oxygen", "Nitrogen", "Carbon Dioxide", "Hydrogen"),
            correctAnswer = "Carbon Dioxide"
        ),
        Question(
            questionText = "What is the hardest natural substance on Earth?",
            options = listOf("Gold", "Iron", "Diamond", "Quartz"),
            correctAnswer = "Diamond"
        ),
        Question(
            questionText = "Which ocean is the largest on Earth?",
            options = listOf("Atlantic Ocean", "Indian Ocean", "Arctic Ocean", "Pacific Ocean"),
            correctAnswer = "Pacific Ocean"
        ),
        Question(
            questionText = "How many bones are there in an adult human body?",
            options = listOf("206", "210", "198", "250"),
            correctAnswer = "206"
        ),
        Question(
            questionText = "Which language has the most native speakers in the world?",
            options = listOf("English", "Spanish", "Mandarin Chinese", "Hindi"),
            correctAnswer = "Mandarin Chinese"
        ),
        Question(
            questionText = "What is the largest mammal in the world?",
            options = listOf("African Elephant", "Blue Whale", "Giraffe", "Hippopotamus"),
            correctAnswer = "Blue Whale"
        ),
        Question(
            questionText = "Which famous scientist developed the theory of general relativity?",
            options = listOf("Isaac Newton", "Albert Einstein", "Nikola Tesla", "Galileo Galilei"),
            correctAnswer = "Albert Einstein"
        ),
        Question(
            questionText = "What is the smallest country in the world by land area?",
            options = listOf("Monaco", "Malta", "Vatican City", "San Marino"),
            correctAnswer = "Vatican City"
        ),
        Question(
            questionText = "Which river is the longest in the world?",
            options = listOf("Amazon", "Nile", "Yangtze", "Mississippi"),
            correctAnswer = "Nile"
        ),
        Question(
            questionText = "What is the primary currency of the United Kingdom?",
            options = listOf("Dollar", "Euro", "Pound Sterling", "Yen"),
            correctAnswer = "Pound Sterling"
        ),
        Question(
            questionText = "Which musical instrument has eighty-eight keys?",
            options = listOf("Organ", "Piano", "Accordion", "Harpsichord"),
            correctAnswer = "Piano"
        ),
        Question(
            questionText = "In computer science, what does 'CPU' stand for?",
            options = listOf("Central Process Unit", "Core Programming Utility", "Central Processing Unit", "Computer Personal Unit"),
            correctAnswer = "Central Processing Unit"
        ),
        Question(
            questionText = "Which planet is known as the 'Red Planet'?",
            options = listOf("Venus", "Mars", "Jupiter", "Mercury"),
            correctAnswer = "Mars"
        ),
        Question(
            questionText = "What is the boiling point of water at sea level in Celsius?",
            options = listOf("90°C", "100°C", "110°C", "95°C"),
            correctAnswer = "100°C"
        ),
        Question(
            questionText = "Which country gifted the Statue of Liberty to the United States?",
            options = listOf("United Kingdom", "France", "Spain", "Germany"),
            correctAnswer = "France"
        ),
        Question(
            questionText = "Who wrote the play 'Romeo and Juliet'?",
            options = listOf("Charles Dickens", "William Shakespeare", "Mark Twain", "Jane Austen"),
            correctAnswer = "William Shakespeare"
        ),
        Question(
            questionText = "Which vitamin is produced when a person is exposed to sunlight?",
            options = listOf("Vitamin A", "Vitamin C", "Vitamin D", "Vitamin K"),
            correctAnswer = "Vitamin D"
        ),
        Question(
            questionText = "What is the capital of Australia?",
            options = listOf("Sydney", "Melbourne", "Canberra", "Perth"),
            correctAnswer = "Canberra"
        ),
        Question(
            questionText = "Which animal is known as the 'Ship of the Desert'?",
            options = listOf("Horse", "Camel", "Donkey", "Llama"),
            correctAnswer = "Camel"
        ),
        Question(
            questionText = "What is the main ingredient in guacamole?",
            options = listOf("Tomato", "Avocado", "Onion", "Lime"),
            correctAnswer = "Avocado"
        ),
        Question(
            questionText = "Which metal is liquid at room temperature?",
            options = listOf("Mercury", "Lead", "Silver", "Aluminum"),
            correctAnswer = "Mercury"
        ),
        Question(
            questionText = "What is the largest desert in the world?",
            options = listOf("Sahara", "Gobi", "Antarctic Desert", "Arabian Desert"),
            correctAnswer = "Antarctic Desert"
        ),
        Question(
            questionText = "Which mythical creature is said to rise from its own ashes?",
            options = listOf("Dragon", "Griffin", "Phoenix", "Unicorn"),
            correctAnswer = "Phoenix"
        ),
        Question(
            questionText = "What is the speed of light approximately?",
            options = listOf("300,000 km/s", "150,000 km/s", "500,000 km/s", "1,000,000 km/s"),
            correctAnswer = "300,000 km/s"
        ),
        Question(
            questionText = "Which geometric shape has five sides?",
            options = listOf("Hexagon", "Octagon", "Pentagon", "Heptagon"),
            correctAnswer = "Pentagon"
        )
    )
}