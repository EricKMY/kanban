package domain.usecase.card.editBlocker;

public class EditBlockerOutput {
    private String blockerDescription;

    public void setBlocker(String blocker) {
        this.blockerDescription = blocker;
    }

    public String getBlocker() {
        return blockerDescription;
    }
}
