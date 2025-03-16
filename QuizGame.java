import java.util.*;

class QuizGame {
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();
    private static List<Question> questions = new ArrayList<>();

    // Colors to make the game look nice
    private static String RESET = "\u001B[0m";
    private static String GREEN = "\u001B[32m";
    private static String RED = "\u001B[31m";
    private static String BLUE = "\u001B[34m";
    private static String YELLOW = "\u001B[33m";

    private static int score = 0;
    private static int highScore = 0;
    private static boolean usedLifeline = false;

    public static void main(String[] args) {
        loadQuestions(); // Load all our fun questions
        boolean wantToPlayAgain;

        do {
            // Reset game stats
            score = 0;
            usedLifeline = false;
            List<Question> gamePack = pickRandomQuestions(10);

            // Welcome message
            System.out.println(BLUE + "\nHey there! Welcome to our awesome Quiz Game!" + RESET);
            System.out.println(YELLOW + "Here's how to play:");
            System.out.println("1. You'll get 10 random questions");
            System.out.println("2. Think fast! You have 10 seconds per question");
            System.out.println("3. Stuck? Type 'lifeline' for a 50-50 chance (but you only get once!)\n" + RESET);

            startQuiz(gamePack);
            showScore();

            // Ask to play again
            System.out.print("\nHad fun? Want another round? (yes/no): ");
            wantToPlayAgain = scanner.next().toLowerCase().startsWith("y");
        } while (wantToPlayAgain);

        System.out.println(GREEN + "\nThanks for playing with us! See you next time! 👋" + RESET);
    }

    private static void startQuiz(List<Question> gamePack) {
        for (int i = 0; i < gamePack.size(); i++) {
            Question currentQ = gamePack.get(i);
            System.out.println("\n" + BLUE + "Question " + (i + 1) + ": " + currentQ.getQuestion() + RESET);

            // Mix up the options
            List<String> mixedOptions = new ArrayList<>(currentQ.getOptions());
            Collections.shuffle(mixedOptions);

            // Show options
            for (int j = 0; j < mixedOptions.size(); j++) {
                System.out.println((j + 1) + ") " + mixedOptions.get(j));
            }

            // Timer stuff
            long startTime = System.currentTimeMillis();
            String answer = "";
            boolean ranOutOfTime = false;

            while (true) {
                if (System.currentTimeMillis() - startTime > 10000) {
                    System.out.println(RED + "Oops! Time's up! 🕒" + RESET);
                    ranOutOfTime = true;
                    break;
                }
                if (scanner.hasNext()) {
                    answer = scanner.next();
                    if (answer.equalsIgnoreCase("lifeline") && !usedLifeline) {
                        help5050(mixedOptions, currentQ.getCorrectAnswer());
                        usedLifeline = true;
                        continue;
                    }
                    break;
                }
            }

            if (ranOutOfTime) continue;

            try {
                int choice = Integer.parseInt(answer);
                if (mixedOptions.get(choice - 1).equals(currentQ.getCorrectAnswer())) {
                    System.out.println(GREEN + "Awesome! You got it right! 🎉 (+10 points)" + RESET);
                    score += 10;
                } else {
                    System.out.println(RED + "Oops! Not quite. The right answer was: " + currentQ.getCorrectAnswer() + " 😅" + RESET);
                }
            } catch (Exception e) {
                System.out.println(RED + "Hmm, that's not a valid answer! Let's skip this one. 🤔" + RESET);
            }
        }
    }

