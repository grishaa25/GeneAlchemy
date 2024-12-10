import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

// this part of our code signifies the abstract super class , subclasses which are later defined are declared here
abstract class Sequence {
    protected String sequence;

    public Sequence(String sequence) {
        this.sequence = sequence;
    }

    public abstract boolean validateSequence();

    public int getLength() {
        return sequence.length();
    }

    public String getSequence() {
        return sequence;
    }
}

// DNAsequence class is extdned by the superclass sequence and hence the property of inheritance and polymorphism (overriding superclass) are used here.
class DNASequence extends Sequence {
    public DNASequence(String sequence) {
        super(sequence);
    }
    // validatesequence function checks whether the user entered the appropreate sequence 
    @Override
    public boolean validateSequence() {
        return sequence.matches("[ATCG]+");
    }
    // this class finds the complement for DNA entered meaning, A changes to t, T to a, C to g and G to c 
    public String findComplement() {
        return sequence.replace('A', 't').replace('T', 'a').replace('C', 'g').replace('G', 'c').toUpperCase();
    }
    // this calculates the gc content meaning the percentage of 'G' and 'C' in the sequence
    public double calculateGCContent() {
        long gcCount = sequence.chars().filter(c -> c == 'G' || c == 'C').count();
        return (gcCount / (double) sequence.length()) * 100;
    }
    //information in a genes DNA is copied into comlimentary RNA 
    public RNASequence transcribeToRNA() {
        return new RNASequence(sequence.replace('T', 'U'));
    }
    //this function finds the palindrome of the sequence, in this fucntion they are finding the reverse of the string 
    public List<String> findPalindrome() {
    List<String> palindromes = new ArrayList<>(); //array initiated
    int length = sequence.length();
    for (int i = 0; i < length; i++) {
        for (int j = i + 2; j <= length; j++) { // Minimum length of 2 for palindromes
            String substring = sequence.substring(i, j);
            String reverseComplement = new StringBuilder(substring)
                    .reverse()
                    .toString()
                    .replace('A', 't')
                    .replace('T', 'a')
                    .replace('C', 'g')
                    .replace('G', 'c')
                    .toUpperCase();
            if (substring.equals(reverseComplement)) { //checking if the function is a plaindrome
                palindromes.add(substring);
            }
        }
    }
    return palindromes;
}
// the motif represents a specific sequence or pattern you want to search for within a larger DNA
//This finds all the starting positions where motif appears
public List<Integer> findMotif(String motif) {
    List<Integer> positions = new ArrayList<>();
    int index = sequence.indexOf(motif);
    while (index != -1) {
        positions.add(index + 1); // 1-based indexing
        index = sequence.indexOf(motif, index + 1);
    }
    return positions;
}
//this is the nucleotide counter, it counts the number of A,T,C,G There is , while using a hashmap the store the existing count
public Map<Character, Integer> countNucleotides() {
    Map<Character, Integer> counts = new HashMap<>();
    counts.put('A', 0);
    counts.put('T', 0);
    counts.put('C', 0);
    counts.put('G', 0);
    for (char c : sequence.toCharArray()) {
        counts.put(c, counts.getOrDefault(c, 0) + 1);
    }
    return counts;
}

}

