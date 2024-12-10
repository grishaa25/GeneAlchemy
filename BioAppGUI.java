import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

// Abstract Base Class - Demonstrates Abstraction
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

// DNA Sequence Class - Demonstrates Inheritance and Polymorphism
class DNASequence extends Sequence {
    public DNASequence(String sequence) {
        super(sequence);
    }

    @Override
    public boolean validateSequence() {
        return sequence.matches("[ATCG]+");
    }

    public String findComplement() {
        return sequence.replace('A', 't').replace('T', 'a').replace('C', 'g').replace('G', 'c').toUpperCase();
    }

    public double calculateGCContent() {
        long gcCount = sequence.chars().filter(c -> c == 'G' || c == 'C').count();
        return (gcCount / (double) sequence.length()) * 100;
    }

    public RNASequence transcribeToRNA() {
        return new RNASequence(sequence.replace('T', 'U'));
    }

    public List<String> findPalindrome() {
    List<String> palindromes = new ArrayList<>();
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
            if (substring.equals(reverseComplement)) {
                palindromes.add(substring);
            }
        }
    }
    return palindromes;
}
public List<Integer> findMotif(String motif) {
    List<Integer> positions = new ArrayList<>();
    int index = sequence.indexOf(motif);
    while (index != -1) {
        positions.add(index + 1); // 1-based indexing
        index = sequence.indexOf(motif, index + 1);
    }
    return positions;
}
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

// RNA Sequence Class - Demonstrates Inheritance and Polymorphism
class RNASequence extends Sequence {
    public RNASequence(String sequence) {
        super(sequence);
    }

    @Override
    public boolean validateSequence() {
        return sequence.matches("[AUCG]+");
    }

    public double calculateGCContent() {
        long gcCount = sequence.chars().filter(c -> c == 'G' || c == 'C').count();
        return (gcCount / (double) sequence.length()) * 100;
    }

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

        StringBuilder proteinSequence = new StringBuilder();
        for (int i = 0; i < rnaSequence.length() - 2; i += 3) {
            String codon = rnaSequence.substring(i, i + 3);
            String aminoAcid = codonMap.getOrDefault(codon, "");
            if (aminoAcid.isEmpty()) break; // Stop translation if invalid codon
            proteinSequence.append(aminoAcid);
        }

        return proteinSequence.toString();
    }
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
            if (substring.equals(reverseComplement)) {
                palindromes.add(substring);
            }
        }
    }
    return palindromes;
}
public List<Integer> findMotif(String motif) {
    List<Integer> positions = new ArrayList<>();
    int index = sequence.indexOf(motif);
    while (index != -1) {
        positions.add(index + 1); // 1-based indexing
        index = sequence.indexOf(motif, index + 1);
    }
    return positions;
}
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

// Protein Sequence Class
class ProteinSequence extends Sequence {
    public ProteinSequence(String sequence) {
        super(sequence);
    }

    @Override
    public boolean validateSequence() {
        return sequence.matches("[ACDEFGHIKLMNPQRSTVWY]+");
    }
}

// Wrapper Class - Demonstrates Composition (Wrapper Class for Extra Functionalities)
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

// GUI Implementation Class with Encapsulation and User Interaction
public class BioAppGUI extends JFrame {
    private JPanel contentPanel = new JPanel(new CardLayout());
    private JTextField sequenceInput = new JTextField(20);