    private static void help5050(List<String> options, String rightAnswer) {
        System.out.println(YELLOW + "\n💡 50-50 Help coming up! Let's remove two wrong answers...\n" + RESET);
        List<String> wrongOnes = new ArrayList<>(options);
        wrongOnes.remove(rightAnswer);

        while (wrongOnes.size() > 1) {
            wrongOnes.remove(random.nextInt(wrongOnes.size()));
        }

        options.clear();
        options.add(rightAnswer);
        options.addAll(wrongOnes);
        Collections.shuffle(options);

        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ") " + options.get(i));
        }
    }

    private static void showScore() {
        System.out.println(GREEN + "\nYour Score: " + score + " points! 🎯" + RESET);
        if (score > highScore) {
            highScore = score;
            System.out.println(GREEN + "WOW! You've set a new high score! 🏆" + RESET);
        }
        System.out.println(YELLOW + "Best Score Ever: " + highScore + " 👑" + RESET);
    }

    private static List<Question> pickRandomQuestions(int howMany) {
        List<Question> selectedOnes = new ArrayList<>(questions);
        Collections.shuffle(selectedOnes);
        return selectedOnes.subList(0, howMany);
    }

    private static void loadQuestions() {
        // Fun trivia questions!
        questions.add(new Question("What is the capital of France?", "Paris", "London", "Rome", "Berlin"));
        questions.add(new Question("Who developed Java?", "James Gosling", "Dennis Ritchie", "Bjarne Stroustrup", "Guido van Rossum"));
        questions.add(new Question("Which planet is known as the Red Planet?", "Mars", "Earth", "Jupiter", "Saturn"));
        questions.add(new Question("What is the output of: System.out.println(2 + \"2\");", "22", "4", "Error", "NaN"));
        questions.add(new Question("What does HTML stand for?", "Hyper Text Markup Language", "High Tech Modern Language", "Hyperlink and Text Markup Language", "Home Tool Markup Language"));
        questions.add(new Question("Which data structure follows LIFO?", "Stack", "Queue", "Array", "Linked List"));
        questions.add(new Question("Who wrote 'Harry Potter'?", "J.K. Rowling", "J.R.R. Tolkien", "Stephen King", "George R.R. Martin"));
        questions.add(new Question("What does 'RAM' stand for?", "Random Access Memory", "Read And Modify", "Rapid Action Module", "Run Access Mode"));
        questions.add(new Question("Which country invented pizza?", "Italy", "France", "USA", "Germany"));
        questions.add(new Question("Who was the first President of the USA?", "George Washington", "Abraham Lincoln", "Thomas Jefferson", "John Adams"));
        questions.add(new Question("What does CPU stand for?", "Central Processing Unit", "Computer Power Unit", "Central Programming Unit", "Core Processing Unit"));
        questions.add(new Question("Which ocean is the largest?", "Pacific Ocean", "Atlantic Ocean", "Indian Ocean", "Arctic Ocean"));
        questions.add(new Question("What is the national animal of India?", "Bengal Tiger", "Lion", "Elephant", "Leopard"));
        questions.add(new Question("What is the chemical symbol for Gold?", "Au", "Ag", "Gd", "Go"));
        questions.add(new Question("Which gas do plants absorb?", "Carbon Dioxide", "Oxygen", "Nitrogen", "Hydrogen"));
        questions.add(new Question("Who painted the Mona Lisa?", "Leonardo da Vinci", "Pablo Picasso", "Vincent van Gogh", "Claude Monet"));
        questions.add(new Question("Which is the largest bone in the human body?", "Femur", "Humerus", "Tibia", "Skull"));
        questions.add(new Question("Who discovered gravity?", "Isaac Newton", "Albert Einstein", "Galileo Galilei", "Nikola Tesla"));
        questions.add(new Question("Which is the longest river in the world?", "Nile", "Amazon", "Yangtze", "Mississippi"));
        questions.add(new Question("What does the 'C' in C++ stand for?", "C language", "Code", "Computer", "Command"));
        questions.add(new Question("Which Indian city is known as the 'Pink City'?", "Jaipur", "Jodhpur", "Udaipur", "Bikaner"));
        questions.add(new Question("Which company created Python?", "Guido van Rossum", "Microsoft", "Sun Microsystems", "Google"));
        questions.add(new Question("Which cricketer has the highest number of international centuries?", "Sachin Tendulkar", "Virat Kohli", "Ricky Ponting", "Brian Lara"));
        questions.add(new Question("What is the SI unit of force?", "Newton", "Joule", "Watt", "Pascal"));
        questions.add(new Question("Which programming language is used for Android app development?", "Java", "Swift", "C#", "Kotlin"));
        questions.add(new Question("Who is the CEO of Tesla?", "Elon Musk", "Jeff Bezos", "Bill Gates", "Sundar Pichai"));
        questions.add(new Question("Which continent has the most countries?", "Africa", "Asia", "Europe", "South America"));
        questions.add(new Question("What is the currency of Japan?", "Yen", "Dollar", "Rupee", "Euro"));
        questions.add(new Question("Which planet is known as the 'Gas Giant'?", "Jupiter", "Mars", "Venus", "Uranus"));
        questions.add(new Question("Which chess piece can only move diagonally?", "Bishop", "Knight", "Rook", "Queen"));
        questions.add(new Question("What does CSS stand for?", "Cascading Style Sheets", "Creative Style Sheets", "Computer Styling System", "Custom Style Syntax"));
        questions.add(new Question("Who invented the telephone?", "Alexander Graham Bell", "Thomas Edison", "Nikola Tesla", "James Watt"));
        questions.add(new Question("Which Indian state is known as the 'Spice Garden'?", "Kerala", "Tamil Nadu", "Andhra Pradesh", "West Bengal"));
        questions.add(new Question("How many bits are in a byte?", "8", "16", "32", "64"));
        questions.add(new Question("Which is the smallest planet in the solar system?", "Mercury", "Pluto", "Mars", "Venus"));
        questions.add(new Question("Which Olympic sport involves vault, beam, and floor exercises?", "Gymnastics", "Diving", "Athletics", "Fencing"));
        questions.add(new Question("Which programming language is known as the 'mother of all languages'?", "C", "Assembly", "Pascal", "Fortran"));
        questions.add(new Question("Which Hollywood movie features a robot named 'Wall-E'?", "Wall-E", "Transformers", "I, Robot", "Big Hero 6"));
        questions.add(new Question("Which gas is most abundant in Earth's atmosphere?", "Nitrogen", "Oxygen", "Carbon Dioxide", "Argon"));
        questions.add(new Question("Which is the world's fastest land animal?", "Cheetah", "Leopard", "Tiger", "Lion"));
        questions.add(new Question("Who wrote the famous book '1984'?", "George Orwell", "J.K. Rowling", "Mark Twain", "Charles Dickens"));
        questions.add(new Question("Which is the only even prime number?", "2", "4", "6", "8"));
        questions.add(new Question("Who is the founder of Facebook?", "Mark Zuckerberg", "Elon Musk", "Steve Jobs", "Larry Page"));
        questions.add(new Question("What does HTTP stand for?", "Hypertext Transfer Protocol", "High Tech Text Processing", "Hyperlink Transmission Protocol", "Hyper Tech Transfer Protocol"));
        questions.add(new Question("Which car company produces the Mustang?", "Ford", "Chevrolet", "BMW", "Ferrari"));
        questions.add(new Question("Who was the first man to walk on the moon?", "Neil Armstrong", "Buzz Aldrin", "Yuri Gagarin", "Michael Collins"));
        questions.add(new Question("Which Indian festival is known as the 'Festival of Lights'?", "Diwali", "Holi", "Navratri", "Eid"));
        questions.add(new Question("Which superhero is known as the 'Dark Knight'?", "Batman", "Superman", "Iron Man", "Spider-Man"));
        questions.add(new Question("Which bird is the symbol of peace?", "Dove", "Eagle", "Owl", "Sparrow"));
    }
}

class Question {
    private String question;
    private String correctAnswer;
    private List<String> options;

    public Question(String question, String correctAnswer, String... wrongOptions) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.options = new ArrayList<>();
        this.options.add(correctAnswer);
        this.options.addAll(Arrays.asList(wrongOptions));
    }

    public String getQuestion() { return question; }
    public String getCorrectAnswer() { return correctAnswer; }
    public List<String> getOptions() { return options; }
}