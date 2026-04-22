import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class WellnessAnalyzer {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Person> people = new ArrayList<>();

        Scanner file = new Scanner(new File("wellness_data.csv"));
        file.nextLine();

        while (file.hasNextLine()) {
            String line = file.nextLine();
            String[] parts = line.split(",");
            String role = parts[0];
            String name = parts[1];
            int steps = Integer.parseInt(parts[2]);
            double sleep = Double.parseDouble(parts[3]);
            double water = Double.parseDouble(parts[4]);

            if (role.equals("student")) {
                people.add(new Student(name, steps, sleep, water));
            } else if (role.equals("teacher")) {
                people.add(new Teacher(name, steps, sleep, water));
            } else if (role.equals("staff")) {
                people.add(new Staff(name, steps, sleep, water));
            }
        }
        file.close();

        System.out.println("Individual Results:");
        ArrayList<Double> scores = new ArrayList<>();
        for (Person p : people) {
            double score = p.calculateWellnessScore();
            scores.add(score);
            System.out.println(p.getName() + " (" + p.getRole() + "): "
                    + String.format("%.2f", score) + " [" + p.getCategory() + "]");
        }

        double total = 0;
        for (double s : scores) {
            total += s;
        }
        double average = total / scores.size();

        ArrayList<Double> sortedScores = new ArrayList<>(scores);
        Collections.sort(sortedScores);
        double median;
        int n = sortedScores.size();
        if (n % 2 == 1) {
            median = sortedScores.get(n / 2);
        } else {
            median = (sortedScores.get(n / 2 - 1) + sortedScores.get(n / 2)) / 2.0;
        }

        double sumSquares = 0;
        for (double s : scores) {
            sumSquares += (s - average) * (s - average);
        }
        double stdev = Math.sqrt(sumSquares / scores.size());

        double highest = Collections.max(scores);
        double lowest = Collections.min(scores);

        double studentTotal = 0;
        double teacherTotal = 0;
        double staffTotal = 0;
        int studentCount = 0;
        int teacherCount = 0;
        int staffCount = 0;

        for (Person p : people) {
            if (p.getRole().equals("Student")) {
                studentTotal += p.calculateWellnessScore();
                studentCount++;
            } else if (p.getRole().equals("Teacher")) {
                teacherTotal += p.calculateWellnessScore();
                teacherCount++;
            } else if (p.getRole().equals("Staff")) {
                staffTotal += p.calculateWellnessScore();
                staffCount++;
            }
        }

        double studentAvg = studentTotal / studentCount;
        double teacherAvg = teacherTotal / teacherCount;
        double staffAvg = staffTotal / staffCount;

        System.out.println();
        System.out.println("Summary Statistics:");
        System.out.println("Overall average: " + String.format("%.2f", average));
        System.out.println("Median: " + String.format("%.2f", median));
        System.out.println("Standard deviation: " + String.format("%.2f", stdev));
        System.out.println("Highest score: " + String.format("%.2f", highest));
        System.out.println("Lowest score: " + String.format("%.2f", lowest));
        System.out.println("Student average: " + String.format("%.2f", studentAvg));
        System.out.println("Teacher average: " + String.format("%.2f", teacherAvg));
        System.out.println("Staff average: " + String.format("%.2f", staffAvg));

        int excellent = 0;
        int good = 0;
        int needs = 0;
        for (Person p : people) {
            if (p.getCategory().equals("Excellent")) {
                excellent++;
            } else if (p.getCategory().equals("Good")) {
                good++;
            } else {
                needs++;
            }
        }

        System.out.println();
        System.out.println("Category Breakdown:");
        System.out.println("Excellent: " + excellent);
        System.out.println("Good: " + good);
        System.out.println("Needs Improvement: " + needs);

        ArrayList<Person> ranked = new ArrayList<>(people);
        for (int i = 0; i < ranked.size() - 1; i++) {
            for (int j = 0; j < ranked.size() - 1 - i; j++) {
                if (ranked.get(j).calculateWellnessScore() < ranked.get(j + 1).calculateWellnessScore()) {
                    Person temp = ranked.get(j);
                    ranked.set(j, ranked.get(j + 1));
                    ranked.set(j + 1, temp);
                }
            }
        }

        System.out.println();
        System.out.println("Top 3 Participants:");
        for (int i = 0; i < 3 && i < ranked.size(); i++) {
            Person p = ranked.get(i);
            System.out.println((i + 1) + ". " + p.getName() + " (" + p.getRole() + "): "
                    + String.format("%.2f", p.calculateWellnessScore()));
        }
    }
}