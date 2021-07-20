import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MultipleWindowsDemo extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JTextArea jta;
    private JButton jbtShowHistogram = new JButton("Show Histogram");
    private Histogram histogram = new Histogram();
    private JFrame histogramFrame = new JFrame();

    public MultipleWindowsDemo() {
        JScrollPane scrollPane = new JScrollPane(jta = new JTextArea());
        scrollPane.setPreferredSize(new Dimension(300, 200));
        jta.setWrapStyleWord(true);
        jta.setLineWrap(true);
        add(scrollPane, BorderLayout.CENTER);
        add(jbtShowHistogram, BorderLayout.SOUTH);
        jbtShowHistogram.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int count[] = countLetters();
                histogram.showHistogram(count);
                histogramFrame.setVisible(true);
            }
        });
        histogramFrame.add(histogram);
        histogramFrame.pack();
        histogramFrame.setTitle("Histogram");
    }

    private int[] countLetters() {
        int count[] = new int[26];
        String text = jta.getText();
        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            if ((character >= 'A') && (character <= 'Z')) {
                count[character - 'A']++;
            } else if ((character >= 'a') && (character <= 'z')) {
                count[character - 'a']++;
            }
        }
        return count;
    }

    public static void main(String srgs[]) {
        MultipleWindowsDemo frame = new MultipleWindowsDemo();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("MultipleWindowsDemo");
        frame.pack();
        frame.setVisible(true);
    }
}