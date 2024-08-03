import java.util.Scanner;

public class os_ass_2 {
    public static void safeSequence(int[] total_resources, int process) {
        Scanner sc = new Scanner(System.in);
        int num_resources = total_resources.length;

        int[][] allocation = new int[process][num_resources];
        int[][] maxNeed = new int[process][num_resources];
        int[][] remainingNeed = new int[process][num_resources];

        // Allocations
        System.out.println("Enter the allocation of resources for each process:");
        for (int i = 0; i < process; i++) {
            for (int j = 0; j < num_resources; j++) {
                System.out.print("Process " + (i + 1) + " - Allocation of Resource " + (char) ('A' + j) + ": ");
                allocation[i][j] = sc.nextInt();
            }
        }

        // Maximum needs
        System.out.println("Enter the maximum need of resources for each process:");
        for (int i = 0; i < process; i++) {
            for (int j = 0; j < num_resources; j++) {
                System.out.print("Process " + (i + 1) + " - Max Need of Resource " + (char) ('A' + j) + ": ");
                maxNeed[i][j] = sc.nextInt();
            }
        }

        // Calculate remaining needs
        for (int i = 0; i < process; i++) {
            for (int j = 0; j < num_resources; j++) {
                remainingNeed[i][j] = maxNeed[i][j] - allocation[i][j];
            }
        }

        // Calculate current availability
        int[] currentAvailability = currentAvailable(total_resources, allocation);

        // Display tabular format
        System.out.println("\nProcess\t\tAllocation\t\tMax Need\t\tRemaining Need");
        for (int i = 0; i < process; i++) {
            System.out.print("P" + (i + 1) + "\t\t");
            for (int j = 0; j < num_resources; j++) {
                System.out.print(allocation[i][j] + "  ");
            }
            System.out.print("\t\t");
            for (int j = 0; j < num_resources; j++) {
                System.out.print(maxNeed[i][j] + "  ");
            }
            System.out.print("\t\t");
            for (int j = 0; j < num_resources; j++) {
                System.out.print(remainingNeed[i][j] + "  ");
            }
            System.out.println();
        }

        // Determine if there is a safe sequence
        boolean[] finished = new boolean[process];
        int[] safeSequence = new int[process];
        int count = 0;

        while (count < process) {
            boolean found = false;
            for (int p = 0; p < process; p++) {
                if (!finished[p]) {
                    boolean canAllocate = true;
                    for (int r = 0; r < num_resources; r++) {
                        if (remainingNeed[p][r] > currentAvailability[r]) {
                            canAllocate = false;
                            break;
                        }
                    }

                    if (canAllocate) {
                        for (int r = 0; r < num_resources; r++) {
                            currentAvailability[r] += allocation[p][r];
                        }
                        safeSequence[count++] = p;
                        finished[p] = true;
                        found = true;
                    }
                }
            }

            if (!found) {
                System.out.println("Deadlock occurred!!! No safe sequence is possible.");
                return;
            }
        }

        // Print the safe sequence
        System.out.print("Safe sequence: ");
        for (int i = 0; i < process; i++) {
            System.out.print("P" + (safeSequence[i] + 1) + " ");
        }
        System.out.println("\nAll processes terminated successfully. No deadlock occurred.");
    }

    public static int[] currentAvailable(int[] total_resources, int[][] allocation) {
        int num_resources = total_resources.length;
        int[] total_of_allocation = new int[num_resources];

        // Sum up all allocations
        for (int i = 0; i < allocation.length; i++) {
            for (int j = 0; j < num_resources; j++) {
                total_of_allocation[j] += allocation[i][j];
            }
        }

        // Calculate current availability
        int[] available = new int[num_resources];
        for (int i = 0; i < num_resources; i++) {
            available[i] = total_resources[i] - total_of_allocation[i];
        }

        return available;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of resource types:");
        int num_resources = sc.nextInt();

        int[] total_resources = new int[num_resources];
        for (int i = 0; i < num_resources; i++) {
            System.out.print("Enter the total number of Resource " + (char) ('A' + i) + ": ");
            total_resources[i] = sc.nextInt();
        }

        System.out.println("Enter the total number of processes:");
        int process = sc.nextInt();

        safeSequence(total_resources, process);
    }
}