    public BioAppGUI() {
        setTitle("GeneAlchemy: Bioinformatics Sequence Analyzer");
        setSize(700, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

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

    // Add the image at the bottom
        ImageIcon homeImage = new ImageIcon("/Users/gmedhareddy/Downloads/medhaapp.jpeg");  // Correct image path
        Image scaledImage = homeImage.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);  // Resize image
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));  // Create a label with the image
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center the image horizontally

        homePanel.add(Box.createVerticalStrut(20));  // Add space above the image
        homePanel.add(imageLabel);  // Add the image label
        homePanel.add(Box.createVerticalStrut(20));  // Add space below the image

    // Add the home panel to the content panel
        contentPanel.add(homePanel, "Home");
}


    private void showDNATasks() {
        JPanel dnaPanel = new JPanel();
        JButton complementButton = new JButton("Find Complement");
        JButton gcButton = new JButton("Calculate GC Content");
        JButton transcribeButton = new JButton("Transcribe to RNA");
        JButton palindromeButton = new JButton("Find Palindromes");
        JButton motifButton = new JButton("Find Motif");
        JButton countButton = new JButton("Count Nucleotides");

        complementButton.addActionListener(e -> analyzeDNASequence(new DNASequence(sequenceInput.getText())));
        gcButton.addActionListener(e -> calculateGCContent(new DNASequence(sequenceInput.getText())));
        transcribeButton.addActionListener(e -> transcribeToRNA(new DNASequence(sequenceInput.getText())));
        palindromeButton.addActionListener(e -> findDNAPalindrome(new DNASequence(sequenceInput.getText())));
        motifButton.addActionListener(e -> findDNAMotif(new DNASequence(sequenceInput.getText())));
        countButton.addActionListener(e -> countDNANucleotides(new DNASequence(sequenceInput.getText())));

        dnaPanel.add(complementButton);
        dnaPanel.add(gcButton);
        dnaPanel.add(transcribeButton);
        dnaPanel.add(palindromeButton);
        dnaPanel.add(motifButton);
        dnaPanel.add(countButton);
        contentPanel.add(dnaPanel, "DNA Tasks");
        showPage("DNA Tasks");

    }

    private void showRNATasks() {
        JPanel rnaPanel = new JPanel();
        JButton gcButton = new JButton("Calculate GC Content");
        JButton transcribeButton = new JButton("Transcribe to Protein");
        JButton palindromeButton = new JButton("Find Palindromes");
        JButton motifButton = new JButton("Find Motif");
        JButton countButton = new JButton("Count Nucleotides");

        gcButton.addActionListener(e -> calculateGCContent(new RNASequence(sequenceInput.getText())));
        transcribeButton.addActionListener(e -> transcribeToProtein(new RNASequence(sequenceInput.getText())));
        palindromeButton.addActionListener(e -> findRNAPalindrome(new RNASequence(sequenceInput.getText())));
        motifButton.addActionListener(e -> findRNAMotif(new RNASequence(sequenceInput.getText())));
        countButton.addActionListener(e -> countRNANucleotides(new RNASequence(sequenceInput.getText())));

        rnaPanel.add(gcButton);
        rnaPanel.add(transcribeButton);
        rnaPanel.add(palindromeButton);
        rnaPanel.add(motifButton);
        rnaPanel.add(countButton);
        contentPanel.add(rnaPanel, "RNA Tasks");
        showPage("RNA Tasks");
    }

    private void analyzeDNASequence(DNASequence dnaSeq) {
        if (dnaSeq.validateSequence()) {
            String complement = dnaSeq.findComplement();
            JOptionPane.showMessageDialog(this, "Complement: " + complement);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid DNA sequence!");
        }
    }

    private void calculateGCContent(Sequence seq) {
        if (seq.validateSequence()) {
            double gcContent = seq instanceof DNASequence ? 
                ((DNASequence) seq).calculateGCContent() : ((RNASequence) seq).calculateGCContent();
            JOptionPane.showMessageDialog(this, "GC Content: " + gcContent + "%");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid sequence!");
        }
    }

    private void transcribeToRNA(DNASequence dnaSeq) {
        if (dnaSeq.validateSequence()) {
            RNASequence rnaSeq = dnaSeq.transcribeToRNA();
            JOptionPane.showMessageDialog(this, "Transcribed RNA: " + rnaSeq.getSequence());
        } else {
            JOptionPane.showMessageDialog(this, "Invalid DNA sequence!");
        }
    }

    private void findDNAPalindrome(DNASequence dnaSeq) {
    if (dnaSeq.validateSequence()) {
        List<String> palindromes = dnaSeq.findPalindrome();
        String message = palindromes.isEmpty() ? "No palindromes found!" : "Palindromes: " + String.join(", ", palindromes);
        JOptionPane.showMessageDialog(this, message);
    } else {
        JOptionPane.showMessageDialog(this, "Invalid DNA sequence!");
    }
}
    private void findDNAMotif(DNASequence dnaSeq) {
    if (dnaSeq.validateSequence()) {
        String motif = JOptionPane.showInputDialog(this, "Enter motif to search:");
        if (motif != null && !motif.isEmpty()) {
            List<Integer> positions = dnaSeq.findMotif(motif.toUpperCase());
            String message = positions.isEmpty() ? "Motif not found!" : "Motif found at positions: " + positions;
            JOptionPane.showMessageDialog(this, message);
        } else {
            JOptionPane.showMessageDialog(this, "Motif cannot be empty!");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Invalid DNA sequence!");
    }
}
    private void countDNANucleotides(DNASequence dnaSeq) {
    if (dnaSeq.validateSequence()) {
        Map<Character, Integer> counts = dnaSeq.countNucleotides();
        String message = "Nucleotide Counts:\n" +
                "A: " + counts.get('A') + "\n" +
                "T: " + counts.get('T') + "\n" +
                "C: " + counts.get('C') + "\n" +
                "G: " + counts.get('G');
        JOptionPane.showMessageDialog(this, message);
    } else {
        JOptionPane.showMessageDialog(this, "Invalid DNA sequence!");
    }
}


    private void transcribeToProtein(RNASequence rnaSeq) {
        if (rnaSeq.validateSequence()) {
            ProteinSequence proteinSeq = rnaSeq.transcribeToProtein();
            JOptionPane.showMessageDialog(this, "Transcribed Protein: " + proteinSeq.getSequence());
        } else {
            JOptionPane.showMessageDialog(this, "Invalid RNA sequence!");
        }
    }

    private void findRNAPalindrome(RNASequence rnaSeq) {
    if (rnaSeq.validateSequence()) {
        List<String> palindromes = rnaSeq.findPalindrome();
        String message = palindromes.isEmpty() ? "No palindromes found!" : "Palindromes: " + String.join(", ", palindromes);
        JOptionPane.showMessageDialog(this, message);
    } else {
        JOptionPane.showMessageDialog(this, "Invalid RNA sequence!");
    }
}
    private void findRNAMotif(RNASequence rnaSeq) {
    if (rnaSeq.validateSequence()) {
        String motif = JOptionPane.showInputDialog(this, "Enter motif to search:");
        if (motif != null && !motif.isEmpty()) {
            List<Integer> positions = rnaSeq.findMotif(motif.toUpperCase());
            String message = positions.isEmpty() ? "Motif not found!" : "Motif found at positions: " + positions;
            JOptionPane.showMessageDialog(this, message);
        } else {
            JOptionPane.showMessageDialog(this, "Motif cannot be empty!");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Invalid RNA sequence!");
    }
}
    private void countRNANucleotides(RNASequence rnaSeq) {
    if (rnaSeq.validateSequence()) {
        Map<Character, Integer> counts = rnaSeq.countNucleotides();
        String message = "Nucleotide Counts:\n" +
                "A: " + counts.get('A') + "\n" +
                "U: " + counts.get('U') + "\n" +
                "C: " + counts.get('C') + "\n" +
                "G: " + counts.get('G');
        JOptionPane.showMessageDialog(this, message);
    } else {
        JOptionPane.showMessageDialog(this, "Invalid RNA sequence!");
    }
}

    private void setupAboutUsPage() {
        JPanel aboutPanel = new JPanel();
        aboutPanel.setLayout(new BoxLayout(aboutPanel, BoxLayout.Y_AXIS));
        aboutPanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("GeneAlchemy: Bioinformatics Sequence Analyzer", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 26));
        title.setForeground(new Color(0, 119, 190));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea aboutText = new JTextArea(
            "Welcome to GeneAlchemy, your personal bioinformatics tool!\n\n" +
            "Dive deep into the world of genetics—whether analyzing DNA/RNA sequences, " +
            "calculating GC content, or transcribing RNA to proteins, we bring it all " +
            "within your reach. Let GeneAlchemy be your guide through the fascinating " +
            "building blocks of life."
        );
        aboutText.setFont(new Font("Georgia", Font.PLAIN, 16));
        aboutText.setLineWrap(true);
        aboutText.setWrapStyleWord(true);
        aboutText.setOpaque(false);
        aboutText.setEditable(false);
        aboutText.setBackground(Color.WHITE);
        aboutText.setAlignmentX(Component.CENTER_ALIGNMENT);
        aboutText.setMaximumSize(new Dimension(600, 150));

        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(500, 10));
        separator.setForeground(new Color(0, 119, 190));

        aboutPanel.add(Box.createVerticalStrut(20));
        aboutPanel.add(title);
        aboutPanel.add(Box.createVerticalStrut(15));
        aboutPanel.add(separator);
        aboutPanel.add(Box.createVerticalStrut(15));
        aboutPanel.add(aboutText);

        JButton funFactButton = new JButton("Fun Fact!");
        funFactButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        funFactButton.setForeground(Color.BLACK);
        funFactButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel funFactLabel = new JLabel("Click for a fun fact about DNA!", SwingConstants.CENTER);
        funFactLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
        funFactLabel.setForeground(new Color(128, 128, 128));
        funFactLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        funFactButton.addActionListener(e -> {
            String[] facts = {
                "If you stretched out all your DNA, it could reach the sun and back over 600 times!",
                "Humans share 60% of their DNA with bananas!",
                "DNA was first discovered in 1869 by Swiss chemist Friedrich Miescher.",
                "99.9% of human DNA is identical across all people."
            };
            int randomIndex = (int) (Math.random() * facts.length);
            funFactLabel.setText(facts[randomIndex]);
        });

        aboutPanel.add(Box.createVerticalStrut(20));
        aboutPanel.add(funFactButton);
        aboutPanel.add(Box.createVerticalStrut(10));
        aboutPanel.add(funFactLabel);

        JLabel createdByLabel = new JLabel("Created by", SwingConstants.CENTER);
        createdByLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        createdByLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        ImageIcon aboutUsImage = new ImageIcon("/Users/nandanareddy/Downloads/aboutus.jpeg");
        Image scaledImage = aboutUsImage.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        aboutPanel.add(Box.createVerticalStrut(20));
        aboutPanel.add(createdByLabel);
        aboutPanel.add(imageLabel);

        contentPanel.add(aboutPanel, "About");
    }

    private void setupUtilitiesPage() {
        JPanel utilitiesPanel = new JPanel();
        utilitiesPanel.setLayout(new BoxLayout(utilitiesPanel, BoxLayout.Y_AXIS));
        utilitiesPanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("GeneAlchemy Utilities", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 26));
        title.setForeground(new Color(0, 119, 190));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

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
        utilitiesText.setFont(new Font("Georgia", Font.PLAIN, 16));
        utilitiesText.setLineWrap(true);
        utilitiesText.setWrapStyleWord(true);
        utilitiesText.setOpaque(false);
        utilitiesText.setEditable(false);
        utilitiesText.setBackground(Color.WHITE);
        utilitiesText.setAlignmentX(Component.CENTER_ALIGNMENT);
        utilitiesText.setMaximumSize(new Dimension(600, 450));

        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(500, 10));
        separator.setForeground(new Color(0, 119, 190));

        utilitiesPanel.add(Box.createVerticalStrut(20));
        utilitiesPanel.add(title);
        utilitiesPanel.add(Box.createVerticalStrut(15));
        utilitiesPanel.add(separator);
        utilitiesPanel.add(Box.createVerticalStrut(15));
        JScrollPane scrollPane = new JScrollPane(utilitiesText);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(600, 450));  // Match the new size
        utilitiesPanel.add(scrollPane);

        contentPanel.add(utilitiesPanel, "Utilities");
    }


    private void showPage(String page) {
        CardLayout cl = (CardLayout) (contentPanel.getLayout());
        cl.show(contentPanel, page);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BioAppGUI app = new BioAppGUI();
            app.setVisible(true);
        });
    }
}