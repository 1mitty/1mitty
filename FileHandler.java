package communitygarden;

import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
// saves the user, appointment, and dontaion details to a file
    public static void saveToFile(Appointment appointment) {
        try (FileWriter writer = new FileWriter("community_garden_order_summary.txt", true)) {
            writer.write("Appointment Details:\n");
            writer.write(appointment.toString() + "\n");
            writer.write("-------------\n");
        } catch (IOException e) {
            System.out.println("An error occurred while saving appointment to file.");
        }
    }

    public static void saveDonationToFile(Donation donation) {
        try (FileWriter writer = new FileWriter("community_garden_donations.txt", true)) {
            writer.write("Donation Details:\n");
            writer.write(donation.toString() + "\n");
            writer.write("-------------\n");
        } catch (IOException e) {
            System.out.println("An error occurred while saving donation to file.");
        }
    }
}
