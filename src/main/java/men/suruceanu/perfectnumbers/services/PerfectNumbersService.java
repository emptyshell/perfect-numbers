package men.suruceanu.perfectnumbers.services;

import men.suruceanu.perfectnumbers.dto.InvalidRangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class PerfectNumbersService {

    private static final Logger logger = LoggerFactory.getLogger(PerfectNumbersService.class);

    public List<Long> getPerfectNumbersByRange(long start, long end) throws InvalidRangeException {

        int THREAD_COUNT_NUMBER = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT_NUMBER);
        List<Future<List<Long>>> threadsPerfectNumbersResult = new ArrayList<>();
        List<Long> perfectNumbersList = Collections.synchronizedList(new ArrayList<>());
        long bucketSize = end / THREAD_COUNT_NUMBER;

        if (start < 0 || end < 0) {
            throw new InvalidRangeException();
        }

        if (start > end) {
            throw new InvalidRangeException("Start of range cannot be higher number then end!");
        }
        long startRangeThread = start;
        long endRangeThread = bucketSize;

        for (int threadIndex = 0; threadIndex < THREAD_COUNT_NUMBER; threadIndex++) {
            long finalStartRangeThread = startRangeThread;
            long finalEndRangeThread = endRangeThread;
            logger.info("Current thread start: {} and end: {}", finalStartRangeThread, finalEndRangeThread);
            threadsPerfectNumbersResult.add(executorService.submit(() -> {

                List<Long> perfectNumbersListInThread = new ArrayList<>();

                for (long numberToCheck = finalStartRangeThread; numberToCheck <= finalEndRangeThread; numberToCheck++) {
                    if (isPerfectNumber(numberToCheck) && numberToCheck >= start) {
                        perfectNumbersListInThread.add(numberToCheck);
                    }
                }

                return perfectNumbersListInThread;
            }));

            startRangeThread = endRangeThread;
            if (endRangeThread > end) {
                endRangeThread = end;
            }
            endRangeThread += bucketSize;
        }


        for (Future<List<Long>> threadResultFuture : threadsPerfectNumbersResult) {
            try {
                perfectNumbersList.addAll(threadResultFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return perfectNumbersList;
    }

    public boolean isPerfectNumber(Long number) {
        if (number < 0) {
            return false;
        }

        long computedPerfectNumber = 0;
        for (long index = 1; index <= number / 2; index++) {
            if (number % index == 0) {
                computedPerfectNumber += index;
            }
        }

        return number == computedPerfectNumber;
    }

}
