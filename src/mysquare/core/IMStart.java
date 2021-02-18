package mysquare.core;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class IMStart {
	
	public static JFrame frame = new JFrame();
	public static JMenuBar mb = new JMenuBar();
	public static JMenu m1,m2,m3,m4;
	public static JMenuItem m2i1, m2i2, m2i3, m3i1,m4i1;
	public static JScrollPane jScrollPane;


	IMStart(){
		m2 = new JMenu("Vistas");
		m2.setMnemonic(KeyEvent.VK_V);
		m3 = new JMenu("Operaciones");
		m3.setMnemonic(KeyEvent.VK_O);
		m4 = new JMenu("Ayuda");
		m4.setMnemonic(KeyEvent.VK_H);
		m2i1 = new JMenuItem("Productos");
		m2i2 = new JMenuItem("Ventas");
		m2i3 = new JMenuItem("Stock");
		//m2i1 = new JMenuItem("Change Data Source");
		m3i1 = new JMenuItem("Modificar Productos");
		m4i1 = new JMenuItem("Sobre el Software");
		m2.add(m2i1);
		m2.add(m2i2);
		m2.add(m2i3);
		m3.add(m3i1);
		m4.add(m4i1);
		mb.add(m2);
		mb.add(m3);
		mb.add(m4);
		m2i1.setBackground(new Color(239,214,186));
        m2i2.setBackground(new Color(239,214,186));
        m2i3.setBackground(new Color(239,214,186));
        m3i1.setBackground(new Color(239,214,186));
        m4i1.setBackground(new Color(239,214,186));
        mb.setBackground(new Color(239,176,137));
	}

	public static void main(String[] args) {
        final String dev_msg = "Developed by NicoReinaldo ";
        frame.setTitle("GO FAR");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setJMenuBar(mb);

        try {
            new IMStart();
            jScrollPane = new JScrollPane(Stock.getStockView());
            frame.getContentPane().add(BorderLayout.CENTER, jScrollPane);
            frame.setVisible(true);

            m2i1.addActionListener(e -> {
                try {
                    frame.getContentPane().removeAll();
                    jScrollPane = new JScrollPane(Production.getProductionView());
                    frame.getContentPane().add(BorderLayout.CENTER, jScrollPane);
                    frame.getContentPane().add(BorderLayout.SOUTH, Production.getProductionPanel());
                    frame.getContentPane().doLayout();
                    frame.update(frame.getGraphics());
                    frame.setVisible(true);
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(frame,"No se pueden recuperar los productos.\nERROR:"+ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                }
            });

            m2i2.addActionListener(e -> {
                try {
                    frame.getContentPane().removeAll();
                    jScrollPane = new JScrollPane(Dispatch.getDispatchView());
                    frame.getContentPane().add(BorderLayout.CENTER, jScrollPane);
                    frame.getContentPane().add(BorderLayout.SOUTH, Dispatch.getDispatchPanel());
                    frame.getContentPane().doLayout();
                    frame.update(frame.getGraphics());
                    frame.setVisible(true);
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(frame,"No se pueden recuperar los productos.\nERROR:"+ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                }
            });

            m2i3.addActionListener(e -> {
                try {
                    frame.getContentPane().removeAll();
                    jScrollPane = new JScrollPane(Stock.getStockView());
                    frame.getContentPane().add(BorderLayout.CENTER, jScrollPane);
                    frame.getContentPane().doLayout();
                    frame.update(frame.getGraphics());
                    frame.setVisible(true);
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(frame,"No se pueden recuperar Stock.\nERROR:"+ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                }
            });

            m3i1.addActionListener(e -> {
                try {
                    frame.getContentPane().removeAll();
                    jScrollPane = new JScrollPane(ModifyProducts.getPanel());
                    frame.getContentPane().add(BorderLayout.CENTER, jScrollPane);
                    frame.getContentPane().doLayout();
                    frame.update(frame.getGraphics());
                    frame.setVisible(true);
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(frame,"No se pueden recuperar Stock.\nERROR:"+ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                }
            });

            m4i1.addActionListener(e -> {
                JOptionPane.showMessageDialog(frame, dev_msg, "Sobre el Software", JOptionPane.INFORMATION_MESSAGE);
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "¡Lo siento! Algo salió mal.\nERROR:" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}