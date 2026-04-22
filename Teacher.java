public class Teacher extends Person {
    public Teacher(String name, int steps, double sleep, double water) {
        super(name, steps, sleep, water);
    }

    @Override
    public double calculateWellnessScore() {
        return 0.3 * (getSteps() / 1000.0)
                + 0.5 * getSleep()
                + 2.5 * getWater();
    }

    @Override
    public String getRole() {
        return "Teacher";
    }
}