// like our DNAsequence class this class also shows inheritence and polymorphism, in the same way, while expending superclas sequence
class RNASequence extends Sequence {
    public RNASequence(String sequence) {
        super(sequence);
    }
    //validatesequence function checks whether the user entered the appropreate sequence for RNA
    @Override
    public boolean validateSequence() {
        return sequence.matches("[AUCG]+");
    }
    //this calculates the gc content meaning the percentage of 'G' and 'C' in the sequence
    public double calculateGCContent() {
        long gcCount = sequence.chars().filter(c -> c == 'G' || c == 'C').count();
        return (gcCount / (double) sequence.length()) * 100;
    }
    //information in a genes RNA is copied into comlimentary protein
    public ProteinSequence transcribeToProtein() {
        return new ProteinSequence(transcribeToProtein(sequence));
    }
    // Transcribes RNA sequence into a protein sequence using the codon map
    private String transcribeToProtein(String rnaSequence) {
        Map<String, String> codonMap = new HashMap<>();
        codonMap.put("AUG", "M"); // Methionine (start codon)
        codonMap.put("UUU", "F"); // Phenylalanine
        codonMap.put("UUC", "F"); // Phenylalanine
        codonMap.put("UUA", "L"); // Leucine
        codonMap.put("UUG", "L"); // Leucine
        codonMap.put("CUU", "L"); // Leucine
        codonMap.put("CUC", "L"); // Leucine
        codonMap.put("CUA", "L"); // Leucine
        codonMap.put("CUG", "L"); // Leucine
        codonMap.put("AUU", "I"); // Isoleucine
        codonMap.put("AUC", "I"); // Isoleucine
        codonMap.put("AUA", "I"); // Isoleucine
        codonMap.put("GUU", "V"); // Valine
        codonMap.put("GUC", "V"); // Valine
        codonMap.put("GUA", "V"); // Valine
        codonMap.put("GUG", "V"); // Valine
        codonMap.put("GGU", "G"); // Glycine
        codonMap.put("GGC", "G"); // Glycine
        codonMap.put("GGA", "G"); // Glycine
        codonMap.put("GGG", "G"); // Glycine
        // (Add all remaining codons to complete the codon map...)
        // This code translates an RNA sequence into a protein sequence by mapping codons (triplets of RNA bases) to their corresponding amino acids using a codon map.
        StringBuilder proteinSequence = new StringBuilder();
        for (int i = 0; i < rnaSequence.length() - 2; i += 3) {
            String codon = rnaSequence.substring(i, i + 3);
            String aminoAcid = codonMap.getOrDefault(codon, "");
            if (aminoAcid.isEmpty()) break; // Stop translation if invalid codon
            proteinSequence.append(aminoAcid);
        }

        return proteinSequence.toString(); //converts to string
    }
    //this function finds the palindrome of the sequence, in this fucntion they are finding the reverse of the string 
    public List<String> findPalindrome() {
    List<String> palindromes = new ArrayList<>();
    int length = sequence.length();
    for (int i = 0; i < length; i++) {
        for (int j = i + 2; j <= length; j++) { // Minimum length of 2 for palindromes
            String substring = sequence.substring(i, j);
            String reverseComplement = new StringBuilder(substring)
                    .reverse()
                    .toString()
                    .replace('A', 'u')
                    .replace('U', 'a')
                    .replace('C', 'g')
                    .replace('G', 'c')
                    .toUpperCase();
            if (substring.equals(reverseComplement)) {//checks if palindrome, by checking if reversed string equals original 
                palindromes.add(substring);
            }
        }
    }
    return palindromes;
}

// the motif represents a specific sequence or pattern you want to search for within a larger DNA
//This finds all the starting positions where motif appears
public List<Integer> findMotif(String motif) {
    List<Integer> positions = new ArrayList<>();
    int index = sequence.indexOf(motif);
    while (index != -1) {
        positions.add(index + 1); // 1-based indexing
        index = sequence.indexOf(motif, index + 1);
    }
    return positions;
}

//this is the nucleotide counter, it counts the number of A,T,C,G There is , while using a hashmap the store the existing count
public Map<Character, Integer> countNucleotides() {
    Map<Character, Integer> counts = new HashMap<>();
    counts.put('A', 0);
    counts.put('U', 0);
    counts.put('C', 0);
    counts.put('G', 0);
    for (char c : sequence.toCharArray()) {
        counts.put(c, counts.getOrDefault(c, 0) + 1);
    }
    return counts;
}

}

// Protein Sequence Class this also extends the super-abstract class sequence and inherits all its roperties
class ProteinSequence extends Sequence {
    public ProteinSequence(String sequence) {
        super(sequence);
    }
    //checks if entered sequence is valid 
    @Override
    public boolean validateSequence() {
        return sequence.matches("[ACDEFGHIKLMNPQRSTVWY]+");
    }
}

// The SequenceWrapper class encapsulates a Sequence object
// maintains a history of changes or actions performed on it.
class SequenceWrapper {
    private Sequence sequence;
    private List<String> history = new ArrayList<>();

    public SequenceWrapper(Sequence sequence) {
        this.sequence = sequence;
    }

    public Sequence getSequence() {
        return sequence;
    }

}

