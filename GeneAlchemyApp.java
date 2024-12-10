import javax.swing.*;//GUI components like buttons, text fields etc 
import java.awt.*;//layout management and event handling 
import java.awt.event.*;//to handle user interactions such as button clicks 
import java.sql.*;//to connect and interact with the database 

public class GeneAlchemyApp {//this is the main class for the application 

    private JFrame frame;//the main applicaton window 
    private JPanel cardPanel;//panel to hold both the login and sign-up forms
    private CardLayout cardLayout;//used to switch between login and signup pages 
    private JTextField userText;//Input fields for username and password 
    private JPasswordField passwordText;
    private JTextField signUpUserText;
    private JPasswordField signUpPasswordText;
    private JPasswordField signUpConfirmPasswordText;
    private Connection conn;//used to establish a connection with the database 

    public static void main(String[] args) {//entry point of the applicaion
        new GeneAlchemyApp();  //creates an instance of the GeneAlchemy class to start the program 
    }

    public GeneAlchemyApp() {//constructor initialises the GUI components and establish the database connection 
        
        //Sets up the main application window with the title, close operation and fixed size
        frame = new JFrame("GeneAlchemy - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        //creates a CardLayout for switching between login and signup pages
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        frame.add(cardPanel);

        //LOGIN PAGE COMPONENTS: 
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);//creates the login page and sets the layout to null for absolute positioning of components 

        JLabel userLabel = new JLabel("Username:");//adds a "Username" label to the login form
        userLabel.setBounds(50, 50, 100, 30);
        loginPanel.add(userLabel);
        userText = new JTextField(20);//adds a text field for entering the username 
        userText.setBounds(150, 50, 160, 30);
        loginPanel.add(userText);

        JLabel passwordLabel = new JLabel("Password:");//adds a "Password" label for the login form
        passwordLabel.setBounds(50, 100, 100, 30);
        loginPanel.add(passwordLabel);
        passwordText = new JPasswordField(20);//adds a password field for entering the password securely 
        passwordText.setBounds(150, 100, 160, 30);
        loginPanel.add(passwordText);

        
        //Adds a "LOGIN" button to trigger the login processes 
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(50, 150, 100, 30);
        loginPanel.add(loginButton);

        //Adds a "SIGN UP" button to navigate to the sign-up page 
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(200, 150, 100, 30);
        loginPanel.add(signUpButton);

        //Define the action for the sign up button switching the view to the sign up form using Card Layout
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "SignUp");  //Show the sign-up page
            }
        });

        //Login button action: fetches the entered username and password when the "login" button is clicked 
        loginButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        String username = userText.getText();
        String password = new String(passwordText.getPassword());

        //Validate login with the database using validateLogin()
        if (validateLogin(username, password)) {
            //if the credentials are valid then it opens the BioAppGUI
            SwingUtilities.invokeLater(() -> {
                BioAppGUI bioAppGUI = new BioAppGUI();
                bioAppGUI.setVisible(true);
            });
            //Closes the login window 
            ((JFrame) SwingUtilities.getWindowAncestor(loginButton)).dispose();
        } else {//otherwise, it shows an error message
            JOptionPane.showMessageDialog(null, "Invalid username or password.");
        }
    }
});


        //creates the sign-up page panel with the null layout 
        JPanel signUpPanel = new JPanel();
        signUpPanel.setLayout(null);

        //Adds fields for entering the username, password and the confirmation password 
        JLabel signUpUserLabel = new JLabel("Username:");
        signUpUserLabel.setBounds(50, 50, 100, 30);
        signUpPanel.add(signUpUserLabel);
        signUpUserText = new JTextField(20);
        signUpUserText.setBounds(150, 50, 160, 30);
        signUpPanel.add(signUpUserText);

        JLabel signUpPasswordLabel = new JLabel("Password:");
        signUpPasswordLabel.setBounds(50, 100, 100, 30);
        signUpPanel.add(signUpPasswordLabel);
        signUpPasswordText = new JPasswordField(20);
        signUpPasswordText.setBounds(150, 100, 160, 30);
        signUpPanel.add(signUpPasswordText);

        JLabel signUpConfirmPasswordLabel = new JLabel("Confirm Password:");
        signUpConfirmPasswordLabel.setBounds(50, 150, 120, 30);
        signUpPanel.add(signUpConfirmPasswordLabel);
        signUpConfirmPasswordText = new JPasswordField(20);
        signUpConfirmPasswordText.setBounds(150, 150, 160, 30);
        signUpPanel.add(signUpConfirmPasswordText);

        JButton signUpSubmitButton = new JButton("Submit");
        signUpSubmitButton.setBounds(150, 200, 100, 30);
        signUpPanel.add(signUpSubmitButton);


        //Fetches the input from the sign up form fields 
        signUpSubmitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = signUpUserText.getText();
                String password = new String(signUpPasswordText.getPassword());
                String confirmPassword = new String(signUpConfirmPasswordText.getPassword());

                if (password.equals(confirmPassword)) {//if the password matches then it inserts the user into the database
                    try {
                        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
                        PreparedStatement stmt = conn.prepareStatement(query);
                        stmt.setString(1, username);
                        stmt.setString(2, password);
                        stmt.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Sign Up Successful!");//then displays the success message and redirects to the login page
                        cardLayout.show(cardPanel, "Login"); 
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage());//otherwise shows an error message
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Passwords do not match!");
                }
            }
        });

        //Add both panels to the card layout container
        cardPanel.add(loginPanel, "Login");
        cardPanel.add(signUpPanel, "SignUp");

        //Show login page by default
        cardLayout.show(cardPanel, "Login");

        frame.setVisible(true);  // Make the frame visible

        //Establish MySQL connection
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/GeneAlchemyDB", "root", "Nandana17!"); 
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //Method to validate login credentials with the database
    private boolean validateLogin(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";//checks if the username and password exist in the database. Returns true if valid, otherwise blue
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            //If a record is found, login is successful
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage());
        }
        return false;  // Return false if no match is found
    }
}