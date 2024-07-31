import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class PomodoroTimer {

    private static int workTime;
    private static int breakTime;
    private static int timeRemaining;
    private static Timer timer;
    private static boolean isPaused = false;
    private static boolean isWorkSession = true;
    private static int completedWorkSessions = 0;
    private static int completedBreakSessions = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter work session duration (minutes): ");
            workTime = scanner.nextInt() * 60;

            System.out.print("Enter break session duration (minutes): ");
            breakTime = scanner.nextInt() * 60;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter an integer value for the duration.");
            scanner.close();
            return;
        }

        timer = new Timer();

        System.out.println("Starting Pomodoro Timer...");
        startWorkSession();

        while (true) {
            System.out.println("Press 'q' to quit, 'p' to pause, 'r' to resume.");
            String input = scanner.next();
            if (input.equalsIgnoreCase("q")) {
                timer.cancel();
                System.out.println("Pomodoro Timer stopped.");
                displaySummary();
                break;
            } else if (input.equalsIgnoreCase("p")) {
                pauseTimer();
            } else if (input.equalsIgnoreCase("r")) {
                resumeTimer();
            } else {
                System.out.println("Invalid input. Please enter 'q', 'p', or 'r'.");
            }
        }

        scanner.close();
    }

    private static void startWorkSession() {
        timeRemaining = workTime;
        isWorkSession = true;
        System.out.printf("Work session started. Time remaining: %02d:%02d\n", workTime / 60, 0);
        startTimer();
    }

    private static void startBreakSession() {
        timeRemaining = breakTime;
        isWorkSession = false;
        System.out.printf("Break session started. Time remaining: %02d:%02d\n", breakTime / 60, 0);
        startTimer();
    }

    private static void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!isPaused) {
                    timeRemaining--;
                    if (timeRemaining <= 0) {
                        timer.cancel();
                        if (isWorkSession) {
                            completedWorkSessions++;
                            System.out.printf("\nWork session ended. Completed work sessions: %d\n",
                                    completedWorkSessions);
                            startBreakSession();
                        } else {
                            completedBreakSessions++;
                            System.out.printf("\nBreak session ended. Completed break sessions: %d\n",
                                    completedBreakSessions);
                            startWorkSession();
                        }
                    } else {
                        int minutes = timeRemaining / 60;
                        int seconds = timeRemaining % 60;
                        System.out.printf("Time remaining: %02d:%02d\r", minutes, seconds);
                    }
                }
            }
        }, 0, 1000);
    }

    private static void pauseTimer() {
        isPaused = true;
        System.out.println("\nTimer paused.");
    }

    private static void resumeTimer() {
        isPaused = false;
        System.out.println("\nTimer resumed.");
    }

    private static void displaySummary() {
        System.out.printf("Summary: Completed work sessions: %d, Completed break sessions: %d\n", completedWorkSessions,
                completedBreakSessions);
    }
}
