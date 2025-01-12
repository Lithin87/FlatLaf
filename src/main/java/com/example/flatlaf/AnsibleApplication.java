package com.example.flatlaf;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class AnsibleApplication {
    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(AnsibleApplication.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new FlatLightLaf());
                AnsibleParser frame = context.getBean(AnsibleParser.class);
                frame.setVisible(true);
            } catch (Exception ex) {
                System.err.println("Failed to initialize FlatLaf");
            }
        });
    }
}
