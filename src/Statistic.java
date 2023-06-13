public class Statistic {
    private int saved;
    private int total;
    private double ratio;
    public Statistic() {
        this.saved = 0;
        this.total = 0;
        this.ratio = 0.0;
    }

    public void updateRatio() {
        ratio = ((double) saved) / total;
    }

    public void merge(Statistic that) {
        this.saved = this.saved + that.getSaved();
        this.total = this.total + that.getTotal();
        updateRatio();
    }

    public int getSaved() {
        return this.saved;
    }

    public int getTotal() {
        return this.total;
    }

    public double getRatio() {
        return this.ratio;
    }

    public void incrementSaved() {
        this.saved++;
        this.total++;
        updateRatio();
    }
    public void incrementTotal() {
        this.total++;
        updateRatio();
    }
}
