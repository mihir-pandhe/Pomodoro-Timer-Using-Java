import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class PomodoroTimer {

    private static int workTime;
    private static int breakTime;
    private static int timeRemaining;
    private static Timer timer;
    private static boolean isWorkSession = true;
    private static int completedWorkSessions = 0;
    private static int completedBreakSessions = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter work session duration (minutes): ");
        workTime = scanner.nextInt() * 60;

        System.out.print("Enter break session duration (minutes): ");
        breakTime = scanner.nextInt() * 60;

        timer = new Timer();

        System.out.println("Starting Pomodoro Timer...");
        startWorkSession();

        while (true) {
            System.out.println("Press 'q' to quit.");
            String input = scanner.next();
            if (input.equalsIgnoreCase("q")) {
                timer.cancel();
                System.out.println("Pomodoro Timer stopped.");
                break;
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
                timeRemaining--;
                if (timeRemaining <= 0) {
                    timer.cancel();
                    if (isWorkSession) {
                        completedWorkSessions++;
                        System.out.printf("\nWork session ended. Completed work sessions: %d\n", completedWorkSessions);
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
        }, 0, 1000);
    }
}
