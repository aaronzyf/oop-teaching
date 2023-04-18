package theater.bo;

public class Menu {
    private final String actionName;
    private final Operation operation;

    public Menu(String actionName, Operation operation) {
        this.actionName = actionName;
        this.operation = operation;
    }


    public String getActionName() {
        return actionName;
    }

    public Operation getOperation() {
        return operation;
    }


    public interface Operation {
        void execute();
    }
}
