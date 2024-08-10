import java.util.Arrays;

public class os_ass_2_3 {
    public static void main(String[] args) {
        int initialHead = 50;
        int[] seekSequence = {60, 79, 92, 114, 176, 11, 34, 41};
        int totalTracks = 200; // Assuming total tracks from 0 to 199

        int totalTraveled = calculateTotalTracks(initialHead, seekSequence, totalTracks);
        System.out.println("Total tracks traversed: " + totalTraveled);
    }

    public static int calculateTotalTracks(int initialHead, int[] seekSequence, int totalTracks) {
        Arrays.sort(seekSequence);

        int totalTraveled = 0;
        int currentTrack = initialHead;

        // Traverse from current head to the highest track
        for (int track : seekSequence) {
            if (track >= currentTrack) {
                totalTraveled = totalTraveled + (track - currentTrack);
                currentTrack = track;
            }
        }

        // Traverse from the highest track to 0
        totalTraveled = totalTraveled + (totalTracks - 1 - seekSequence[seekSequence.length - 1]);
        currentTrack = 0;

        // Traverse from 0 to the lowest track
        for (int track : seekSequence) {
            if (track <= currentTrack) {
                totalTraveled = totalTraveled + (currentTrack - track);
                currentTrack = track;
            }
        }

        return totalTraveled;
    }
}
