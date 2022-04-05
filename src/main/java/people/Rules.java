package people;

public class Rules {
    public AppSettings settings;
    private boolean test;

    public Rules() {
        this.settings = AppSettings.get_instance();
    }

    public Rules(boolean test) {
        this();
        this.test = test;
    }

    public double calculateProbabilityOfInfection(int time) {
        return 1 - (1 / Math.sqrt(time));
    }

    public boolean canChangeStatusToExposed(Person person) {
        if(person.status != PersonStatus.Susceptible)
            return false;
        // TODO: obliczyc prawdopodobienstwa dla sasiadow
        return true;
    }
    public boolean canChangeStatusToInfected(Person person) {
        if(person.status != PersonStatus.Exposed)
            return false;

        return person.lastInfectionTime +
                this.settings.Params.getInfectionStartDuration() >= settings.CurrentTime &&
                this.settings.random.nextDouble() <= calculateProbabilityOfInfection(person.exposedTime());
    }
    public boolean canChangeStatusToRecovery(Person person) {
        if(person.status != PersonStatus.Infected)
            return false;

        return person.lastInfectionTime +
                this.settings.Params.getInfectionStartDuration() +
                this.settings.Params.getInfectionDuration() <= settings.CurrentTime;
    }
    public boolean canChangeStatusToSusceptible(Person person) {
        if(person.status != PersonStatus.Recovered)
            return false;

        return person.lastInfectionTime +
                this.settings.Params.getInfectionStartDuration() +
                this.settings.Params.getInfectionDuration() +
                this.settings.Params.getRecoveryDuration() <= settings.CurrentTime;
    }
}
