package halstead.dto;

public class EstimationProgrammerModel {
    private final double currentRating;
    private final double estimatedBugsCount;

    public EstimationProgrammerModel(double currentRating, double estimatedBugsCount) {
        this.currentRating = currentRating;
        this.estimatedBugsCount = estimatedBugsCount;
    }

    public double getCurrentRating() {
        return currentRating;
    }

    public double getEstimatedBugsCount() {
        return estimatedBugsCount;
    }
}
