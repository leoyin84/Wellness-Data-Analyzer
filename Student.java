public class Student extends Person {
    public Student(String name, int steps, double sleep, double water) {
        super(name, steps, sleep, water);
    }

    @Override
    public double calculateWellnessScore() {
        return 0.4 * (getSteps() / 1000.0)
                + 0.4 * getSleep()
                + 2 * getWater();
    }

    @Override
    public String getRole() {
        return "Student";
    }
}