// GUI Implementation Class with Encapsulation- meaning this is used as our user interface/front end
//where the user is unaware of the working of the code but can use the operational model
public class BioAppGUI extends JFrame {
    private JPanel contentPanel = new JPanel(new CardLayout());
    private JTextField sequenceInput = new JTextField(20);
    //sets the title and size of our model
    public BioAppGUI() {
        setTitle("GeneAlchemy: Bioinformatics Sequence Analyzer");
        setSize(700, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //key features that will show up in model
        setupNavigationPanel();
        setupHomePage();
        setupAboutUsPage();
        setupUtilitiesPage();
        add(contentPanel);
    }

    private void setupNavigationPanel() {
        JPanel navPanel = new JPanel(new FlowLayout());
        navPanel.add(new JLabel("GeneAlchemy"));
        JButton homeButton = new JButton("Home");
        JButton aboutButton = new JButton("About Us");
        JButton utilitiesButton = new JButton("Utilities");

        homeButton.addActionListener(e -> showPage("Home"));
        aboutButton.addActionListener(e -> showPage("About"));
        utilitiesButton.addActionListener(e -> showPage("Utilities"));

        navPanel.add(homeButton);
        navPanel.add(aboutButton);
        navPanel.add(utilitiesButton);
        add(navPanel, BorderLayout.NORTH);
    }

    private void setupHomePage() {
       JPanel homePanel = new JPanel();
        homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS));  // Stack components vertically

    
        JLabel sequenceLabel = new JLabel("Enter Sequence: ");
        sequenceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center align the label

    // Create the sequence input text field
        sequenceInput = new JTextField(20);  // Text field for sequence input
        sequenceInput.setPreferredSize(new Dimension(150, 30));  // Set fixed width and height
        sequenceInput.setMaximumSize(new Dimension(150, 30));  // Prevent text field from growing too large
        sequenceInput.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center align the text field

    // Create a panel to hold the label and the text field horizontally
        JPanel sequencePanel = new JPanel();
        sequencePanel.setLayout(new BoxLayout(sequencePanel, BoxLayout.X_AXIS));  // Stack label and input field horizontally
        sequencePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sequencePanel.add(sequenceLabel);  // Add label
        sequencePanel.add(Box.createHorizontalStrut(10));  // Add horizontal spacing between label and text field
        sequencePanel.add(sequenceInput);  // Add text field

    // Create the buttons for DNA and RNA analysis
        JButton dnaButton = new JButton("Analyze DNA");
        JButton rnaButton = new JButton("Analyze RNA");

        dnaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        rnaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        dnaButton.setPreferredSize(new Dimension(400, 80));  // Set width and height
        rnaButton.setPreferredSize(new Dimension(400, 80));  // Set width and height

        dnaButton.addActionListener(e -> showDNATasks());
        rnaButton.addActionListener(e -> showRNATasks());

    // Add the sequence input panel and buttons to the home panel
        homePanel.add(sequencePanel);  // Add the sequence input panel
        homePanel.add(Box.createVerticalStrut(10));  // Add some space between the input field and the buttons
        homePanel.add(dnaButton);
        homePanel.add(rnaButton);

