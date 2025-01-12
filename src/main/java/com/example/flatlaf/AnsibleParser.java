package com.example.flatlaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
class AnsibleParser extends JFrame {

    @Value("${app.window.title:FlatLaf Demo}")
    private String windowTitle;

    @Autowired
    SomeService someService;
    @Autowired
    AnotherService anotherService;

    @PostConstruct
    private void initializeUI() {
        setTitle(windowTitle);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel(someService.getTitle()); // Using injected service
        JLabel titleLabel1 = new JLabel(anotherService.processText("asas")); // Using injected service
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JTextArea textAreaLeft = new JTextArea();
        textAreaLeft.setLineWrap(true);
        textAreaLeft.setWrapStyleWord(true);
        textAreaLeft.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JTabbedPane tabbedPaneRight = new JTabbedPane();

        JTextArea tab1TextArea = new JTextArea();
        tab1TextArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabbedPaneRight.addTab("Tab 1", new JScrollPane(tab1TextArea));

        JTextArea tab2TextArea = new JTextArea();
        tab2TextArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabbedPaneRight.addTab("Tab 2", new JScrollPane(tab2TextArea));

        JTextArea tab3TextArea = new JTextArea();
        tab3TextArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabbedPaneRight.addTab("Tab 3", new JScrollPane(tab3TextArea));

        JTextArea tab4TextArea = new JTextArea();
        tab4TextArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabbedPaneRight.addTab("Tab 4", new JScrollPane(tab4TextArea));

        JTextField textField = new JTextField(20);
        JLabel textFieldLabel = new JLabel("Service Name:");
        JButton processButton = new JButton("PROCESS");

        processButton.addActionListener(e -> {
            String searchString = textField.getText().trim();
            if (!searchString.isEmpty()) {
                String[] lines = textAreaLeft.getText().split("\\n");
                String result = java.util.Arrays.stream(lines)
                        .dropWhile(line -> !line.equals(searchString))
                        .collect(Collectors.joining("\n"));

                tab1TextArea.setText(result);
                tab2TextArea.setText(result);
                tab3TextArea.setText(result);
                tab4TextArea.setText(result);
            }
        });

        String directoryPath = "S:\\Self-Study\\FlatLaf\\src\\main\\resources";
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(directoryPath));
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            try (Scanner scanner = new Scanner(selectedFile)) {
                StringBuilder fileContent = new StringBuilder(); 
                while (scanner.hasNextLine()) {
                    String fg = scanner.nextLine();
                    System.out.println(fg);
                    fileContent.append(fg).append("\n");
                }

                textAreaLeft.setText(fileContent.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        JScrollPane scrollPaneLeft = new JScrollPane(textAreaLeft);
        scrollPaneLeft.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPaneLeft, tabbedPaneRight);
        splitPane.setDividerLocation(550);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        JPanel textFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        textFieldPanel.add(textFieldLabel);
        textFieldPanel.add(textField);
        textFieldPanel.add(processButton);

        northPanel.add(textFieldPanel, BorderLayout.NORTH);
        northPanel.add(titleLabel, BorderLayout.CENTER);

        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(splitPane, BorderLayout.CENTER);

        add(mainPanel);
    }
}
