import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ToolButton extends JButton implements ActionListener {

    ToolButton( String txt, Font font){

        this.setText(txt);
        this.setFont(font);
        this.setFocusable(false);
        this.setPreferredSize(new Dimension(80, 30));
        this.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == this){
            System.out.println("Start");
        }
    }
}
