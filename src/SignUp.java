import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUp extends Frame implements ActionListener {
    // Declaring form fields
    TextField tfUsername, tfPassword, tfFirstName, tfLastName, tfEmail, tfPhone, tfAddress, tfAge;
    Button btnSubmit;
    Label lblMessage;  // Label to display success or failure message

    public SignUp() {
        // Set frame layout with padding for cleaner look
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding between elements

        // Add form labels and text fields in a structured layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new Label("Username: "), gbc);
        tfUsername = new TextField(20);
        gbc.gridx = 1;
        add(tfUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new Label("Password: "), gbc);
        tfPassword = new TextField(20);
        tfPassword.setEchoChar('*');
        gbc.gridx = 1;
        add(tfPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new Label("First Name: "), gbc);
        tfFirstName = new TextField(20);
        gbc.gridx = 1;
        add(tfFirstName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new Label("Last Name: "), gbc);
        tfLastName = new TextField(20);
        gbc.gridx = 1;
        add(tfLastName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new Label("Email: "), gbc);
        tfEmail = new TextField(20);
        gbc.gridx = 1;
        add(tfEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new Label("Phone: "), gbc);
        tfPhone = new TextField(20);
        gbc.gridx = 1;
        add(tfPhone, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new Label("Address: "), gbc);
        tfAddress = new TextField(20);
        gbc.gridx = 1;
        add(tfAddress, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        add(new Label("Age: "), gbc);
        tfAge = new TextField(20);
        gbc.gridx = 1;
        add(tfAge, gbc);

        // Submit button
        btnSubmit = new Button("Submit");
        gbc.gridx = 1;
        gbc.gridy = 8;
        btnSubmit.setBackground(Color.LIGHT_GRAY);
        btnSubmit.addActionListener(this);
        add(btnSubmit, gbc);

        // Message label to show success or error message below the submit button
        lblMessage = new Label("");
        gbc.gridx = 1;
        gbc.gridy = 9;
        add(lblMessage, gbc);

        // Frame properties
        setTitle("Signup Form");
        setSize(500, 500); // Increased size for better spacing
        setVisible(true);
    }

    // Handling button click
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnSubmit) {
            // Get values from form fields
            String username = tfUsername.getText();
            String password = tfPassword.getText();
            String firstName = tfFirstName.getText();
            String lastName = tfLastName.getText();
            String email = tfEmail.getText();
            String phone = tfPhone.getText();
            String address = tfAddress.getText();
            String age = tfAge.getText();

            // Check if all fields are filled
            if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() ||
                email.isEmpty() || phone.isEmpty() || address.isEmpty() || age.isEmpty()) {
                lblMessage.setForeground(Color.RED);
                lblMessage.setText("All fields must be filled.");
            } else {
                // Send data to the database
                boolean isSuccessful = insertIntoDatabase(username, password, firstName, lastName, email, phone, address, age);
                if (isSuccessful) {
                    lblMessage.setForeground(Color.GREEN);
                    lblMessage.setText("Signup successful!");
                } else {
                    lblMessage.setForeground(Color.RED);
                    lblMessage.setText("Signup failed.");
                }
            }
        }
    }

    // Insert data into MySQL database
    public boolean insertIntoDatabase(String username, String password, String firstName, String lastName, 
                                   String email, String phone, String address, String age) {
        Connection connection = Conn.getConnection(); // Use the connection module
        if (connection != null) {
            try {
                // Prepare the SQL query
                String query = "INSERT INTO users (username, password, first_name, last_name, email, phone, address, age) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, firstName);
                preparedStatement.setString(4, lastName);
                preparedStatement.setString(5, email);
                preparedStatement.setString(6, phone);
                preparedStatement.setString(7, address);
                preparedStatement.setString(8, age);

                // Execute the query
                int result = preparedStatement.executeUpdate();
                return result > 0;  // Return true if insertion is successful
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        new SignUp(); // Create the form
    }
}
