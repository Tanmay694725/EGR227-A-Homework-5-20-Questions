// This is a starter file for QuestionsGame.
//
// You should delete this comment and replace it with your class
// header comment.

import java.io.PrintStream;
import java.util.Scanner;

public class QuestionsGame {
    private QuestionNode rootTree; // Questions tree
    private Scanner console;
    public QuestionsGame(Scanner questions) {
        read(questions);
    }

    public QuestionsGame(String initialObject) {
        rootTree = new QuestionNode(initialObject);

    }

    public void read(Scanner input) {
        rootTree = newRootTree(input);
    }
    private QuestionNode newRootTree(Scanner input) {
        if (!input.nextLine().equals("A:")) {
            return new QuestionNode(input.nextLine(), newRootTree(input), newRootTree(input));
        } else {
            return new QuestionNode(input.nextLine());
        }
    }

    private QuestionNode readHelp(Scanner input) {
        return null;
    }

    public void write(PrintStream output) {
        write(output, rootTree);
    }
    private void write(PrintStream output, QuestionNode current) {
        if (current.back != null || current.front != null) {
            output.println("Q:" + current.data);
            write(output, current.back);
            write(output, current.front);
        } else {
            output.println("A:" + current.text);
        }
    }
    public void askQuestions(Scanner console) {
        rootTree = askQuestions(rootTree,console);
    }
    private QuestionNode askQuestions(QuestionNode current,Scanner console) {
        if (current.back != null || current.front != null) {
            if (yesTo(current.data, console)) {
                current.back = askQuestions(current.back,console);
            } else {
                current.front = askQuestions(current.front,console);
            }
        } else {
            if (yesTo("I guess that your object is " + current.data + "!\nAm I right?", console)) {
                System.out.println("Awesome! I win!");
            } else {
                System.out.println("Boo! I Lose.  Please help me get better!");
                System.out.print("What is your object? ");
                String answer = console.nextLine();
                System.out.println("Please give me a yes/no question that distinguishes between " + answer + " and " + current.data + "." );
                System.out.print("Q: ");
                String question = console.nextLine();
                if (yesTo("Is the answer \"yes\" for " + answer +"?", console)) {
                    current = new QuestionNode(question, new QuestionNode(answer), current);
                } else {
                    current = new QuestionNode(question, current, new QuestionNode(answer));
                }
            }
        }
        return current;
    }
    public boolean yesTo(String prompt, Scanner console) {
        System.out.print(prompt + " (y/n)? ");
        String response = console.nextLine().trim().toLowerCase();
        return response.startsWith("y");
    }

    public void play(Scanner console) {
        askQuestions(console);
    }

    public void saveQuestions(PrintStream printStream) {
        if (printStream == null) {
            throw new  IllegalArgumentException();
        }
        saveHelper(rootTree,printStream);

    }
    public void saveHelper(QuestionNode node, PrintStream printStream) {
        if(node == null) {
            return;
        }
        if (node.back == null) {
            printStream.println("A:");
        } else {
            printStream.println("Q:");
        }
        printStream.println(node.data);
        saveHelper(node.back,printStream);
        saveHelper(node.front,printStream);
    }


    private static class QuestionNode {
        public String data; // Stores text at this node
        public QuestionNode back; // Left sub-tree
        public QuestionNode front; // Right sub-tree
        public String text;

        // Constructs a Question Node with text set and the subtrees set to null.
        public QuestionNode(String textSet) {
            this(textSet, null, null);
        }

        // Constructs a Question Node with text set to 'a',
        // the yes subtree set to 'yes' and the no subtree set to 'no'.
        public QuestionNode(String textSet, QuestionNode yesString, QuestionNode noString) {
            data = textSet;
            back = yesString;
            front = noString;
        }
    }
}

