import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final String END_COMMAND = "END";
    private static final String ADD_COMMAND = "ADD";
    private static final String UPDATE_COMMAND = "UPDATE";
    private static final String DELETE_COMMAND = "DELETE";
    private static final String MARK_IN_PROGRESS_COMMAND = "MARK-IN-PROGRESS";
    private static final String MARK_DONE_COMMAND = "MARK-DONE";
    private static final String LIST_COMMAND = "LIST";

    public static void main(String[] args) throws IOException {

        String command = "";
        Scanner scanner = new Scanner(System.in);

        while (!Objects.equals(command = scanner.nextLine(), END_COMMAND)) {
            TaskTrackerService taskTrackerService = new TaskTrackerService();
            String[] commandInputArray = command.split(" ");
            String commandType = commandInputArray[0];
            int taskId;
            String taskName, taskStatus;
            switch (commandType) {
                case ADD_COMMAND:
                    taskName = Arrays
                            .stream(commandInputArray, 1, commandInputArray.length)
                            .collect(Collectors.joining(" "));
                    taskTrackerService.add(taskName);
                    break;
                case UPDATE_COMMAND:
                    taskId = Integer.parseInt(commandInputArray[1]);
                    taskName = Arrays
                            .stream(commandInputArray, 1, commandInputArray.length)
                            .collect(Collectors.joining(" "));
                    taskTrackerService.update(taskId, taskName);
                    break;
                case DELETE_COMMAND:
                    taskId = Integer.parseInt(commandInputArray[1]);
                    taskTrackerService.delete(taskId);
                    break;
                case MARK_IN_PROGRESS_COMMAND:
                    taskId = Integer.parseInt(commandInputArray[1]);
                    taskTrackerService.markInProgress(taskId);
                    break;
                case MARK_DONE_COMMAND:
                    taskId = Integer.parseInt(commandInputArray[1]);
                    taskTrackerService.markDone(taskId);
                    break;
                case LIST_COMMAND:
                    if (commandInputArray.length > 1) {
                        taskStatus = commandInputArray[1];
                        taskTrackerService.list(taskStatus);
                    } else {
                        taskTrackerService.list();
                    }
                    break;
            }
        }
    }
}