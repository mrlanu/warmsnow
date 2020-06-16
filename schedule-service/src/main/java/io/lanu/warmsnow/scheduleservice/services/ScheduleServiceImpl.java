package io.lanu.warmsnow.scheduleservice.services;

import io.lanu.warmsnow.scheduleservice.models.TaskViewModel;
import io.lanu.warmsnow.common_models.requests.FieldUpgradeRequest;
import io.lanu.warmsnow.scheduleservice.clients.VillagesServiceClient;
import io.lanu.warmsnow.scheduleservice.entities.TaskEntity;
import io.lanu.warmsnow.scheduleservice.repositories.ScheduleRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
        long finalDuration = request.getField().getTimeToNextLevel() * 1000 + scheduledQueueDuration;

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
        TaskEntity newTask = new TaskEntity(taskId, request.getVillageId(), request.getField().getPosition(),
                request.getField().getLevel(), finalDuration);
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

    @Override
    public List<TaskViewModel> findAllTasksByVillageId(String villageId) {
        List<TaskEntity> taskEntities = scheduleRepository.findAllByVillageId(villageId);
        return taskEntities.stream()
                .map(t -> {
                    long timeLeft = SCHEDULED_FUTURE_MAP.get(t.getTaskId()).getDelay(TimeUnit.MILLISECONDS) / 1000;
                    return new TaskViewModel(t.getTaskId(), t.getPosition(), t.getLevel(), timeLeft);
                })
                .collect(Collectors.toList());
    }

    private void deleteByTaskId(String taskId) {
        scheduleRepository.deleteByTaskId(taskId);
    }
}
