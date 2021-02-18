package mysquare.core;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class Dispatch {

    public static DefaultTableModel model;

    public static JTable getDispatchView() throws Exception{
        model = new DefaultTableModel();
        JTable table = new JTable(model);
        model.addColumn("VENDIDO EN LA FECHA");
        model.addColumn("PRODUCTOS");
        model.addColumn("STOCK");

        ResultSet data = null;
        try {
            data = Db.fetchData("sold_records");
            while(data.next()) {
                model.addRow(new Object[]{data.getString(1), data.getString(2), data.getString(3)});
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        table.setGridColor(new Color(239,214,186));
        return table;
    }

    public static JPanel getDispatchPanel(){
        JPanel panel = new JPanel();
        Utility obj = new Utility();
        JComboBox<String> cb1 = new JComboBox<String>(obj.getProductList());

        // Components Added using Flow Layout
        JLabel lab1 = new JLabel("Producto");
        panel.add(lab1);
        panel.add(cb1);


        JLabel lab4 = new JLabel("Cantidad");
        JTextField tf1 = new JTextField(5);
        panel.add(lab4);
        panel.add(tf1);

        JButton rmvBtn = new JButton("Vender");
        panel.add(rmvBtn);

        rmvBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String product = cb1.getSelectedItem().toString();
                int qty = Integer.parseInt(tf1.getText());
                try {
                    ResultSet dataNew = Db.sellProduct(product, qty);
                    model.setRowCount(0);
                    while(dataNew.next()) {
                        model.addRow(new Object[]{dataNew.getString(1), dataNew.getString(2), dataNew.getString(3)});
                    }
                } catch (Exception e) {
                    try {
                        throw new Exception("No se pudo vender el producto.");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        panel.setBackground(new Color(239,176,137));
        return panel;
    }
}
