package mysquare.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyProducts {

    public static JPanel getPanel(){
        JPanel panel = new JPanel();
        JTextField product = new JTextField(40);
        product.setToolTipText("Ingrese un unico Producto");
        product.setBounds(15, 25, 250, 30);
        JTextField colour = new JTextField(40);
        Icon iconAdd = new ImageIcon("C:\\Inventory_Management\\src\\addButton.jpg");
        JButton addProduct = new JButton(iconAdd);
        addProduct.setBounds(280, 25, 30, 30);
        Icon iconDelete = new ImageIcon("C:\\Inventory_Management\\src\\deleteButton.jpg");
        JButton rmvProduct = new JButton(iconDelete);
        rmvProduct.setBounds(320, 25, 30, 30);
        panel.setLayout(null);
        panel.add(product);
        panel.add(addProduct);
        panel.add(rmvProduct);

        addProduct.addActionListener(e -> {
            try {
                Db.addItem("product_list", product.getText());
                product.setText("");
            } catch (Exception err) {
                JOptionPane.showConfirmDialog(IMStart.frame, "Producto ya agregado.\nERROR:"+err.getMessage(),"WARNING",JOptionPane.WARNING_MESSAGE );
            }
        });
        
        rmvProduct.addActionListener(e -> {
            try {
                Db.removeItem("product_list","pname", product.getText());
                product.setText("");
            } catch (Exception err) {
                JOptionPane.showConfirmDialog(IMStart.frame, "Producto no encontrado.\nERROR:"+err.getMessage(),"WARNING",JOptionPane.WARNING_MESSAGE );
            }
        });
        panel.setBackground(new Color(239,214,186));
        return panel;
    }
        /*if(btnCode.equals("Change Data Source")) {
            String str = JOptionPane.showInputDialog("Enter New Data Source:");
            Utility.setSource(str);
            System.out.print(str);
        }
        if(btnCode.equals("Home")) {
            IMStart.changePanel(IMStart.mp);
        }*/
}