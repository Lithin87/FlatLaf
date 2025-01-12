package com.example.flatlaf;

import com.formdev.flatlaf.FlatLightLaf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class FlatlafApplication {
    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(FlatlafApplication.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new FlatLightLaf());
                FlatLafDemo frame = context.getBean(FlatLafDemo.class);
                frame.setVisible(true);
            } catch (Exception ex) {
                System.err.println("Failed to initialize FlatLaf");
            }
        });
    }
}

@Component
class FlatLafDemo extends JFrame {

	@Value("${app.window.title:FlatLaf Demo}")
    private String windowTitle;
	
	@Autowired
    private  SomeService someService;
	@Autowired
    private  AnotherService anotherService;


    public FlatLafDemo() {
        initializeUI();
    }
    
    private void initializeUI() {
		setTitle(windowTitle);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel(someService.getTitle()); // Using injected service
        JLabel titleLabel1 = new JLabel(anotherService.processText("asas")); // Using injected service
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainPanel);
    }
}

@Component
class SomeService {
    public String getTitle() {
        return "Enter your text:";
    }
}


@Service
class AnotherService {
    public String processText(String text) {
        return "as"
    }
}
