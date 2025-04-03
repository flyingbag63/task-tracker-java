import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.concurrent.Task;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class TaskTrackerService {
    private final String TASK_DIRECTORY_JSON_PATH = "src/tasks.json";

    public void add(String taskName) throws IOException, RuntimeException {
        List<TaskDTO> taskDTOList = this.getTasks();
        Integer taskId = this.getTaskId(taskDTOList);
        taskId++;
        this.validateTaskNamePresence(taskDTOList, taskName);
        taskDTOList.add(new TaskDTO(taskId, taskName, TaskStatus.TODO.toString()));
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(this.TASK_DIRECTORY_JSON_PATH), taskDTOList);
    }

    private Integer getTaskId(List<TaskDTO> taskDTOList) {
        return taskDTOList.stream().mapToInt(TaskDTO::getId).max().orElse(0);
    }

    public void update(Integer taskId, String taskName) throws IOException, RuntimeException {
        List<TaskDTO> taskDTOList = this.getTasks();
        Integer finalTaskId = taskId;
        TaskDTO task = taskDTOList.stream()
                .filter(taskDTO -> Objects.equals(taskDTO.getId(), finalTaskId))
                .findFirst().orElseThrow(RuntimeException::new);
        task.setName(taskName);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(this.TASK_DIRECTORY_JSON_PATH), taskDTOList);
    }

    public void delete(Integer taskId) throws IOException, RuntimeException {
        List<TaskDTO> taskDTOList = this.getTasks();
        Integer finalTaskId = taskId;
        TaskDTO task = taskDTOList.stream()
                .filter(taskDTO -> Objects.equals(taskDTO.getId(), finalTaskId))
                .findFirst().orElseThrow(RuntimeException::new);
        taskDTOList.remove(task);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(this.TASK_DIRECTORY_JSON_PATH), taskDTOList);
    }

    public void markInProgress(Integer taskId) throws IOException, RuntimeException {
        List<TaskDTO> taskDTOList = this.getTasks();
        Integer finalTaskId = taskId;
        TaskDTO task = taskDTOList.stream()
                .filter(taskDTO -> Objects.equals(taskDTO.getId(), finalTaskId))
                .findFirst().orElseThrow(RuntimeException::new);
        task.setStatus(TaskStatus.IN_PROGRESS.toString());
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(this.TASK_DIRECTORY_JSON_PATH), taskDTOList);
    }

    public void markDone(Integer taskId) throws IOException, RuntimeException {
        List<TaskDTO> taskDTOList = this.getTasks();
        Integer finalTaskId = taskId;
        TaskDTO task = taskDTOList.stream()
                .filter(taskDTO -> Objects.equals(taskDTO.getId(), finalTaskId))
                .findFirst().orElseThrow(RuntimeException::new);
        task.setStatus(TaskStatus.COMPLETED.toString());
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(this.TASK_DIRECTORY_JSON_PATH), taskDTOList);
    }

    public void list() throws IOException, RuntimeException {
        List<TaskDTO> taskDTOList = this.getTasks();
        System.out.println(taskDTOList);
    }

    public void list(String taskStatus) throws IOException, RuntimeException {
        List<TaskDTO> taskDTOList = this.getTasks();
        List<TaskDTO> filteredTasks = taskDTOList.stream()
                .filter(taskDTO -> Objects.equals(taskDTO.getStatus(), taskStatus))
                .collect(Collectors.toList());
        System.out.println(filteredTasks);
    }

    private List<TaskDTO> getTasks() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return new ArrayList<>(Arrays.asList(
                mapper.readValue(new File(this.TASK_DIRECTORY_JSON_PATH), TaskDTO[].class))
        );
    }

    private void validateTaskNamePresence(List<TaskDTO> taskDTOList, String taskName) throws RuntimeException {
        Optional<TaskDTO> existingTaskDTO = taskDTOList.stream()
                .filter(taskDTO -> Objects.equals(taskDTO.getName(), taskName))
                .findFirst();

        if (existingTaskDTO.isPresent()) {
            throw new RuntimeException("TASK_ALREADY_PRESENT_WITH_NAME::" + taskName);
        }
    }
}
