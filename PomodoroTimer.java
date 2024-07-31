import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class PomodoroTimer {

    private static int workTime;
    private static int breakTime;
    private static int timeRemaining;
    private static Timer timer;

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
        System.out.printf("Work session started. Time remaining: %02d:%02d\n", workTime / 60, 0);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeRemaining--;
                if (timeRemaining <= 0) {
                    timer.cancel();
                    System.out.println("\nWork session ended. Take a break!");
                    startBreakSession();
                } else {
                    int minutes = timeRemaining / 60;
                    int seconds = timeRemaining % 60;
                    System.out.printf("Time remaining: %02d:%02d\r", minutes, seconds);
                }
            }
        }, 0, 1000);
    }

    private static void startBreakSession() {
        timeRemaining = breakTime;
        System.out.printf("Break session started. Time remaining: %02d:%02d\n", breakTime / 60, 0);
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeRemaining--;
                if (timeRemaining <= 0) {
                    timer.cancel();
                    System.out.println("\nBreak session ended. Back to work!");
                    startWorkSession();
                } else {
                    int minutes = timeRemaining / 60;
                    int seconds = timeRemaining % 60;
                    System.out.printf("Time remaining: %02d:%02d\r", minutes, seconds);
                }
            }
        }, 0, 1000);
    }
}
