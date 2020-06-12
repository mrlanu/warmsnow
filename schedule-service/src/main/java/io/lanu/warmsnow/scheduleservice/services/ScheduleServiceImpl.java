package io.lanu.warmsnow.scheduleservice.services;

import io.lanu.warmsnow.common_models.requests.FieldUpgradeRequest;
import io.lanu.warmsnow.scheduleservice.clients.VillagesServiceClient;
import io.lanu.warmsnow.scheduleservice.entities.TaskEntity;
import io.lanu.warmsnow.scheduleservice.repositories.ScheduleRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

    private ScheduleRepository scheduleRepository;
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private VillagesServiceClient villagesServiceClient;

    private static final Map<String, ScheduledFuture> SCHEDULED_FUTURE_MAP = new ConcurrentHashMap<>();

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository,
                               ThreadPoolTaskScheduler threadPoolTaskScheduler,
                               VillagesServiceClient villagesServiceClient) {
        this.scheduleRepository = scheduleRepository;
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
        this.villagesServiceClient = villagesServiceClient;
    }

    @Override
    public String scheduleTask(FieldUpgradeRequest request) {
        final String taskId = UUID.randomUUID().toString();

        // count duration for the task
        long scheduledQueueDuration = getDurationsAllTasksFromQueue(request.getVillageId());
        long finalDuration = request.getField().getTimeToNextLevel() + scheduledQueueDuration;

        //schedule the task
        ScheduledFuture scheduledFuture = threadPoolTaskScheduler.schedule(
                () -> {
                    //completed task removing from map & DB
                    log.info("Task complete.");
                    deleteByTaskId(taskId);
                    SCHEDULED_FUTURE_MAP.remove(taskId);
                    villagesServiceClient.upgradeField(request);
                },
                new Date(System.currentTimeMillis() + finalDuration));

        log.info("Task has been scheduled. Duration - " + finalDuration);

        //creating new Task end persist it to map & DB
        TaskEntity newTask = new TaskEntity(taskId, request.getVillageId());
        scheduleRepository.save(newTask);
        SCHEDULED_FUTURE_MAP.put(taskId, scheduledFuture);

        return taskId;
    }

    private Long getDurationsAllTasksFromQueue(String villageId){
        return scheduleRepository.findAllByVillageId(villageId)
                .stream()
                .mapToLong(t -> SCHEDULED_FUTURE_MAP.get(t.getTaskId()).getDelay(TimeUnit.MILLISECONDS))
                .sum();
    }

    /*@Override
    public List<TaskViewModel> findAllTasksByVillageId(String villageId) {
        List<TaskEntity> taskEntities = taskRepository.findAllByVillageId(villageId);
        return taskEntities.stream()
                .map(t -> new TaskViewModel(t.getTaskId(), t.getItemId(), t.getPosition(), t.getToLevel(),
                        DurationFormatUtils
                                .formatDuration(SCHEDULED_FUTURE_MAP
                                        .get(t.getTaskId()).getDelay(TimeUnit.MILLISECONDS), "HH:mm:ss")))
                .collect(Collectors.toList());
    }*/

    private void deleteByTaskId(String taskId) {
        scheduleRepository.deleteByTaskId(taskId);
    }
}