    // Add the image at the bottom of Home page
        ImageIcon homeImage = new ImageIcon("/Users/gmedhareddy/Downloads/medhaapp.jpeg");  
        Image scaledImage = homeImage.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);  // Resize image
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));  
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center image horizontally

        homePanel.add(Box.createVerticalStrut(20));  //Add some space before and after the image
        homePanel.add(imageLabel);  
        homePanel.add(Box.createVerticalStrut(20));  

        contentPanel.add(homePanel, "Home"); // Add home panel to the main content area
}

    // Function to show the DNA tasks page
    private void showDNATasks() {  
        JPanel dnaPanel = new JPanel();
        // Add buttons for different DNA tasks
        JButton complementButton = new JButton("Find Complement");
        JButton gcButton = new JButton("Calculate GC Content");
        JButton transcribeButton = new JButton("Transcribe to RNA");
        JButton palindromeButton = new JButton("Find Palindromes");
        JButton motifButton = new JButton("Find Motif");
        JButton countButton = new JButton("Count Nucleotides");

        // Setting up actions for each button
        complementButton.addActionListener(e -> analyzeDNASequence(new DNASequence(sequenceInput.getText())));
        gcButton.addActionListener(e -> calculateGCContent(new DNASequence(sequenceInput.getText())));
        transcribeButton.addActionListener(e -> transcribeToRNA(new DNASequence(sequenceInput.getText())));
        palindromeButton.addActionListener(e -> findDNAPalindrome(new DNASequence(sequenceInput.getText())));
        motifButton.addActionListener(e -> findDNAMotif(new DNASequence(sequenceInput.getText())));
        countButton.addActionListener(e -> countDNANucleotides(new DNASequence(sequenceInput.getText())));

        // Add buttons to the panel
        dnaPanel.add(complementButton);
        dnaPanel.add(gcButton);
        dnaPanel.add(transcribeButton);
        dnaPanel.add(palindromeButton);
        dnaPanel.add(motifButton);
        dnaPanel.add(countButton);
        contentPanel.add(dnaPanel, "DNA Tasks");  // Show the DNA tasks page
        showPage("DNA Tasks");
    }

    // Function to show the RNA tasks page
    private void showRNATasks() {
        JPanel rnaPanel = new JPanel();
        // Add buttons for different RNA tasks
        JButton gcButton = new JButton("Calculate GC Content");
        JButton transcribeButton = new JButton("Transcribe to Protein");
        JButton palindromeButton = new JButton("Find Palindromes");
        JButton motifButton = new JButton("Find Motif");
        JButton countButton = new JButton("Count Nucleotides");

        // Set up actions for each button
        gcButton.addActionListener(e -> calculateGCContent(new RNASequence(sequenceInput.getText())));
        transcribeButton.addActionListener(e -> transcribeToProtein(new RNASequence(sequenceInput.getText())));
        palindromeButton.addActionListener(e -> findRNAPalindrome(new RNASequence(sequenceInput.getText())));
        motifButton.addActionListener(e -> findRNAMotif(new RNASequence(sequenceInput.getText())));
        countButton.addActionListener(e -> countRNANucleotides(new RNASequence(sequenceInput.getText())));

        // Add buttons to the panel
        rnaPanel.add(gcButton);
        rnaPanel.add(transcribeButton);
        rnaPanel.add(palindromeButton);
        rnaPanel.add(motifButton);
        rnaPanel.add(countButton);
        contentPanel.add(rnaPanel, "RNA Tasks");  // Show the RNA tasks page
        showPage("RNA Tasks");
    }

    // Function to analyze DNA sequence
    private void analyzeDNASequence(DNASequence dnaSeq) {  // Check if the DNA sequence is valid
        if (dnaSeq.validateSequence()) {
            String complement = dnaSeq.findComplement(); //find the complement of the DNA sequence
            JOptionPane.showMessageDialog(this, "Complement: " + complement);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid DNA sequence!"); // error message if invalid
        }
    }

    // Function to calculate GC content of a sequence
    private void calculateGCContent(Sequence seq) {
        if (seq.validateSequence()) {  //check if the sequence is valid
            double gcContent = seq instanceof DNASequence ? 
                ((DNASequence) seq).calculateGCContent() : ((RNASequence) seq).calculateGCContent(); // Calculate GC content
            JOptionPane.showMessageDialog(this, "GC Content: " + gcContent + "%"); //displays GC content result
        } else {
            JOptionPane.showMessageDialog(this, "Invalid sequence!"); //error message if invalid
        }
    }

    // Function to transcribe DNA to RNA
    private void transcribeToRNA(DNASequence dnaSeq) {
        if (dnaSeq.validateSequence()) {  // Check if the DNA sequence is valid
            RNASequence rnaSeq = dnaSeq.transcribeToRNA();
            JOptionPane.showMessageDialog(this, "Transcribed RNA: " + rnaSeq.getSequence()); // Show transcribed RNA
        } else {
            JOptionPane.showMessageDialog(this, "Invalid DNA sequence!");  // Show error message if invalid
        }
    }

    // Function to find DNA palindromes
    private void findDNAPalindrome(DNASequence dnaSeq) {
    if (dnaSeq.validateSequence()) { // Check if the DNA sequence is valid
        List<String> palindromes = dnaSeq.findPalindrome();  //find palindromes in DNA sequence
        String message = palindromes.isEmpty() ? "No palindromes found!" : "Palindromes: " + String.join(", ", palindromes);
        JOptionPane.showMessageDialog(this, message); //Display the result
    } else {
        JOptionPane.showMessageDialog(this, "Invalid DNA sequence!"); //error message if invalid sequence
    }
}

    // Function to find DNA motifs
    private void findDNAMotif(DNASequence dnaSeq) {
    if (dnaSeq.validateSequence()) {  // Check if the DNA sequence is valid
        String motif = JOptionPane.showInputDialog(this, "Enter motif to search:");
        if (motif != null && !motif.isEmpty()) {  //check if the motif is valid
            List<Integer> positions = dnaSeq.findMotif(motif.toUpperCase()); // Find motif positions in DNA sequence
            String message = positions.isEmpty() ? "Motif not found!" : "Motif found at positions: " + positions;
            JOptionPane.showMessageDialog(this, message);
        } else {
            JOptionPane.showMessageDialog(this, "Motif cannot be empty!"); // error message if motif is empty
        }
    } else {
        JOptionPane.showMessageDialog(this, "Invalid DNA sequence!"); // Show error message if invalid
    }
}

    // Function to count nucleotides in DNA sequence
    private void countDNANucleotides(DNASequence dnaSeq) {
    if (dnaSeq.validateSequence()) {  // Check if the DNA sequence is valid
        Map<Character, Integer> counts = dnaSeq.countNucleotides(); // Count the nucleotides
        String message = "Nucleotide Counts:\n" +
                "A: " + counts.get('A') + "\n" +
                "T: " + counts.get('T') + "\n" +
                "C: " + counts.get('C') + "\n" +
                "G: " + counts.get('G');  // Show the counts
        JOptionPane.showMessageDialog(this, message);
    } else {
        JOptionPane.showMessageDialog(this, "Invalid DNA sequence!"); //error message if inavalid 
    }
}

    // Function to transcribe RNA sequence to Protein
    private void transcribeToProtein(RNASequence rnaSeq) {
        if (rnaSeq.validateSequence()) { // Check if the RNA sequence is valid
            ProteinSequence proteinSeq = rnaSeq.transcribeToProtein(); // Transcribe RNA to Protein
            JOptionPane.showMessageDialog(this, "Transcribed Protein: " + proteinSeq.getSequence()); // Display the transcribed protein sequence
    } else {
            JOptionPane.showMessageDialog(this, "Invalid RNA sequence!");  //Show error message if the RNA sequence is invalid
        }
    }

    // Function to find palindromes in RNA sequence
    private void findRNAPalindrome(RNASequence rnaSeq) {
    if (rnaSeq.validateSequence()) {  // Check if the RNA sequence is valid
        List<String> palindromes = rnaSeq.findPalindrome();  // Find palindromes in the RNA sequence
        String message = palindromes.isEmpty() ? "No palindromes found!" : "Palindromes: " + String.join(", ", palindromes); // If palindromes are found, display them; otherwise, show no palindromes found
        JOptionPane.showMessageDialog(this, message); //display result 
    } else {
        JOptionPane.showMessageDialog(this, "Invalid RNA sequence!"); //error message if the RNA sequence is invalid
    }
}
    // Function to find a motif subsequence in RNA sequence
    private void findRNAMotif(RNASequence rnaSeq) {
    if (rnaSeq.validateSequence()) {  // Check if the RNA sequence is valid
        //user to input the motif to search for
        String motif = JOptionPane.showInputDialog(this, "Enter motif to search:"); 
        if (motif != null && !motif.isEmpty()) { //Check if the motif is non-empty
            // Search for the motif in the RNA sequence
            List<Integer> positions = rnaSeq.findMotif(motif.toUpperCase());
            // If motif is found, display its positions, else show "not found"
            String message = positions.isEmpty() ? "Motif not found!" : "Motif found at positions: " + positions;
            JOptionPane.showMessageDialog(this, message);
        } else {
            JOptionPane.showMessageDialog(this, "Motif cannot be empty!"); //Show error if motif is empty
        }
    } else {
        JOptionPane.showMessageDialog(this, "Invalid RNA sequence!"); //Show error if RNA sequence is invalid
    }
    }
}
    // Function to count the number of occurrences of each nucleotide in the RNA sequence
    private void countRNANucleotides(RNASequence rnaSeq) {
    if (rnaSeq.validateSequence()) {
        // Count the occurrences of each nucleotide (A, U, C, G) in the RNA sequence
        Map<Character, Integer> counts = rnaSeq.countNucleotides();
        
        // Prepare a formatted string displaying the count of each nucleotide
        String message = "Nucleotide Counts:\n" +
                "A: " + counts.get('A') + "\n" +
                "U: " + counts.get('U') + "\n" +
                "C: " + counts.get('C') + "\n" +
                "G: " + counts.get('G');
        JOptionPane.showMessageDialog(this, message);
    } else {
        JOptionPane.showMessageDialog(this, "Invalid RNA sequence!"); // Show an error message if the RNA sequence is invalid
    }
}
    //About us page 
    private void setupAboutUsPage() {
        JPanel aboutPanel = new JPanel();  //Create a JPanel for About us section 
        aboutPanel.setLayout(new BoxLayout(aboutPanel, BoxLayout.Y_AXIS)); //Set layout to vertical
        aboutPanel.setBackground(Color.WHITE); //set background color to white
        // Create the title label
        JLabel title = new JLabel("GeneAlchemy: Bioinformatics Sequence Analyzer", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 26));
        title.setForeground(new Color(0, 119, 190));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Create the description text area
        JTextArea aboutText = new JTextArea(
            "Welcome to GeneAlchemy, your personal bioinformatics tool!\n\n" +
            "Dive deep into the world of genetics—whether analyzing DNA/RNA sequences, " +
            "calculating GC content, or transcribing RNA to proteins, we bring it all " +
            "within your reach. Let GeneAlchemy be your guide through the fascinating " +
            "building blocks of life."
        );
        aboutText.setFont(new Font("Georgia", Font.PLAIN, 16));
        aboutText.setLineWrap(true); // Enable line wrapping
        aboutText.setWrapStyleWord(true); // Ensure words are wrapped properly
        aboutText.setOpaque(false); // Set background to transparent
        aboutText.setEditable(false); // Make text area non-editable
        aboutText.setBackground(Color.WHITE); // Set background color to white
        aboutText.setAlignmentX(Component.CENTER_ALIGNMENT);
        aboutText.setMaximumSize(new Dimension(600, 150));
        // Create a separator to visually separate sections
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(500, 10));
        separator.setForeground(new Color(0, 119, 190));
        // Add components to the panel in a structured manner
        aboutPanel.add(Box.createVerticalStrut(20));
        aboutPanel.add(title);
        aboutPanel.add(Box.createVerticalStrut(15));
        aboutPanel.add(separator);
        aboutPanel.add(Box.createVerticalStrut(15));
        aboutPanel.add(aboutText);
        // Create the "Fun Fact!" button
        JButton funFactButton = new JButton("Fun Fact!");
        funFactButton.setFont(new Font("SansSerif", Font.BOLD, 14)); 
        funFactButton.setForeground(Color.BLACK); // Set button text color
        funFactButton.setAlignmentX(Component.CENTER_ALIGNMENT); //Align button at the center
        JLabel funFactLabel = new JLabel("Click for a fun fact about DNA!", SwingConstants.CENTER);
        funFactLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
        funFactLabel.setForeground(new Color(128, 128, 128));
        funFactLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Action listener for the "Fun Fact!" button to display a random facts
        funFactButton.addActionListener(e -> {
            String[] facts = {
                "If you stretched out all your DNA, it could reach the sun and back over 600 times!",
                "Humans share 60% of their DNA with bananas!",
                "DNA was first discovered in 1869 by Swiss chemist Friedrich Miescher.",
                "99.9% of human DNA is identical across all people."
            };
            int randomIndex = (int) (Math.random() * facts.length); // Selects a random fact
            funFactLabel.setText(facts[randomIndex]);  //Update label with the selected fun fact
        });

        aboutPanel.add(Box.createVerticalStrut(20));
        aboutPanel.add(funFactButton);
        aboutPanel.add(Box.createVerticalStrut(10));
        aboutPanel.add(funFactLabel);
        // Create the "Created by" label
        JLabel createdByLabel = new JLabel("Created by", SwingConstants.CENTER);
        createdByLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        createdByLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        //Scale the image to fit the layout
        ImageIcon aboutUsImage = new ImageIcon("/Users/nandanareddy/Downloads/aboutus.jpeg");
        Image scaledImage = aboutUsImage.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Add the "Created by" label and image to the panel
        aboutPanel.add(Box.createVerticalStrut(20));
        aboutPanel.add(createdByLabel); // Add the "Created by" label
        aboutPanel.add(imageLabel); // Add the image label
        // Add the about panel to the content panel
        contentPanel.add(aboutPanel, "About");
    }

    // Function to set up the "Utilities" page 
    private void setupUtilitiesPage() {
        JPanel utilitiesPanel = new JPanel(); // Create a JPanel for the utilities section
        utilitiesPanel.setLayout(new BoxLayout(utilitiesPanel, BoxLayout.Y_AXIS)); // Set layout to vertical
        utilitiesPanel.setBackground(Color.WHITE); // Set background color to white
        // Create the title label for the utilities section
        JLabel title = new JLabel("GeneAlchemy Utilities", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 26));  // Set title font style and size
        title.setForeground(new Color(0, 119, 190)); // Set title color
        title.setAlignmentX(Component.CENTER_ALIGNMENT); // Align title at the center
        //create the description text space with utilities for DNA and RNA tasks 
        JTextArea utilitiesText = new JTextArea(
            "Welcome to the GeneAlchemy Utilities!\n\n" +
            "Below are the functionalities available for DNA and RNA sequences:\n\n" +
            "DNA Functions:\n" +
            "1. GC Content Calculator: Calculates the GC content percentage in the DNA sequence.\n" +
            "2. Complement Finder: Finds the complementary DNA strand (A-T, T-A, C-G, G-C).\n" +
            "3. Transcribe to RNA: Converts the DNA sequence to an RNA sequence (replaces T with U).\n" +
            "4. Motif Finder: Finds all occurrences of a specific motif in the DNA sequence.\n" +
            "5. Palindrome Finder: Finds palindromes (sequences that read the same forward and backward).\n" +
            "6. Nucleotide Counter: Counts occurrences of A, T, C, G nucleotides in the DNA sequence.\n\n" +
            "RNA Functions:\n" +
            "1. GC Content Calculator: Calculates the GC content percentage in the RNA sequence.\n" +
            "2. Transcribe to Protein: Converts the RNA sequence into a protein sequence based on codons.\n" +
            "3. Motif Finder: Finds all occurrences of a specific motif in the RNA sequence.\n" +
            "4. Palindrome Finder: Finds palindromes in the RNA sequence.\n" +
            "5. Nucleotide Counter: Counts occurrences of A, U, C, G nucleotides in the RNA sequence.\n\n" +
            "Explore these tools to analyze your genetic sequences and gain insights into DNA and RNA!"
        );
        utilitiesText.setFont(new Font("Georgia", Font.PLAIN, 16)); //set font style and size
        utilitiesText.setLineWrap(true); //Enable line wrapping
        utilitiesText.setWrapStyleWord(true); 
        utilitiesText.setOpaque(false);  //set background to transparent
        utilitiesText.setEditable(false);
        utilitiesText.setBackground(Color.WHITE); //set background color to white
        utilitiesText.setAlignmentX(Component.CENTER_ALIGNMENT);
        utilitiesText.setMaximumSize(new Dimension(600, 450));
        // Create a separator to separate sections
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(500, 10)); //Set the size of the separator
        separator.setForeground(new Color(0, 119, 190)); // Set color of the separator
        // Add components to the panel in a structured manner
        utilitiesPanel.add(Box.createVerticalStrut(20));
        utilitiesPanel.add(title);
        utilitiesPanel.add(Box.createVerticalStrut(15));
        utilitiesPanel.add(separator);
        utilitiesPanel.add(Box.createVerticalStrut(15));
        JScrollPane scrollPane = new JScrollPane(utilitiesText);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(600, 450));  
        utilitiesPanel.add(scrollPane); // Add the scroll pane to the panel
        // Add the utilities panel to the content panel 
        contentPanel.add(utilitiesPanel, "Utilities");
    }


    private void showPage(String page) {
        CardLayout cl = (CardLayout) (contentPanel.getLayout()); //Get the CardLayout instance
        cl.show(contentPanel, page); //show the page 
    }
    // Main function to run the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BioAppGUI app = new BioAppGUI();
            app.setVisible(true); //Set the application window to be visible
        });
    }
