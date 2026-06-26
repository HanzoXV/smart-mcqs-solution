# Smart MCQs App

This is a simple app that allows you to choose the correct answers to fun quiz questions. It helps you test your knowledge, track your daily progress, and see your best scores as you learn and improve.

---

## Overview of the Dashboard

The dashboard features 4 cards, displaying:
* Total Score Accuracy
* Last Score
* Highest Score
* Quizzes Attempted
* Line Graph (setup using Compose Canvas) that plots score over time, showing progression
* Features a start quiz button that starts a quiz

---

## Implemented Features

* **Progress Tracking** -> Visualize your learning journey with a clean, interactive line graph.
* **Smart Learning** -> Uses a Spaced Repetition algorithm to boost your long-term retention.
* **Clean MVVM Architecture** -> ensuring the code is maintainable and scalable.
* **Performance History** -> Stores your quiz results locally using Room Database, allowing you to review past performance.
* **Answer Review** -> Get instant feedback and detailed insights after every quiz submission.

---

## How does Spaced Repetition Algorithm Work?

### The Concept
Spaced repetition is a learning technique built around one idea: **the harder you find something, the sooner you should see it again.**

To put it simply, imagine the questions as a queue. Every time a question is answered correctly, it is pushed way back into the queue so it does not come back any time soon. Similarly, if you fail to answer a question correctly, it needs to be ahead in the queue so it can be shown sooner.

This is how apps like **Duolingo** handle this.

---

### How It's Implemented Here

Every question in the database stores two extra fields:

* **intervalDays** — how many days until this question should appear again -> Increases on each correct attempt
* **nextReviewDate** — the exact timestamp of when the question is next due, stored as a Long to simplify date parsing/calculations.

-> When someone gets a question right, interval days are multiplied by 2.5. (Why 2.5, because it is the researched "ease factor", basically suggesting the best time to recall things). **Next Review Date** is set as current date + interval days.

-> When someone gets a question wrong, interval days are set to 1. **Next Review Date** is set to 1 day after the attempt. 

-> What if you start a quiz and there are no questions at all because every question's review date is greater than current time?
* **Option 1** was to show a message asking the user to return later.
* **Option 2** was to randomize the questions after sorting the list based on review date so to keep the questions coming.

I went with option 2.
