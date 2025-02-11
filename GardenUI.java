package communitygarden;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GardenUI {

    private JFrame frame;
    private JTextField nameField, phoneField, dateField, timeField;
    private JComboBox<String> amPmComboBox;
    private JButton registerButton, appointmentButton, donateButton;
    private JTextArea outputArea;

    // List to store registered users
    private ArrayList<User> registeredUsers = new ArrayList<>();

    public GardenUI() {
        frame = new JFrame("Community Garden");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        nameField = new JTextField(20);
        phoneField = new JTextField(20);
        dateField = new JTextField(20); // Text field for date input
        timeField = new JTextField(10); // Text field for time input
        amPmComboBox = new JComboBox<>(new String[]{"AM", "PM"}); // AM/PM combo box
        registerButton = new JButton("Register");
        appointmentButton = new JButton("Make Appointment");
        donateButton = new JButton("Make a Donation");
        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);

        frame.add(new JLabel("Enter your name:"));
        frame.add(nameField);
        frame.add(new JLabel("Enter your phone number:"));
        frame.add(phoneField);
        frame.add(registerButton);

        frame.add(new JLabel("Enter appointment date (YYYY-MM-DD):"));
        frame.add(dateField);
        frame.add(new JLabel("Enter appointment time (HH:mm):"));
        frame.add(timeField);
        frame.add(new JLabel("Select AM/PM:"));
        frame.add(amPmComboBox);
        frame.add(appointmentButton);

        frame.add(donateButton);
        frame.add(new JScrollPane(outputArea));

        // Register button listener
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String phone = phoneField.getText();
                User user = new User(name, phone);
                registeredUsers.add(user);
                outputArea.append("Registration successful for " + name + "\n");
            }
        });

        // Appointment button listener
        appointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (registeredUsers.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No users registered yet. Please register first.");
                    return;
                }

                String dateString = dateField.getText(); // Get the date from the text field
                String timeString = timeField.getText(); // Get the time from the text field
                String amPm = (String) amPmComboBox.getSelectedItem(); // Get the selected AM/PM

                // Add AM/PM to the time
                timeString += " " + amPm;

                // Validate the date and time
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date selectedDate = null;
                try {
                    selectedDate = dateFormat.parse(dateString); // Parse the input date
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid date format. Please use YYYY-MM-DD.");
                    return;
                }

                if (TimeValidator.isValidAppointmentTime(selectedDate, timeString)) {
                    Appointment appointment = new Appointment(registeredUsers.get(registeredUsers.size() - 1), selectedDate, timeString);
                    outputArea.append("Appointment scheduled for " + appointment.getUser().getName() + " on " + selectedDate + " at " + timeString + "\n");
                    FileHandler.saveToFile(appointment);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid time entered. Please ensure it's within the garden's operating hours.");
                }
            }
        });

        // Donation button listener
        donateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] donationTypes = {"Money", "Seeds", "Soil", "Fertilizer", "Voluntary Labor", "Tools"};
                String donationType = (String) JOptionPane.showInputDialog(frame, "Select donation type:", "Donation", JOptionPane.QUESTION_MESSAGE, null, donationTypes, donationTypes[0]);

                if (donationType != null) {
                    String amount = JOptionPane.showInputDialog("Enter donation amount or description:");
                    Donation donation = new Donation(donationType, amount);
                    outputArea.append("Donation received: " + donation.toString() + "\n");
                    FileHandler.saveDonationToFile(donation);
                }
            }
        });
    }

    public void showUI() {
        frame.setVisible(true);
    }
}
