package mysquare.core;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class Stock {

    public static JTable getStockView() throws Exception{
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        model.addColumn("PRODUCTO");
        model.addColumn("CANTIDAD");
        ResultSet data = null;
        try {
            data = Db.fetchData("products");
            while(data.next()) {
                model.addRow(new Object[]{data.getString(1), data.getString(2)});
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        table.setGridColor(new Color(239,214,186));
        return table;
    }
}



